package com.KoreaIT.java.am.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.KoreaIT.java.am.config.Config;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/article/modify")
public class ArticleModifyServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		

		Connection conn = null;

		String driverName = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driverName);

		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다.");
			System.out.println("프로그램을 종료합니다.");
			return;
		}

		try {
			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassword());

			int id = Integer.parseInt(request.getParameter("id"));

			SecSql sql = SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			HttpSession session = request.getSession();
			int loginedId = (int)session.getAttribute("loginedId");
			
			String loginedMemberId = (String)session.getAttribute("loginedMemberId");
			
			
			Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
			
			if(loginedMemberId==null) {
				
				response.getWriter()
				.append(String.format("<script>alert('로그인이 필요합니다.'); location.replace('../member/login');</script>"));
				
				return;
			}

			if(loginedId != (int)articleRow.get("memberId")) {
				
				response.getWriter()
				.append(String.format("<script>alert('%d번글에 대한 권한이 없습니다.'); location.replace('../home/main');</script>",articleRow.get("id")));
				
				return;
			}

			request.setAttribute("articleRow", articleRow);
			request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}