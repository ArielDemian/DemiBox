<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modify note</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/">Go home.</a>
	<br />
	<a href="${pageContext.request.contextPath}/view_notes">Go back.</a>
	<br />
	<sf:form action="${pageContext.request.contextPath}/note_modified" commandName="note" method="POST">
		<input name="id" type="hidden" value="${note.id}" />
		<table>
			<tr>
				<td>Title:</td>
				<td><sf:input name="title" path="title" type="text" value="${note.title}" /></td>
				<td><sf:errors path="title" /></td>
			</tr>
			<tr>
				<td>Creation date:</td>
				<td><input name="creation_date_time" type="text" readonly="readonly" value="<fmt:formatDate value="${note.creationDate}" pattern="dd-MMM-yy HH:mm" />" /></td>
			</tr>
			<tr>
				<td>Last change:</td>
				<td><input name="last_modification_date_time" type="text" readonly="readonly" value="<fmt:formatDate value="${note.modifyDate}" pattern="dd-MMM-yy HH:mm" />" /></td>
			</tr>
			<tr>
				<td>Text:</td>
				<td><sf:textarea name="text" path="text" rows="45" cols="100" value="${note.text}" maxlength="16777214" /></td>
				<td><sf:errors path="text" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Save" /></td>
			</tr>
		</table>
	</sf:form>
</body>
</html>