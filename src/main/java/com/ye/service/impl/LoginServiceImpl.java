package com.ye.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.ye.dao.UserMapper;
import com.ye.domain.UserBean;
import com.ye.redis.RedisGetUsername;
import com.ye.service.LoginService;
import com.ye.util.TokenUtil;

@Service @Transactional
public class LoginServiceImpl implements LoginService{
	@Autowired
	public UserMapper userMapper;
	@Autowired
	private RedisGetUsername redisGetUsername;
	//普通用户页面登录逻辑
		@Override
		public int getUserLogin(HttpServletRequest request,UserBean userbean,HttpSession session) throws Exception{
			int result = 1;
			UserBean user = userMapper.getUserByEmail(userbean.getEmail());
			//获取用户真实ip
			String userIP = request.getRemoteAddr()+request.getHeader("User-Agent");
			if(user!=null&&user.getPassword().equals(userbean.getPassword())){
				TokenUtil token = new TokenUtil();
				//redis中添加用户名
				redisGetUsername.addUsername(userIP, user.getName());
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
				
			}else{
				result =0;
			}
			return result;
		}
		//管理员用户页面登录逻辑
		@Override
		public int adminLogin(UserBean userbean,HttpSession session) throws Exception{
			int result = 1;
			UserBean user = userMapper.getUserByEmail(userbean.getEmail());
			if(user!=null&&user.getRoleid()!=2&&user.getPassword().equals(userbean.getPassword())){
				TokenUtil token = new TokenUtil();
				//加密token
				user.setPassword("******");
				String usermessage = token.CreateTokenNoTime(JSON.toJSONString(user));
				//添加session
				session.setAttribute("name", user.getName());
				session.setAttribute("user", usermessage);
				session.setAttribute("usernameid", user.getName()+"ID"+user.getId());
				//session有效期
				session.setMaxInactiveInterval(900);
			}else{
				result = 0;
			}
			return result;
		}
}
