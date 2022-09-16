<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>

	<h1> 게시물 작성 페이지</h1>

	<table border="2" bordercolor="green">
	
		<colgroup>
			<col width="50" />
			<col width="200" />
		</colgroup>
		<tr>
			<th>제목</th>
			<th>내용</th>
		</tr>
		
		<tr>
			<td><input type="text" /></td>
			<td><input type="text" /></td>
		</tr>

	</table>
		<div><a href="list" >리스트로 돌아가기</a> </div>
</body>
</html>