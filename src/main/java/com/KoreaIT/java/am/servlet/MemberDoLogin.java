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

@WebServlet("/member/doLogin")
public class MemberDoLogin extends HttpServlet {

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
			

			SecSql sql = SecSql.from("SELECT *");
			sql.append("FROM `member`");
			sql.append("WHERE loginId =?",loginId);
			
			Map<String, Object> memberRow = DBUtil.selectRow(conn, sql);
			
			if(memberRow.isEmpty()) {
				response.getWriter()
				.append(String.format("<script>alert('존재하지 않는 회원입니다.'); location.replace('../member/join');</script>"));
				
				return;
			}
			
			if(((String)memberRow.get("loginPw")).equals(loginPw)==false) {
				response.getWriter()
				.append(String.format("<script>alert('비밀번호 불일치'); history.back();</script>"));
				
				return;
			}
			
			
			HttpSession session = request.getSession();

			session.setAttribute("loginedMemberId", memberRow.get("loginId"));
			session.setAttribute("loginedId", memberRow.get("id"));
			
			
			response.getWriter()
					.append(String.format("<script>alert('%s님 로그인 완료'); location.replace('../home/main');</script>",memberRow.get("name")));

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