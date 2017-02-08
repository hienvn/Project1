package com.revature.web;

import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import com.revature.services.EmailService;

/**
 * 
 * """""Controller"""""
 *
 */
public class RequestHelper {

	public String processLogin(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		HttpSession session = req.getSession();
		String uname = req.getParameter("username");
		System.out.println(uname);
		String pass = DataService.getStoredPassService(uname);
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

	public String resetPass(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		String uname = req.getParameter("curUname");
		String email = req.getParameter("curEmail");
		User u = (new ERS_DAO()).retrieveUserInfo(uname);
		String result = DataService.resetPassService(u, uname, email);
		return result;
	}

	@SuppressWarnings("unchecked")
	public String createReimbursement(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("curUser");
		long r_amount = 0;
		String r_description = null;
		byte[] r_receipt = null;
		long uid_author = u.getUser_id();
		long r_type_id = 0;
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
							r_amount = Long.parseLong(value);
						if (name.equals("desc"))
							r_description = value;
						if (name.equals("type"))
							r_type_id = Long.parseLong(value);
					}
				}
				// File uploaded successfully
				req.setAttribute("message", "File Uploaded Successfully");
			} catch (Exception ex) {
				req.setAttribute("message", "File Upload Failed due to " + ex);
			}
		} else {
			req.setAttribute("message", "Sorry this Servlet only handles file upload request");
		}
		Reimbursement re = new Reimbursement(r_amount, r_description, r_receipt, uid_author, r_type_id);
		new ERS_DAO().createNewReimbursement(re);
		refreshPage(req, resp);
		return "e_new_reimbursement.jsp";
	}

	@SuppressWarnings("rawtypes")
	public String updateReimbursement(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("curUser");
		Enumeration params = req.getParameterNames();
		String paramName = null;
		while (params.hasMoreElements())
			paramName = (String) params.nextElement();
		String newStatus = req.getParameter(paramName);
		long statusID = 0;
		if (newStatus.equals("Approve"))
			statusID = 2;
		else if (newStatus.equals("Deny"))
			statusID = 3;
		else
			statusID = 1;
		Reimbursement r = new Reimbursement(Long.parseLong(paramName), u.getUser_id(), statusID);
		new ERS_DAO().updateReimbursement(r);
		refreshPage(req, resp);
		String email = new ERS_DAO().retrieveEmailInfo(Long.parseLong(paramName));
		System.out.println(email);
		try {
			EmailService.generateAndSendEmail(email, "Update status for one of your reimbursement requests",
					"Your reimbursement ID = " + paramName + "<p/>New status is: " + newStatus);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "m_reimbursements.jsp";
	}

	public String createUser(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		String username = req.getParameter("username");
		String password = UUID.randomUUID().toString().substring(0, 8);
		String firstname = req.getParameter("fname");
		String lastname = req.getParameter("lname");
		String email = req.getParameter("email");
		long role_id = Long.parseLong(req.getParameter("role"));
		User u = new User(username, password, firstname, lastname, email, role_id);
		if (new ERS_DAO().retrieveUserInfo(username) != null) {
			return null;
		}
		new ERS_DAO().createNewUser(u);

		refreshPage(req, resp);
		try {
			EmailService.generateAndSendEmail(email, "Congratulation, your account has been created successfully",
					"<p/>Your username is: " + username + "<p/>Your temporary password is " + password
							+ "<p/>Please change this temporary password after your first log in to the website. Thank You!!!");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "m_employees.jsp";
	}

	public String updateUser(HttpServletRequest req, HttpServletResponse resp) {
		System.err.println("[LOG] Processing request with helper : " + req.getRequestURI());
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("curUser");
		String username = u.getUsername();
		String password = req.getParameter("inputPass");
		String firstname = req.getParameter("inputFname");
		String lastname = req.getParameter("inputLname");
		String email = req.getParameter("inputEmail");
		long role_id = u.getRole_id();
		System.out.println(username + password + firstname + lastname + email + role_id);
		u = new User(username, password, firstname, lastname, email, role_id);
		new ERS_DAO().updateUser(u);
		;
		refreshPage(req, resp);
		if (role_id == 1)
			return "employeePage.jsp";
		else
			return "managerPage.jsp";
	}

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
