<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>搜索结果|BBS</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
</head>
<body>
<%@ include file="header.jsp" %>
<!-- 课程列表 -->
${one.getOne_title()}
${userbean.getName()}
<div class="table-responsive">
  <table class="table">
  <tr>
   <td>课程名</td>
   <td>教师</td>
   <td>课程简介</td>
   <td>最近更新时间</td>
   <td align="center">立即查看</td>
  </tr>
<c:forEach items="${one_two}" var="node"> 
  <tr>
   <td><c:out value="${node.two_title}"></c:out> </td>
   <td><c:out value="${node.two_teacher_name}"></c:out></td>
   <td><c:out value="${node.two_profile}"></c:out></td>
   <td><c:out value="${node.two_update_time}"></c:out></td>
   <td align="center">
   <a target="_blank" href="${pageContext.request.contextPath}/Threeboard_content?
   one_id=${one.one_id}
   &two_id=<c:out value="${node.two_id}"></c:out>">
   <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
   </td>
  </tr>
</c:forEach>
</table>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>