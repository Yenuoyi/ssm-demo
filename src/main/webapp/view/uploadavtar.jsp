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
.hide{display:none;}
</style>
<script>
function Uploadfile(){
	var file=document.getElementById("myfile").value;
	if(file==""){
		alert("文件不能为空！")
		return;
	}
	  //document.getElementById("form1").submit();
	var form = new FormData(document.getElementById("form1"));
	$.ajax({
        url:"${pageContext.request.contextPath}/UploadAvtar",
        type:"post",
        data:form,
        processData:false,
        contentType:false,
        success:function(result){
            if(result==1){
            	alert("上传成功！")
            }else{
            	alert("上传失败,头像大小不可超过2M！")
            }
        },
        error:function(e){
            alert("错误！！");
            window.clearInterval(timer);
        }
    });        
	}
	function imgPreview(fileDom){
    //判断是否支持FileReader
    if (window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }

    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    if (!imageType.test(file.type)) {
        alert("请选择图片！");
        return;
    }
    //读取完成
    reader.onload = function(e) {
        //获取图片dom
        var img = document.getElementById("preview");
        //图片路径设置为读取的图片
        img.src = e.target.result;
    };
    reader.readAsDataURL(file);
}
</script>
<body>
<%@ include file="header.jsp" %>

<div class="panel panel-info">
  <div class="panel-heading">请您选择需要上传的头像</div>
  <div class="panel-body">
<table align="center">
<tr><td><p>现使用头像</p></td></tr>
<tr><td><img alt="" src="${ sessionScope.headpicurl}" width="150px" height="200px"></td>
</tr></table>
  <table align="center">
  <tr>
  <td>
  <img id="preview"/>
  </td>
  </tr>
  </table>
  <form id="form1" name="form1" method="post" enctype="multipart/form-data">
  <table align="center">
  <tr align="center" height="50">
   <td align="center">
   <input name="avtar" id="avtar" value="Avtar/" style='display:none'>
   <input name="myfile" id="myfile" type="file" size="20" accept="image/gif,image/jpeg,image/jpg,image/png" onchange="imgPreview(this)" ></td>
  </tr>    
  <tr align="center"align="center" height="30">   
    <td align="left">
    <input type="button" name="Upload" value="提交" onclick="Uploadfile()">
    <input type="reset" name="reset" value="重置" >
   </td>
  </tr>
 </table>
</form>
  </div>
</div>
</body>
