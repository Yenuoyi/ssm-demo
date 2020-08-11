 package com.ye.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ye.dao.UserMapper;
import com.ye.domain.UserBean;
import com.ye.service.UserService;
@Service @Transactional
public class UserServiceImpl implements UserService {
	//@Resource(name="userMapper")
	@Autowired
	public UserMapper userMapper;
	//创建新用户(Create)
	@Override
	public int addUser(UserBean userbean) throws Exception{
		int result = userMapper.addUser(userbean);
		return result;
	}
			
	//删除用户(Delete)
	@Override
	public int deleteUserByEmail(String email) throws Exception{
		int result = userMapper.deleteUserByEmail(email);
		return result;
	}
	
	//更新用户(Update)
	@Override
	public int updateUser(UserBean userbean) throws Exception{
		int result = userMapper.updateUser(userbean);
		return result;
	}
	
	//更改用户密码
	@Override
	public int updateUser_password(String password ,String email) throws Exception{
		int result = userMapper.updateUser_password(password,email);
		return result;
	}
	
	//更改用户头像地址
	@Override
	public int updateUser_headpicurl(String headpicurl ,String email) throws Exception{
		int result = userMapper.updateUser_headpicurl(headpicurl,email);
		return result;
	}
	//根据用角色id查询
	@Override
	public List<UserBean> getUserByRoleid(int id) throws Exception {
		List<UserBean> userbean = userMapper.getUserByRoleid(id);
		return userbean;
	}	
		
	//根据用户id查询
	@Override
	public UserBean getUserById(int id) throws Exception{	 
		UserBean userbean = userMapper.getUserById(id);
		if(userbean==null){
			throw new Exception("user is not existed");
		}
		return userbean;
	}
		
	//根据用户手机号查询
	@Override
	public UserBean getUserByIphone(String iphone) throws Exception{	 
		UserBean userbean = userMapper.getUserByIphone(iphone);
		if(userbean==null){
			throw new Exception("user is not existed");
		}
	    return userbean;
	}

	//根据用户email查询
	@Override
	public UserBean getUserByEmail(String email) throws Exception{	 
		UserBean userBean = userMapper.getUserByEmail(email);
		return userBean;
	}
	
	//通过用户姓名模糊查询
	@Override
	public List<UserBean> getUserByName(String name) throws Exception{    
	    List<UserBean> userbean = userMapper.getUserByName(name);
	    return userbean;
	}
		
	//通过用户专业查询
	@Override
	public List<UserBean> getUserByProfessional(String professional) throws Exception{	
		List<UserBean> userbean = userMapper.getUserByProfessional(professional);
		return userbean;
	}
		
	//通过用户角色和专业查询
	@Override
	public List<UserBean> getUserByRoleid_professional(UserBean userbean) throws Exception{
		List<UserBean> users = userMapper.getUserByRoleid_professional(userbean);
		return users;
	}
		
	//获取所有用户
	@Override
	public List<UserBean> getAllUsers() throws Exception{
		List<UserBean> users= userMapper.getAllUsers();
		return users;
	}
			
	
}
