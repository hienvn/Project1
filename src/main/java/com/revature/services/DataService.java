package com.revature.services;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.revature.beans.User;
import com.revature.database.ERS_DAO;


public class DataService {
	
	//Helping method to generate new password for user and send email with new password to their provided email address
	public static String resetPassService(User u, String curUname, String curEmail) {
		String tempPass = UUID.randomUUID().toString().substring(0, 8);
		if (u == null) {
			return "wrong";
		} else {
			if (!u.getEmail().equals(curEmail))
				return "wrong";
			else {
				u.setPassword(tempPass);
				new ERS_DAO().updateUser(u);
			}
		}
		try {
			EmailService.generateAndSendEmail(curEmail, "You password has been reset", "<p/>Your username is: "
					+ curUname + "<p/>Your temporary password is " + tempPass
					+ "<p/>Please change this temporary password after your first log in to the website. Thank You!!!");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "done";
	}
	
	//Helping method to insert new user into the db and send email to notify the new username and password
	public static String createUserService(User u) {
		if (new ERS_DAO().retrieveUserInfo(u.getUsername()) != null) {
			return null;
		}
		new ERS_DAO().createNewUser(u);
		try {
			EmailService.generateAndSendEmail(u.getEmail(), "Congratulation, your account has been created successfully",
					"<p/>Your username is: " + u.getUsername() + "<p/>Your temporary password is " + u.getPassword()
							+ "<p/>Please change this temporary password after your first log in to the website. Thank You!!!");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "m_employees.jsp";
	}
}
