package com.revature.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.database.DAO;
import com.revature.database.ERS_DAO;

public class testGeneral {

	static User testUser1;
	static User testUser11;
	static User testUser2;
	static Reimbursement re1;
	static Reimbursement re11;
	static Reimbursement re2;
	static DAO testDao = new ERS_DAO();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testUser1 = new User(3000, "testName1", "testPass1", "Hien1", "Testing1", "kakaka1@yahoo.com", 1, "Employee");
		testUser11 = new User(3000, "testName1", "testPass11", "Hien11", "Testing11", "kakaka1@yahoo.com", 1, "Employee");
		testUser2 = new User(3001, "testName2", "testPass2", "Hien2", "Testing2", "kakaka2@yahoo.com", 2, "Manager");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		testDao.deleteReimbursement("testName1");
		testDao.deleteReimbursement("testName2");
		testDao.deleteUser("testName1");
		testDao.deleteUser("testName2");
		testUser1 = null;
		testUser2 = null;
		re1 = null;
		re11 = null;
		
	}

	@Before
	public void setUp() throws Exception {
		
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testCreateEmployee() {
		
		testDao.insertNewEmployee(testUser1);
		assertNotNull(testDao.retrieveUserInfo("testName1"));
		//System.out.println(testDao.getStoredPass("testName1"));
		assertEquals("testPass1", testDao.getStoredPass("testName1"));
		testDao.deleteUser("testName1");
	}
	@Test
	public void testCreateUser() {
		testDao.createNewUser(testUser2);
		assertNotNull(testDao.retrieveUserInfo("testName2"));
		testDao.deleteUser("testName2");
	}
	
	@Test
	public void testUpdateUser() {
		testDao.createNewUser(testUser1);
		testDao.updateUser(testUser11);
		User updatedUser = testDao.retrieveUserInfo("testName1");
		assertEquals("testPass11", updatedUser.getPassword());
		testDao.deleteUser("testName1");
	}
	
	@Test
	public void testCreateReimbursement() {
		testDao.createNewUser(testUser11);
		re1 = new Reimbursement(400, 135, "testingReason", null, new Timestamp(System.currentTimeMillis() / 1000 * 1000),
				new Timestamp(System.currentTimeMillis() / 1000 * 1000), testDao.getUserID("testName1"), 0, 2, 1, "Housing", "Pending");
		testDao.createNewReimbursement(re1);
		assertNotNull(testDao.getReimbursementsByEmployee(testUser11));
		testDao.deleteReimbursement("testName1");
		testDao.deleteUser("testName1");
	}
	
	@Test
	public void testUpdateReimbursement() {
		
		testDao.createNewUser(testUser11);
		testDao.createNewUser(testUser2);
		testUser11.setUser_id(testDao.getUserID("testName1"));
		re1 = new Reimbursement(400, 135, "testingReason", null, new Timestamp(System.currentTimeMillis() / 1000 * 1000),
				new Timestamp(System.currentTimeMillis() / 1000 * 1000), testDao.getUserID("testName1"), 0, 2, 1, "Housing", "Pending");
		testDao.createNewReimbursement(re1);
		Reimbursement re = testDao.getReimbursementsByEmployee(testUser11).get(0);
		re11 = new Reimbursement(re.getR_id(), 135, "testingReason", null, new Timestamp(System.currentTimeMillis() / 1000 * 1000),
				new Timestamp(System.currentTimeMillis() / 1000 * 1000), 3000, testDao.getUserID("testName2"), 2, 3, "Housing", "Denied");
		testDao.updateReimbursement(re11);
		
		Reimbursement updatedRe = testDao.getReimbursementsByEmployee(testUser11).get(0);
		assertEquals("Denied", updatedRe.getR_status());
		testDao.deleteReimbursement("testName1");
		testDao.deleteUser("testName1");
		testDao.deleteUser("testName2");
	}
	
	

}
