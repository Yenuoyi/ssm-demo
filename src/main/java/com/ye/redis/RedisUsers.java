package com.ye.redis;

import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.ye.domain.UserBean;

@Repository
public class RedisUsers {
	@Resource
	private RedisTemplate<String, Object> redisTemplate; 
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	ValueOperations<String, Object> valueops;
	public void setUser(String email,UserBean userBean){
		valueops.set(email, userBean);
	}
	public boolean isUser(String email){
		valueops = redisTemplate.opsForValue();
		return redisTemplate.hasKey(email);
	}
	public UserBean getUser(String email){
		valueops = redisTemplate.opsForValue();
		return (UserBean)valueops.get(email);
	}
	public void deleteUser(String email){
		valueops = redisTemplate.opsForValue();
		redisTemplate.delete(email);
	}
}
