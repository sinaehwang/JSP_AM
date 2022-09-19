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

@WebServlet("/member/doJoin")
public class MemberDoJoin extends HttpServlet {

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

			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			String name = request.getParameter("name");

			SecSql sql = SecSql.from("SELECT COUNT(*) `member`");
			sql.append("WHERE loginId=?",loginId);
			
			int joinableLoginId = DBUtil.selectRowIntValue(conn, sql);
			
			if(joinableLoginId>0) {
				response.getWriter()
				.append(String.format("<script>alert('%s는 이미 사용중인 아이디입니다.');</script>",joinableLoginId));
			}
					
			sql = SecSql.from("INSERT INTO `member` SET");
			sql.append("regDate = NOW(),");
			sql.append("loginId =?,", loginId);
			sql.append("loginPw =?,", loginPw);
			sql.append("name =?", name);

			int id = DBUtil.insert(conn, sql);

			response.getWriter()
					.append(String.format("<script>alert('%s님이 가입했습니다.'); location.replace('../home/main');</script>", name));

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