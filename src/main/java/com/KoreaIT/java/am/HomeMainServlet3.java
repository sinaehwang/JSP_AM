package com.KoreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home/printDan")
public class HomeMainServlet3 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String inputedDan = request.getParameter("dan");
		
		if(inputedDan==null) {
			inputedDan="1";
		}
		
		//int dan = 9;
		int dan = Integer.parseInt(inputedDan);
		
		String inputedLimit = request.getParameter("limit");
		
		if(inputedLimit==null) {
			inputedLimit="1";
		}
		
		int limit = Integer.parseInt(inputedLimit);
		
		String inputedcolor = request.getParameter("color");
		
		if(inputedcolor==null) {
			inputedcolor="black";
		}
		

		response.getWriter().append(String.format("<div style=\"color:%s;\">%d단</div>",inputedcolor, dan));

		for (int i = 1; i <= limit; i++) {
			response.getWriter().append(String.format("<div style=\"color:%s;\">%d*%d = %d</div>",inputedcolor,dan, i, dan * i));
		}
	}

}
