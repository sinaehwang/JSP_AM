
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>

	<h1> 게시물 작성 페이지</h1>

	<form action="doWrite" method="post">
	
		<colgroup>
			<col width="50" />
			<col width="200" />
		</colgroup>
		
		<div>제목 : <input type="text" name="title" placeholder="제목을 입력하세요"></div>
		<div>내용 : <textarea type="text" name="body"  placeholder="내용을 입력하세요"></textarea></div>
		
		<button type="submit">글쓰기</button>
	</form>
		<div><a href="list" >리스트로 돌아가기</a> </div>
</body>
</html>