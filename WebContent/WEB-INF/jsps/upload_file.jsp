<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload file</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/">Go home.</a>
	<br />
	<a href="${pageContext.request.contextPath}/view_files">Go back.</a>
	<br />
	<form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload">
		File to upload: <input type="file" name="file"><br /> <input type="submit" value="Upload">
	</form>
	<br />
	<p>Disclaimer: You take full responsibility of the contents of the files that you personally upload. If, for any reason, the contents of the files result to be illegal or pose a threat to the stability of the system, they will be removed without notice.</p>
</body>
</html>