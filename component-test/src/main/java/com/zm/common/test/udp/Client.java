package com.zm.common.test.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import com.zm.common.test.io.IOUtils;

/**
 * 发送端
 * <li>使用DatagramSocket 指定端口创建发送端
 * <li>准备数据，转成字节数组
 * <li>封装成DatagramPacket包裹,需要指定目的地址
 * <li>发送包裹send(DatagramPacket)
 * <li>释放资源
 * 
 * @author zengming
 *
 */
public class Client {
	public static void main(String[] args) throws IOException {
		System.out.println("发送方启动中......");
		// 使用DatagramSocket 指定端口创建发送端
		DatagramSocket client = new DatagramSocket(8888);
		// 准备数据，转成字节数组
		Data data = new Data();
		data.setId("007");
		data.setName("周星驰");
		data.setMessage("大内密探");
		data.setNote("猪肉王子");
		byte[] datas = IOUtils.toBytes(data);
		// 封装成DatagramPacket包裹,需要指定目的地址
		DatagramPacket packet = new DatagramPacket(datas, 0, datas.length, new InetSocketAddress("localhost", 6666));
		// 发送包裹send(DatagramPacket)
		client.send(packet);
		client.close();
	}

}
