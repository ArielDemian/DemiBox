<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User's events</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/">Go home</a>
	<br />
	<a href="${pageContext.request.contextPath}/create_event">Create new event</a>
	<table border="1">
		<tr>
			<td><b>Date</b></td>
			<td><b>Details</b></td>
		</tr>
		<c:forEach var="event" items="${events}">
			<tr>
				<td><fmt:formatDate value="${event.date}" pattern="dd-MMM-yyyy hh:mm" /></td>
				<td><c:out value="${event.details}" /></td>
				<td><a href="${pageContext.request.contextPath}/delete_event?id=${event.id}">Delete event</a></td>
			</tr>
		</c:forEach>
	</table>
	<br />Description: 24 hours before the event you will be notified via email.
</body>
</html>