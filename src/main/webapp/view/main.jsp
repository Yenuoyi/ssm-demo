<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>Hello|BBS</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<style>
p {
overflow:hidden; 
text-overflow:ellipsis;
display: -webkit-box;
-webkit-line-clamp: 3;
-webkit-box-orient: vertical;
font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;
}
div.pbody {
    background-color: #FCFCFC;
	float:left;
	overflow:hidden;display:block"
    width: 120px;
	height: 70px;
    border: 1px solid #EEE8CD;
    padding: 1px;
    margin: 1px;
}
div.bodyleftmid {
    background-color: #FCFCFC;
	float:left;
    width: 410px;
	height: 160px;
    border: 1px solid #EEE8CD;
    padding: 2px;
    margin: 5px;
}
div.imga{ 
	float:left;
	margin: 5px;
	width:100px; 
	height:140px;
	overflow:hidden
} 
div.imga img{
	max-width:100px;
	_width:expression(this.width > 100 ? "100px" : this.width);
	padding-top: 2px;
	padding-bottom: 2px;
	padding-left: 2px;
	padding-rgiht: 8px;
} 
div.pad {
    padding-top: 10px;
    padding-left: 130px;
}
</style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="row">
<div class="col-xs-12 col-md-8">
<!-- 带标题的面板 -->
<div class="panel panel-info">
<div class="panel-heading">最新课题</div>
  <div class="panel-body">
<c:forEach items="${two_ten}" var="node">
<div class="bodyleftmid">
<div class="imga">
<a target="_blank" href="${pageContext.request.contextPath}/Threeboard_content?one_id=${node.two_one_id}&two_id=${node.two_id}">
			<img width="100" height="140" src="${node.two_teacher_headpicurl }" /></a>
		</div>
		<div class="pad">
		<a target="_blank" href="${pageContext.request.contextPath}/Threeboard_content?one_id=${node.two_one_id}&two_id=${node.two_id}">
		课程名：${node.two_title }
		</a>
		<p><font size="2">教师：${node.two_teacher_name }</font></p>
		<font size="2">课程简介：</font>
		<p>${node.two_profile }</p>
        </div>
</div>
</c:forEach>
</div>
</div>
</div>
<!-- 左边内部全部结束 -->

<!-- 栅格系统右边 -->
<div class="col-xs-6 col-md-4">
<!-- 热门课题 -->
<div class="panel panel-info">
<div class="list-group">
  <a href="#" class="list-group-item list-group-item-info">
    热门课题
  </a>
  <table class="table">
  <c:forEach items="${two_statistics_five}" var="node">
  <tr>
  <td>
  <c:out value="${node.two_title}"></c:out>
  </td>
  <td>
  <a target="_blank" href="${pageContext.request.contextPath}/Threeboard_content?one_id=${node.two_one_id}&two_id=${node.two_id}">
   <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
   </td>
  </tr>
  </c:forEach>
  </table>
</div>
</div>
<div class="panel panel-info">
  <!-- Default panel contents -->
<div class="list-group">
  <a href="#" class="list-group-item list-group-item-info">
    专题推荐
  </a>
  <!-- Table -->
  <table class="table">
   <c:forEach items="${three_statistics_five}" var="node"> 
  <tr>
  <td>
   <c:out value="${node.three_title}"></c:out> 
  </td>
  <td>
  <a target="_blank" href="${pageContext.request.contextPath}/Content?two_id=${node.three_two_id}&three_id=<c:out value="${node.three_id}"></c:out>">
   <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
  </td>
  </tr>
  </c:forEach>
</table>
</div>
</div>
<!-- 热点新闻 -->
<div class="panel panel-info">
<div class="list-group">
  <a href="#" class="list-group-item list-group-item-info">
    热点新闻
  </a>
  <table class="table">
  <c:forEach items="${news}" var="node">
  <tr height="50">
  <td>
  <c:out value="${node.title}"></c:out>
  </td>
  <td>
  <a target="_blank" href="${node.url}">
   <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
   </td>
  </tr>
  </c:forEach>
  </table>
</div>
</div>
</div>
</div>
<%
Object mess =  request.getAttribute("search_message");
 %>
 <% if (mess != null) { %>
    <script type="text/javascript">
        alert("<%=mess%>");
</script>
<% } %>
</body>
</html>