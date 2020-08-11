package com.ye.service;

import java.util.List;

import com.ye.domain.TwoboardBean;

public interface TwoboardService {
	//添加课程
	int addTwoboard(TwoboardBean twoboard) throws Exception;
	//根据课程id删除课程
	int deleteTwoboard(int two_id) throws Exception;
	//教师根据课程id修改课程信息
	int updateTwoboard(TwoboardBean twoboard) throws Exception;
	//管理员根据课程ID修改课程负责人
	int updateTwoboard_two_id(TwoboardBean twoboard) throws Exception;
	//管理员更改个人信息同时更改课程表中教师姓名 
	int updateTwoboard_two_name(String two_teacher_name,int two_teacher_id) throws Exception;
	//根据课程名查询指定课程
	List<TwoboardBean> getTwoboardtitle(String two_title) throws Exception;
	//根据教师姓名查课程
	List<TwoboardBean> getTwoboardteacher(String two_teacher_name) throws Exception;
	//根据教师id查课程
	List<TwoboardBean> getTwoboardteacher_id(int two_teacher_id, int start, int end) throws Exception;
	//分页查询教师id课程
	String getTwoboardTeacherIdPage(int two_teacher_id, int start, int end) throws Exception;
	//根据教师id查课程总数
	int getTwoboardTeacherIdTotal(int two_teacher_id) throws Exception;
	//根据专业名查课程
	List<TwoboardBean> getTwoboardone_id(int two_one_id) throws Exception;
	//查询专业所有课程总数
	int getTwoIdTotalByOneId(int two_one_id) throws Exception;
	//专业课程分页接口
	String getTwoboardPage(int twoOneId,int start, int end);
	//根据课程id查课程
	TwoboardBean getTwoboardtwo_id(int two_id) throws Exception;
	//查询全部课程
	List<TwoboardBean> getAllTwoboard(int start, int end) throws Exception;
	//全部课程分页
	String getAllTwoboardPage(int start, int end) throws Exception;
	//查询全部课程总数
	int getAllTwoboardTotal() throws Exception;
	//修改课程评论数
	void update_two_statistics() throws Exception;
	//统计评论总数前五的课程
	List<TwoboardBean> gettwo_statistics_five() throws Exception;
	//统计最新前十的课程
	List<TwoboardBean> getTwo_ten() throws Exception;

}
