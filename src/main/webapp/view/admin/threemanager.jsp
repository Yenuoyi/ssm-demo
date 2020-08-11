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

<title>管理课程目录|BBS</title>
<style type="text/css">
p {
letter-spacing : 1px;
font-size : 12pt;
line-height : 20px;
overflow: hidden;
display: -webkit-box;
-webkit-line-clamp: 5;
-webkit-box-orient: vertical;
}
</style>
</head>
<body>
<%@ include file="adminheader.jsp" %>
<script type="text/javascript">
//更改课程信息
function twochange(){
	var two_title_submit = $("#two_title").val();
	var two_profile_submit = $("#two_profile").val();
	var title = /[\w\u4E00-\u9FA5]{2,15}/;
	if(!title.test(two_title_submit)){
		alert("课程名仅可以是数字，字母，汉字，下划线，长度2-15之间！")
		return;
	}
	var profile = /[\w\u4E00-\u9FA5]{2,300}/;
	if(!profile.test(two_profile_submit)){
		alert("课程简介仅可以是数字，字母，汉字，下划线，长度2-300之间！")
		return;
	}
	var two_id=${two.getTwo_id() };
	var two = {"two_id":two_id,"two_title":two_title_submit,"two_profile":two_profile_submit};
	var twoboard = JSON.stringify(two);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/Admin/ChangeTwo",  
            contentType:"application/json",  
            data:twoboard,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("修改成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("修改失败，建议重新登录再次尝试！");
                	 }else{
                		 alert("系统异常请联系管理员！");
                	 }
                 }
            },
            error:function(result){
            	alert("更改异常，请联系管理员！")
            }
        });  
    } 

//添加目录信息
function addthree(){
	var title = /[\w\u4E00-\u9FA5]{2,15}/;
	var three_title_submit = $("#three_title").val();
	var three_profile_submit = $("#three_profile").val();
	if(!title.test(three_title_submit)){
		alert("目录名仅可以是数字，字母，汉字，下划线，长度2-15之间！")
		return;
	}
	var profile = /[\w\u4E00-\u9FA5]{2,300}/;
	if(!profile.test(three_profile_submit)){
		alert("目录简介仅可以是数字，字母，汉字，下划线，长度2-300之间！")
		return;
	}
	var three_two_id = $("#three_two_id").val();
	var three = {"three_two_id":three_two_id,"three_title":three_title_submit,"three_profile":three_profile_submit};
	var threeboard = JSON.stringify(three);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/Admin/AddThree",  
            contentType:"application/json",  
            data:threeboard,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("添加成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("添加失败，建议重新登录再次尝试！");
                	 }else{
                		 alert("系统异常请联系管理员！");
                	 }
                 }
            },
            error:function(result){
            	alert("系统超级异常，请联系管理员！")
            }
        });  
    } 
    
//删除指定目录
function deleteview(three_id){
	var id=three_id;
	var three = {"three_id":id};
	var threeboard = JSON.stringify(three);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/Admin/DeleteThree",  
            contentType:"application/json",  
            data:threeboard,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("删除成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("删除失败，建议重新登录再次尝试！");
                	 }else{
                		 alert("系统异常请联系管理员！");
                	 }
                 }
            },
            error:function(result){
            	alert("更改异常，请联系管理员！")
            }
        });  
    } 
</script>
<!-- 课程信息面板 -->
<div class="panel panel-primary">
  <div class="panel-heading">课程信息</div>
  <div class="panel-body">
  <form>
  <table class="table">
  <tr>
  <td>课程名：<input type="text" value="${two.getTwo_title() }" id="two_title" name="two_title" maxlength="15"></td>
  <td>课程简介：<br><textarea style='width: 500px' rows='5' id="two_profile" name="two_profile" 
  maxlength="300" >${two.getTwo_profile() }</textarea></td>
  <td><input type= "button" value="更改" onclick="twochange()"></td>
  </tr>
  </table>
  </form>
  </div>
</div>

<!-- 添加目录面板 -->
<div class="panel panel-default">
  <div class="panel-heading">添加目录</div>
  <div class="panel-body">
    <form id="myForm">
    <input type="hidden" value="${two.getTwo_id()}" id="three_two_id"  name="three_two_id"/>
    <table class="table table-striped">
    <tr>
    <td align="center">目录标题:<input type="text"  id="three_title"  name="three_title" maxlength="15"/></td>
    <td>
    目录介绍:<br><textarea style='width: 500px' rows='5' id="three_profile" name="three_profile" 
  maxlength="300" ></textarea>
  </td>
    <td colspan="2" align="center">
    <input type= "button" value="添加目录" onclick="addthree()">
    </td>
    </tr>
    </table>
    </form>
  </div>
</div>
<!-- 展示所有目录 -->
<table class="table" id="table_id_example">
<thead>
  <tr>
  <td width=20%>章节名称</td>
  <td width=55%>章节简介</td>
  <td align="center" width=10%>评论数</td>
  <td align="center" width=15%>删除</td>
  </tr>
</thead>
<tbody>
<c:forEach items="${three}" var="node">
<tr>
<td width=20%><c:out value="${node.three_title }"></c:out></td>
<td width=55%><p><c:out value="${node.three_profile }"></c:out></p></td>
<td align="center" width=10%><c:out value="${node.three_statistics }"></c:out></td>
<td align="center" width=15%><input type="button" onclick="deleteview(${node.three_id })" value="删除"></td>
</tr>
</c:forEach>
</tbody>
</table>
<!--第三步：初始化Datatables-->
<script type="text/javascript">
$(document).ready(function(){
    $('#table_id_example').DataTable();
});
</script>
</body>
</html>