package com.ye.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ye.dao.TwoboardMapper;
import com.ye.domain.TwoboardBean;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:ApplicationContext-dao.xml"}) //spring.xml
public class TwoboardMapperTest {

	int result;
	@Autowired
	private TwoboardMapper twoboardMapper;
	
	@Test
	public void testAddTwoboard() {
		TwoboardBean twoboard = new TwoboardBean();
		twoboard.setTwo_one_id(3);
		twoboard.setTwo_teacher_name("叶问");
		twoboard.setTwo_teacher_id(2);
		twoboard.setTwo_profile("这是一个晴朗的造成");
		twoboard.setTwo_title("早操");
		result = twoboardMapper.addTwoboard(twoboard);
		assertEquals(1,result);
	}

	@Test
	public void testDeleteTwoboard() {
		result = twoboardMapper.deleteTwoboard(26);
		assertEquals(1,result);
	}

	@Test
	public void testUpdateTwoboard() {
		TwoboardBean twoboard = new TwoboardBean();
		twoboard.setTwo_profile("这是一个晴朗的造成");
		twoboard.setTwo_title("早操");
		twoboard.setTwo_id(27);
		result = twoboardMapper.updateTwoboard(twoboard);
		assertEquals(1,result);
	}

	@Test
	public void testUpdateTwoboard_two_id() {
		TwoboardBean twoboard = new TwoboardBean();
		twoboard.setTwo_teacher_id(5);
		twoboard.setTwo_teacher_name("JJ");
		twoboard.setTwo_id(27);
		result = twoboardMapper.updateTwoboard(twoboard);
		assertEquals(1,result);
	}

	@Test
	public void testUpdateTwoboard_two_name() {
		result = twoboardMapper.updateTwoboard_two_name("JJ", 5);
	}

	@Test
	public void testGetTwoboardtitle() {
		twoboardMapper.getTwoboardtitle("早操");
	}

	@Test
	public void testGetTwoboardteacher() {
		twoboardMapper.getTwoboardteacher("叶冰");
	}

	@Test
	public void testGetTwoboardteacher_id() {
		twoboardMapper.getTwoboardteacher_id(1,0,10);
	}

	@Test
	public void testGetTwoboardone_id() {
		twoboardMapper.getTwoboardone_id(1,0,10);
	}

	@Test
	public void testGetTwoboardtwo_id() {
		twoboardMapper.getTwoboardtwo_id(1);
	}

	@Test
	public void testGetAllTwoboard() {
		twoboardMapper.getAllTwoboard(0,10);
	}

	@Test
	public void testUpdate_two_statistics() {
		twoboardMapper.update_two_statistics();
	}

	@Test
	public void testTwo_statistics_five() {
		twoboardMapper.two_statistics_five();
	}

	@Test
	public void testTwo_ten() {
		twoboardMapper.two_ten();
	}

}
