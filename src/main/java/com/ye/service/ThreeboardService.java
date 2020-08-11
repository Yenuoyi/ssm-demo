package com.ye.service;

import java.util.List;
import com.ye.domain.ThreeboardBean;

public interface ThreeboardService {
	//创建目录
	int addThreeboard(ThreeboardBean threeboard) throws Exception;
	//删除目录
	int deleteThreeboard(ThreeboardBean threeboard) throws Exception;
	//根据课程id查询所属目录
	List<ThreeboardBean> getThreeboard_Bytwoid(int three_two_id) throws Exception;
	//根据课程id获取最热门的章节
	ThreeboardBean getThreeboardTop_Bytwoid(int three_two_id) throws Exception;
	//根据章节id查章节
	ThreeboardBean getThreeboardthree_id(int three_id) throws Exception;
	//统计评论总数前五的课程
	List<ThreeboardBean> getthree_statistics_five() throws Exception;
	//查询全部目录
	List<ThreeboardBean> getAllThreeboard() throws Exception;
	//统计评论中每章节的评论数并更新至three表中
	int updateThree_statistics();
}
