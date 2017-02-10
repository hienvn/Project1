package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.database.ERS_DAO;
import com.revature.services.DataService;

/**
 * 
 * """""Controller"""""
 *
 */
public class RequestHelper {
	//Static list to hold any change manager make on employee reimbursement list.
	//It will not be committed until the manager click submit
	public static List<Reimbursement> reUpdateQueue = new ArrayList<Reimbursement>();
	
	//When user input valid username and password, compare them with the record stored in db
	//and redirect user to their main page based on their roles
	public String processLogin(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		HttpSession session = req.getSession();
		String uname = req.getParameter("username");
		System.out.println(uname);
		String pass = new ERS_DAO().getStoredPass(uname);
		if (req.getParameter("password").equals(pass)) {
			System.out.println(uname);
			session.setAttribute("authState", new Object());
			User u = (new ERS_DAO()).retrieveUserInfo(uname);
			session.setAttribute("curUser", u);
			session.setAttribute("curRe", (new ERS_DAO()).getReimbursementsByEmployee(u));
			if (u.getRole_id() == 1) {
				session.setAttribute("curRe", (new ERS_DAO()).getReimbursementsByEmployee(u));
				return "employeePage.jsp";
			} else if (u.getRole_id() == 2) {
				session.setAttribute("curRe", (new ERS_DAO()).getAllReimbursements());
				session.setAttribute("curE", (new ERS_DAO()).getAllEmployee());
				return "managerPage.jsp";
			}
		} else {
			session.setAttribute("authState", null);
			System.out.println("Wrong username/password");
			return "failed";
		}
		return "login.jsp";
	}
	
	//When user click on reset pass, call helping method to generate a random pass and update the user data,
	//also send an email with the new password to the user 
	public String resetPass(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		String uname = req.getParameter("curUname");
		String email = req.getParameter("curEmail");
		User u = (new ERS_DAO()).retrieveUserInfo(uname);
		String result = DataService.resetPassService(u, uname, email);
		return result;
	}
	
	//Take user input from web page and convert them to the right format before calling helping method to store them in the db
	@SuppressWarnings("unchecked")
	public String createReimbursement(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("curUser");
		double r_amount = 0;
		String r_description = null;
		byte[] r_receipt = null;
		long uid_author = u.getUser_id();
		long r_type_id = 0;
		//Use Apache Common Fileupload to convert receipt image into byte[] and store in db as a blob,
		//when read the image back out from the db, convert it into a Base64String and send the string to the jsp
		if (ServletFileUpload.isMultipartContent(req)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						r_receipt = item.get();
					} else if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString();
						System.out.println(name + " " + value);
						if (name.equals("amount"))
							r_amount = Double.parseDouble(value);
						if (name.equals("desc"))
							r_description = value;
						if (name.equals("type"))
							r_type_id = Long.parseLong(value);
					}
				}
			} catch (Exception ex) {
				System.err.println("New Reimbursement Read Input Failed");
			}
		} else {
			System.err.println("New Reimbursement Upload Failed");
		}
		Reimbursement re = new Reimbursement(r_amount/100, r_description, r_receipt, uid_author, r_type_id);
		new ERS_DAO().createNewReimbursement(re);
		refreshPage(req, resp);
		return "e_new_reimbursement.jsp";
	}
	
	//If manager change the status of reimbursements in the check box, queue the change in the list
	public String updateReimbursement(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("curUser");
		String reID = req.getParameter("re_id");
		String newStatus = req.getParameter("newstatus");
		long statusID = 0;
		if (newStatus.equals("Approve"))
			statusID = 2;
		else if (newStatus.equals("Deny"))
			statusID = 3;
		else
			statusID = 1;
		System.out.println(reID + newStatus);
		Reimbursement r = new Reimbursement(Long.parseLong(reID), u.getUser_id(), statusID);
		reUpdateQueue.add(r);
		return newStatus;
	}
	
	//WHen manager click submit change, update all changes stored in the list and clear the list
	public static String commitReimbursement(HttpServletRequest req, HttpServletResponse resp){
		for(Reimbursement r : reUpdateQueue){
			new ERS_DAO().updateReimbursement(r);
		}
		System.out.println(reUpdateQueue);
		reUpdateQueue.clear();
		new RequestHelper().refreshPage(req, resp);
		
		return "m_reimbursements.jsp";
	}
	
	//If the manager cancel the changes, clear the list
	public static String cancelReimbursementChange(HttpServletRequest req, HttpServletResponse resp){
		reUpdateQueue.clear();
		return "managerPage.jsp";
	}
	
	//Get manager input from the web page and call helping methods to create new employee with random pass,
	//also send an email confirmation with new username and password to the employee provided email address
	public String createUser(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		String username = req.getParameter("username");
		String password = UUID.randomUUID().toString().substring(0, 8);
		String firstname = req.getParameter("fname");
		String lastname = req.getParameter("lname");
		String email = req.getParameter("email");
		long role_id = Long.parseLong(req.getParameter("role"));
		User u = new User(username, password, firstname, lastname, email, role_id);
		System.out.println(u);
		String result = DataService.createUserService(u);
		System.out.println(result);
		refreshPage(req, resp);
		return result;
	}
	
	//Collect input from user when they select edit profile in their profile modal and update them to the db
	public String updateUser(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("curUser");
		String username = u.getUsername();
		String password = req.getParameter("inputPass");
		String firstname = req.getParameter("inputFname");
		String lastname = req.getParameter("inputLname");
		String email = u.getEmail();
		long role_id = u.getRole_id();
		System.out.println(username + password + firstname + lastname + email + role_id);
		u = new User(username, password, firstname, lastname, email, role_id);
		new ERS_DAO().updateUser(u);
		refreshPage(req, resp);
		if (role_id == 1)
			return "employeePage.jsp";
		else
			return "managerPage.jsp";
	}
	
	//This method's purpose is to reload and update user session to the current state if there's any change
	public void refreshPage(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("curUser");
		u = (new ERS_DAO()).retrieveUserInfo(u.getUsername());
		session.setAttribute("curUser", u);
		if (u.getRole_id() == 1) {
			session.setAttribute("curRe", (new ERS_DAO()).getReimbursementsByEmployee(u));
		} else {
			session.setAttribute("curRe", (new ERS_DAO()).getAllReimbursements());
			session.setAttribute("curE", (new ERS_DAO()).getAllEmployee());
		}
	}
}
