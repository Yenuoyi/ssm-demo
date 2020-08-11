<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>课程|BBS</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Pagination/pages-min.js" ></script>
<style>
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
<%@ include file="header.jsp" %>
<div class="panel panel-primary">
  <div class="panel-heading">
  <!-- 课程列表 -->
  ${one.getOne_title()}
  </div>
  <div class="panel-body">
    <!-- 内容 -->
  <table class="table" id="table_id_example">
  <thead>
  <tr>
   <td width="10%">课程名</td>
   <td width="6%">教师</td>
   <td width="57%">课程简介</td>
   <td width="12%" align="center">最近更新时间</td>
   <td align="center" width="15%">立即查看</td>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${one_two}" var="node"> 
  <tr>
   <td width="10%"><h4><c:out value="${node.two_title}"></c:out></h4></td>
   <td width="6%"><c:out value="${node.two_teacher_name}"></c:out></td>
   <td width="57%"><p><c:out value="${node.two_profile}"></c:out></p></td>
   <td width="12%" align="center"><c:out value="${node.two_update_time}"></c:out></td>
   <td align="center" width="15%">
   <a target="_blank" href="${pageContext.request.contextPath}/Threeboard_content?two_id=${node.two_id}">
   <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
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
	Pages:parseInt('${one_two_total}'),//总页数
	TopDownpage:true, // 是否开启上一页 下一页功能 true未开启 false为关闭 默认开启
	pageNum:5, // 页码数 共显示多少个页码 默认5 建议设置 奇数 比较美观
	PageOn:function(e){
        var curpage = JSON.stringify({"twoOneId": '${one.getOne_id()}',"curpage":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/TwoboardPage",  
		    contentType:"application/json", 
		    data:curpage,
		    success:function(result) {
                var addview = "<table class='table' id='table_id_example'><thead><tr>"+
                "<td width='10%'>课程名</td>"+
                "<td width='6%'>教师</td>"+
                "<td width='57%'>课程简介</td>"+
                "<td width='12%' align='center'>最近更新时间</td>"+
                "<td align='center' width='15%'>立即查看</td></tr>"+
               "</thead><tbody>";
               var object = JSON.parse(result);
                for(i=0;i<object.length; i++){
                	addview = addview+"<tr>"+
                	"<td width='10%'><h4>"+object[i].two_title+"</h4></td>"+
                	"<td width='6%'>"+object[i].two_teacher_name+"</td>"+
                	"<td width='57%'><p>"+object[i].two_profile+"</p></td>"+
                	"<td width='12%'align='center'>"+object[i].two_update_time+"</td>"+
                	"<td width='15%' align='center'>"+
                	"<a target='_blank' href='${pageContext.request.contextPath}/Threeboard_content?two_id="+object[0].two_id+"'>"+
                	"<span class='glyphicon glyphicon-eye-open' aria-hidden='true'></span></a>"+
                	"</td></tr>"
                }
                addview = addview+"</tbody></table>";
		    	$(".panel-body").html(addview);
		    }
		    })
	},
	JumpOn:function(e){
		var curpage = JSON.stringify({"twoOneId": '${one.getOne_id()}',"curpage":e});
		$.ajax({  
        	type:"post", 
		    headers: {
		    "content-Type":"application/json",
		    },
		    url:"${pageContext.request.contextPath}/TwoboardPage",  
		    contentType:"application/json", 
		    data:curpage,
		    success:function(result) {
                var addview = "<table class='table' id='table_id_example'><thead><tr>"+
                "<td width='10%'>课程名</td>"+
                "<td width='6%'>教师</td>"+
                "<td width='57%'>课程简介</td>"+
                "<td width='12%'>最近更新时间</td>"+
                "<td align='center' width='15%'>立即查看</td></tr>"+
               "</thead><tbody>";
               var object = JSON.parse(result);
                for(i=0;i<object.length; i++){
                	addview = addview+"<tr>"+
                	"<td width='10%'>"+object[i].two_title+"</td>"+
                	"<td width='6%'>"+object[i].two_teacher_name+"</td>"+
                	"<td width='57%'><p>"+object[i].two_profile+"</p></td>"+
                	"<td width='12%'>"+object[i].two_update_time+"</td>"+
                	"<td width='15%' align='center'>"+
                	"<a target='_blank' href='${pageContext.request.contextPath}/Threeboard_content?two_id="+object[0].two_id+"'>"+
                	"<span class='glyphicon glyphicon-eye-open' aria-hidden='true'></span></a>"+
                	"</td>"+
                	"</tr>"
                }
                addview = addview+
                +"</tbody></table>";
		    	$(".twolist").html(addview);
		    }
		    })
	},
	ActiveClass:"paging-selecte",
});
</script>
</body>
</html>