<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modify note</title>
<script src="${pageContext.request.contextPath}/static/jQuery/jquery.js"></script>
<script type="text/javascript">
	$(function() {
		$('#form').submit(function() {
			var password = $('#new_password').val();
			var confirmPassword = $('#confirm_password').val();
			if (password != confirmPassword) {
				alert("Passwords do not match!")
				return false;
			} else {
				return true;
			}
		});
	});
</script>
</head>
<body>
	<a href="${pageContext.request.contextPath}/">Go home.</a>
	<br />
	<form id="form" action="${pageContext.request.contextPath}/change_password" method="POST">
		<table>
			<tr>
				<td>Old password:</td>
				<td><input name="old_password" type="password" /></td>
			</tr>
			<tr>
				<td>New password:</td>
				<td><input name="new_password" id="new_password" type="password" /></td>
			</tr>
			<tr>
				<td>Confirm password:</td>
				<td><input name="confirm_password" id="confirm_password" type="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Save" /></td>
			</tr>
		</table>
	</form>
	<br />
	<a href="${pageContext.request.contextPath}/delete_account" onClick="return confirm('Are you sure you want to delete your account?');" target="_blank">Delete account.</a>
</body>
</html>