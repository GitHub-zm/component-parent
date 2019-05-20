package com.zm.common.test.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegisterChannel implements Runnable {

	private Socket client;

	private DataInputStream dis;

	private DataOutputStream dos;

	public RegisterChannel(Socket client) {
		super();
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
			dos = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// Do Nothing
		}

	}

	@Override
	public void run() {
		try {
			// 操作数据：输入输出流操作
			String data = dis.readUTF();
			// 分析数据
			String[] dataArray = data.split("&");
			for (String s : dataArray) {
				String[] info = s.split("=");
				System.out.println(info[0] + "--->" + info[1]);
			}
			// 输出
			if (dataArray.length == 2) {
				dos.writeUTF("登录成功，已跳转成功界面");
			} else {
				dos.writeUTF("登录失败，已跳转失败界面");
			}
			dos.close();
			// 释放资源
			dis.close();
			client.close();
		} catch (Exception e) {
			// Do Nothing
		}

	}

}
