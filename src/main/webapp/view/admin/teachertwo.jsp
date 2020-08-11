<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Pagination/pages-min.js" ></script>
<title>管理所属课程|BBS</title>
<style type="text/css">
p {
letter-spacing : 1px;
font-size : 15px;
line-height : 20px;
overflow: hidden;
display: -webkit-box;
-webkit-line-clamp: 3;
-webkit-box-orient: vertical;
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
		height:30px;
		width: 40px;
		border-right: none;
	}
	.paging input[type='button']{
		height:30px;
		border-left: none;
		background-color: #fff;
		cursor: pointer;
	}
	.paging ul:nth-child(2){
		margin-left: 10px;
	}
</style>
</head>
<body>
<%@ include file="adminheader.jsp" %>
<script>
//删除指定课程
function deleteview(two_id_id){
	var id=two_id_id;
	var two = {"two_id":id};
	var twoboard = JSON.stringify(two);
        $.ajax({  
        	type: 'POST', 
            headers: {
            	"content-Type":"application/json",
            	 },
           //将token存储发送请求头
           //beforeSend: function(request) {
          // request.setRequestHeader("two_teacher_name", user);
           // },
            url:"${pageContext.request.contextPath}/Admin/DeleteTwo",  
            contentType:"application/json",  
            data:twoboard,  
            success:function(result){
            	 if (result==1) {//根据返回值进行跳转
            		 alert("删除成功!")
                     window.location.reload(); 
                 }else{
                	 if(result==0){
                		 alert("删除失败，建议重新登录再次尝试！");
                	 }else{
                		 alert("系统异常请联系管理员！");
                	 }
                 }
            },
            error:function(result){
            	alert("更改异常，请联系管理员！")
            }
        });  
    } 
</script>
<div class="panel panel-primary">
  <div class="panel-heading">
     课程管理
  </div>
  <div class="panel-body">
<table class="table" id="table_id_example">
  <thead>
  <tr>
  <td width="8%">课程id</td>
  <td width="11%">课程名</td>
  <td width="11%">所属教师</td>
  <td width="8%">教师id</td>
  <td width="8%">专业代码</td>
  <td width="40%">简介</td>
  <td width="8%">评论统计</td>
  <td width="15%">编辑</td>
  </tr>
  </thead>
  <!-- 实现分页查看 -->
  <tbody>
  <c:forEach items="${alltwo}" var="node">
  <tr>
  <td width="8%"><c:out value="${node.two_id }"></c:out></td>
  <td width="11%"><c:out value="${node.two_title }"></c:out></td>
  <td width="11%"><c:out value="${node.two_teacher_name }"></c:out></td>
  <td width="8%"><c:out value="${node.two_teacher_id }"></c:out></td>
  <td width="8%"><c:out value="${node.two_one_id }"></c:out></td>
  <td width="40%"><p><c:out value="${node.two_profile }"></c:out></p></td>
  <td width="8%"><c:out value="${node.two_statistics}"></c:out></td>
  <td width="15%">
  <form action="${pageContext.request.contextPath}/Admin/Threeview" method="post">
  <input value="${node.two_id }" type="hidden" id="two_id" name="two_id">
  <button>编辑</button>
  </form>
  <button type="button" onclick="deleteview(${node.two_id })">删除</button>
  </td>
  </tr>
  </c:forEach>
  </tbody>
</table>
</div>
</div>
<!-- 分页 -->
	<div style="width: 100%; background-color: #fff; padding: 5px; text-align: center; margin-top: 10px;box-sizing:border-box;">
	<div class="paging"></div>
	</div>
	<p></p>
<script type="text/javascript">
$(".paging").Page({
	Pages:parseInt('${alltwototal}'),//总页数
	TopDownpage:true, // 是否开启上一页 下一页功能 true未开启 false为关闭 默认开启
	pageNum:5, // 页码数 共显示多少个页码 默认5 建议设置 奇数 比较美观
	PageOn:function(e){
        var curpage = JSON.stringify({"curpage":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/Admin/TwobardTeacherIDPage",  
		    contentType:"application/json", 
		    data:curpage,
		    success:function(result) {
                var addview = "<table class='table' id='table_id_example'><thead><tr>"+
                "<td width='8%'>课程id</td>"+
                "<td width='11%'>课程名</td>"+
                "<td width='11%'>所属教师</td>"+
                "<td width='8%'>教师id</td>"+
                "<td width='8%'>专业代码</td>"+
                "<td width='40%'>简介</td>"+
                "<td width='8%'>评论统计</td>"+
                "<td width='15%'>编辑</td></tr>"+
               "</thead><tbody>";
               var object = JSON.parse(result);
                for(i=0;i<object.length; i++){
                	addview = addview+"<tr>"+
                	"<td width='8%'><h4>"+object[i].two_id+"</h4></td>"+
                	"<td width='11%'>"+object[i].two_title+"</td>"+
                	"<td width='11%'><p>"+object[i].two_teacher_name+"</p></td>"+
                	"<td width='8%'>"+object[i].two_teacher_id+"</td>"+
                	"<td width='8%'>"+object[i].two_one_id+"</td>"+
                	"<td width='40%'>"+object[i].two_profile+"</td>"+
                    "<td width='8%'>"+object[i].two_statistics+"</td>"+
                    "<td width='15%'>"+
                    "<form action='${pageContext.request.contextPath}/Admin/Threeview' method='post'>"+
                    "<input value='"+object[i].two_id+"' type='hidden' id='two_id' name='two_id'>"+
                    "<button>编辑</button>"+
                    "</form>"+
                    "<button type='button' onclick='deleteview("+object[i].two_id+")'>删除</button>"
                    +"</td></tr>";
                }
                addview = addview+"</tbody></table>";
		    	$(".panel-body").html(addview);
		    }
		    })
	},
	JumpOn:function(e){
		var curpage = JSON.stringify({"curpage":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/Admin/TwobardTeacherIDPage",  
		    contentType:"application/json", 
		    data:curpage,
		    success:function(result) {
                var addview = "<table class='table' id='table_id_example'><thead><tr>"+
                "<td width='8%'>课程id</td>"+
                "<td width='11%'>课程名</td>"+
                "<td width='11%'>所属教师</td>"+
                "<td width='8%'>教师id</td>"+
                "<td width='8%'>专业代码</td>"+
                "<td width='40%'>简介</td>"+
                "<td width='8%'>评论统计</td>"+
                "<td width='15%'>编辑</td></tr>"+
               "</thead><tbody>";
               var object = JSON.parse(result);
                for(i=0;i<object.length; i++){
                	addview = addview+"<tr>"+
                	"<td width='8%'><h4>"+object[i].two_id+"</h4></td>"+
                	"<td width='11%'>"+object[i].two_title+"</td>"+
                	"<td width='11%'><p>"+object[i].two_teacher_name+"</p></td>"+
                	"<td width='8%'>"+object[i].two_teacher_id+"</td>"+
                	"<td width='8%'>"+object[i].two_one_id+"</td>"+
                	"<td width='40%'>"+object[i].two_profile+"</td>"+
                    "<td width='8%'>"+object[i].two_statistics+"</td>"+
                    "<td width='15%'>"+
                    "<form action='${pageContext.request.contextPath}/Admin/Threeview' method='post'>"+
                    "<input value='"+object[i].two_id+"' type='hidden' id='two_id' name='two_id'>"+
                    "<button>编辑</button>"+
                    "</form>"+
                    "<button type='button' onclick='deleteview("+object[i].two_id+")'>删除</button>"
                    +"</td></tr>";
                }
                addview = addview+"</tbody></table>";
		    	$(".panel-body").html(addview);
		    }
		    })
	},
	ActiveClass:"paging-selecte",
});
</script>
</body>
</html>