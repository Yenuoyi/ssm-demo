<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/image/favicon.jpg" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<title>人脸识别</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
    <body>
<div id="contentHolder">
<div class="panel panel-default">
  <div class="panel-heading">请输入你的帐号，然后点击拍照，最后上传，请等待0.5到1分钟进行验证！</div>
  <div class="panel-body">
    <input type="text" placeholder="请输入你的帐号！" id="email" name="email">
<br>
<table align="center"><tr align="center">
<td align="center" width="320">

           <!--video用于显示媒体设备的视频流，自动播放-->
          <video id="video" autoplay style="width: 480px;height: 320px"></video>
         <!--拍照按钮-->
        <div>
       <button id="capture">拍照</button>
         </div>
    </td>
<td align="center" width="320">
<!--描绘video截图-->
<canvas id="canvas" width="480" height="320"></canvas>
    <button id="sc" style="display:block" >上传</button>
</td></tr></table>
</div>
</div>
</div>

<script>
//访问用户媒体设备的兼容方法
function getUserMedia(constrains,success,error){
    if(navigator.mediaDevices.getUserMedia){
        //最新标准API
        navigator.mediaDevices.getUserMedia(constrains).then(success);
    } else if (navigator.webkitGetUserMedia){
        //webkit内核浏览器
        navigator.webkitGetUserMedia(constrains).then(success);
    } else if (navigator.mozGetUserMedia){
        //Firefox浏览器
        navagator.mozGetUserMedia(constrains).then(success);
    } else if (navigator.getUserMedia){
        //旧版API
        navigator.getUserMedia(constrains).then(success);
    }
}
var video = document.getElementById("video");
var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");

//成功的回调函数
function success(stream){
    //兼容webkit内核浏览器
    var CompatibleURL = window.URL || window.webkitURL;
    //将视频流设置为video元素的源
    video.src = CompatibleURL.createObjectURL(stream);
    //播放视频
    video.play();
}

//异常的回调函数
function error(error){
    console.log("访问用户媒体设备失败：",error.name,error.message);
}
if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia){
    //调用用户媒体设备，访问摄像头
    getUserMedia({audio: true,
        video:{width:480,height:320}
    },success,error);
} else {
    alert("你的浏览器不支持访问用户媒体设备");
}

//注册拍照按钮的单击事件
document.getElementById("capture").addEventListener("click",function(){
    //绘制画面
    context.drawImage(video,0,0,480,320);
});
    document.getElementById("sc").addEventListener("click", function () {
        var imgData=document.getElementById("canvas").toDataURL("image/png");
        //var data=imgData.substr(22);
      //将图像转换为base64数据
        var base64Data = imgData.split(",")[1];
        //$.post('${pageContext.request.contextPath}/Face',{'imgstr':base64Data});
        var content = {'imgstr':base64Data,'email':$("#email").val()};
        var contents = JSON.stringify(content);
        $.ajax({  
            type:"post", 
            headers: {
            	"content-Type":"application/json",
            	 },
            url:"${pageContext.request.contextPath}/Face",  
            contentType:"application/json",  
            data:contents,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 window.location.href ="${pageContext.request.contextPath}/NewMain";
                 }else{
                	 alert("因为您太丑，人脸实在无法识别，请密码登录吧！！！")
                 }
            }  
        });  
    });
</script>
</body>
</html>