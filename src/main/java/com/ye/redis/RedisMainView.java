package com.ye.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.ye.domain.ThreeboardBean;
import com.ye.domain.TwoboardBean;

@Repository
public class RedisMainView {
	@Resource
	private StringRedisTemplate stringRedisTemplate; 
	public StringRedisTemplate getRedisTemplate() {
		return stringRedisTemplate;
	}
	public void setRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}
	ValueOperations<String, String> valueops;
	//将最近更新前十的课程添加至redis中，有效期半小时
	public void setTwoTen(List<TwoboardBean> list){
		String listString = JSONObject.toJSONString(list);
		//第一个key，第二个过期时间ms，第三个value
		valueops = stringRedisTemplate.opsForValue();
		valueops.set("twoTen", listString, 1, TimeUnit.HOURS);
	}
	//从redis中获取最新的前十课程
	public List<TwoboardBean> getTwoTen(){
		valueops = stringRedisTemplate.opsForValue();
		return JSONObject.parseArray(valueops.get("twoTen").toString(), TwoboardBean.class);
	}
	//判断redis中是否有最新的前十课程
	public boolean isTwoTen(){
		return stringRedisTemplate.hasKey("twoTen");
	}
	
	//添加热门前五课程
	public void setTwoFive(List<TwoboardBean> list){
		String listString = JSONObject.toJSONString(list);
		//第一个key，第二个过期时间ms，第三个value
		valueops = stringRedisTemplate.opsForValue();
		valueops.set("twoFive", listString, 1, TimeUnit.HOURS);
	}
	//获取热门前五课程
	public List<TwoboardBean> getTwoFive(){
		valueops = stringRedisTemplate.opsForValue();
		return JSONObject.parseArray(valueops.get("twoFive").toString(), TwoboardBean.class);
	}
	//判断热门前五课程是否在redis中存在
	public boolean isTwoFive(){
		return stringRedisTemplate.hasKey("twoFive");
	}
	
	//添加热门前五章节
	public void setThreeFive(List<ThreeboardBean> list){
		String listString = JSONObject.toJSONString(list);
		//第一个key，第二个过期时间ms，第三个value
		valueops = stringRedisTemplate.opsForValue();
		valueops.set("threeFive", listString, 1, TimeUnit.HOURS);
	}
	//获取热门前五章节
	public List<ThreeboardBean> getThreeFive(){
		valueops = stringRedisTemplate.opsForValue();
		return JSONObject.parseArray(valueops.get("threeFive").toString(), ThreeboardBean.class);
	}
	//判断热门前五章节是否在redis中存在
	public boolean isThreeFive(){
		return stringRedisTemplate.hasKey("threeFive");
	}
}
