package com.zm.common.test.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RegisterServer {

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
				// 分析数据
				String[] dataArray = data.split("&");
				for (String s : dataArray) {
					String[] info = s.split("=");
					System.out.println(info[0] + "--->" + info[1]);
				}
				// 输出
				DataOutputStream dos = new DataOutputStream(client.getOutputStream());
				if (dataArray.length == 2) {
					dos.writeUTF("登录成功，已跳转成功界面");
				} else {
					dos.writeUTF("登录失败，已跳转失败界面");
				}
				dos.close();
				// 释放资源
				dis.close();
				// client.close();
			}

		} finally {
			server.close();
		}
	}

}
