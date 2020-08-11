package com.ye.controll;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ye.domain.ThreeboardBean;
import com.ye.domain.TwoboardBean;
import com.ye.domain.UserBean;
import com.ye.service.CommentService;
import com.ye.service.OneboardService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;
import com.ye.util.TokenUtil;

@Controller
public class AdminControll {
	@Autowired
	UserService userService;
	@Autowired
    private CommentService commentService;
	@Autowired
	private OneboardService oneboardService;
	@Autowired
	private TwoboardService twoboardService;
	@Autowired
	private ThreeboardService threeboardService;

	//访问个人信息
	@RequestMapping("/Admin/Information")
	public ModelAndView Information(ServletRequest request) throws Exception{
		ModelAndView  view;
		try{
			view = new ModelAndView("admin/information");
			//获取session中用户信息
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String user = session.getAttribute("user").toString();
			//解锁token
			TokenUtil token = new TokenUtil();
			String unsign = token.Vertify(user);
			//将token转换成json格式
			JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
			//json以string格式输出
			String email = json.get("email").getAsString();
			//获取个人信息
			UserBean usermess= userService.getUserByEmail(email);
			//添加个人信息
			view.addObject("userbean", usermess);
			view.addObject("one",oneboardService.getAllOneboard());
		}catch(Exception e){
			e.printStackTrace();
			view = new ModelAndView("500page");
		}
		 return view;
	}
	
	//访问添加课程页面
	@RequestMapping("/Admin/Addtwoview")
	public ModelAndView Addtwoview(UserBean userbean,ServletRequest request) throws Exception{
		ModelAndView  view;
		view = new ModelAndView("admin/addtwo");
		view.addObject("one",oneboardService.getAllOneboard());
		 return view;
	}
	//添加课程管理器
	@RequestMapping(value="/Admin/AddTwo",method = RequestMethod.POST)
	@ResponseBody
	public int AddTwo(@RequestBody TwoboardBean twoboard,HttpServletRequest request) throws Exception{
		int result;
		if(oneboardService.getOneboardByid(twoboard.getTwo_one_id())!=null){
			try{
				//获取session中用户信息
				HttpSession session = request.getSession();
				String user = session.getAttribute("user").toString();
				//解锁token
				TokenUtil token = new TokenUtil();
				String unsign = token.Vertify(user);
				//将token转换成json格式
				JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
				twoboard.setTwo_teacher_id(json.get("id").getAsInt());
				twoboard.setTwo_teacher_name(json.get("name").getAsString());
				twoboard.setTwo_teacher_headpicurl(json.get("headpicurl").getAsString());
				}catch(Exception e){
					result = 2;
				};
			result = twoboardService.addTwoboard(twoboard);
		}else{
			result=3;
			System.out.println("有人入侵！");
		}	
			return result;
		}
	
	//访问教师所属课程页面
	@RequestMapping("/Admin/Looktwoview")
	public ModelAndView Looktwoview(UserBean userbean,ServletRequest request) throws Exception{
		ModelAndView  view;
		try{
			view = new ModelAndView("admin/teachertwo");
			//获取session中用户信息
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String user = session.getAttribute("user").toString();
			TokenUtil token = new TokenUtil();
			String unsign = token.Vertify(user);
			//将token转换成json格式
			JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
			//json以string格式输出
			int roleid = json.get("roleid").getAsInt();
			int teacher_id = json.get("id").getAsInt();
			view.addObject("one",oneboardService.getAllOneboard());
			//判断用户角色
			if(roleid==0){
				//添加所有课程
				view.addObject("alltwo", twoboardService.getAllTwoboard(0,10));
				view.addObject("alltwototal",twoboardService.getAllTwoboardTotal());
			}else{
				view.addObject("alltwototal",twoboardService.getTwoboardTeacherIdTotal(teacher_id));
				view.addObject("alltwo", twoboardService.getTwoboardteacher_id(teacher_id,0,10));
			}
		}catch(Exception e){
			e.printStackTrace();
			view = new ModelAndView("500page");
		}
		 return view;
	}
	//教师id课程分页
	@RequestMapping(value="/Admin/TwobardTeacherIDPage",method = RequestMethod.POST,produces="text/html;charset=UTF-8;")
	@ResponseBody
	public String getTwoboardTeacherIdPage(@RequestBody String pagination,ServletRequest request) throws Exception{
		//获取session中用户信息
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String user = session.getAttribute("user").toString();
		TokenUtil token = new TokenUtil();
		String unsign = token.Vertify(user);
		//将token转换成json格式
		JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
		//json以string格式输出
		JSONObject object = JSONObject.parseObject(pagination);
		int twoTeacherId = json.get("id").getAsInt();
		int curpage = object.getIntValue("curpage");
		int start = curpage*10-10;
		int end = 10;
		if(json.get("roleid").getAsInt()==0){
			return twoboardService.getAllTwoboardPage(start, end);
		}else{
			return twoboardService.getTwoboardPage(twoTeacherId, start, end);
		}
	}
	
	//访问课程目录
	//首先接收请求的目录所属的课程id，通过课程id判断是对应教师在操作。
		@RequestMapping(value="/Admin/Threeview",method = RequestMethod.POST)
		public ModelAndView Threeview(UserBean userbean,TwoboardBean twoboard,HttpServletRequest request) throws Exception{
			ModelAndView  view = null;
			try{
				//获取session中用户信息
				HttpSession session = request.getSession();
				String user = session.getAttribute("user").toString();
				TokenUtil token = new TokenUtil();
				String unsign = token.Vertify(user);
				//将token转换成json格式
				JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
				//json以string格式输出
				int teacher_id = json.get("id").getAsInt();
				int roleid = json.get("roleid").getAsInt();
				TwoboardBean two = twoboardService.getTwoboardtwo_id(twoboard.getTwo_id());
				if(teacher_id==two.getTwo_teacher_id()||roleid==0){
					view = new ModelAndView("admin/threemanager");
					view.addObject("two", twoboardService.getTwoboardtwo_id(twoboard.getTwo_id()));
					view.addObject("three",threeboardService.getThreeboard_Bytwoid(twoboard.getTwo_id()));
				}else{
					view = new ModelAndView("admin/adminlogin");
				}
			}catch(Exception e){
				e.printStackTrace();
				view = new ModelAndView("admin/adminlogin");
			}
			 return view;
		}
		
		//删除指定课程
		@RequestMapping(value="/Admin/DeleteTwo",method = RequestMethod.POST)
		@ResponseBody
		public int DeleteTwo(@RequestBody TwoboardBean twoboard,ServletRequest request) throws Exception{
			int result = 0;
			try{
				//获取session中用户信息
				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
				String user = session.getAttribute("user").toString();
				TokenUtil token = new TokenUtil();
				String unsign = token.Vertify(user);
				//将token转换成json格式
				JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
				//json以string格式输出
				int teacher_id = json.get("id").getAsInt();
				int roleid = json.get("roleid").getAsInt();
				TwoboardBean two = twoboardService.getTwoboardtwo_id(twoboard.getTwo_id());
				if(teacher_id==two.getTwo_teacher_id()||roleid==0){
					result = twoboardService.deleteTwoboard(twoboard.getTwo_id());
				}else{
					result = 3;
					//有人非法操作
				}
				}catch(Exception e){
					result = 2;
				};
				return result;
			}
		
		//更改指定课程
		@RequestMapping(value="/Admin/ChangeTwo",method = RequestMethod.POST)
		@ResponseBody
		public int ChangeTwo(@RequestBody TwoboardBean twoboard,HttpServletRequest request) throws Exception{
			int result;
			try{
				//获取session中用户信息
				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
				String user = session.getAttribute("user").toString();
				TokenUtil token = new TokenUtil();
				String unsign = token.Vertify(user);
				//将token转换成json格式
				JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
				//json以string格式输出
				int teacher_id = json.get("id").getAsInt();
				int roleid = json.get("roleid").getAsInt();
				TwoboardBean two = twoboardService.getTwoboardtwo_id(twoboard.getTwo_id());
				if(teacher_id==two.getTwo_teacher_id()||roleid==0){
					//根据课程id修改课程名和课程简介
					result = twoboardService.updateTwoboard(twoboard);
				}else{
					result = 3;
					System.out.println("有人入侵！");
				}
				
				}catch(Exception e){
						result = 2;
					};
				return result;
			}
		
		//删除指定目录
		@RequestMapping(value="/Admin/DeleteThree",method = RequestMethod.POST)
		@ResponseBody
		public int DleteThree(@RequestBody ThreeboardBean threeboard,HttpServletRequest req) throws Exception{
			int result = 0;
			try{
				//获取session中用户信息
				HttpSession session = req.getSession();
				String user = session.getAttribute("user").toString();
				TokenUtil token = new TokenUtil();
				String unsign = token.Vertify(user);
				//将token转换成json格式
				JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
				//json以string格式输出
				int teacher_id = json.get("id").getAsInt();
				int roleid = json.get("roleid").getAsInt();
				//获取目录具体信息
				threeboard = threeboardService.getThreeboardthree_id(threeboard.getThree_id());
				//获取课程目录教师
				TwoboardBean two = twoboardService.getTwoboardtwo_id(threeboard.getThree_two_id());
				if(teacher_id==two.getTwo_teacher_id()||roleid==0){
					//根据课程id修改课程名和课程简介
					result = threeboardService.deleteThreeboard(threeboard);
				}else{
					result = 3;
				}
				}catch(Exception e){
						result = 2;
					};
				return result;
			}
		//添加目录
		@RequestMapping(value="/Admin/AddThree",method = RequestMethod.POST)
		@ResponseBody
		public int AddThree(@RequestBody ThreeboardBean threeboard,ServletRequest request) throws Exception{
			int result = 0;
			try{
				//获取session中用户信息
				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
				String user = session.getAttribute("user").toString();
				TokenUtil token = new TokenUtil();
				String unsign = token.Vertify(user);
				//将token转换成json格式
				JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
				//json以string格式输出
				int teacher_id = json.get("id").getAsInt();
				int roleid = json.get("roleid").getAsInt();
				TwoboardBean two = twoboardService.getTwoboardtwo_id(threeboard.getThree_two_id());
				if(teacher_id==two.getTwo_teacher_id()||roleid==0){
					result = threeboardService.addThreeboard(threeboard);
				}else{
					result = 3;
				}
			}catch(Exception e){
				e.printStackTrace();
				result = 2;
			}
				return result;
			}
}
