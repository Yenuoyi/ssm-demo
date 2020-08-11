<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta charset="UTF-8">
<title>人脸识别</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/js/webcam/cs.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/webcam/jquery.webcam.min.js"></script>
</head>
<body>
<div id="contentHolder">
<div class="panel panel-default">
  <div class="panel-heading">
  <h3>请保证你已在网站上传过你的清晰证件照，以及保证你的电脑安装了flash插件！</h3>
  <h3>如果没有flash插件可以使用360浏览器</h3>
  <h3>输入你的帐号，然后点击拍照,倒计时3s，系统自动上传，请等待0.5到1分钟进行验证，期间请勿进行任何操作！</h3></div>
  <div class="panel-body">
<input type="text" placeholder="请输入你的帐号！" id="email" name="email">
    <button class="play">拍照</button>
    <div id="status">倒计时</div>
    <table><tr align="center">
    <td align="center" width="320">
    <div id="webcam"></div>  
    </td></tr></table>    
    </div>
    </div>
    </div>  
</body>
<script>
    var w = 320, h = 240;                                       //摄像头配置,创建canvas
    var pos = 0, ctx = null, saveCB, image = [];
    var canvas = document.createElement("canvas");
    $("body").append(canvas);
    canvas.setAttribute('width', w);
    canvas.setAttribute('height', h);
    ctx = canvas.getContext("2d");
    image = ctx.getImageData(0, 0, w, h);
    $("#webcam").webcam({
        width: w,
        height: h,
        mode: "callback",                       //stream,save，回调模式,流模式和保存模式
        swffile: "http://yebingavtar.oss-cn-shenzhen.aliyuncs.com/webcam/jscam_canvas_only.swf",
        onTick: function(remain) { 
            if (0 == remain) {
                $("#status").text("拍照成功!");
            } else {
                $("#status").text("倒计时"+remain + "秒钟...");
            }
        },
        onSave: function(data){              //保存图像
            var col = data.split(";");
            var img = image;
            for(var i = 0; i < w; i++) {
                var tmp = parseInt(col[i]);
                img.data[pos + 0] = (tmp >> 16) & 0xff;
                img.data[pos + 1] = (tmp >> 8) & 0xff;
                img.data[pos + 2] = tmp & 0xff;
                img.data[pos + 3] = 0xff;
                pos+= 4;
            }
            if (pos >= 4 * w * h) {
                ctx.putImageData(img,0,0);      //转换图像数据，渲染canvas
                pos = 0;
                Imagedata=canvas.toDataURL().substring(22);  //上传给后台的图片数据
              //将图像转换为base64数据
                //var base64Data = Imagedata.split(",")[1];
                //$.post('${pageContext.request.contextPath}/Face',{'imgstr':base64Data});
                var content = {'imgstr':Imagedata,'email':$("#email").val()};
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
                    		 window.location.href ="http://119.23.51.148/Vertx/Welcome";
                         }else{
                        	 alert("因为您太丑，人脸实在无法识别，请密码登录吧！！！")
                         }
                    }  
                });  
            }
        },
        onCapture: function () {               //捕获图像
            webcam.save();      
        },
        debug: function (type, string) {       //控制台信息
            console.log(type + ": " + string);
        },
        onLoad: function() {                   //flash 加载完毕执行
            console.log('加载完毕！')
            var cams = webcam.getCameraList();
            for(var i in cams) {
                $("body").append("<p>" + cams[i] + "</p>");
            }
        }
    });  

    $(".play").click(function(){
        webcam.capture(3);        //拍照，参数3是倒计时
    });

</script>
</html>