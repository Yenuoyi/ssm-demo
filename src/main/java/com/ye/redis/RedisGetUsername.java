package com.ye.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisGetUsername {
	@Resource
	private RedisTemplate<String, Object> redisTemplate; 
	ValueOperations<String, Object> valueops;
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	//获取用户名
	public String getUsername(String userIP){
		valueops = redisTemplate.opsForValue();
		String username = null;
		if(redisTemplate.hasKey(userIP)){
			username = valueops.get(userIP).toString();
		}
		return username;
	}
	//往redis中添加用户名
	public void addUsername(String userIP,String username){
		valueops = redisTemplate.opsForValue();
		valueops.set(userIP, username, 1, TimeUnit.HOURS);
	}
}
