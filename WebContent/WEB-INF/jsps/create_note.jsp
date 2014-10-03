<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create note</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/">Go home.</a>
	<br />
	<a href="${pageContext.request.contextPath}/view_notes">Go back.</a>
	<br />
	<sf:form action="${pageContext.request.contextPath}/note_created" method="POST" accept-charset="UTF-8" commandName="note">
		<table>
			<tr>
				<td>Title:</td>
				<td><sf:input name="title" path="title" type="text" /></td>
				<td><sf:errors path="title" /></td>
			</tr>
			<tr>
				<td>Text:</td>
				<td><sf:textarea name="text" path="text" rows="45" cols="100" maxlength="16777214" /></td>
				<td><sf:errors path="text" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Save" /></td>
			</tr>
		</table>
	</sf:form>
</body>
</html>