package com.revature.database;

import java.util.List;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public interface DAO {

	User retrieveUserInfo(String uname);

	User retrieveUserInfo(int uid);

	List<User> getAllEmployee();

	List<Reimbursement> getAllReimbursements();

	List<Reimbursement> getReimbursementsByEmployee(User u);

	String getStoredPass(String uname);

	void insertNewEmployee(User u);

	void updateUser(User u);

	void createNewReimbursement(Reimbursement r);

	void updateReimbursement(Reimbursement r);

	void createNewUser(User u);

	String retrieveEmailInfo(long reID);

	void deleteUser(String uname);

	long getUserID(String uname);

	void deleteReimbursement(String uname);

}
