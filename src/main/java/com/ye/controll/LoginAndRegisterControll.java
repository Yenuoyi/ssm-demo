package com.ye.controll;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ye.domain.UserBean;
import com.ye.service.CommentService;
import com.ye.service.LoginService;
import com.ye.service.OneboardService;
import com.ye.service.RegisterService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;
import com.ye.util.CodeUtil;

@Controller
public class LoginAndRegisterControll {
	@Autowired
    private UserService userService;
	@Autowired
    private CommentService commentService;
	@Autowired
    private OneboardService oneboardService;
	@Autowired
    private TwoboardService twoboardService;
	@Autowired
    private ThreeboardService threeboardService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private LoginService loginService;
	@RequestMapping("/AdminHello")
	public String adminLoginView(){
		return "admin/adminlogin";
	}
	// 管理员登录
	@RequestMapping("/AdminLogin")
	public ModelAndView adminLogin(UserBean userbean,HttpSession session) throws Exception{
		ModelAndView  view=null;
		int result = loginService.adminLogin(userbean, session);
		if(result==1){
			view = new ModelAndView("admin/adminmain");
		}else{
			view = new ModelAndView("admin/adminlogin");
			view.addObject("message", "帐号或密码错误，请重新再试，谢谢！");
		}
		return view;
	}
	
	//普通用户登录
	@RequestMapping("/Hello")
	public String Login(){
		return "login";
	}
	//首次访问主页
	@RequestMapping("/Welcome")
	@ResponseBody
	public int getUserByEmail(@RequestBody UserBean userbean, HttpServletRequest request,HttpSession session) throws Exception{
		int result = loginService.getUserLogin(request, userbean, session);
		return result;
	}
	
//	//返回主页
//	@RequestMapping("/AAA")
//	public ModelAndView NewMain(UserBean userbean){
//		ModelAndView  view=null;
//		try {
//			view = new ModelAndView("main");
//			//今日头条接口
//			NewsUtil  news = NewsUtil.getInstance();
//			//编写统计前五名课程方法
//			List<TwoboardBean> twoboard_id_list = twoboardService.gettwo_statistics_five();
//			//编写统计前五名章节方法
//			List<ThreeboardBean> threeboard_id_list = threeboardService.getthree_statistics_five();
//			//添加前五名课程至main中
//			view.addObject("two_statistics_five",twoboard_id_list);
//			//添加前五名章节至main中
//			view.addObject("three_statistics_five",threeboard_id_list);
//			//添加最新的前十课程至main中
//			view.addObject("two_ten",twoboardService.getTwo_ten());
//			//添加所有专业
//			view.addObject("oneboard",oneboardService.getAllOneboard());
//			//添加热点新闻
//			view.addObject("news", news.getnews());
//			return view;
//		} catch (Exception e) {
//			e.printStackTrace();
//			view = new ModelAndView("login");
//			return view;
//		}
//	}
	//注册接口
	@RequestMapping("/Register/View")
	public ModelAndView RegisterService() throws Exception{	
		ModelAndView  view= new ModelAndView("register");
		view.addObject("oneboard", oneboardService.getAllOneboard());
		return view;
	}
	//注册验证码
	@RequestMapping("/Register/Code")
	public void RegisterCodeService(HttpServletRequest request, HttpServletResponse response) throws Exception{	
		try {
			// 生成验证码实例
			CodeUtil util = CodeUtil.getInstance();
			util.init();
			request.getSession().setAttribute("sessionCode", util.getStr());
			ServletOutputStream stream = response.getOutputStream();
			ImageIO.write(util.getImage(), "JPEG", stream);
			stream.flush();
			stream.close();
			} catch (IOException e) {
				System.out.println("验证码生成失败");
			}
	}
	//注册服务
	@RequestMapping("/Register/service")
	@ResponseBody
	public int Regitser(@RequestBody UserBean userbean,HttpServletRequest req) throws Exception{
		HttpSession session = req.getSession();
		String code = session.getAttribute("sessionCode").toString();
		session.removeAttribute("sessionCode");
		if(!userbean.getCode().equals(code)){
			return 2;
		}
		//首先判断是否属于已存在的用户
		if(registerService.addRegister(userbean)==1){
			return 1;
		}else{
			return 0;
		}
	}
	
	//确认注册
	@RequestMapping("/Register/webMail")
	public String webMail(UserBean userbean) throws Exception{
		if(registerService.addRegisterSure(userbean)==1){
			return "login";
			}else{
				return "new_register";
			}
	}
}
