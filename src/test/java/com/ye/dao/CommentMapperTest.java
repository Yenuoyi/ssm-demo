package com.ye.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ye.dao.CommentMapper;
import com.ye.domain.CommentBean;

//spring加载注入bean文件
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:ApplicationContext-dao.xml"}) //spring.xml

public class CommentMapperTest {

	@Autowired
	private CommentMapper commentMapper;
	@Test
	public void testAddComment() {
		int result;
		CommentBean comment = new CommentBean();
		comment.setComment_content("单元测试就则个样子");
		comment.setComment_name("叶问");
		comment.setComment_name_id(2);
		comment.setComment_three_id(16);
		comment.setComment_two_id(25);
		result = commentMapper.addComment(comment);
		assertEquals(1, result);
	}

	@Test
	public void testDeleteComment() {
		int result;
		result = commentMapper.deleteComment(16);
		assertEquals(1,result);
	}

	@Test
	public void testUpdateCommentname() {
		commentMapper.updateCommentname("叶冰", 1);
	}

	@Test
	public void testGetComment() {
		commentMapper.getComment(9,0,10);
	}

}
