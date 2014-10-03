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
	<a href="${pageContext.request.contextPath}/upload_file">Upload new file</a>
	<table border="1">
		<tr>
			<td><b>File name</b></td>
			<td><b>Upload time</b></td>
		</tr>
		<c:forEach var="file" items="${files}">
			<tr>
				<td><c:out value="${file.name}" /></td>
				<td><fmt:formatDate value="${file.uploadDateTime}" pattern="dd-MMM-yy HH:mm" /></td>
				<td><a href="${pageContext.request.contextPath}/download?id=${file.id}">Download file</a></td>
				<td><a href="${pageContext.request.contextPath}/delete_file?id=${file.id}">Delete file</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>