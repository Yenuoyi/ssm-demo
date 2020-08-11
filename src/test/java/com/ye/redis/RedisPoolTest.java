package com.ye.redis;

import com.ye.domain.UserBean;
import com.ye.util.SerializeUtilTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPoolTest {
	//redis通用配置
	private static final String redisHost = "119.23.51.148";
	private static final Integer redisPort = 6379;
	private static final String redisPass = "123456";
	//连接池
	private static JedisPool pool = null;
	public RedisPoolTest(){
		pool = new JedisPool(redisHost,redisPort);
	}
	public static void main(String[] args){
		Jedis jedis = pool.getResource();
		jedis.auth(redisPass);
		UserBean user = new UserBean();
		user.setEmail("19689405@qq.com");
//		jedis.set("19689405@qq.com".getBytes(),SerializeUtil.ObjTOSerialize(user));
		System.out.println(jedis.ping());
		System.out.println(SerializeUtilTest.unSerialize(jedis.get("847977675@qq.com".getBytes())));
		System.out.println(jedis.exists("847977675@qq.com".getBytes()));
	}
}
