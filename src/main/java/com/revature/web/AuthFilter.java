package com.revature.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// Auth code
		System.out.println("Authenticating");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		boolean logged_in = false;

		if (session != null) {
			Object authState = session.getAttribute("authState");
			if (authState != null) {
				request.setAttribute("user", authState);
				logged_in = true;
			}
		}

		if (logged_in) {
			// Continue to where you were going
			chain.doFilter(req, resp);
		} else {
			response.sendRedirect("master");
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
