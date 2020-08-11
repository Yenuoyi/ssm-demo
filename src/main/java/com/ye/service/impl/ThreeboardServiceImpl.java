package com.ye.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ye.dao.ThreeboardMapper;
import com.ye.domain.ThreeboardBean;
import com.ye.redis.RedisGetThreeByTwoId;
import com.ye.redis.RedisMainView;
import com.ye.service.ThreeboardService;

@Service @Transactional
public class ThreeboardServiceImpl implements ThreeboardService{
	@Autowired
	public ThreeboardMapper threeboardMapper;
	@Autowired
	private RedisGetThreeByTwoId redisGetThreeByTwoId;
	@Autowired
	private RedisMainView redisMainView;
	int result;
	//添加课程目录
	@Override
	public int addThreeboard(ThreeboardBean threeboard) throws Exception {
		result = threeboardMapper.addThreeboard(threeboard);
		if(redisGetThreeByTwoId.isTwoId(threeboard.getThree_two_id())){
			redisGetThreeByTwoId.deleteTwoId(threeboard.getThree_two_id());
		}
		return result;
	}
	//删除课程目录
	@Override
	public int deleteThreeboard(ThreeboardBean threeboard) throws Exception {
		result = threeboardMapper.deleteThreeboard(threeboard);
		if(redisGetThreeByTwoId.isTwoId(threeboard.getThree_two_id())){
		redisGetThreeByTwoId.deleteTwoId(threeboard.getThree_two_id());
		}
		return result;
	}
	//通过课程id查询目录
	@Override
	public List<ThreeboardBean> getThreeboard_Bytwoid(int three_two_id) throws Exception {
		if(redisGetThreeByTwoId.isTwoId(three_two_id)){
			return redisGetThreeByTwoId.getThree(three_two_id);
		}else{
			List<ThreeboardBean> threeboard = threeboardMapper.getThreeboard_Bytwoid(three_two_id);
			redisGetThreeByTwoId.setThreeByTwoId(threeboard, three_two_id);
			return threeboard;
		}
	}
	//根据课程id获取最热门的章节
	@Override
	public ThreeboardBean getThreeboardTop_Bytwoid(int three_two_id) throws Exception {
		ThreeboardBean threeboard = threeboardMapper.getThreeboardTop_Bytwoid(three_two_id);
		return threeboard;
	}
	//查询全部目录
	@Override
	public List<ThreeboardBean> getAllThreeboard() throws Exception {
		List<ThreeboardBean> threeboard = threeboardMapper.getAllThreeboard();
		return threeboard;
	}
	@Override
	public ThreeboardBean getThreeboardthree_id(int three_id) throws Exception {
		ThreeboardBean threeboard = threeboardMapper.getThreeboardthree_id(three_id);
		return threeboard;
	}
	
	//获取评论数前五的章节
	@Override
	public List<ThreeboardBean> getthree_statistics_five() throws Exception {
		//如果redis中存在评论数前五章节的数据，则从redis中获取，否则则从数据库中获取并添加至redis，有效期半小时。
		if(redisMainView.isThreeFive()){
			return redisMainView.getThreeFive();
		}else{
			List<ThreeboardBean> threeboard_id_list = threeboardMapper.three_statistics_five();
			redisMainView.setThreeFive(threeboard_id_list);
			return threeboard_id_list;
		}
	}
	
	//统计评论中每章节的评论数并更新至three表中
	@Override
	public int updateThree_statistics() {
		return threeboardMapper.updateThree_statistics();
	}
	
}
