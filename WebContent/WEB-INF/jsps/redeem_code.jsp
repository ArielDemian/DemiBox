<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Redeem your code</title>
</head>
<body>
	<h1>Welcome to DemiBox!</h1>
	<br />
	<h4>Redeem your code and get a DemiBox account. Insert code and email address down below:</h4>
	<br />
	<form action="${pageContext.request.contextPath}/redeem" method="POST">
		Code: <input name="code" type="text" /><br /> Email address: <input name="email" type="text" /><br /> <input type="submit" value="Send" />
	</form>
</body>
</html>