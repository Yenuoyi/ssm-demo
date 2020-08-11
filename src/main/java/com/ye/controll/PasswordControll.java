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
import com.ye.domain.OneboardBean;
import com.ye.domain.UserBean;
import com.ye.service.OneboardService;
import com.ye.service.UserService;
import com.ye.util.SendEmail;

@Controller
public class PasswordControll {
	@Autowired
    private UserService userService;
	@Autowired
    private OneboardService oneboardService;

	//后台管理员密码修改入口
	@RequestMapping("/PasswordChange")
	public ModelAndView PasswordChange(ServletRequest request,UserBean userbean,OneboardBean one) throws Exception{
		ModelAndView  view;
		try{
			view = new ModelAndView("admin/passwordchange");
		}catch(Exception e){
			view = new ModelAndView("500page");
		}
		return view;
	}
	
	//普通用户管密码修改入口
	@RequestMapping("/UserPassword")
	public ModelAndView UserPassword(ServletRequest request,UserBean userbean,OneboardBean one) throws Exception{
		ModelAndView  view;
		try{
			view = new ModelAndView("userpassword");
			view.addObject("oneboard", oneboardService.getAllOneboard());
		}catch(Exception e){
			view = new ModelAndView("500page");
		}
		return view;
	}
	
	//更改密码控制器
	@RequestMapping(value="/UpdateUser_password",method = RequestMethod.POST)
	@ResponseBody
	public int UpdateUser_password(@RequestBody UserBean userbean , HttpServletRequest request) throws Exception{
		int result;
		try{
			//获取原密码
			//获取session中用户信息
			HttpSession session = request.getSession();
			String email = session.getAttribute("email").toString();
			//将token转换成json格式
			//json以string格式输出
			String password = userService.getUserByEmail(email).getPassword();
			//判断原密码是否正确
			if(userbean.getCode().equals(password)){
				result = userService.updateUser_password(userbean.getPassword(),email);
				if(result==1){
					session.removeAttribute("user");
				}
			}else{
				result = 3;
			}
			}catch(Exception e){
				result = 2;
			};
			return result;
	}
	
	//忘记密码申请界面
	@RequestMapping("LostPassword")
	public ModelAndView LostPassword(){
		ModelAndView view = new ModelAndView("lostpassword");
		return view;
		
	}
	//忘记密码发送邮件
	@RequestMapping(value="/SendPassword",method = RequestMethod.POST)
	@ResponseBody
	public int SendPassword(@RequestBody UserBean userbean) throws Exception{
		int result = 0;
		String email = userbean.getEmail();
		UserBean user = userService.getUserByEmail(email);
		if(user!=null){
			String password = user.getPassword();
			String send = "这是你的密码，请查收哦！密码：".concat(password);
			//发送邮件
			SendEmail getsend = new SendEmail();	
			//aaa代表token存储用户信息，0代表是发送注册邮件
			result = getsend.send(send,email,1);
		}else{
			result=0;
		}

		return result;
	}
}