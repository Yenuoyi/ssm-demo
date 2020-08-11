<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>管理员后台|BBS</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

</head>
<body>
<!-- 导入头 -->
<%@ include file="adminheader.jsp" %>

<div class="jumbotron">
  <h1>Hello, world!</h1>
  <p>这是简单面板，纯属为了美观，没啥的。</p>
  <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
</div>
<%
Object mess =  request.getAttribute("message");
 %>
 <% if (mess != null) { %>
    <script type="text/javascript">
        alert("<%=mess%>");
</script>
<% } %>
</body>
</html>