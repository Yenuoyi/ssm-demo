<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>教学视频</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<table class="table table-bordered">  
   <c:forEach items="${videoUrl}" var="videoUrl">  
   <tr>
   <td>
   <a target="blank" href="http://yebingavtar.oss-cn-shenzhen.aliyuncs.com/${videoUrl}"><c:out value="${videoUrl}"></c:out></a></td>
   </tr>   
   </c:forEach>
   </table>
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
   <form name='formA${index}' action='${pageContext.request.contextPath}/Video' method='post'>
   <input type='hidden' name='board' value='${board}'/> 
   </form>
   <a href="javascript:document.formA${index}.submit();"><c:out value="${board}"></c:out></a></td>
   </tr>   
   </c:forEach>
   
   <c:forEach items="${document}" var="document">  
   <tr>
   <td>
   <a target="blank" href="http://yebingavtar.oss-cn-shenzhen.aliyuncs.com/${document}"><c:out value="${document}"></c:out></a>
   </td>
   </tr>   
   </c:forEach>
   </table>
</body>
</html>
