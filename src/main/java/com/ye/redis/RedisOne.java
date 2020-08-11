package com.ye.redis;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.ye.domain.OneboardBean;

@Repository
public class RedisOne {
	@Resource
	private StringRedisTemplate stringRedisTemplate; 
	
	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}
	ValueOperations<String, String> valueops;
	//添加专业至redis
	public void setOne(List<OneboardBean> list){
		String listString = JSONObject.toJSONString(list);
		valueops = stringRedisTemplate.opsForValue();
		valueops.set("oneboard", listString);
	}
	//从redis中获取专业
	public List<OneboardBean> getOne(){
		valueops = stringRedisTemplate.opsForValue();
		List<OneboardBean> list = JSONObject.parseArray(valueops.get("oneboard").toString(), OneboardBean.class);
		return list;
	}
	//从redis中删除专业
	public void deleteOne(){
		stringRedisTemplate.delete("oneboard");
	}
	//判断redis中是否拥有专业
	public boolean isOne(){
		return stringRedisTemplate.hasKey("oneboard");
	}
}
