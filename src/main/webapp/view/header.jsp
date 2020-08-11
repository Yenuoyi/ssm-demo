<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page language="java" import="com.ye.util.*,com.ye.domain.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>I|BBS</title>
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/image/favicon.jpg" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/datatables.min.css" />
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script>
function checkSeach(){

	var re=/\S{1,20}/;
	if(!re.test($("#two_title").val())){
		alert("课程名不可为空，长度1-20之间！")
		return;
	}
	  document.getElementById("formid").submit();
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
      <p class="navbar-brand">BBS</p>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="http://119.23.51.148/Vertx/Welcome">首页</a></li>
        <li><a target="blank" href="${pageContext.request.contextPath}/ChatView">在线交流室</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">学习资源<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li>
            <a target="blank" href='${pageContext.request.contextPath}/Download'>文件资源下载</a>
            </li>
            <li>
               <form name='formA' action='${pageContext.request.contextPath}/Video' method='post'>
               <input type='hidden' name='board' value='video/'/> 
               </form>
               <a href="javascript:document.formA.submit();">教学视频</a>
   </li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">专业分类<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <c:forEach items="${oneboard}" var="node"> 
            <li><a target="_blank" href="${pageContext.request.contextPath}/Twoboard_content?one_id=${node.one_id}"><c:out value="${node.one_title}"></c:out> </a></li>
            </c:forEach>
          </ul>
        </li>
      </ul>
      <!-- 查找课程表单 -->
      <form class="navbar-form navbar-left" action="${pageContext.request.contextPath}/Search" method="post" id="formid">
        <div class="form-group">
          <input style="display:none;" id="Text1" type="text" />
          <input type="text" class="form-control" placeholder="可以搜索想要的课程哦！" id="two_title" name="two_title" maxlength="20">
        </div>
        <button type="button" class="btn btn-default" onclick="checkSeach()">Submit</button>
      </form>
      <ul class="nav navbar-nav navbar-right">
        <li><a>${ sessionScope.name}</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span><span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${pageContext.request.contextPath}/UserInfor">个人信息</a></li>
            <li><a href="${pageContext.request.contextPath}/UserPassword">密码修改</a></li>
            <li><a target="blank" href="${pageContext.request.contextPath}/uploadFaceView">人脸上传</a></li>
            <li><a target="blank" href="${pageContext.request.contextPath}/uploadAvtarView">头像管理</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">退出</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
</body>
</html>