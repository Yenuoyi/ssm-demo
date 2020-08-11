package com.ye.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ye.domain.OneboardBean;
import com.ye.domain.ThreeboardBean;
import com.ye.domain.TwoboardBean;
import com.ye.redis.RedisGetUsername;
import com.ye.service.OneboardService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.util.NewsUtil;
/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SimpleREST extends AbstractVerticle {
	//先加载spring实例
	private final static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:ApplicationContext-dao.xml","classpath:ApplicationContext-service.xml"});
	@Autowired
    private OneboardService oneboardService;
	@Autowired
    private TwoboardService twoboardService;
	@Autowired
    private ThreeboardService threeboardService;
	@Autowired
	private RedisGetUsername redisGetUsername;
  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
	  Vertx vertx = Vertx.vertx();
      vertx.deployVerticle(new SimpleREST());
  }

  @Override
  public void start() {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route().handler(CookieHandler.create());
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
    router.get("/Vertx/:Welcome").handler(this::handleGetProduct);

    vertx.createHttpServer().requestHandler(router::accept).listen(8081);
  }

  //获取用户名处理器
  private String handleGetSession(RoutingContext routingContext){
	  redisGetUsername = (RedisGetUsername)applicationContext.getBean("redisGetUsername");
	  HttpServerRequest req = routingContext.request();
      String userIP = req.remoteAddress().host()+req.getHeader("User-Agent");
      String cnt = redisGetUsername.getUsername(userIP);
      return cnt;
  }
  //主页面逻辑处理器
  private void handleGetProduct(RoutingContext routingContext){
	  //获取session
	  String username = handleGetSession(routingContext);
    oneboardService =(OneboardService)applicationContext.getBean("oneboardServiceImpl");
    twoboardService = (TwoboardService)applicationContext.getBean("twoboardServiceImpl");
    threeboardService = (ThreeboardService)applicationContext.getBean("threeboardServiceImpl");
  //需要返回到界面的参数
    //今日头条接口
		NewsUtil  news = NewsUtil.getInstance();
		List<TwoboardBean> twoboardFiveList = null;
		List<TwoboardBean> twoboardTenList = null;
		List<ThreeboardBean> threeboardFiveList = null;
		List<OneboardBean> oneboard_list = null;
		try{
		//获取所有的专业
		oneboard_list = oneboardService.getAllOneboard();
		//编写统计前五名课程方法
		twoboardFiveList = twoboardService.gettwo_statistics_five();
		//获取最新的前十名课程
		twoboardTenList = twoboardService.getTwo_ten();
		//编写统计前五名章节方法
		threeboardFiveList = threeboardService.getthree_statistics_five();
		}catch(Exception e){
			e.printStackTrace();
		}
      //自定义h5页面
      StringBuffer responseContent = new StringBuffer();
      responseContent.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
      responseContent.append("<html><head>"
      		+ "<meta http-equiv='Content-Type' name='viewport' content='text/html; charset=UTF-8'>"
      		+ "<title>Hello|BBS</title>"
      		+ "<link rel='icon' type='image/png' href='http://yebingavtar.oss-cn-shenzhen.aliyuncs.com/BBSCommonFile/favicon.jpg' />"
      		+ "<link rel='stylesheet' href='https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css' "
      		+ "integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u' crossorigin='anonymous'>"
      		+ "<script src='https://code.jquery.com/jquery-3.2.1.min.js'></script>"
      		+ "<script src='https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js' "
      		+ "integrity='sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa' crossorigin='anonymous'></script>"
      		+ "<style>p {"
      		+ "overflow: hidden;display: -webkit-box;-webkit-line-clamp: 3;-webkit-box-orient: vertical;"
      		+ "font-family: 'Arial','Microsoft YaHei','黑体','宋体',sans-serif;}div.bodyleftmid {"
      		+ "background-color: #FCFCFC;float:left;width: 410px;height: 160px;border: 1px solid #EEE8CD;"
      		+ "padding: 2px; margin: 5px;}"
      		+ "div.imga{ float:left;margin: 5px;width:100px; height:140px;overflow:hidden} "
      		+ "div.imga img{max-width:100px;_width:expression(this.width > 100 ? '100px' : this.width);"
      		+ "padding-top: 2px;padding-bottom: 2px;padding-left: 2px;padding-rgiht: 8px;} "
      		+ "div.pad {padding-top: 10px;padding-left: 130px;}"
      		+ "</style></head>"
      		+ "<body>"
      		+ "<nav class='navbar navbar-default'>"
      		+ "<div class='container-fluid'>"
      		+ "<!-- Brand and toggle get grouped for better mobile display -->"
      		+ "<div class='navbar-header'>"
      		+ "<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1' aria-expanded='false'>"
      		+ "<span class='sr-only'>Toggle navigation</span>"
      		+ "<span class='icon-bar'></span>"
      		+ "<span class='icon-bar'></span>"
      		+ "<span class='icon-bar'></span>"
      		+ "</button>"
      		+ "<p class='navbar-brand'>BBS</p>"
      		+ "</div>"
      		+ "<!-- Collect the nav links, forms, and other content for toggling -->"
      		+ "<div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>"
      		+ "<ul class='nav navbar-nav'>"
      		+ "<li class='active'><a href='http://119.23.51.148/Vertx/Welcome'>首页</a></li>"
      		+ "<li><a target='blank' href='http://119.23.51.148/BBS/ChatView'>在线交流室</a></li>"
      		+ "<li class='dropdown'>"
      		+ "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>学习资源<span class='caret'></span></a>"
      		+ "<ul class='dropdown-menu'>"
      		+ "<li>"
      		+ "<a target='blank' href='http://119.23.51.148/BBS/Download'>文件资源下载</a>"
      		+ "</li>"
      		+ "<li><a target='blank' href='http://119.23.51.148/BBS/Video'>教学视频</a></li>"
      		+ "</ul></li>"
      		+ "<li class='dropdown'>"
      		+ "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>专业分类<span class='caret'></span></a>"
      		+ "<ul class='dropdown-menu'>");
      
      		for(OneboardBean one : oneboard_list){
      			responseContent.append("<li><a target='_blank' href='http://119.23.51.148/BBS/Twoboard_content?one_id="+one.getOne_id()+"'>"+one.getOne_title()+"</a></li>"
);
      		}
      		responseContent.append("</ul></li></ul>"
      		+ "<!-- 查找课程表单 -->"
      		+ "<form class='navbar-form navbar-left' action='http://119.23.51.148/BBS/Search' method='post' id='formid'>"
      		+ "<div class='form-group'>"
      		+ "<input style='display:none;' id='Text1' type='text' />"
      		+ "<input type='text' class='form-control' placeholder='可以搜索想要的课程哦！' id='two_title' name='two_title' maxlength='20'>"
      		+ "</div>"
      		+ "<button type='button' class='btn btn-default' onclick='checkSeach()'>Submit</button>"
      		+ "</form>"
      		+ "<ul class='nav navbar-nav navbar-right'>");
      		if(username==null){
      			responseContent.append("<li><a href='http://119.23.51.148/BBS/Hello'>登录</a></li>");
      		}else{
      			responseContent.append("<li><a>"+username+"</a></li>");
      		}
      		responseContent.append("<li class='dropdown'>"
      		+ "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'><span class='glyphicon glyphicon-cog' aria-hidden='true'></span><span class='caret'></span></a>"
      		+ "<ul class='dropdown-menu'>"
      		+ "<li><a href='http://119.23.51.148/BBS/UserInfor'>个人信息</a></li>"
      		+ "<li><a href='http://119.23.51.148/BBS/UserPassword'>密码修改</a></li>"
      		+ "<li><a target='blank' href='http://119.23.51.148/BBS/uploadFaceView'>人脸上传</a></li>"
      		+ "<li><a target='blank' href='http://119.23.51.148/BBS/uploadAvtarView'>头像管理</a></li>"
      		+ "<li><a href='http://119.23.51.148/BBS/logout'>退出</a></li>"
      		+ "</ul>"
      		+ "</li>"
      		+ "</ul>"
      		+ "</div>"
      		+ "</div>"
      		+ "</nav>"
      		+ "<!--栅格系统开始-->"
      		+ "<div class='row'>"
      		+ "<div class='col-xs-12 col-md-8'>"
      		+ "<!-- 最新课程css -->"
      		+ "<div class='panel panel-info'>"
      		+ "<div class='panel-heading'>最新课题</div>"
      		+ "<div class='panel-body'>");
      
              for(TwoboardBean twoTen : twoboardTenList){
              	responseContent.append("<div class='bodyleftmid'><div class='imga'>"
              			+ "<a target='_blank' href='http://119.23.51.148/BBS/Threeboard_content?one_id="+twoTen.getTwo_one_id()+"&two_id="+twoTen.getTwo_id()+"'>"
              			+ "<img width='100' height='140' src='"+twoTen.getTwo_teacher_headpicurl()+"' /></a>"
              			+ "</div>"
              			+ "<div class='pad'>"
              			+ "<a target='_blank' href='http://119.23.51.148/BBS/Threeboard_content?one_id="+twoTen.getTwo_one_id()+"&two_id="+twoTen.getTwo_id()+"'>"
              			+ "课程名："+twoTen.getTwo_title()+"</a>"
              			+ "<p><font size='2'>教师："+twoTen.getTwo_teacher_name()+"</font></p>"
              			+ "<font size='2'>课程简介：</font><p>"+twoTen.getTwo_profile()+"</p></div></div>");
              }
              responseContent.append("</div></div></div><!-- 左边内部全部结束 -->"
      		+ "<div class='col-xs-6 col-md-4'>"
      		+ "<!-- 热门课题 -->"
      		+ "<div class='panel panel-info'>"
      		+ "<div class='list-group'>"
      		+ "<a href='#' class='list-group-item list-group-item-info'>"
      		+ "热门课题</a><table class='table'>");
      		for(TwoboardBean twoFive : twoboardFiveList){
      			responseContent.append("<tr>"
      		+ "<td>"+twoFive.getTwo_title()+"</td>"
      		+ "<td><a target='_blank' href='http://119.23.51.148/BBS/Threeboard_content?one_id="+twoFive.getTwo_one_id()+"&two_id="+twoFive.getTwo_id()+"'>"
      		+ "<span class='glyphicon glyphicon-eye-open' aria-hidden='true'></span></a></td>"
      		+ "</tr>");
      		}
      		responseContent.append("</table>"
      		+ "</div></div>"
      		+ "<div class='panel panel-info'>"
      		+ "<!-- Default panel contents -->"
      		+ "<div class='list-group'>"
      		+ "<a href='#' class='list-group-item list-group-item-info'>"
      		+ "专题推荐</a>"
      		+ "<!-- Table -->"
      		+ "<table class='table'>");
      		for(ThreeboardBean threeFive : threeboardFiveList){
      			responseContent.append("<tr>"
                  		+ "<td>"
                  		+ threeFive.getThree_title()
                  		+ "</td>"
                  		+ "<td>"
                  		+ "<a target='_blank' href='http://119.23.51.148/BBS/Content?two_id="+threeFive.getThree_two_id()+"&three_id="+threeFive.getThree_id()+"'>"
                  		+ "<span class='glyphicon glyphicon-eye-open' aria-hidden='true'></span></a>"
                  		+ "</td>"
                  		+ "</tr>");
      		}
      		responseContent.append("</table></div></div>"
      		+ "<!-- 热点新闻 -->"
      		+ "<div class='panel panel-info'><div class='list-group'>"
      		+ "<a href='#' class='list-group-item list-group-item-info'>"
      		+ "热点新闻"
      		+ "</a>"
      		+ "<table class='table'>");
      		
      		Iterator<Map<String,String>> iterator = news.getnews().iterator();
      		while(iterator.hasNext()){
      			Map<String,String> map = iterator.next();
      			responseContent.append("<tr height='50'>"
                  		+ "<td>"
                  		+ map.get("title")
                  		+ "</td>"
                  		+ "<td>"
                  		+ "<a target='_blank' href='"+map.get("url")+"'>"
                  		+ "<span class='glyphicon glyphicon-eye-open' aria-hidden='true'></span></a>"
                  		+ "</td>"
                  		+ "</tr>");
      		}
      		responseContent.append("</table></div></div></div></div>"
      		+ "<footer> <p align='center'>© 19689405@qq.com 版权所有     开发者：叶冰 <br /> </p></footer> </body></html>");
    HttpServerResponse response = routingContext.response();
    response.putHeader("Content-Encoding", "UTF-8");
    response.putHeader("content-type", "text/html").end(responseContent.toString());
  }
}

