<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录成功页面</title>
</head>
<body style="auto:center">
	<h1><font color="blue">list登录成功页面</font></h1>
	<br/>
	welcome:<shiro:principal></shiro:principal>
	<br/><br/>
	<shiro:hasRole name="admin"><!-- 有admin权限 -->
		有admin权限，下面所有页面都可以访问
	</shiro:hasRole>
	<shiro:hasRole name="123"><!-- 有admin权限 -->
		有123权限，只能访问user页面
	</shiro:hasRole>
	
	<br/><br/>
	<a href="admin.jsp">admin页面</a>
	<a href="user.jsp">user页面</a>
	<br/><br/>
	<a href="shiro/test">testShiroAnnotation</a>
	<br/>
	<a href="shiro/logout">退出登录</a>
</body>
</html>