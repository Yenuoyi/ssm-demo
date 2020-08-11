/*
列表分页脚本 最后更新时间2017.12.10-18.49
$(*).Page.({type},PageClass);
(*)可以是任何HTML元素容器
{type} 是Page的元素属性合集 下列是可用的属性：
			Page:1,  动态当前页 默认1
			Pages:false, 总页数 必须配置 否则将不会有页码出现
			PageNum:5,   显示可点击的页码数量 默认5 建议使用奇数 较美观
			ActiveClass:"paging-selecte",  以点击的页码的css样式 需要填写否则无样式
			Jump:true,  跳转功能 true为开启 false为关闭
			JumpVal:"跳转",  跳转功能显示的文字 建议越短越好
			EndPage:true, 开启显示首页功能
			HomePage:true, 开启显示尾页功能
			EhCondition:10, 首尾页按钮在当前X页之后显示 默认为10， 0为一直显示
			TopDownPage:true, 开启上下页功能
			PageTitle:"第x页", 页码title文字，x为页码数字，参照格式： 这是第x页，x ，点击前往第x页信息 必须有x（注意是小写）
			TopText:"«",  上页按钮显示的文字
			TopTitle:"上一页", 上一页title属性要显示的文字
			DownText:"»", 下页按钮显示的文字
			DownTitle:"下一页", 下一页title属性要显示的文字
			PageOn:false,  点击页码事件 编写格式function(e,d){ 这里写入你的代码,e为回传的当前页id 比如当前页为8 e回传一个8,d返回总页数}
			JumpOn:false,  点击跳转按钮事件 编写格式function(e,d){ 这里写入你的代码,e为回传的当前页id 比如当前页为8 e回传一个8,d返回总页数}
			PageCustom:"page", 页码的自定义属性，用于记录当前页，如果已经占用请更改，没有不要随意改动
			Alert:false  用于提醒功能弹出的提醒层，如果有第三方插件可以直接写成function(e,d){这里写入你的代码} 执行你的操作

PageClass 是一个预设的css标签样式，回传一个true开启

注意 需要区分大小写
*/

(function($){
jQuery.fn.extend({
	Page:function(type,PageClass){
		var $Page=$("<ul>");
		var $Jump=$("<ul>");
		var PageSon="<li>";
		var e=$(this);
		var Config = $.extend(true,{
			Page:1,
			Pages:false,
			PageNum:5,
			ActiveClass:"page-click",
			Jump:true,
			JumpVal:"跳转",
			EndPage:true,
			HomePage:true,
			EhCondition:10,
			TopDownPage:true,
			PageTitle:"第x页",
			TopText:"«",
			TopTitle:"上一页",
			DownText:"»",
			DownTitle:"下一页",
			PageOn:false,
			JumpOn:false,
			PageCustom:"page",
			Alert:false,
		},type);

		if(PageClass){style();}
		
		Config.Page=Config.Page>Config.Pages?Config.Pages:Config.Page;

		var PageR=parseInt(Config.Page)+parseInt(Config.PageNum/2);
			PageR=PageR>Config.Pages?Config.Pages:PageR;
		
		var PageL=parseInt(Config.Page)-parseInt(Config.PageNum/2);
			PageL=PageL<1?1:PageL;
		
		for(i=PageL;i<PageR+1;i++){
			if(i==Config.Page){
				$Page.append($(PageSon).addClass(Config.ActiveClass).text(i).attr("title",Config.PageTitle.replace(/x/g,i)));
			}else{
				$Page.append($(PageSon).attr(Config.PageCustom,i).text(i).attr("title",Config.PageTitle.replace(/x/g,i)));
			}
		}
		if(Config.EndPage && (Config.Pages>Config.EhCondition-1 && Config.Page<(Config.Pages-parseInt(Config.PageNum/2)))){
			$Page.append($(PageSon).attr(Config.PageCustom,Config.Pages).text(".."+Config.Pages).attr("title",Config.PageTitle.replace(/x/g,Config.Pages)));
			}
		if(Config.HomePage && Config.Page>Config.EhCondition-1){
		   $Page.prepend($(PageSon).attr(Config.PageCustom,1).text("1..").attr("title",Config.PageTitle.replace(/x/g,"1")));
		   }
		
		if(Config.TopDownPage && Config.Pages){
			
		if(Config.Page!=1){
			$Page.prepend($(PageSon).attr(Config.PageCustom,parseInt(Config.Page)-1).text(Config.TopText).attr("title",Config.TopTitle));
		}
			
		if(Config.Page!=Config.Pages){
			$Page.append($(PageSon).attr(Config.PageCustom,parseInt(Config.Page)+1).text(Config.DownText).attr("title",Config.DownTitle));
		}
			
		}
		$(e).html("").append($Page);
		
		if(Config.Jump){$(e).append(
			$Jump.append($("<input>").attr("type","text")).append($("<input>").attr("type","button").val(Config.JumpVal).attr("title",Config.JumpVal))
		)}
		
		$Page.find("*[Page]").click(function(){
		var pageId=$(this).attr(Config.PageCustom);
		$(e).Page({
			Page:pageId,
			Pages:Config.Pages,
			PageNum:Config.PageNum,
			ActiveClass:Config.ActiveClass,
			Jump:Config.JumpJump,
			JumpVal:Config.JumpVal,
			EndPage:Config.EndPage,
			HomePage:Config.HomePage,
			EhCondition:Config.EhCondition,
			TopDownPage:Config.TopDownPage,
			TopText:Config.TopText,
			DownText:Config.DownText,
			PageOn:Config.PageOn,
			JumpOn:Config.JumpOn,
			PageCustom:Config.PageCustom,
			Alert:Config.Alert,
	},PageClass);
			if(Config.PageOn){Config.PageOn(pageId,Config.Pages);} else{alert("注意！您未绑定事件");}
			
	});
		
		
	$Jump.find("*[type='text']").bind("mouseleave keydown keyup",function(){
	if(isNaN($(this).val())){
		$(this).val("");
		}
	});
		
	$Jump.find("*[type='button']").click(function(){
		var vla=$(this).prevAll().val();
		if(!vla ||isNaN(vla)){vla.val("");return false;}
		if(vla>Config.Pages){if(Config.Alert){Config.Alert(vla,Config.Pages);}else{alert("您输入的值大于总页数");}return false;}
		$(e).Page({
			Page:vla,
			Pages:Config.Pages,
			PageNum:Config.PageNum,
			ActiveClass:Config.ActiveClass,
			Jump:Config.JumpJump,
			JumpVal:Config.JumpVal,
			EndPage:Config.EndPage,
			HomePage:Config.HomePage,
			EhCondition:Config.EhCondition,
			TopDownPage:Config.TopDownPage,
			TopText:Config.TopText,
			DownText:Config.DownText,
			PageOn:Config.PageOn,
			JumpOn:Config.JumpOn,
			PageCustom:Config.PageCustom,
			Alert:Config.Alert,
	},PageClass);
		if(Config.JumpOn){Config.JumpOn(vla,Config.Pages);}  else{alert("注意！您未绑定事件");}
	});

	function style(){
		$Page.addClass("pa1");
		$Jump.addClass("pa2");
		var page_=PageSon.substring(1,PageSon.length-1);
		if($("style[page]").length>0){return false;}
		var Pagecss=".pa1{float:left;display:inline;box-sizing: border-box;user-select:none;}";
		var Jumpcss=".pa2{padding-left: 20px;display:inline;box-sizing: border-box;}";
		var Page_Son=".pa1 "+page_+"{transition: all 0.30s ease-in-out;-webkit-transition: all 0.30s ease-in-out;-moz-transition: all 0.30s ease-in-out;-o-transition: all 0.30s ease-in-out;-ms-transition: all 0.30s ease-in-out;display:inline;list-style-type:none;padding: 6px 12px;cursor: pointer;border: #000 1px solid;background-color: #fff;color: #000;margin: 2px;}.pa1 "+page_+":hover{background-color:#000; color:#fff;}";
		var Page_click=".page-click{background-color: #000!important;color: #fff!important;font-weight: bold!important;}.page-click:hover{background-color:#000!important;color: #fff!important;}";
		var Jumpbtn=".pa2 input[type='button']{margin-top: 9px;height:30px;background-color: #fff;cursor: pointer;display: inline-block;margin-top: 0px;border:1px solid #000;border-left: none;}";
		var Jumptext=".pa2 input[type='text']{margin-top: 10px;display: inline-block;border:1px solid #000;height:26px;width: 50px;border-right: none;}";
			
		$("head").append("<style page=1>"+Jumpcss+Jumpbtn+Jumptext+Pagecss+Page_Son+Page_click+"</style>");
	
		
		
	}}
});
})(jQuery);

