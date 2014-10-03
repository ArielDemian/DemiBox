<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DemiBox</title>
</head>
<body>
	<h1>Welcome to DemiBox!</h1>
	<br />
	<h3>Here you can store your stuff and access it whenever you want and like!</h3>
	<br />
	<ul>
		<li><a href="${pageContext.request.contextPath}/create_note">Insert new note</a></li>
		<li><a href="${pageContext.request.contextPath}/view_notes">View notes</a></li>
	</ul>
	<hr />
	<ul>
		<li><a href="${pageContext.request.contextPath}/upload_file">Upload file</a></li>
		<li><a href="${pageContext.request.contextPath}/view_files">View files</a></li>
	</ul>
	<hr />
	<ul>
		<li><a href="${pageContext.request.contextPath}/create_event">Create new event</a></li>
		<li><a href="${pageContext.request.contextPath}/view_events">View events</a></li>
	</ul>
	<hr />
	<ul>
		<li><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>
		<li><a href="${pageContext.request.contextPath}/manage_account">Manage account</a></li>
	</ul>
</body>
</html>