<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page language="java" import="com.ye.util.*,com.ye.domain.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员后台|BBS</title>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/image/favicon.jpg" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/datatables.min.css" />
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript">
//下载文件资源超链接
function goToClient(board){ 
window.open("${pageContext.request.contextPath}/Upload?board=" + board+"&result=1");
} 
function Oneview(){
	$.ajax({  
        type:"POST", 
        url:'${pageContext.request.contextPath}/Admin/Super/Oneview',  
        success:function(view){
        	 if (view!="") {
        		  myWindow=window.open();
        		  myWindow.document.write(view);
        		  myWindow.focus();
             }else{
            	 alert("你无此权限！")
             }
        }  
    });  
}
function Teacherview(){
	$.ajax({  
        type:"POST", 
        url:'${pageContext.request.contextPath}/Admin/Super/Teacherview',  
        success:function(view){
        	 if (view!="") {
        		  myWindow=window.open();
        		  myWindow.document.write(view);
        		  myWindow.focus();
             }else{
            	 alert("你无此权限！")
             }
        }  
    });  
}
</script>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Brand</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="${pageContext.request.contextPath}/Admin/Information">个人信息查看 <span class="sr-only">(current)</span></a></li>
        <li><a href="${pageContext.request.contextPath}/PasswordChange">密码修改</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">课程操作 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${pageContext.request.contextPath}/Admin/Addtwoview">添加课程</a></li>
            <li><a href="${pageContext.request.contextPath}/Admin/Looktwoview">查看所有课程</a></li>
            <li role="separator" class="divider"></li>
            <li>
            <a target="blank" href='${pageContext.request.contextPath}/Admin/Upload'>上传文件</a></li>
            <li role="separator" class="divider"></li>
            <!-- <li><a href="${pageContext.request.contextPath}/PasswordChange">摆设Two</a></li> -->
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">${ sessionScope.name}</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">超级管理员入口<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a target="blank" href="${pageContext.request.contextPath}/Admin/Super/Oneview" >查看所有专业</a></li>
            <li role="separator" class="divider"></li>
            <li><a target="blank" href="${pageContext.request.contextPath}/Admin/Super/Teacherview" >教师管理</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">退出</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</body>
</html>