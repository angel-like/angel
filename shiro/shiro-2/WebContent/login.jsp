<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<body>
	<h1><font color="blue">登录页面</font></h1>
	<form action="shiro/login" method="POST">
		username:<input type="text" name="username">
		<br/><br/>
		password:<input type="password" name="password">
		<br/><br/>
		<input type="submit" value="submit">
	</form>
</body>
</html>