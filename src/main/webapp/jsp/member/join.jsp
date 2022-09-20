
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>

	<h1> 회원가입</h1>
	<script>
	
		var JoinForm__submitDone = false;
		
		function JoinForm__submit(form) {
			
			if(JoinForm__submitDone){
				alert( '가입진행중입니다.' );
				return;
			}
			form.loginId.value = form.loginId.value.trim();
	        if ( form.loginId.value.length== 0 ) {
	            alert( '아이디를 입력해주세요' );
	            form.loginId.focus();
	            return;
	          }
	        
			form.loginPw.value = form.loginPw.value.trim();
	        if ( form.loginPw.value.length== 0 ) {
	            alert( '비밀번호를 입력해주세요' );
	            form.loginPw.focus();
	            return;
	          }
	        
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
	        if ( form.loginPwConfirm.value.length== 0 ) {
	            alert( '비밀번호확인을 입력해주세요' );
	            form.loginPwConfirm.focus();
	            return;
	          }
	        
	        if ( form.loginPwConfirm.value != form.loginPw.value ) {
	            alert( '비밀번호가 일치하지않습니다.' );
	            form.loginPw.focus();
	            return;
	          }
	        
	        
			form.name.value = form.name.value.trim();
	        if ( form.name.value.length== 0 ) {
	            alert( '이름을 입력해주세요' );
	            form.name.focus();
	            return;
	          }
	        form.submit();
	        JoinForm__submitDone = true;
		}
		</script>
	
	<form action="doJoin" method="post" onsubmit="JoinForm__submit(this); return false;">
	
		<colgroup>
			<col width="50" />
			<col width="200" />
		</colgroup>
		
		<div>아이디 : <input type="text" name="loginId" placeholder="아이디를 입력하세요"></div>
		<div>비밀번호 : <input type="password" name="loginPw"  placeholder="비밀번호를 입력하세요"></div>
		<div>비밀번호확인 : <input type="password" name="loginPwConfirm"  placeholder="비밀번호를 확인해주세요"></div>
		<div>이름 : <input type="text" name="name"  placeholder="이름을 입력하세요"></div>
		<div>
		<button type="submit">가입하기</button>
		<button type="button"><a href="../home/main">취소</a></button>
		</div>
	</form>
		
</body>
</html>