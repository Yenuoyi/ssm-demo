package com.ye.controll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ye.ai.FaceRecognition;
import com.ye.domain.UserBean;
import com.ye.redis.RedisGetUsername;
import com.ye.service.UserService;
import com.ye.util.TokenUtil;

@Controller
public class FaceLoginControll {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisGetUsername redisGetUsername;
	@RequestMapping("/FaceLogin")
	public String loginView(){
		return "facelogin";
	}
	@RequestMapping("/Face")
	@ResponseBody
	public int userLogin(@RequestBody String data, HttpServletRequest request,HttpSession session) throws Exception{
		JSONObject jsonObject = JSONObject.parseObject(data);
		String imgstr = jsonObject.getString("imgstr");
		String email = jsonObject.getString("email");
		int result = FaceRecognition.face(imgstr, email);
		if(result==1){
			UserBean user = userService.getUserByEmail(email);
			//获取用户真实ip
			String userIP = request.getRemoteAddr()+request.getHeader("User-Agent");
			//redis中添加用户名
			redisGetUsername.addUsername(userIP, user.getName());
			TokenUtil token = new TokenUtil();
			//加密token，同时设置有效时间为1小时
			user.setPassword("******");
			String usermessage = token.CreateTokenNoTime(JSON.toJSONString(user));
			//添加session
			session.setAttribute("name", user.getName());
			session.setAttribute("user", usermessage);
			session.setAttribute("email", user.getEmail());
			session.setAttribute("headpicurl", user.getHeadpicurl());
			//session有效期
			session.setMaxInactiveInterval(900);
		}
		return result;
	}
}
