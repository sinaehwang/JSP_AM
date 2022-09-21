package com.KoreaIT.java.am.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.config.Config;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;
import com.mysql.cj.jdbc.Driver;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

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

			HttpSession session = request.getSession();

			boolean isLogined = false;
			int loginedId = -1;
			Map<String, Object> loginedMemebrRow = null;

			if (session.getAttribute("loginedMemberId") != null) {
				loginedId = (int) session.getAttribute("loginedId");
				isLogined = true;

				SecSql sql = SecSql.from("SELECT * FROM `member`");
				sql.append("WHERE id = ?;", loginedId);
				loginedMemebrRow = DBUtil.selectRow(conn, sql);

			}

			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedId", loginedId);
			request.setAttribute("loginedMemebrRow", loginedMemebrRow);
			
			int page = 1;

			if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
				page = Integer.parseInt(request.getParameter("page"));
			}

			int itemsInAPage = 10;

			int limitFrom = (page - 1) * itemsInAPage;

			SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
			sql.append("FROM article");

			int totalCount = DBUtil.selectRowIntValue(conn, sql);
			int totalPage = (int) Math.ceil((double) totalCount / itemsInAPage);
			
			sql = SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC");
			sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

			List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

			request.setAttribute("page", page);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("articleRows", articleRows);
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);

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