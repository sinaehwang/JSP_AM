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

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		HttpSession session =request.getSession();
		
		if(session.getAttribute("loginedMemberId") ==null) {
			
			response.getWriter()
			.append(String.format("<script>alert('로그인후 이용해주세요'); location.replace('../member/login');</script>"));
			
			return;
	
			
		}
		// DB 연결
		
		Connection conn = null;

		String driverName = Config.getDBDriverName();

		try {
			Class.forName(driverName);

		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다.");
			System.out.println("프로그램을 종료합니다.");
			return;
		}

		try {
			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassword());

			String title = request.getParameter("title");
			String body = request.getParameter("body");
			
			
			
			int loginedId = (int) session.getAttribute("loginedId");

			SecSql sql = SecSql.from("INSERT INTO article SET");
			sql.append("regDate = NOW(),");
			sql.append("memberId =?,", loginedId);
			sql.append("title =?,", title);
			sql.append("`body` =?", body);
			

			int id = DBUtil.insert(conn, sql);

			response.getWriter()
					.append(String.format("<script>alert('%d번 글이 생성되었습니다.'); location.replace('list');</script>", id));
			// request.getRequestDispatcher("/jsp/article/write.jsp").forward(request,
			// response);

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