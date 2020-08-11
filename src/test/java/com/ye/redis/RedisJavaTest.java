package com.ye.redis;


import redis.clients.jedis.Jedis;

public class RedisJavaTest {
    public static void main(String[] args) {
    	Jedis jedis = null;
    	try{
            //连接本地的 Redis 服务
            jedis = new Jedis("119.23.51.148",6379,10000);
            jedis.auth("123456");
            System.out.println("连接成功");
            //查看服务是否运行
            System.out.println(jedis);  
            System.out.println(jedis.set("yebing", "twoTen"));  
            jedis.lpush("list", "yebing");
            jedis.lpush("list", "tudou");
            jedis.lpushx("list", "erha","doubi");
            System.out.println(jedis.lrange("list", 0, 10));
            jedis.flushAll();
    	}finally{
    	}

    }
}
