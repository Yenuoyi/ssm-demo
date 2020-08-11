package com.ye.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ye.dao.TwoboardMapper;
import com.ye.domain.TwoboardBean;
import com.ye.redis.RedisMainView;
import com.ye.service.TwoboardService;

@Service @Transactional
public class TwoboardServiceImpl implements TwoboardService{
	@Autowired
	public TwoboardMapper twoboardMapper;
	@Autowired
	private RedisMainView redisMainView;
	int result;
	@Override
	public int addTwoboard(TwoboardBean twoboard) throws Exception {
		result = twoboardMapper.addTwoboard(twoboard);
		return result;
	}
	@Override
	public int deleteTwoboard(int two_id) throws Exception {
		result = twoboardMapper.deleteTwoboard(two_id);
		return result;
	}
	//根据课程id，修改课程名和简介
	@Override
	public int updateTwoboard(TwoboardBean twoboard) throws Exception {
		result = twoboardMapper.updateTwoboard(twoboard);
		return result;
	}
	//根据课程id更新
	@Override
	public int updateTwoboard_two_id(TwoboardBean twoboard) throws Exception {
		result = twoboardMapper.updateTwoboard_two_id(twoboard);
		return result;
	}
	//根据课程名获得课程
	@Override
	public List<TwoboardBean> getTwoboardtitle(String two_title) throws Exception {
		List<TwoboardBean> twoboard = twoboardMapper.getTwoboardtitle(two_title);
		return twoboard;
	}
	//根据教师名获得课程
	@Override
	public List<TwoboardBean> getTwoboardteacher(String two_teacher_name) throws Exception {
		List<TwoboardBean> twoboard = twoboardMapper.getTwoboardteacher(two_teacher_name);
		return twoboard;
	}
	//根据专业信息获得课程
	@Override
	public List<TwoboardBean> getTwoboardone_id(int two_one_id) throws Exception {
		List<TwoboardBean> twoboard = twoboardMapper.getTwoboardone_id(two_one_id,0,10);
		return twoboard;
	}
	//根据专业id查询专业课程总数
	public int getTwoIdTotalByOneId(int two_one_id) throws Exception{
		int result;
		result = twoboardMapper.getTwoIdTotalByOneId(two_one_id);
		if(result%10==0){
			result = result/10;
		}else{
			result = result/10+1;
		}
		return result;
	}
	//专业课程分页查看
	public String getTwoboardPage(int twoOneId,int start, int end){
		List<TwoboardBean> twoboard = twoboardMapper.getTwoboardone_id(twoOneId,start,end);
		return JSONObject.toJSONString(twoboard);
	}
	//根据课程id获得课程
	@Override
	public TwoboardBean getTwoboardtwo_id(int two_id) throws Exception {
		TwoboardBean twoboard = twoboardMapper.getTwoboardtwo_id(two_id);
		return twoboard;
	}
	//初始化获取所有的课程
	@Override
	public List<TwoboardBean> getAllTwoboard(int start, int end) throws Exception {
		List<TwoboardBean> twoboard = twoboardMapper.getAllTwoboard(start,end);
		return twoboard;
	}
	//所有课程分页
	@Override
	public String getAllTwoboardPage(int start, int end) throws Exception {
		return JSONObject.toJSONString(twoboardMapper.getAllTwoboard(start, end));
	}
	//获取所有课程总数
	@Override
	public int getAllTwoboardTotal() throws Exception{
		int result = twoboardMapper.getAllTwoboardTotal();
		if(result%10==0){
			result = result/10;
		}else{
			result = result/10+1;
		}
		return result;
	}
	
	//更新所有课程统计
	@Override
	public void update_two_statistics() throws Exception {
		twoboardMapper.update_two_statistics();
	}
	
	//获取评论前五的课程
	public List<TwoboardBean> gettwo_statistics_five() throws Exception{
		if(redisMainView.isTwoFive()){
			return redisMainView.getTwoFive();
		}else{
			List<TwoboardBean> twoboard_id_list;
			twoboard_id_list = twoboardMapper.two_statistics_five();
			redisMainView.setTwoFive(twoboard_id_list);
			return twoboard_id_list; 
		}
	}
	//获取最新的前十课程
	@Override
	public List<TwoboardBean> getTwo_ten() throws Exception {
		if(redisMainView.isTwoTen()){
			List<TwoboardBean> list = redisMainView.getTwoTen();
			return list;
		}else{
			List<TwoboardBean> twoboard_id_list;
			twoboard_id_list = twoboardMapper.two_ten();
			redisMainView.setTwoTen(twoboard_id_list);
			return twoboard_id_list; 
		}
	}
	//根据教师id查课程
	@Override
	public List<TwoboardBean> getTwoboardteacher_id(int two_teacher_id,int start,int end) throws Exception {
		List<TwoboardBean> twoboard = twoboardMapper.getTwoboardteacher_id(two_teacher_id,start,end);
		return twoboard;
	}
	//教师id课程分页
	public String getTwoboardTeacherIdPage(int two_teacher_id,int start,int end) throws Exception {
		return JSONObject.toJSONString(twoboardMapper.getTwoboardteacher_id(two_teacher_id, start, end));
	}
	//根据教师id查课程总数
	public int getTwoboardTeacherIdTotal(int two_teacher_id) throws Exception {
		int result = twoboardMapper.getTwoboardTeacherIdTotal(two_teacher_id);
		if(result%10==0){
			result = result/10;
		}else{
			result = result/10+1;
		}
		return result;
	}
	//管理员更改个人信息同时更改课程表中教师姓名 
	@Override
	public int updateTwoboard_two_name(String two_teacher_name, int two_teacher_id) throws Exception {
		result = twoboardMapper.updateTwoboard_two_name(two_teacher_name, two_teacher_id);
		return 0;
	}
}
