package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.services.RequestHelper;

@WebServlet("/newu")
public class CreateUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("CreateUserServlet - POST");

		String nextPage = new RequestHelper().createUser(req, resp);
		System.out.println(nextPage);
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.write(nextPage);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("CreateUserServlet - GET");
		doPost(req, resp);
		// req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

}
