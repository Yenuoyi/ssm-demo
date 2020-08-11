<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page language="java" import="com.ye.util.*,com.ye.domain.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Welcome to BBS, please call me Mr. Ye</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/image/favicon.jpg" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<script type="text/javascript">
function login(){
	var content = {'email':$("#email").val(),'password':$("#password").val()};
	var contents = JSON.stringify(content);
	$.ajax({
		type:"post", 
	    headers: {
	    "content-Type":"application/json",
	    },
	    url:"${pageContext.request.contextPath}/Welcome",  
	    contentType:"application/json", 
	    data:contents,
	    success:function(result) {
	    	if (result==1) {//根据返回值进行跳转
	   		 window.location.href ="http://119.23.51.148/Vertx/Welcome";
	        }else{
	       	 alert("帐号或密码错误，请重新输入！")
	        }
	    }
	})
}
</script>
<body>
<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<!-- Start Sign In Form -->
					<form id="myForm" class="fh5co-form animate-box" data-animate-effect="fadeIn">
						<h2 align="center">Mr.Ye BBS</h2>
						<div class="form-group">
						<div calss="form-control">
							<input placeholder="Email" id="email" name="email" class="form-control" >
							</div>
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" >
						</div>
							<p align="center">
							<a target="_blank" href="${pageContext.request.contextPath}/FaceLogin">人脸登录</a>|
							<a target="_blank" href="${pageContext.request.contextPath}/LostPassword">忘记密码</a>| <a target ="_blank" href="${pageContext.request.contextPath}/Register/View">注册</a></p>
						<div class="form-group">
							<p align="center"><input type="button" value="Sign In" onclick="login()" class="btn btn-primary"></p>
						</div>
					</form>
				</div>
			</div>
</div>
<!-- 密码错误提示脚本 -->
<%
Object mess =  request.getAttribute("message");
 %>
 <% if (mess != null) { %>
    <script type="text/javascript">
        alert("<%=mess%>");
</script>
<% } %>

<!-- token过期重新登录脚本 -->
<%
Object expired =  request.getAttribute("expired");
 %>
 <% if (expired != null) { %>
    <script type="text/javascript">
        alert("<%=expired%>");
</script>
<% } %>
<!-- 注册邮件已发送脚本 -->
<%
Object register =  request.getAttribute("register");
 %>
 <% if (register != null) { %>
    <script type="text/javascript">
        alert("<%=register%>");
</script>
<% } %>
</body>
</html>