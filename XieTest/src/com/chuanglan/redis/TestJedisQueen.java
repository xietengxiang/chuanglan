package com.chuanglan.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class TestJedisQueen {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.auth("Chuanglan$@#@45");
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("memberId", "888");
		logMap.put("mobile", "13817694186");
		logMap.put("logInfo", "Test Logger to redis to db...");
		addLogToRedis(jedis, logMap);
		addLogToRedis(jedis, logMap);
		System.out.println("===");
		

		List<String> list = jedis.lrange("log:list:888:13817694186",0,-1);
		Map<String, String> map = jedis.hgetAll("nokey");
//		jedis.del("log:list:888:13817694186");
		System.out.println(list);
		System.out.println(map);
	}
	
	/**
	 * 新增日志队列
	 * @param logMap
	 */
	public static void addLogToRedis( Jedis jedis, Map<String, String> logMap ){
		
		//提交到REDIS
		jedis.lpush(String.format("log:list:%s:%s", logMap.get("memberId"), logMap.get("mobile")) , logMap.get("logInfo"));
	}
}
