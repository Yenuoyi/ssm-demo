package com.ye.service;

import com.ye.domain.UserBean;

public interface RegisterService {
	//添加申请记录
	int addRegister(UserBean userbean) throws Exception;	
	//申请确定
	int addRegisterSure(UserBean userbean) throws Exception;
}
