package com.ye.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ye.domain.TwoboardBean;
@Repository
public interface TwoboardMapper {
	//添加课程
	int addTwoboard(TwoboardBean twoboard);
	//根据课程id删除课程
	int deleteTwoboard(int two_id);
	//教师根据课程id修改课程名和简介
	int updateTwoboard(TwoboardBean twoboard);
	//管理员根据课程ID修改课程负责人
	int updateTwoboard_two_id(TwoboardBean twoboard);
	//管理员更改个人信息同时更改课程表中教师姓名 
	int updateTwoboard_two_name(@Param("two_teacher_name") String two_teacher_name,@Param("two_teacher_id") int two_teacher_id);
	//根据课程名查询指定课程
	List<TwoboardBean> getTwoboardtitle(String two_title);
	//根据教师姓名查课程
	List<TwoboardBean> getTwoboardteacher(String two_teacher_name);
	//根据教师id查课程
	List<TwoboardBean> getTwoboardteacher_id(@Param("two_teacher_id") int two_teacher_id, @Param("start") int start, @Param("end") int end);
	//根据教师id查课程总数
	int getTwoboardTeacherIdTotal(int two_teacher_id);
	//根据专业名查课程
	List<TwoboardBean> getTwoboardone_id(@Param("twoOneId")int twoOneId,@Param("start")int start, @Param("end")int end);
	//根据专业id获取专业总课程数
	int getTwoIdTotalByOneId(int two_one_id);
	//课程分页查看
	List<TwoboardBean> getTwoboardPage(@Param("twoOneId")int twoOneId,@Param("start")int start, @Param("end")int end);
	//根据课程id查课程
	TwoboardBean getTwoboardtwo_id(int two_id);
	//查询全部课程
	List<TwoboardBean> getAllTwoboard(@Param("start") int start, @Param("end") int end);
	//查询全部课程总数
	int getAllTwoboardTotal();
	//统计并修改课程评论数
	int update_two_statistics();
	//统计评论总数前五的课程
	List<TwoboardBean> two_statistics_five();
	//统计最新前十的课程
	List<TwoboardBean> two_ten();
}
