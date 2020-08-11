<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Upload to upload file |BBS</title>
</head>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />

<style>
* { font-family: "宋体"; font-size: 14px }
</style>
<script>
function Uploadfile(){
	var file=document.getElementById("myfile").value;
	if(file==""){
		alert("文件不能为空！")
		return;
	}
	  document.getElementById("form1").submit();
	}
</script>
<body>
<%@ include file="adminheader.jsp" %>
<div class="panel panel-info">
  <div class="panel-heading">请您选择需要上传的文件</div>
  <div class="panel-body">
    <form id="form1" name="form1" method="post" action="${pageContext.request.contextPath}/Admin/Uploadfile" enctype="multipart/form-data">
 <table align="center">
  <tr align="center">
    <td align="center">
    <select name="folderdocument" id="folderdocument">
    <c:forEach items="${folder}" var="folder">  
    <option value="<c:out value="${folder}"></c:out>"><c:out value="${folder}"></c:out></option>
    </c:forEach>
    </select>
    </td>
  </tr>   
  <tr align="center" height="50">
   <td align="center"><input name="myfile" id="myfile" type="file" size="20" ></td>
  </tr>    
  <tr align="center"align="center" height="30">   
    <td align="center">
    <input type="button" name="Upload" value="提交" onclick="Uploadfile()">
    <input type="reset" name="reset" value="重置" >
   </td>
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