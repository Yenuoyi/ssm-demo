<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<title>专业资源下载|BBS</title>
</head>
<body>
<%@ include file="header.jsp" %>
<table class="table table-bordered">  
   <tr>
   <td>
   <input type="button" id="backBtn" name="button" class="button_return" value="返回上一页"
   onclick="location.href='javascript:history.go(-1);'">
   </td>
   </tr>
   <c:set var="index" value="0" />   
   <c:forEach items="${board}" var="board">  
   <c:set var="index" value="${index+1}" />
   <tr>
   <td>
   <form name='formA${index}' action='${pageContext.request.contextPath}/Download' method='post'>
   <input type='hidden' name='board' value='${board}'/> 
   </form>
   <a href="javascript:document.formA${index}.submit();"><c:out value="${board}"></c:out></a></td>
   </tr>   
   </c:forEach>
   
   <c:set var="doindex" value="0" />
   <c:forEach items="${document}" var="document">  
   <c:set var="doindex" value="${doindex+1}" />
   <tr>
   <td>
   <form name='formB${doindex}' action='${pageContext.request.contextPath}/DownloadFile' method='post'>
   <input type='hidden' name='document' value='${document}'/> 
   </form>
   <c:out value="${document}"></c:out>
   <c:if test="${doindex ==1 }">
   <a href="javascript:document.formB${doindex}.submit();">下载文件夹</a>
   </c:if>
   <c:if test="${doindex !=1 }">
   <a href="javascript:document.formB${doindex}.submit();">下载文件</a>
   </c:if>
   </td>
   </tr>   
   </c:forEach>
   </table>
</body>
</html>
