package com.ye.emailstatistics;

import org.springframework.beans.factory.annotation.Autowired;

import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;

public class CommentAutoStatis {
	@Autowired
	private TwoboardService twoboardService;
	@Autowired
	private ThreeboardService threeboardService;
	public void autoStaticsTwoComment() throws Exception{
		//统计并更新所有课程评论数
		twoboardService.update_two_statistics();
		//统计并更新所有章节评论数
		threeboardService.updateThree_statistics();
	}
}
