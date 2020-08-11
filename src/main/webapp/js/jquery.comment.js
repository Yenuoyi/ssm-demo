(function($){
	function crateCommentInfo(obj){
		
		
		if(typeof(obj.comment_createtime) == "undefined" || obj.comment_createtime == ""){
			obj.comment_createtime = getNowDateFormat();
		}
		
		var el = "<div class='comment-info'><header><img src='"+obj.comment_name_url+"'></header>" +
				"<div class='comment-right'><h3>"+obj.comment_name+"</h3>"
				+"<div class='comment-content-header'><span><i class='glyphicon glyphicon-time'></i>"+obj.comment_createtime+"</span>";
		
		if(typeof(obj.address) != "undefined" && obj.browse != ""){
			el =el+"<span><i class='glyphicon glyphicon-map-marker'></i>"+obj.address+"</span>";
		}
		el = el+"</div><p class='content'>"+obj.comment_content+"</p><div class='comment-content-footer'><div class='row'><div class='col-md-10'>";
		
		if(typeof(obj.osname) != "undefined" && obj.osname != ""){
			el =el+"<span><i class='glyphicon glyphicon-pushpin'></i> 来自:"+obj.osname+"</span>";
		}
		
		if(typeof(obj.browse) != "undefined" && obj.browse != ""){
			el = el + "<span><i class='glyphicon glyphicon-globe'></i> "+obj.browse+"</span>";
		}
		
		el = el + "</div><div class='col-md-2'><span class='reply-btn'>回复</span>" +
		" <input style='display:none'; id='commentID' type='text' value='"+obj.comment_id+"'/>" +
		" <input style='display:none'; id='commentName' type='text' value='"+obj.comment_name+"'/>" +
		" <input style='display:none'; id='commentNameID' type='text' value='"+obj.comment_name_id+"'/>" +
				"</div></div></div><div class='reply-list'>";
		if(obj.reply != "" && obj.reply.length > 0){
			var arr = obj.reply;
			for(var j=0;j<arr.length;j++){
				var replyObj = arr[j];
				el = el+createReplyComment(replyObj);
			}
		}
		el = el+"</div></div></div>";
		return el;
	}
	
	//返回每个回复体内容
	function createReplyComment(reply){
		var replyEl = "<div class='reply'><div><a href='javascript:void(0)' class='replyname'>"+reply.reply_name+"</a>:<a href='javascript:void(0)'>@"+reply.reply_comment_name+"</a><span>"+reply.reply_content+"</span></div>"
						+ "<p><span>"+reply.reply_createtime+"</span> <span class='reply-list-btn'>回复</span>" +
						" <input style='display:none'; id='commentID' type='text' value='"+reply.reply_comment_id+"'/>" +
						" <input style='display:none'; id='commentName' type='text' value='"+reply.reply_name+"'/>" +
						" <input style='display:none'; id='commentNameID' type='text' value='"+reply.reply_name_id+"'/>" +
								"</p></div>";
		return replyEl;
	}
	function getNowDateFormat(){
		var nowDate = new Date();
		var year = nowDate.getFullYear();
		var month = filterNum(nowDate.getMonth()+1);
		var day = filterNum(nowDate.getDate());
		var hours = filterNum(nowDate.getHours());
		var min = filterNum(nowDate.getMinutes());
		var seconds = filterNum(nowDate.getSeconds());
		return year+"-"+month+"-"+day+" "+hours+":"+min+":"+seconds;
	}
	function filterNum(num){
		if(num < 10){
			return "0"+num;
		}else{
			return num;
		}
	}
	function replyClick(el){
		var commentID = el.parent().find("#commentID").val();
		var commentName = el.parent().find("#commentName").val();
		var commentNameID = el.parent().find("#commentNameID").val();
		el.parent().parent().append("<div class='replybox'><textarea cols='80' rows='50' placeholder='来说点什么....' class='mytextarea' ></textarea><span class='send'>发送</span></div>")
		.find(".send").click(function(){
			var content = $(this).prev().val();
			if(content != ""){
				var parentEl = $(this).parent().parent().parent().parent();
				var obj = new Object();
				obj.reply_name= sessionName; 
				if(el.parent().parent().hasClass("reply")){
					//console.log("1111");
					obj.reply_comment_name = el.parent().parent().find("a:first").text();
				}else{
					//console.log("2222");
					obj.reply_comment_name=parentEl.find("h3").text();
				}
				obj.reply_content=content;
				obj.reply_createtime = getNowDateFormat();
				var replyString = createReplyComment(obj);
				//发送到服务器
				var content = {"reply_two_id":twoID,"reply_three_id":threeID,"reply_content":content,"reply_comment_id":commentID,"reply_comment_name":commentName,"reply_comment_name_id":commentNameID};
				var contents = JSON.stringify(content);
				 $.ajax({  
			            type:"post", 
			            headers: {
			            	"content-Type":"application/json",
			            	 },
			            url:"Reply",  
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
				$(".replybox").remove();
				parentEl.find(".reply-list").append(replyString).find(".reply-list-btn:last").click(function(){alert("不能回复自己");});
			}else{
				alert("空内容");
			}
		});
	}
	
	
	$.fn.addCommentList=function(options){
		var defaults = {
			data:[],
			add:""
		}
		var option = $.extend(defaults, options);
		//加载数据
		if(option.data.length > 0){
			var dataList = option.data;
			var totalString = "";
			for(var i=0;i<dataList.length;i++){
				var obj = dataList[i];
				var objString = crateCommentInfo(obj);
				totalString = totalString+objString;
			}
			$(this).append(totalString).find(".reply-btn").click(function(){
				if($(this).parent().parent().find(".replybox").length > 0){
					$(".replybox").remove();
				}else{
					$(".replybox").remove();
					replyClick($(this));
				}
			});
			$(".reply-list-btn").click(function(){
				if($(this).parent().parent().find(".replybox").length > 0){
					$(".replybox").remove();
				}else{
					$(".replybox").remove();
					replyClick($(this));
				}
			})
		}
		
		//添加新数据
		if(option.add != ""){
			obj = option.add;
			var str = crateCommentInfo(obj);
			$(this).prepend(str).find(".reply-btn").click(function(){
				replyClick($(this));
			});
		}
	}
	
	
})(jQuery);