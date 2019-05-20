package com.zm.common.test.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端
 * <li>建立连接,使用Socket创建客户端-----需要服务端地址和端口
 * <li>操作数据：输入输出流操作
 * <li>释放资源
 * 
 * @author zengming
 *
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("------Client------");
		// 建立连接,使用Socket创建客户端-----需要服务端地址和端口
		Socket client = new Socket("localhost", 8888);
		// 操作数据：输入输出流操作
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		String data = "Hello World, I Am Client";
		dos.writeUTF(data);
		dos.flush();
		// 释放资源
		dos.close();
		client.close();
	}

}
