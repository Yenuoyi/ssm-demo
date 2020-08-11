package com.ye.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ye.dao.RegisterMapper;
import com.ye.domain.UserBean;
import com.ye.util.CodeUtil;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:ApplicationContext-dao.xml"}) //spring.xml

public class RegisterMapperTest {

	@Autowired
	private RegisterMapper registerMapper;
	int result;
	@Test
	public void testAddRegister() {
		UserBean register = new UserBean();
		CodeUtil getcode = CodeUtil.getInstance();
		String code = getcode.code();
		register.setCode(code);
		register.setEmail("565645456@qq.com");
		register.setIphone("15629223525");
		register.setName("Dalao");
		register.setPassword("000000");
		register.setProfessional("软件系");
		result = registerMapper.addRegister(register);
		assertEquals(1,result);
	}

	@Test
	public void testGetRegisterByEmail() {
		UserBean register = new UserBean();
		register = registerMapper.getRegisterByEmail("847977675@qq.com");
		assertNotNull(register);
	}

	@Test
	public void testUpdateRegister() {
		UserBean register = new UserBean();
		CodeUtil getcode = CodeUtil.getInstance();
		String code = getcode.code();
		register.setCode(code);
		register.setEmail("565645456@qq.com");
		register.setIphone("15629223525");
		register.setName("Dalao");
		register.setPassword("000000");
		register.setProfessional("软件系");
		result = registerMapper.updateRegister(register);
		assertEquals(1,result);
	}

}
