package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/master")
public class MasterServlet extends HttpServlet {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MasterServlet - POST");
		System.err.println("[LOG] Request sent to Front Controller");
		String nextPage = new RequestHelper().processLogin(req, resp);
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.write(nextPage);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MasterServlet - GET");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

}
