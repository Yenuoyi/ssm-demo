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
<title>管理专业|BBS</title>
</head>
<body>
<%@ include file="adminheader.jsp" %>
<script type="text/javascript">
//添加专业信息
function addone(){
	var title = /[\w\u4E00-\u9FA5]{2,15}/;
	var one_title = $("#add_one_title").val();
	if(!title.test(one_title)){
		alert("专业名仅可以是数字，字母，汉字，下划线，长度2-15之间！")
		return;
	}
	var one = {"one_title":one_title};
	var oneboard = JSON.stringify(one);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
           //将token存储发送请求头
           //beforeSend: function(request) {
           //request.setRequestHeader("admin", user);
           // },
            url:"${pageContext.request.contextPath}/Admin/Super/AddOne",  
            contentType:"application/json",  
            data:oneboard,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("添加成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("添加失败，建议刷新再次尝试！");
                	 }else{
                		 alert("非法操作，请先登录！");
                	 }
                 }
            },
            error:function(result){
            	alert("系统超级异常，请联系管理员！")
            }
        });  
    } 

//更改专业信息
function changeone(one_id,one_title){
	var title = /[\w\u4E00-\u9FA5]{2,15}/;
	if(!title.test(one_title)){
		alert("专业名仅可以是数字，字母，汉字，下划线，长度2-15之间！")
		return;
	}
	var id=one_id;
	var title =one_title;
	var one = {"one_id":id,"one_title":title};
	var oneboard = JSON.stringify(one);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
           //将token存储发送请求头
           //beforeSend: function(request) {
           //request.setRequestHeader("admin", user);
           // },
            url:"${pageContext.request.contextPath}/Admin/Super/ChangeOne",  
            contentType:"application/json",  
            data:oneboard,  
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

//删除指定专业
function deleteone(one_id){
	var id=one_id;
	var one = {"one_id":id};
	var oneboard = JSON.stringify(one);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
           //将token存储发送请求头
           //beforeSend: function(request) {
           //request.setRequestHeader("admin", user);
           // },
            url:"${pageContext.request.contextPath}/Admin/Super/DeleteOne",  
            contentType:"application/json",  
            data:oneboard,  
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
<!-- 添加专业面板 -->
<div class="panel panel-primary">
  <div class="panel-heading">添加专业</div>
  <div class="panel-body">
  <form id="myForm">
    <table class="table table-striped">
    <tr>
    <td align="center">专业名:<input type="text"  id="add_one_title"  name="add_one_title" maxlength="15"/></td>
    <td colspan="2" align="center">
    <input type= "button" value="添加专业" onclick="addone()">
    </td>
    </tr>
    </table>
    </form>
  </div>
</div>

<!-- 所有专业面板 -->
<div class="panel panel-default">
  <div class="panel-heading">所有专业</div>
  <div class="panel-body">
    <table class="table" id="table_id_example">
<thead>
  <tr>
  <td align="center">专业id</td>
  <td align="center">专业名</td>
  <td align="center">编辑</td>
  </tr>
</thead>
<tbody>
<c:set var="index" value="0" />
<c:forEach items="${one}" var="node">
<tr>
<td align="center"><c:out value="${node.one_id }"></c:out></td>
<c:set var="index" value="${index+1}" />
<td align="center"><input style="text-align:center" type="text" value="${node.one_title }" id="change_one_title${index}" name="change_one_title${index}" maxlength="15"><p style="display:none;">${node.one_title }</p></td>
<td align="center">
<input type="button" onclick="changeone(${node.one_id },eval(document.getElementById('change_one_title${index}')).value)" value="修改">
<input type="button" onclick="deleteone(${node.one_id })" value="删除">
<a href="${pageContext.request.contextPath}/Admin/Super/Lookone_twoview?one_id=${node.one_id }"><input type= "button" value="查看本专业课程"></a>
</td>
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