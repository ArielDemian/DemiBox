<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User's notes</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/">Go home</a>
	<br />
	<a href="${pageContext.request.contextPath}/create_note">Create new note</a>
	<table border="1">
		<tr>
			<td><b>Title</b></td>
			<td><b>Creation date</b></td>
			<td><b>Last modification</b></td>
		</tr>
		<c:forEach var="note" items="${notes}">
			<tr>
				<td><c:out value="${note.title}" /></td>
				<td><fmt:formatDate value="${note.creationDate}" pattern="dd-MMM-yy HH:mm" /></td>
				<td><fmt:formatDate value="${note.modifyDate}" pattern="dd-MMM-yy HH:mm" /></td>
				<td><a href="${pageContext.request.contextPath}/modify_note?id=${note.id}">Modify note</a></td>
				<td><a href="${pageContext.request.contextPath}/delete_note?id=${note.id}">Delete note</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>