<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>密码管理|BBS</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
</head>
<body>
<!-- 导入头 -->
<%@ include file="header.jsp" %>
<script>
function CheckAll(id){
    //密码验证
    var regpassword = /\w{6,20}/;
    if(!regpassword.test($("#new_password").val())){
    	alert("密码长度在6-20位，且仅为数字、字母、下划线")
    }else{
    	//密码二次验证
    	var pass1 = document.getElementById("new_password");
        var pass2 = document.getElementById("sure_password");
        if (pass1.value != pass2.value){
        	alert("两次输入的密码不匹配");
        }else{
        	password(id);
        }
    }
}
//表单提交
function password(){
	var old_password = $("#old_password").val();
	var new_password = $("#new_password").val();
	var sure_password = $("#sure_password").val();
	var password = {"password":sure_password,"code":old_password};
	var pass = JSON.stringify(password);
        $.ajax({  
            type:"post", 
            headers: {
            	"content-Type":"application/json",
            	 },
            //将token存储发送请求头
            //beforeSend: function(request) {
            //request.setRequestHeader("admin", user);
            //},
            url:"${pageContext.request.contextPath}/UpdateUser_password",  
            contentType:"application/json",  
            data:pass,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("更改成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==3){
                		 alert("原密码错误！");
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
<!-- 密码修改表单 -->
<div class="panel panel-primary">
  <div class="panel-heading">用户密码修改</div>
  <div class="panel-body">
   <form id="myForm">
<table class="table table-striped">
<tr>
<td align="center">原密码: <input type="text" id="old_password" name="old_password" maxlength="20"></td>
</tr>
<tr>
<td align="center">新密码:<input type="text" id="new_password" name="new_password" maxlength="20"/></td>
</tr>
<tr>
<td align="center">确认新密码:<input type="text" id="sure_password"  name="sure_password" maxlength="20"/></td>
</tr>
<tr>
<td align="center">
<input type= "button" value="提交更改信息" onclick="CheckAll(${userbean.getId() })">
</td>
</tr>
</table>
</form>
  </div>
</div>
</body>
</html>