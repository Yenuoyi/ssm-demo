package com.ye.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ye.domain.CommentBean;
import com.ye.domain.ThreeboardBean;
import com.ye.domain.TwoboardBean;
import com.ye.domain.UserBean;
@Repository
public interface StatisticsMapper {
	//统计一周内评论数
	int commentCount();
	//统计一周内最热门的前五课程
	List<TwoboardBean> twoCount();
	//统计一周内最热门的前五专题
	 List<ThreeboardBean> threeCount();
	//统计一周内回答次数最多的前五教师id以及评论数
	List<CommentBean> teacherCountByCount(); 
	//获取统计一周内回答次数最多的前五教师详细信息
	List<UserBean> teacherCountBDetail(List<Integer> list);
}
