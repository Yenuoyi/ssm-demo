package com.ye.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ye.domain.UserBean;
@Repository
public interface UserMapper {
	//创建用户
	public int addUser(UserBean userbean);
	
	//通过用户email删除用户
	public int deleteUserByEmail(String Email);
	
	//更新用户资料
	public int updateUser(UserBean userbean);
	
	
	//更新用户密码
	public int updateUser_password(@Param("password") String password,@Param("email") String email);
	
	//更新用户头像地址
	public int updateUser_headpicurl(@Param("headpicurl") String headpicurl,@Param("email") String email);
	
	//通过id查询用户
	public UserBean getUserById(int id);
	
	//通过手机查询用户
	public UserBean getUserByIphone(String iphone);
	
	//通过邮箱查询用户
	public UserBean getUserByEmail(String email);
	
	//通过姓名查询用户
	public List<UserBean> getUserByName(String name);
	
	//通过角色id查询用户
	public List<UserBean> getUserByRoleid(int roleid);
	
	//通过专业查询用户
	public List<UserBean> getUserByProfessional(String professional);
	
	//通过角色id和专业查询用户
	public List<UserBean> getUserByRoleid_professional(UserBean userbean);
	
	//获得所有用户
	public List<UserBean> getAllUsers();

}
