package com.zm.common.test.jedis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * jedis客户端
 */
public class JedisClient {
	private Jedis jedis = null;

	public Jedis getJedis() {
		if (jedis == null) {
			// 连接redis，redis的默认端口是6379
			jedis = new Jedis("localhost", 6379);
		}
		return jedis;
	}

	/**
	 * redis提供了mget、mset方法，但没有提供mdel方法;<br>
	 * 如果要实现这个功能，可以借助Pipeline来模拟批量删除，Jedis支持Pipeline特性，可以通过jedis实现
	 * 
	 * @param keys
	 */
	public void mdel(List<String> keys) {
		Pipeline pipeline = this.jedis.pipelined();
		for (String key : keys) {
			pipeline.del(key); // 此时命令并非真正执行
		}
		// 真正执行命令
		pipeline.sync(); // 除了pipline.sync(),还可以使用pipeline.syncAndReturnAll()将pipeline的命令进行返回。
	}

	public static void main(String[] args) {
		JedisClient client = new JedisClient();
		Jedis jedis = client.getJedis();

		// 验证密码，如果没有设置密码这段代码省略
		jedis.auth("password");
		jedis.connect();
		jedis.set("key", "value");
		jedis.disconnect();// 断开连接

	}

}
