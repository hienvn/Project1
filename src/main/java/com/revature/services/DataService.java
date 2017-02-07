package com.revature.services;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.revature.beans.User;
import com.revature.database.ERS_DAO;

/*
 * Services are good for transactions
 */
public class DataService {
	private final static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private final static String username = "PROJECT_1";
	private final static String password = "p4ssw0rd";

	public static String getStoredPassService(String uname) {
		return new ERS_DAO().getStoredPass(uname);
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "done";
	}

}
