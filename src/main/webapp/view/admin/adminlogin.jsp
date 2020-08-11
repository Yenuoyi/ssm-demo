<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page language="java" import="com.ye.util.*,com.ye.domain.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Welcome to BBS, please call me Mr. Ye</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/image/favicon.jpg" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>	
<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<!-- Start Sign In Form -->
					<form id="myForm" action="${pageContext.request.contextPath}/AdminLogin" method="post" class="fh5co-form animate-box" data-animate-effect="fadeIn">
						<h2 align="center">管理员后台</h2>
						<div class="form-group">
						<div calss="form-control">
							<input placeholder="Email" id="email" name="email" class="form-control" >
							</div>
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" >
						</div>
							<p align="center"><a target="_blank" href="${pageContext.request.contextPath}/LostPassword">忘记密码?</a></p>
						<div class="form-group">
							<p align="center"><input type="submit" value="Sign In" class="btn btn-primary"></p>
						</div>
					</form>
					<!-- END Sign In Form -->

				</div>
			</div>
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