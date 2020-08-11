package com.ye.dao;

import org.springframework.stereotype.Repository;

import com.ye.domain.UserBean;
@Repository
public interface RegisterMapper {
	//添加申请记录
	int addRegister(UserBean userbean);
	//通过邮箱查询
	UserBean getRegisterByEmail(String email);	
	//ͨ更新
	int updateRegister(UserBean userbean);

}
