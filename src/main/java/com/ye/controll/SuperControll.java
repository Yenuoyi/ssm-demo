package com.ye.controll;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.ye.domain.OneboardBean;
import com.ye.domain.UserBean;
import com.ye.service.OneboardService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;

@Controller
public class SuperControll {
	@Autowired
    private UserService userService;
	@Autowired
	private OneboardService oneboardService;
	@Autowired
	private TwoboardService twoboardService;
	@Autowired
	private ThreeboardService threeboardService;
	
	//清除session，退出登录
	@RequestMapping("/logout")
	public String Logout(HttpSession session){
		session.removeAttribute("user");
		return "login";
	}
	
	//访问专业信息
	@RequestMapping("/Admin/Super/Oneview")
	public ModelAndView Oneview(UserBean userbean) throws Exception{
		ModelAndView  view;
		view = new ModelAndView("admin/onemanager");
		//添加所有专业
		view.addObject("one",oneboardService.getAllOneboard());
		 return view;
	}
	
	//添加专业管理器
	@RequestMapping(value="/Admin/Super/AddOne",method = RequestMethod.POST)
	@ResponseBody
	public int AddOne(@RequestBody OneboardBean oneboard) throws Exception{
		int result;
		//添加专业
		result = oneboardService.addOneboard(oneboard);
		return result;
	}
	
	//更改指定专业
	@RequestMapping(value="/Admin/Super/ChangeOne",method = RequestMethod.POST)
	@ResponseBody
	public int ChangeTwo(@RequestBody OneboardBean oneboard) throws Exception{
		int result;
		result = oneboardService.updateOneboard(oneboard);
		return result;
		}
			
	//删除指定专业
	@RequestMapping(value="/Admin/Super/DeleteOne",method = RequestMethod.POST)
	@ResponseBody
	public int DleteThree(@RequestBody OneboardBean oneboard) throws Exception{
		int result;
		//根据课程id修改课程名和课程简介
		result = oneboardService.deleteOneboardById(oneboard.getOne_id());
		return result;
	}
	
	//访问教师信息
	@RequestMapping("/Admin/Super/Teacherview")
	public ModelAndView Teacherview(UserBean userbean) throws Exception{
	ModelAndView  view = null;
	view = new ModelAndView("admin/teachermanager");
	//添加教师管理页面信息
	view.addObject("one",oneboardService.getAllOneboard());
	view.addObject("teacher", userService.getUserByRoleid(1));
	return view;
	}
		
	//添加教师控制器
	@RequestMapping(value="/Admin/Super/AddTeacher",method = RequestMethod.POST)
	@ResponseBody
	public int AddTeacher(@RequestBody UserBean userbean) throws Exception{
			int result;
			if(userService.getUserByEmail(userbean.getEmail())==null){
			//创建时间
				Date date = new Date();
				java.sql.Date sqldate;
				sqldate = new java.sql.Date(date.getTime());
				userbean.setCreate_time(sqldate);
				userbean.setPassword("000000");
				userbean.setRoleid(1);
				//添加教师
				result = userService.addUser(userbean);
				}else{
					result = 4;
				}
			return result;
		}
	
		//更改指定教师信息
		@RequestMapping(value="/Admin/Super/ChangeTeacher",method = RequestMethod.POST)
		@ResponseBody
		public int ChangeTeacher(@RequestBody UserBean userbean) throws Exception{
			int result = 0;
			result = userService.updateUser(userbean);
			return result;
		}
				
		//删除指定教师
		@RequestMapping(value="/Admin/Super/DeleteTeacher",method = RequestMethod.POST)
		@ResponseBody
		public int DeleteTeacher(@RequestBody UserBean userbean) throws Exception{
			int result = userService.deleteUserByEmail(userbean.getEmail());	
			return result;
		}
		
		//访问专业所属课程页面
		@RequestMapping("/Admin/Super/Lookone_twoview")
		public ModelAndView Lookone_twoview(UserBean userbean,OneboardBean oneboard) throws Exception{
			ModelAndView  view;
			view = new ModelAndView("admin/superOneTwo");
			//统计所有课程评论数
			twoboardService.update_two_statistics();
		    //添加对应专业课程
			view.addObject("oneId",oneboard.getOne_id());
			view.addObject("alltwo", twoboardService.getTwoboardone_id(oneboard.getOne_id()));
			view.addObject("alltwototal",twoboardService.getTwoIdTotalByOneId(oneboard.getOne_id()));
			return view;
		}
		
		//专业id课程分页
		@RequestMapping(value="/Admin/Super/TwobardOneIDPage",method = RequestMethod.POST,produces="text/html;charset=UTF-8;")
		@ResponseBody
		public String getTwoboardTeacherIdPage(@RequestBody String pagination) throws Exception{
			//json以string格式输出
			JSONObject object = JSONObject.parseObject(pagination);
			int curpage = object.getIntValue("curpage");
			int oneId = object.getIntValue("oneId");
			int start = curpage*10-10;
			int end = 10;
			return twoboardService.getTwoboardPage(oneId, start, end);
		}
}
