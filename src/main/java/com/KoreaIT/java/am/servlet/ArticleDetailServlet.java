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

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
					conn = DriverManager.getConnection(Config.getDBUrl(),Config.getDBUser(),Config.getDBPassword());

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
					
					int id = Integer.parseInt(request.getParameter("id"));

					SecSql sql = SecSql.from("SELECT *");
					sql.append("FROM article");
					sql.append("WHERE id =?",id);


					Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);

					request.setAttribute("articleRow", articleRow);
					request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);

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