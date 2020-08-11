package com.ye.service;

import java.util.List;
import com.ye.domain.UserBean;

public interface UserService {

	//添加用户
	int addUser(UserBean userbean) throws Exception;
	//根据email删除用户(Delete)
	int deleteUserByEmail(String email) throws Exception;
	//根据email定位修改用户基本资料(Update)
	int updateUser(UserBean userbean) throws Exception;
	//修改用户密码
	int updateUser_password(String password,String email) throws Exception;
	//修改用户头像地址
	int updateUser_headpicurl(String headpicurl,String email) throws Exception;
	//根据用户id查询
	UserBean getUserById(int id) throws Exception;
	//根据角色id查询
	List<UserBean> getUserByRoleid(int id) throws Exception;
	//根据用户手机查询
	UserBean getUserByIphone(String iphone) throws Exception;	
	//根据用户邮箱查询
	UserBean getUserByEmail(String email) throws Exception;
	//根据姓名查询用户,可模糊查询
	List<UserBean> getUserByName(String name) throws Exception;
	//根据专业查询用户所有资料 
	List<UserBean> getUserByProfessional(String professional) throws Exception;
	//根据角色roleid和专业查询用户所有资料 
	List<UserBean> getUserByRoleid_professional(UserBean userbean) throws Exception;
	//查询全部用户
	List<UserBean> getAllUsers() throws Exception;

}
