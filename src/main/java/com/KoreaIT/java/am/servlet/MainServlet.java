package com.KoreaIT.java.am.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/home/main")
public class MainServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		boolean isLogined = false;
		
		int loginedId = -1;
		
		
		
		if(session.getAttribute("loginedMemberId")!=null) {
			loginedId = (int)session.getAttribute("loginedId");
			isLogined =true;;
		}
		
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedId", loginedId);
		
		
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
	}
	
	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	
	
}
