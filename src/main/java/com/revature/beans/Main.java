package com.revature.beans;

import com.revature.database.ERS_DAO;

public class Main {

	public static void main(String[] args) {
		System.out.println(new ERS_DAO().getStoredPass("harry"));

	}

}
