<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<title>登录日志查看|BBS</title>
</head>
<body>
<%@ include file="adminheader.jsp" %>
<script type="text/javascript">
//删除最早的一百条日志
function deleteHundred(){
	$.ajax({
		type: 'POST', 
        headers: {"content-Type":"application/json"},
        url:"${pageContext.request.contextPath}/Admin/Super/deleteHundredLoginLog",  
        contentType:"application/json",  
        success:function(result){
        	 if (result==0) {//根据返回值进行跳转
        		 alert("删除失败，建议刷新尝试or联系超级管理员！");
                 
             }else{
            	 alert("删除成功!")
            	 window.location.reload(); 
             }
        },
        error:function(result){
        	alert("删除异常，请联系管理员！")
        }
       
	})
}
//删除所有日志
function deleteAll(){
	$.ajax({
		type: 'POST', 
        headers: {"content-Type":"application/json"},
        url:"${pageContext.request.contextPath}/Admin/Super/deleteAllLoginLog",  
        contentType:"application/json",  
        success:function(result){
        	 if (result==0) {//根据返回值进行跳转
        		 alert("删除失败，建议刷新尝试or联系超级管理员！");
    
             }else{
            	 alert("删除成功!")
                 window.location.reload(); 
             }
        },
        error:function(result){
        	alert("删除异常，请联系管理员！")
        }
       
	})
}

</script>
<!-- 删除日志面板 -->
<div class="panel panel-primary">
  <div class="panel-heading">删除日志</div>
  <div class="panel-body">
  <input type= "button" value="删除最早的一百条日志" onclick="deleteHundred()">
  <input type= "button" value="删除所有日志" onclick="deleteAll()">
  </div>
</div>
<div class="panel panel-default">
  <div class="panel-heading">登录日志</div>
  <div class="panel-body">
    <table class="table" id="table_id_example">
<thead>
  <tr>
  <td align="center">loginlog_id</td>
  <td align="center">用户名</td>
  <td align="center">登录ip</td>
  <td align="center">登录时间</td>
  <td align="center">登录状态</td>
  <td align="center">用户角色</td>
  <td align="center">用户email</td>
  </tr>
</thead>
<tbody>
<c:forEach items="${loginlog}" var="node">
<tr>
<td align="center"><c:out value="${node.loginlog_id}"></c:out></td>
<td align="center"><c:out value="${node.user_name}"></c:out></td>
<td align="center"><c:out value="${node.user_ip}"></c:out></td>
<td align="center"><c:out value="${node.loginlog_time}"></c:out></td>
<td align="center"><c:out value="${node.loginlog_state}"></c:out></td>
<td align="center"><c:out value="${node.roleid}"></c:out></td>
<td align="center"><c:out value="${node.user_email}"></c:out></td>
</tr>
</c:forEach>
</tbody>
</table>
  </div>
</div>

<!--第三步：初始化Datatables-->
<script type="text/javascript">
$(document).ready(function(){
    $('#table_id_example').DataTable({
    	"aaSorting" : [[0, "desc"]] //默认的排序方式，第2列，升序排列
    });
});
</script>
</body>
</html>
