package com.zm.common.test.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class RegisterClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("------Client------");

		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入用户名：");
		String userName = console.readLine();
		System.out.println("请输入密码：");
		String pwd = console.readLine();
		// 建立连接,使用Socket创建客户端-----需要服务端地址和端口
		Socket client = new Socket("localhost", 8888);
		// 操作数据：输入输出流操作
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		String data = "userName=" + userName + "&pwd=" + pwd;
		dos.writeUTF(data);
		dos.flush();
		// 接受服务端返回信息
		DataInputStream dis = new DataInputStream(client.getInputStream());
		String result = dis.readUTF();
		System.out.println(result);
		// 释放资源
		dos.close();
		client.close();
	}

}
