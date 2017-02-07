package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateuser")
public class UpdateUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("updateUserServlet - POST");

		System.err.println("[LOG] Request sent to Front Controller");
		String nextPage = new RequestHelper().updateUser(req, resp);
		req.getRequestDispatcher(nextPage).forward(req, resp);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("updateUserServlet - GET");
		doPost(req, resp);
		// req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

}