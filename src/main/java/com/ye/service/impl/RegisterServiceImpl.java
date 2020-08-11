package com.ye.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ye.dao.RegisterMapper;
import com.ye.dao.UserMapper;
import com.ye.domain.UserBean;
import com.ye.service.RegisterService;
import com.ye.util.CodeUtil;
import com.ye.util.SendEmail;
import com.ye.util.TokenUtil;

@Service
public class RegisterServiceImpl implements RegisterService{
	
	@Autowired
	public RegisterMapper registerMapper;
	@Autowired
	private UserMapper userMapper;

	//注册服务
	@Override
	public int addRegister(UserBean userbean) throws Exception{
		int result = 1;
		CodeUtil getcode = CodeUtil.getInstance();
		String code = getcode.code();
		userbean.setCode(code);
		//首先判断是否属于已存在的用户
		if(userMapper.getUserByEmail(userbean.getEmail())==null){
			//判断此邮箱是否存在在申请表中，否：在申请表中添加记录，是：更新该数据的code码
			if(registerMapper.getRegisterByEmail(userbean.getEmail())==null){
				registerMapper.addRegister(userbean);
				}else{
					registerMapper.updateRegister(userbean);
				}	
			TokenUtil token = new TokenUtil();
			//加密token，在解密函数中设置有效时间为1小时
			String aaa = token.CreateTokenTime(JSON.toJSONString(userbean));
			//发送邮件
			SendEmail getsend = new SendEmail();	
			//aaa代表token存储用户信息，0代表是发送注册邮件
			getsend.send(aaa,userbean.getEmail(),0);
		}else{
			result = 0;
		}
		return result;
	}

	//申请确认
	@Override
	public int addRegisterSure(UserBean userbean) throws Exception {
		int result = 1;
		UserBean user = null;
		TokenUtil token = new TokenUtil();
		//解密token
		String unsign = token.Vertify(userbean.getCode(), 3600);
		//将token转换成json格式
		JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
		//json以string格式输出
		String email = json.get("email").getAsString();
		String code = json.get("code").getAsString();
		//根据token中携带的email在注册表中查找申请记录
		user = registerMapper.getRegisterByEmail(email);
		Date date = new Date();
		java.sql.Date sqldate;
		sqldate = new java.sql.Date(date.getTime());
		user.setCreate_time(sqldate);
		//判断token中的code码是否与数据库中的一致
		if(user.getCode().equals(code)){
			//在user表中添加记录
			userMapper.addUser(user);
		}else{
			result = 0;
		}
		return result;
	}
}
