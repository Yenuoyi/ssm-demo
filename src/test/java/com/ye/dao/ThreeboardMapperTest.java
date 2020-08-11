package com.ye.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ye.dao.ThreeboardMapper;
import com.ye.domain.ThreeboardBean;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:ApplicationContext-dao.xml"}) //spring.xml

public class ThreeboardMapperTest {

	int result;
	@Autowired
	private ThreeboardMapper threeboardMapper;
	
	@Test
	public void testAddThreeboard() {
		ThreeboardBean threeboard = new ThreeboardBean();
		threeboard.setThree_profile("不知道啥玩意，测试用着线");
		threeboard.setThree_title("你说啥");
		threeboard.setThree_two_id(18);
		result = threeboardMapper.addThreeboard(threeboard);
		assertEquals(1,result);
	}

	@Test
	public void testDeleteThreeboard() {
		ThreeboardBean threeboard = new ThreeboardBean();
		threeboard.setThree_profile("不知道啥玩意，测试用着线");
		threeboard.setThree_title("你说啥");
		threeboard.setThree_two_id(188);
		result = threeboardMapper.deleteThreeboard(threeboard);
		assertEquals(1,result);
	}

	@Test
	public void testUpdateThreeboard() {
		ThreeboardBean threeboard = new ThreeboardBean();
		threeboard.setThree_profile("好好学习，天天向上");
		threeboard.setThree_title("雅思");
		threeboard.setThree_id(3);
		result = threeboardMapper.updateThreeboard(threeboard);
		assertEquals(1,result);
	}

	@Test
	public void testGetThreeboard() {
		threeboardMapper.getThreeboard("雅思");
	}

	@Test
	public void testGetThreeboard_Bytwoid() {
		threeboardMapper.getThreeboard_Bytwoid(3);
	}

	@Test
	public void testGetThreeboardthree_id() {
		threeboardMapper.getThreeboard_Bytwoid(9);
	}

	@Test
	public void testThree_statistics_five() {
		threeboardMapper.three_statistics_five();
	}

	@Test
	public void testGetAllThreeboard() {
		threeboardMapper.getAllThreeboard();
	}

}
