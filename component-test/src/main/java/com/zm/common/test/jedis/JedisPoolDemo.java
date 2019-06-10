package com.zm.common.test.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolDemo {

	// Jedispool
	JedisCommands jedisCommands;
	JedisPool jedisPool;
	// common-pool 连接池配置，
	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	String ip = "192.168.58.99";
	int port = 6379;
	int timeout = 2000;

	public JedisPoolDemo() {
		// 初始化jedis
		// 设置配置
		jedisPoolConfig.setMaxTotal(1024);
		jedisPoolConfig.setMaxIdle(100);
		jedisPoolConfig.setMaxWaitMillis(100);
		jedisPoolConfig.setTestOnBorrow(false);
		jedisPoolConfig.setTestOnReturn(true);
		// 初始化JedisPool
		jedisPool = new JedisPool(jedisPoolConfig, ip, port, timeout);
		//
		Jedis jedis = jedisPool.getResource();

		jedisCommands = jedis;
	}

	public void setValue(String key, String value) {
		this.jedisCommands.set(key, value);
	}

	public String getValue(String key) {
		return this.jedisCommands.get(key);
	}

	public static void main(String[] args) {
		JedisPoolDemo testJedis = new JedisPoolDemo();
		testJedis.setValue("testJedisKey", "testJedisValue");
	}

}
