<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>评论|BBS</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.comment.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Pagination/pages.js" ></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">

</head>
<style>
	.container{
		width: 1000px;
	}
	.commentbox{
		width: 900px;
		margin: 20px auto;
	}
	.mytextarea {
	    width: 100%;
	    overflow: auto;
	    word-break: break-all;
	    height: 100px;
	    color: #000;
	    font-size: 1em;
	    resize: none;
	}
	.comment-list{
		width: 900px;
		margin: 20px auto;
		clear: both;
		padding-top: 20px;
	}
	.comment-list .comment-info{
		position: relative;
		margin-bottom: 20px;
		margin-bottom: 20px;
		border-bottom: 1px solid #ccc;
	}
	.comment-list .comment-info header{
		width: 10%;
		position: absolute;
	}
	.comment-list .comment-info header img{
		width: 100%;
		border-radius: 50%;
		padding: 5px;
	}
	.comment-list .comment-info .comment-right{
		padding:5px 0px 5px 11%; 
	}
	.comment-list .comment-info .comment-right h3{
		margin: 5px 0px;
	}
	.comment-list .comment-info .comment-right .comment-content-header{
		height: 25px;
	}
	.comment-list .comment-info .comment-right .comment-content-header span,.comment-list .comment-info .comment-right .comment-content-footer span{
		padding-right: 2em;
		color: #aaa;
	}
	.comment-list .comment-info .comment-right .comment-content-header span,.comment-list .comment-info .comment-right .comment-content-footer span.reply-btn,.send,.reply-list-btn{
		cursor: pointer;
	}
	.comment-list .comment-info .comment-right .reply-list {
		border-left: 3px solid #ccc;
		padding-left: 7px;
	}
	.comment-list .comment-info .comment-right .reply-list .reply{
		border-bottom: 1px dashed #ccc;
	}
	.comment-list .comment-info .comment-right .reply-list .reply div span{
		padding-left: 10px;
	}
	.comment-list .comment-info .comment-right .reply-list .reply p span{
		padding-right: 2em;
		color: #aaa;
	}
	<!-- 分页 -->
	.paging{
		display: inline-block;
		background: #f9f9f9;
		padding: 15px;
		border-radius: 5px;
		border: 1px solid #ddd;
	}
	.pp{
		display: inline-block;
		background: #f9f9f9;
		padding: 10px;
		border-radius: 5px;
		border: 1px solid #ddd;
	}
	.paging ul{
		padding: 0;
		margin: 0;
		display: inline-block;
		float: center;
	}
	.paging li{
		display: inline-block;
		float: center;
		padding: 6px 12px;
		cursor: pointer;
	}
	.paging li:hover{
		background-color: #ddd;
	}
	.paging-selecte{
		background-color: #4CAF50!important;
		color: #fff!important;
	}
	.paging-selecte:hover{
		background-color: #4CAF50!important;
		color: #fff!important;
	}
	.paging input[type='text'],.paging input[type='button']{
		display: inline-block;
		margin-top: 0px;
		border:1px solid #ccc;
	}
	.paging input[type='text']{
		height:29px;
		width: 40px;
		border-right: none;
	}
	.paging input[type='button']{
		height:29px;
		border-left: none;
		background-color: #fff;
		cursor: pointer;
	}
	.paging ul:nth-child(2){
		margin-left: 10px;
	}
	
	
	.a{ 
		font-family: "Microsoft YaHei", "微软雅黑", "Lantinghei SC", "Open Sans", Arial, "Hiragino Sans GB", "STHeiti", "WenQuanYi Micro Hei", SimSun, sans-serif;
	text-align: center;
	margin: 0 200px;
	margin-top: 70px;
	background-color: #fff;
	min-height: 300px;
	border-radius: 10px;
	font-size: 16px;
	padding: 10px;
	box-sizing:border-box;
	}
	.a p{
		text-align: left;
		margin: 15px 100px;
		line-height:30px;
</style>
<body>
<%@ include file="header.jsp" %>
<div class="panel panel-primary">
  <div class="panel-heading">
  <!-- 课程章节列表 -->
${one.getOne_title()}
${two.getTwo_title()}
${three.getThree_title()}
  </div>
  <div class="panel-body">
    <div class="table-responsive">
  <table class="table" id="myTable">
  <tr>
   <td>章节学习讨论简介</td>
  </tr>
  <tr>
  <td>${three.getThree_profile()}</td>
  </tr>
</table>
</div>
</div>
</div>

<div class="panel panel-primary">
  <!-- Default panel contents -->
  <div class="panel-heading">这些是精彩的评论哦！</div>
<div class="container">
	<div class="commentbox">
		<textarea cols="80" rows="50" placeholder="来说几句吧......" class="mytextarea" id="content"></textarea>
		<div class="btn btn-info pull-right" id="comment">评论</div>
	</div>
	<div class="comment-list"></div>
	<!-- 分页 -->
	<div style="width: 100%; background-color: #fff; padding: 5px; text-align: center; margin-top: 10px;box-sizing:border-box;">
	<div class="paging"></div>
	</div>
</div>
</div>
<script>
$(".paging").Page({
	Pages:parseInt('${threeContentTotal}'),//总页数
	TopDownpage:true, // 是否开启上一页 下一页功能 true未开启 false为关闭 默认开启
	pageNum:5, // 页码数 共显示多少个页码 默认5 建议设置 奇数 比较美观
	PageOn:function(e){
        var curpage = JSON.stringify({"comment_three_id": '${three.getThree_id()}',"curpage":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/ContentPagination",  
		    contentType:"application/json", 
		    data:curpage,
		    success:function(result) {
            $(".comment-list").html("");
            var commentJson = JSON.parse(result);
            var obj = commentJson.comment;
		    	$(".comment-list").addCommentList({data:obj,add:""});
        }
		    })
	},
	JumpOn:function(e){
		var page = JSON.stringify({"comment_three_id": '${three.getThree_id()}',"page":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/ContentPagination",  
		    contentType:"application/json", 
		    data:page,
		    success:function(result) {
            $(".comment-list").html("");
            var commentJson = JSON.parse(result);
            var obj = commentJson.comment;
		    	$(".comment-list").addCommentList({data:obj,add:""});
        }
		    })
	},
	ActiveClass:"paging-selecte",
});
$(".pp").Page({
	Pages:parseInt('${threeContentTotal}'),//总页数
	TopDownpage:true, // 是否开启上一页 下一页功能 true未开启 false为关闭 默认开启
	pageNum:5, // 页码数 共显示多少个页码 默认5 建议设置 奇数 比较美观
	PageOn:function(e){
        var curpage = JSON.stringify({"comment_three_id": '${three.getThree_id()}',"curpage":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/ContentPagination",  
		    contentType:"application/json", 
		    data:curpage,
		    success:function(result) {
            $(".comment-list").html("");
            var commentJson = JSON.parse(result);
            var obj = commentJson.comment;
		    	$(".comment-list").addCommentList({data:obj,add:""});
        }
		    })
	},
	JumpOn:function(e){
		var page = JSON.stringify({"comment_three_id": '${three.getThree_id()}',"page":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/ContentPagination",  
		    contentType:"application/json", 
		    data:page,
		    success:function(result) {
            $(".comment-list").html("");
            var commentJson = JSON.parse(result);
            var obj = commentJson.comment;
		    	$(".comment-list").addCommentList({data:obj,add:""});
        }
		    })
	},
	ActiveClass:"paging-selecte",
});	
	//Pages:200,
	//Alert:function(){confirm("注意您，没有绑定对应的点击事件，此条为自定义弹窗")}
//},true);
</script>
<script type="text/javascript">
var sessionName="${ sessionScope.name}";
var threeID = "${three.getThree_id()}";
var twoID = "${two.getTwo_id()}";
<%
Object mess =  request.getAttribute("three_content");
 %>
 <% if (mess != null) { %>
       var commentJson= <%=mess%>
<% } %>
var obj = commentJson.comment;
	//初始化数据
	$(function(){
		$(".comment-list").addCommentList({data:obj,add:""});
		$("#comment").click(function(){
			var obj = new Object();
			obj.comment_name_url="${ sessionScope.headpicurl}";
			obj.comment_name="${ sessionScope.name}";
			obj.comment_content=$("#content").val();
			obj.reply="";
			var content = {"comment_two_id":'${two.getTwo_id()}',"comment_three_id":'${three.getThree_id()}',"comment_content":$("#content").val()};
			var contents = JSON.stringify(content);
			 $.ajax({  
		            type:"post", 
		            headers: {
		            	"content-Type":"application/json",
		            	 },
		            url:"${pageContext.request.contextPath}/Comment",  
		            contentType:"application/json",  
		            data:contents,  
		            success:function(result){
		            	 if (result==1) {//根据返回值进行跳转
		                     alert("评论成功！")
		                 }else{
		                	 alert("失败")
		                 }
		            }  
		        });  
			$(".comment-list").addCommentList({data:[],add:obj});
		});
	})
</script>
</body>
</html>