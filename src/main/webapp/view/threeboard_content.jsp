<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1" content="text/html; charset=UTF-8">
<title>章节|BBS</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<style>
p {
letter-spacing : 1px;
font-size : 12pt;
line-height : 20px;
overflow: hidden;
display: -webkit-box;
-webkit-line-clamp: 3;
-webkit-box-orient: vertical;
}
</style>
</head>
<body>
<%@ include file="header.jsp" %>
<!-- 课程章节列表 -->
<div class="panel panel-primary">
  <div class="panel-heading">
${one.getOne_title()}
${two.getTwo_title()}
</div>
  <div class="panel-body">
    <div class="table-responsive">
    
  <!-- 热门章节 -->
  <div class="panel panel-info">
  <div class="panel-heading">热门章节</div>
  <table class="table">
  <tr>
  <td width="150" align="center"><c:out value="${topthree.three_title}"></c:out></td>
  <td><p><c:out value="${topthree.three_profile}"></c:out></p></td>
  <td align="center" width="200">
   <a target="_blank" href="${pageContext.request.contextPath}/Content?one_id=${one.getOne_id()}&two_id=${two.getTwo_id()}
   &three_id=<c:out value="${topthree.three_id}"></c:out>">
   <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
   </td>
  </tr>
    </table>
  </div>
    
    <!-- 普通章节 -->
  <table class="table" id="table_id_example">
  <thead>
  <tr>
   <td width="150" align="center">章节名</td>
   <td>章节简介</td>
   <td width="200" align="center">进入讨论</td>
  </tr>
  </thead>
  <tbody>

  <c:forEach items="${two_three}" var="node"> 
  <tr>
  <td width="150" align="center"><c:out value="${node.three_title}"></c:out></td>
  <td><p><c:out value="${node.three_profile}"></c:out></p></td>
  <td align="center" width="200">
   <a target="_blank" href="${pageContext.request.contextPath}/Content?one_id=${one.getOne_id()}&two_id=${two.getTwo_id()}
   &three_id=<c:out value="${node.three_id}"></c:out>">
   <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
   </td>
  </tr>
  </c:forEach>
  </tbody>
</table>
</div>
  </div>
</div>
<!--第三步：初始化Datatables-->
<script type="text/javascript">
$(document).ready(function(){
    $('#table_id_example').DataTable({
    	 "oLanguage": { //国际化配置 
             "sProcessing" : "正在获取数据，请稍后...",   
             "sLengthMenu" : "显示 _MENU_ 条",   
             "sZeroRecords" : "这个老师比较懒，啥也没有！",   
             "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",   
             "sInfoEmpty" : "记录数为0",   
             "sInfoFiltered" : "(全部记录数 _MAX_ 条)",   
             "sInfoPostFix" : "",   
             "sSearch" : "搜索",   
             "sUrl" : "",   
             "oPaginate": {   
                 "sFirst" : "第一页",   
                 "sPrevious" : "上一页",   
                 "sNext" : "下一页",   
                 "sLast" : "最后一页"   
             } 
         }
    });
});
</script>
</body>
</html>