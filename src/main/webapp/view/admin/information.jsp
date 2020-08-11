<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>个人信息|BBS</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

<script>
//表单检查
function Check(){
	var re=/^[0-9]{11}$/;
	var regname = /[\w\u4E00-\u9FA5]{2,6}/;
	var name = $("#name").val();
	if(!re.test($("#iphone").val())){
		alert("手机号错误！！!")
	}else{
		if(!regname.test(name)){
			alert("姓名仅可以是数字，字母，汉字，下划线，长度2-6之间！")
		}else{
			infor_json();
		}
	}
}
//表单提交
function infor_json(){  
	var id = $("#id").val();
	var name = $("#name").val();
	var professional = $("#professional").val();
	var iphone = $("#iphone").val();
	var newuser = {"id":id,"name":name,"professional":professional,"iphone":iphone};
	var newusers = JSON.stringify(newuser);
        $.ajax({  
            type:"post", 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/InforChange",  
            contentType:"application/json",  
            data:newusers,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("更改成功查看!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("更改失败，建议重新登录再次尝试！");
                	 }else{
                		 alert("系统异常，请联系管理员！");
                	 }
                 }
            },
            error:function(result){
            	alert("更改异常，请联系管理员！")
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
<td align="center">id: <input type="text" id="id" value="${userbean.getId()}" disabled="true"></td>
<td align="center">姓名: <input type="text" id="name" value="${userbean.getName()}" name="name" maxlength="6" /></td>
</tr>
<tr>
<td align="center">专业: 
<select name="professional" id="professional">
<option value="${userbean.getProfessional()}"  selected >${userbean.getProfessional()}</option>
<c:forEach items="${one}" var="node">  
<option value="<c:out value="${node.one_title}"></c:out>"><c:out value="${node.one_title}"></c:out></option>
</c:forEach>
</select>
</td>
<td align="center">联系方式: <input type="text" id="iphone" value="${userbean.getIphone()}" name="iphone" maxlength="11" /></td>
</tr>
<tr>
<td align="center">email:<input type="text" id="email" value="${userbean.getEmail()}" name="email" disabled="true"/></td>
<td align="center">创建时间： <input type="text" id="create_time" value="${userbean.getCreate_time()}" name="create_time" disabled="true"/></td>
</tr>
<tr>
<td colspan="2" align="center">
<input type= "button" value="提交更改信息" onclick="Check()">
</td>
</tr>
</table>
</form>
</body>
</html>