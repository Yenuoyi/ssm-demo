package com.ye.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ye.dao.UserMapper;
import com.ye.domain.UserBean;
import com.ye.util.CodeUtil;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:ApplicationContext-dao.xml"}) //spring.xml

public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;
	int result;
	
	@Test
	public void testAddUser() {
		UserBean userbean = new UserBean();
		CodeUtil getcode = CodeUtil.getInstance();
		String code = getcode.code();
		userbean.setCode(code);
		userbean.setEmail("565645456@qq.com");
		userbean.setIphone("15629223525");
		userbean.setName("Dalao");
		userbean.setPassword("000000");
		userbean.setProfessional("软件系");
		result = userMapper.addUser(userbean);
		assertEquals(0,result);
	}

	@Test
	public void testDeleteUserById() {
		result = userMapper.deleteUserByEmail("12156");
		assertEquals(1,result);
	}

	@Test
	public void testUpdateUser() {
		UserBean userbean = new UserBean();
		userbean.setName("黑白");
		userbean.setIphone("1923652456");
		userbean.setProfessional("数码系");
		userbean.setId(20);
		result = userMapper.updateUser(userbean);
		assertEquals(1,result);
	}

	@Test
	public void testUpdateUser_password() {
		result = userMapper.updateUser_password("123456", "asdas");
		assertEquals(1,result);
	}

	@Test
	public void testGetUserById() {
		UserBean user = new UserBean();
		user = userMapper.getUserById(1);
		assertNotNull(user);
	}

	@Test
	public void testGetUserByIphone() {
		UserBean user = new UserBean();
		user = userMapper.getUserByIphone("15917934393");
		assertNotNull(user);
	}

	@Test
	public void testGetUserByEmail() {
		UserBean user = new UserBean();
		user = userMapper.getUserByEmail("19689405@qq.com");
		assertNotNull(user);
	}

	@Test
	public void testGetUserByName() {
		List<UserBean> user = new ArrayList<UserBean>();
		user = userMapper.getUserByName("ye");
		if(!user.isEmpty()){
			result = 1;
			assertEquals(1,result);
		}else{
			result = 0;
			assertEquals(0,result);
		}
	}

	@Test
	public void testGetUserByRoleid() {
		List<UserBean> user = new ArrayList<UserBean>();
		user = userMapper.getUserByRoleid(0);
		if(!user.isEmpty()){
			result = 1;
		}else{
			result = 0;
		}
		assertEquals(1,result);
	}

	@Test
	public void testGetUserByProfessional() {
		List<UserBean> user = userMapper.getUserByProfessional("数码系");
		if(!user.isEmpty()){
			result = 1;
			assertEquals(1,result);
		}else{
			result = 0;
			assertEquals(0,result);
		}
	}

	@Test
	public void testGetUserByRoleid_professional() {
		UserBean userbean = new UserBean();
		userbean.setRoleid(0);
		userbean.setProfessional("软件系");
		List<UserBean> user = userMapper.getUserByRoleid_professional(userbean);
		if(!user.isEmpty()){
			result = 1;
			assertEquals(1,result);
		}else{
			result = 0;
			assertEquals(0,result);
		}
	}

	@Test
	public void testGetAllUsers() {
		List<UserBean> user = userMapper.getAllUsers();
		if(!user.isEmpty()){
			result = 1;
		}else{
			result = 0;
		}
		assertEquals(1,result);
	}

}
