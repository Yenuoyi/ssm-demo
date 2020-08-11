<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>添加课程|BBS</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/image/favicon.jpg" />

<script>
function Check(){
	var result = "OK";
	//姓名认证[\u4E00-\uFA29]|[\uE7C7-\uE7F3]
	var title = /[\w\u4E00-\u9FA5]{2,15}/;
	//if(!title.test(two_title.value)){
	if(!title.test($("#two_title").val())){
		result = "NO";
		alert("课程名仅可以是数字，字母，汉字，下划线，长度2-15之间！")
		return;
	}
	var profile = /[\w\u4E00-\u9FA5]{2,300}/;
	if(!profile.test($("#two_profile").val())){
		result = "NO";
		alert("课程简介仅可以是数字，字母，汉字，下划线，长度2-300之间！")
		return;
	}
	if(result=="OK"){
		infor_json();
	}
}
//表单提交
function infor_json(){
	var two_title = $("#two_title").val();
	var two_one_id = $("#two_one_id").val();
	var two_teacher_id = $("#two_teacher_id").val();
	var two_teacher_name = $("#two_teacher_name").val();
	var two_profile = $("#two_profile").val();
	var two = {"two_title":two_title,"two_one_id":two_one_id,"two_teacher_id":two_teacher_id,"two_teacher_name":two_teacher_name,"two_profile":two_profile};
	var twoboard = JSON.stringify(two);
        $.ajax({  
            type:"post", 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/Admin/AddTwo",  
            contentType:"application/json",  
            data:twoboard,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("添加成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("添加失败，建议重新登录再次尝试！");
                	 }else{
                		 alert("系统异常，请联系管理员！");
                	 }
                 }
            },
            error:function(result){
            	alert("异常，请联系管理员！")
            }
        });  
    } 
</script>
</head>
<body>
<!-- 导入头 -->
<%@ include file="adminheader.jsp" %>
<form id="myForm">
<table class="table table-striped">
<tr>
<td align="center">课程名: <input type="text" id="two_title" name="two_title" maxlength="15"></td>
<td align="center">专业: 
<select name="professionall" id="two_one_id">
<c:forEach items="${one}" var="node">  
<option value="<c:out value="${node.one_id}"></c:out>"><c:out value="${node.one_title}"></c:out></option>
</c:forEach>
</select></td>
</tr>
<tr>
<td align="center" colspan="2">课程简介:<input type="text" id="two_profile"  name="two_profile" maxlength="300"/></td>
</tr>
<tr>
<td colspan="2" align="center">
<input type= "button" value="添加课程" onclick="Check()">
</td>
</tr>
</table>
</form>
</body>
</html>