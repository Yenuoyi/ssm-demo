<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page language="java" import="com.ye.util.*,com.ye.domain.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>错误页面,重新登录|BBS</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
</head>
<body>
<div class="jumbotron">
  <h1>Hello, BBS!</h1>
  <p>Sorry, your login has timed out, please re-login Thank you!</p>
  <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/Hello" role="button">Login</a></p>
</div>
</body>
</html>