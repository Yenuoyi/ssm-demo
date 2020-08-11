<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>BBS进阶之路|BBS</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
function register(){
	var name = $("#name").val();
	var email = $("#email").val();
	var iphone = $("#iphone").val();
	var professional = $("#professional").val;
	var password = $("#password").val();
	var code = $("#code").val();
	content = {"name":name,"email":email,"iphone":iphone,,"professional":professional,"password":password,"code":code};
	var contents = JSON.stringify(content);
	$.ajax({
		type:"post", 
        headers: {
        	"content-Type":"application/json",
        	 },
        url:"${pageContext.request.contextPath}/Register/service",  
        contentType:"application/json",  
        data:contents,  
        success:function(result){
        	 if (result==1) {//根据返回值进行跳转
                 alert("注册申请已提交，请前往邮箱确认！")
                 window.location.href ="${pageContext.request.contextPath}/Hello";
             }
        	 if(result==0){
        		 alert("请勿重复注册！")
        	 }
        	 if(result==2){
        		 alert("请输入正确的验证码！")
        	 }
        }  
	})
}
function CheckAll(){
	//姓名认证[\u4E00-\uFA29]|[\uE7C7-\uE7F3]
	var regname = /[\w\u4E00-\u9FA5]{2,6}/;
	//if(!regname.test(user_name.value)){
	if(!regname.test($("#name").val())){
		alert("姓名仅可以是数字，字母，汉字，下划线，长度2-6之间！")
		return;
	}
	//邮箱验证
	var x=document.forms["myForm"]["email"].value;
    var atpos=x.indexOf("@");
    var dotpos=x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){
    alert("不是一个有效的 e-mail 地址");
    }
	//手机号验证
	var regiphone=/^[0-9]{11}$/;   /*定义验证表达式*/
	if(!regiphone.test(iphone.value)){
		alert("手机号不符合规范！")
		return;
	}
    //密码验证
    var regpassword = /\w{6,20}/;
    if(!regpassword.test(password.value)){
    	alert("密码长度在6-20位，且仅为数字、字母、下划线")
    	return;
    }
	//密码二次验证
	var pass1 = document.getElementById("password");
    var pass2 = document.getElementById("surepassword");
    if (pass1.value != pass2.value){
    	pass1.setCustomValidity("两次输入的密码不匹配");
    	return;
    }
    register();
}

</script>
</head>
<body>
<div class="panel panel-primary">
  <div class="panel-heading">开启你的论坛之路！</div>
  <div class="panel-body">
<form name="myForm" id="myForm">
   <table class="table">
   <tr>
	<td align="center"><font color="red">*</font>姓名：<input type="text" placeholder="姓名" required id="name" name="name"  maxlength="6"/></td>
	<td align="center"><font color="red">*</font>登录邮箱：<input type="email" placeholder="Email" required id="email" name="email"/></td>
   </tr>
   <tr>
   <td align="center"><font color="red">*</font>手机号：<input type="text" placeholder="手机号" required id="iphone" name="iphone"/></td>
   <td align="center"><font color="red">*</font>专业：
   <select id="professional" name="professional">
   <c:forEach items="${oneboard}" var="node">  
   <option value="<c:out value="${node.one_title}"></c:out>"><c:out value="${node.one_title}"></c:out></option>
   </c:forEach>
</select>
   </td>
   </tr>
   <tr>
   <td align="center"><font color="red">*</font>密码：<input type="password" placeholder="密码" required id="password" name="password"/></td>
   <td align="center"><font color="red">*</font>确认密码：<input type="password" placeholder="确认密码" required id="surepassword" name="surepassword"/></td>
   </tr>
   <tr>
   <td align="center"><font color="red">*</font>验证码：<input type="text" placeholder="请输入验证码" required id="code" name="code"></td>
   <td align="center">
   <img src="${pageContext.request.contextPath}/Register/Code" id="imgObj" alt="验证码">
   </td>
   </tr>
   <tr>
   <td colspan="2" align="center"><input type="button" value="register" onclick="CheckAll()"/></td>
   </tr>
   </table>
</form>
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