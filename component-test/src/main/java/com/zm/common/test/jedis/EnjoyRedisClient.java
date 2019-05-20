package com.zm.common.test.jedis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * RESP协议
 * 
 * 手写redis客户端
 */
public class EnjoyRedisClient {
	private Socket socket;
	private OutputStream write;
	private InputStream read;

	public EnjoyRedisClient(String host, int port) throws IOException {
		socket = new Socket(host, port);
		write = socket.getOutputStream();
		read = socket.getInputStream();
	}

	public void set(String key, String val) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("*3").append("\r\n");// 代表3个参数
		sb.append("$3").append("\r\n");// 第一个参数的长度
		sb.append("SET").append("\r\n");// 第一个参数的内容

		sb.append("$").append(key.getBytes().length).append("\r\n");// 第二个参数长度
		sb.append(key).append("\r\n");// 第二个参数内容

		sb.append("$").append(val.getBytes().length).append("\r\n");// 第三个参数长度
		sb.append(val).append("\r\n");// 第三个参数内容

		write.write(sb.toString().getBytes());
		byte[] bytes = new byte[1024];
		read.read(bytes);
		System.out.println("set---------------------------------------------");
		System.out.println(new String(bytes));
	}

	public void get(String key) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("*2").append("\r\n");// 代表2个参数
		sb.append("$3").append("\r\n");// 第一个参数长度
		sb.append("GET").append("\r\n");// 第一个参数的内容

		sb.append("$").append(key.getBytes().length).append("\r\n");// 第二个参数长度
		sb.append(key).append("\r\n");// 第二个参数内容

		write.write(sb.toString().getBytes());
		byte[] bytes = new byte[1024];
		read.read(bytes);
		System.out.println("get---------------------------------------------");
		System.out.println(new String(bytes));
	}

	public static void main(String[] args) throws IOException {
		EnjoyRedisClient jedis = new EnjoyRedisClient("127.0.0.1", 6379);
		jedis.set("test", "001");
		jedis.get("test");
	}

}
