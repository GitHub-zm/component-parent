package com.zm.common.test.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收端
 * <li>指定端口,使用ServerSocket创建接收端
 * <li>阻塞式等待连接accept
 * <li>操作数据：输入输出流操作
 * <li>释放资源
 * 
 * @author zengming
 *
 */
public class Server {

	public static void main(String[] args) throws IOException {
		System.out.println("------Server------");
		// 指定端口,使用ServerSocket创建接收端
		ServerSocket server = new ServerSocket(8888);
		try {
			while (true) {
				// 阻塞式等待连接accept
				Socket client = server.accept();
				System.out.println("一个客户端建立连接");
				// 操作数据：输入输出流操作
				DataInputStream dis = new DataInputStream(client.getInputStream());
				String data = dis.readUTF();
				System.out.println(data);
				// 释放资源
				dis.close();
				// client.close();
			}
		} finally {
			server.close();
		}
	}

}
