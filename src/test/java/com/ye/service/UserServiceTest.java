package com.ye.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ye.domain.UserBean;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:ApplicationContext-dao.xml","classpath*:ApplicationContext-service.xml","classpath*:ApplicationContext-trans.xml"}) 

public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
    private UserService userService;
	@Test
	public void test() throws Exception {
		UserBean user = userService.getUserByEmail("19689405@qq.com");
		System.out.println(user);
	}
	@Autowired
    private TwoboardService twoboardService;
	@Test
	public void sss() throws Exception{
		System.out.println(twoboardService.getAllTwoboard(0,10));
	}
}
