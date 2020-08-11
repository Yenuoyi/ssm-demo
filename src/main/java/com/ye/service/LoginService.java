package com.ye.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.ye.domain.UserBean;

public interface LoginService {
	//普通用户页面登录逻辑
	int getUserLogin(HttpServletRequest request,UserBean userbean,HttpSession session) throws Exception;
	//管理员用户页面登录逻辑
	int adminLogin(UserBean userbean,HttpSession session) throws Exception;
}
