package com.ye.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ye.dao.OneboardMapper;
import com.ye.domain.OneboardBean;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:ApplicationContext-dao.xml"}) //spring.xml

public class OneboardMapperTest {

	int result;
	@Autowired
	private OneboardMapper oneboard;
	OneboardBean one = new OneboardBean();
//	@Test
//	public void testAddOneboard() {
//		one.setOne_title("单元测试");
//		result = oneboard.addOneboard(one);
//	}

	@Test
	public void testDeleteOneboardById() {
		result = oneboard.deleteOneboardById(81);
	}

	@Test
	public void testUpdateOneboard() {
		one.setOne_id(15);
		one.setOne_title("English");
		result = oneboard.updateOneboard(one);
	}

	@Test
	public void testGetOneboardBytitle() {
		List<OneboardBean> bean = new ArrayList<OneboardBean>();
		bean = oneboard.getOneboardBytitle("软件");
		boolean A;
		if(bean.isEmpty()){
			A = false;
		}else{
			A = true;
		}
		assertTrue(A);
	}

	@Test
	public void testGetOneboardByid() {
		one = oneboard.getOneboardByid(1);
		assertNotNull(one);
	}

	@Test
	public void testGetAllOneboard() {
		List<OneboardBean> bean = new ArrayList<OneboardBean>();
		bean = oneboard.getAllOneboard();
		boolean A;
		if(bean.isEmpty()){
			A = false;
		}else{
			A = true;
		}
		assertTrue(A);
	} 

}
