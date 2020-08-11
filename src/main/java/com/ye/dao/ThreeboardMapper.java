package com.ye.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ye.domain.ThreeboardBean;
@Repository
public interface ThreeboardMapper {
	//添加课程目录
	int addThreeboard(ThreeboardBean threeboard);
	//根据目录删除目录
	int deleteThreeboard(ThreeboardBean threeboard);
	//更新目录信息
	int updateThreeboard(ThreeboardBean threeboard);
	//根据章节名模糊搜索得出章节
	List<ThreeboardBean> getThreeboard(String three_title);
	//根据课程id获取章节
	List<ThreeboardBean> getThreeboard_Bytwoid(@Param("three_two_id") int three_two_id);
	//根据课程id获取最热门的章节
	ThreeboardBean getThreeboardTop_Bytwoid(@Param("three_two_id") int three_two_id);
	//根据章节id查询
	ThreeboardBean getThreeboardthree_id(int three_id);
	//统计前五章节
	List<ThreeboardBean> three_statistics_five();
	//获取所有章节
	List<ThreeboardBean> getAllThreeboard();
	//统计评论中每章节的评论数并更新至three表中
	int updateThree_statistics();
}
