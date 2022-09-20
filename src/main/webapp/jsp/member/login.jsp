
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

	<h1> 로그인</h1>
	<script>
	
		var LoginForm__submitDone = false;
		
		function LoginForm__submit(form) {
			
			if(LoginForm__submitDone){
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
	        
	        if ( form.loginPwConfirm.value != form.loginPw.value ) {
	            alert( '비밀번호가 일치하지않습니다.' );
	            form.loginPw.focus();
	            return;
	          }
	        
	        form.submit();
	        LoginForm__submitDone = true;
		}
		</script>
	
	<form action="doLogin" method="post" onsubmit="LoginForm__submit(this); return false;">
	
		<colgroup>
			<col width="50" />
			<col width="200" />
		</colgroup>
		
		<div>아이디 : <input type="text" name="loginId" placeholder="아이디를 입력하세요"></div>
		<div>비밀번호 : <input type="password" name="loginPw"  placeholder="비밀번호를 입력하세요"></div>
		
		<div>
		<button type="submit">로그인</button>
		<button type="button"><a href="../home/main">취소</a></button>
		</div>
	</form>
		
</body>
</html>