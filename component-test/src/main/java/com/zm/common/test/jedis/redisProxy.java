package com.zm.common.test.jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 集群redis服务器-----分表分库
 * 
 * @author zengming
 *
 */
public class redisProxy {

	private static List<EnjoyRedisClient> clients = new ArrayList<>();

	static {
		try {
			clients.add(new EnjoyRedisClient("127.0.0.1", 80));
			clients.add(new EnjoyRedisClient("127.0.0.1", 81));
			clients.add(new EnjoyRedisClient("127.0.0.1", 82));
		} catch (IOException e) {
		}
	}

	public void set(String key, String val) throws IOException {
		// 取模
		clients.get(key.length() % 3).set(key, val);

	}

}
