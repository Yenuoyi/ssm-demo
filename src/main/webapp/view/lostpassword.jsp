<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page language="java" import="com.ye.util.*,com.ye.domain.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<head>
<meta charset="utf-8">
<title>忘记密码，please call me Mr. Ye|BBS</title>
<script type="text/javascript">
//表单提交
function password_json(){
	var mail = $("#email").val();
	var email_password = {"email":mail};
	var user = JSON.stringify(email_password);
        $.ajax({  
            type:"post", 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/SendPassword",  
            contentType:"application/json",  
            data:user,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("密码已发至你的邮箱，记得好好保管哦!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("密码发送失败，请确认邮箱再次尝试！");
                	 }else{
                		 alert("系统异常，请联系管理员！");
                	 }
                 }
            },
            error:function(result){
            	alert("更改异常，请联系管理员！")
            }
        });  
    } 
</script>
</head>
<body>
<div class="jumbotron">
  <h1>忘记密码并不可怕!</h1>
  <form>
  <p>
     登录邮箱：<input type="text" id="email" name="email">
  </p>
  <p><input type="button" onclick="password_json()" value="发送密码至我的邮箱"></p>
  </form>
</div>
</body>
</html>