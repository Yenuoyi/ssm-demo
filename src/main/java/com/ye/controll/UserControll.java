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
import com.ye.domain.UserBean;
import com.ye.service.CommentService;
import com.ye.service.OneboardService;
import com.ye.service.RegisterService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;
import com.ye.util.TokenUtil;

@Controller
public class UserControll {
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
	
	
	//访问用户个人信息
	@RequestMapping("/UserInfor")
	public ModelAndView Information(UserBean userbean,HttpServletRequest req ) throws Exception{
		ModelAndView  view;
		try{
			view = new ModelAndView("userinfor");
			//获取session中用户信息
			HttpSession session = req.getSession();
			String user = session.getAttribute("user").toString();
			//解锁token
			TokenUtil token = new TokenUtil();
			String unsign = token.Vertify(user);
			//将token转换成json格式
			JSONObject json = JSONObject.parseObject(unsign);
			//json以string格式输出
			String email = json.getString("email");
			//添加个人信息
			view.addObject("userbean", userService.getUserByEmail(email));
			view.addObject("oneboard",oneboardService.getAllOneboard());
		}catch(Exception e){
			e.printStackTrace();
			view = new ModelAndView("500page");
		}
		 return view;
	}
	
	//修改个人信息控制器
		@RequestMapping(value="/InforChange",method = RequestMethod.POST)
		@ResponseBody
		public int Inforchange(@RequestBody UserBean userbean,ServletRequest request) throws Exception{
			int result=1;
			try{
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
				int id = json.get("id").getAsInt();
				String name = json.get("name").getAsString();
				String email = json.get("email").getAsString();
				int roleid = json.get("roleid").getAsInt();
				//判断更改信息者是否是老师
				if(roleid==1||roleid==0){
					twoboardService.updateTwoboard_two_name(name, id);
				}
				//更改个人信息添加
				userbean.setId(id);
				userbean.setEmail(email);
				userbean.setRoleid(roleid);
				commentService.updateTwoboard_two_name(name, id);
				result = userService.updateUser(userbean);
				if(result==1){
					session.removeAttribute("name");
					session.setAttribute("name", userbean.getName());
				}
				}catch(Exception e){
					result = 2;
				};
				return result;
			}
}
