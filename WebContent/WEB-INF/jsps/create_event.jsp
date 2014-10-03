<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jQuery/jquery.datetimepicker.css" />
<script src="${pageContext.request.contextPath}/static/jQuery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/jQuery/jquery.datetimepicker.js"></script>
<script type="text/javascript">
	$(function() {
		jQuery('#datetimepicker').datetimepicker();
	});
</script>
<title>Create note</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/view_notes">Go back.</a>
	<br />
	<a href="${pageContext.request.contextPath}/">Go home.</a>
	<br />
	<form action="${pageContext.request.contextPath}/event_created" method="POST">
		Date and time: <input id="datetimepicker" name="date_time" type="text"></input><br />Details:
		<textarea name="details" rows="5" cols="30" maxlength="16777214"></textarea>
		<br /> <input type="submit" value="Save" /><br />Description: 24 hours before the event you will be notified via email.
	</form>
</body>
</html>