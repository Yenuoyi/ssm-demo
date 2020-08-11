<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/datatables.min.css" />
<title>教师管理|BBS</title>
</head>
<body>
<%@ include file="adminheader.jsp" %>
<script type="text/javascript">
//添加教师表单验证
function addteachersure(){
	var result = "OK";
	//姓名认证[\u4E00-\uFA29]|[\uE7C7-\uE7F3]
	var regname = /[\w\u4E00-\u9FA5]{2,6}/;
	if(!regname.test($("#add_teacher_name").val())){
		result = "NO";
		alert("姓名仅可以是数字，字母，汉字，下划线，长度2-6之间！")
		return;
	}
	//邮箱验证
	var x=document.forms["myForm"]["add_teacher_email"].value;
    var atpos=x.indexOf("@");
    var dotpos=x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){
    	result = "NO";
    	alert("不是一个有效的 e-mail 地址");
    	return;
    }
	//手机号验证
	var regiphone=/^[0-9]{11}$/;   /*定义验证表达式*/
	if(!regiphone.test(add_teacher_iphone.value)){
		result = "NO";
		alert("手机号不符合规范！")
		return;
	}
	if(result=="OK"){
		addteacher();
	}
}
//添加教师信息
function addteacher(){
	var name = $("#add_teacher_name").val();
	var professional = $("#add_teacher_professional").val();
	var iphone = $("#add_teacher_iphone").val();
	var email = $("#add_teacher_email").val();
	var teacher = {"name":name,"professional":professional,"iphone":iphone,"email":email};
	var teach = JSON.stringify(teacher);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
           //将token存储发送请求头
           //beforeSend: function(request) {
           //request.setRequestHeader("admin", user);
           // },
            url:"${pageContext.request.contextPath}/Admin/Super/AddTeacher",  
            contentType:"application/json",  
            data:teach,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("添加成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==2){
                		 alert("添加失败，该教师邮箱已存在！");
                	 }else{
                		 if(result==0){
                			 alert("添加失败，请刷新再次尝试！");
                		 }else{
                			 alert("非法操作，请先登录！");
                		 }
                	 }
                 }
            },
            error:function(result){
            	alert("系统超级异常，请联系管理员！")
            }
        });  
    } 

//更改教师信息
function changeteacher(teacher_id,teacher_name,teacher_iphone,teacher_email){
	//姓名认证[\u4E00-\uFA29]|[\uE7C7-\uE7F3]
	var regname = /[\w\u4E00-\u9FA5]{2,6}/;
	if(!regname.test(teacher_name)){
		alert("姓名仅可以是数字，字母，汉字，下划线，长度2-6之间！");
		return;
	}
	//手机号验证
	var regiphone=/^[0-9]{11}$/;   /*定义验证表达式*/
	if(!regiphone.test(teacher_iphone)){
		alert("手机号不符合规范！")
		return;
	}
	var id=teacher_id;
	var professional = $("#change_teacher_professional").val();
	var name = teacher_name;
	var iphone = teacher_iphone;
	var email = teacher_email;
	var teacher = {"id":id,"name":name,"professional":professional,"iphone":iphone,"email":email};
	var teach = JSON.stringify(teacher);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/Admin/Super/ChangeTeacher",  
            contentType:"application/json",  
            data:teach,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("修改成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("修改失败，建议刷新再次尝试！");
                	 }else{
                		 alert("非法操作，请先登录！");
                	 }
                 }
            },
            error:function(result){
            	alert("更改异常，请联系管理员！")
            }
        });  
    } 

//删除指定教师
function deleteteacher(teacher_email){
	var email=teacher_email;
	var teacher= {"email":email};
	var teach = JSON.stringify(teacher);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/Admin/Super/DeleteTeacher",  
            contentType:"application/json",  
            data:teach,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("删除成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("删除失败，建议刷新尝试or联系超级管理员！");
                	 }else{
                		 alert("非法操作，请先登录！");
                	 }
                 }
            },
            error:function(result){
            	alert("更改异常，请联系管理员！")
            }
        });  
    } 
</script>
<!-- 添加教师面板 -->
<div class="panel panel-primary">
  <div class="panel-heading">添加教师</div>
  <div class="panel-body">
  <form id="myForm">
    <table class="table table-striped">
    <tr>
    <td align="center">姓名：<input type="text" id="add_teacher_name" name="add_teacher_name" maxlength="6"/></td>
    <td align="center">专业:
    <select name="professionall" id="add_teacher_professional">
    <c:forEach items="${one}" var="node">  
    <option value="<c:out value="${node.one_title}"></c:out>"><c:out value="${node.one_title}"></c:out></option>
    </c:forEach>
    </select>
    </td>
    </tr>
    <tr>
    <td align="center">手机：<input type="text" id="add_teacher_iphone" name="add_teacher_iphone" maxlength="11"/></td>
    <td align="center">email:<input type="email"  id="add_teacher_email"  name="add_teacher_email"/></td>
    </tr>
    <tr>
    <td colspan="2" align="center">
    <input type= "button" value="添加教师" onclick="addteachersure()">
    </td>
    </tr>
    </table>
    </form>
  </div>
</div>

<!-- 所有教师面板 -->
<div class="panel panel-default">
  <div class="panel-heading">所有教师</div>
  <div class="panel-body">
    <table class="table" id="table_id_example">
<thead>
  <tr>
  <td align="center">教师id</td>
  <td align="center">姓名</td>
  <td align="center">专业</td>
  <td align="center">手机</td>
  <td align="center">email</td>
  <td align="center">创建时间</td>
  <td align="center">编辑</td>
  </tr>
</thead>
<tbody>
<c:set var="index" value="0" />
<c:forEach items="${teacher}" var="node">
<tr>
<c:set var="index" value="${index+1}" />
<td align="center"><c:out value="${node.id }"></c:out></td>
<td align="center">
<input type="text" value="${node.name }" name="change_teacher_name${index}" id="change_teacher_name${index}" maxlength="6">
<tb style="display:none;">${node.name }</tb>
</td>
<td align="center">
<select name="professional" id="change_teacher_professional">
<option value="${node.professional }"  selected >${node.professional }</option>
<c:forEach items="${one}" var="no">  
<option value="<c:out value="${no.one_title}"></c:out>"><c:out value="${no.one_title}"></c:out></option>
</c:forEach>
</select>
</td>
<td align="center">
<input type="text" value="${node.iphone }" name="change_teacher_iphone${index}" id="change_teacher_iphone${index}" maxlength="11">
<tb style="display:none">${node.iphone }</tb>
</td>
<td align="center"><input type="text" value="${node.email }" name="change_teacher_email${index}" id="change_teacher_email${index}" disabled="true">
<tb style="display:none">${node.email }</tb></td>
<td align="center"><c:out value="${node.create_time }"></c:out></td>
<td align="center">
<input type="button" onclick="changeteacher(${node.id },eval(document.getElementById('change_teacher_name${index}')).value,eval(document.getElementById('change_teacher_iphone${index}')).value,eval(document.getElementById('change_teacher_email${index}')).value)" value="修改">
<input type="button" onclick="deleteteacher('${node.email }')" value="删除"></td>
</tr>
</c:forEach>
</tbody>
</table>
  </div>
</div>

<!--第三步：初始化Datatables-->
<script type="text/javascript">
$(document).ready(function(){
    $('#table_id_example').DataTable();
});
</script>
</body>
</html>