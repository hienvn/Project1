package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/newu")
public class CreateUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("CreateUserServlet - POST");

		String nextPage = new RequestHelper().createUser(req, resp);
		if (nextPage != null)
			req.getRequestDispatcher(nextPage).forward(req, resp);
		else {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Your username has already been registered, please pick another one!!!');");
			out.println("</script>");
			req.getRequestDispatcher("new_employee.jsp").forward(req, resp);
		}
		// resp.getWriter().write("Your username has already been registered,
		// please pick another one!!!");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("CreateUserServlet - GET");
		doPost(req, resp);
		// req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

}
