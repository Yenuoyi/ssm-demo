package com.ye.redis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.ye.domain.ThreeboardBean;

@Repository
public class RedisGetThreeByTwoId {
	@Resource
	private RedisTemplate<String, Object> redisTemplate; 
	ValueOperations<String, Object> valueops;
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	//在redis中加入课程目录
	public void setThreeByTwoId(List<ThreeboardBean> list,int twoId){
		String listString = JSONObject.toJSONString(list);
		valueops = redisTemplate.opsForValue();
		valueops.set("twoId"+twoId, listString);
		
	}
	//从redis中删除课程
	public void deleteTwoId(int twoId){
		redisTemplate.delete("twoId"+twoId);
	}
	//判断redis中是否存在这个课程目录
	public boolean isTwoId(int twoId){
		return redisTemplate.hasKey("twoId"+twoId);
	}
	//获取课程目录
	public List<ThreeboardBean> getThree(int twoId){
		valueops = redisTemplate.opsForValue();
		List<ThreeboardBean> list = JSONObject.parseArray(valueops.get(("twoId"+twoId)).toString(), ThreeboardBean.class);
		return list;
	}
}
