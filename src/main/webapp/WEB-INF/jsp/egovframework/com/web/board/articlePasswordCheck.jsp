<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>check password</title>
</head>
<script type="text/javascript">
function passCheck() {
	if(document.frm.pass.value.length==0){
		alert("비밀번호를 입력하세요.");
		return false;
	}
	return true;
}
</script>
<body>
	<div align="center">
		<h1>비밀번호 확인</h1>
		<form action="/cop/bbs/guestArticle.do" name="frm" method="post">
		<input type="hidden" name="bbsId" value="${bbsId}" >
		<input type="hidden" name="nttId" value="${nttId}" >
		<input type="hidden" name="state" value="${state}" >
		<input type="hidden" name="menuNo" value="${menuNo}" >
			<table style="width: 80%;">
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pass" size="20"></td>				
				</tr>
			</table>
			<br>
			<input type="submit" value=" 확 인 " onclick="return passCheck()">
			<br><br>
			${msg}
		</form>
	</div>
</body>
</html>