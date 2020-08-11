package com.ye.redis;

import java.util.List;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.alibaba.fastjson.JSONObject;
import com.ye.domain.UserBean;

public class Test {
	//redis通用配置
	private static final String redisHost = "119.23.51.148";
	private static final Integer redisPort = 6379;
	private static final String redisPass = "123456";
	//连接池
	private static JedisPool pool = new JedisPool(redisHost,redisPort);;
	public static void main(String[] args){
		Jedis jedis = pool.getResource();
		jedis.auth(redisPass);
		List<UserBean> list = JSONObject.parseArray(jedis.get("123456"),UserBean.class);
		System.out.println(list.get(0).getEmail());
		System.out.println(jedis.get("twoId"+16));
	}
}
