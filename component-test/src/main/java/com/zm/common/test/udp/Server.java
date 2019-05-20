package com.zm.common.test.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.zm.common.test.io.IOUtils;

/**
 * 接收端
 * <li>使用DatagramSocket 指定端口创建接收端
 * <li>使用容器封装成DatagramPacket包裹
 * <li>阻塞式接收包裹Receive(DatagramPacket)
 * <li>分析数据
 * <ul>
 * <li>byte[] getData();
 * <li>getLength()
 * </ul>
 * <li>释放资源
 * 
 * @author zengming
 *
 */
public class Server {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("接收方启动中......");
		// 使用DatagramSocket 指定端口创建接收端
		// DatagramSocket client = new DatagramSocket(8888);//端口已被占用
		DatagramSocket server = new DatagramSocket(6666);
		// 使用容器封装成DatagramPacket包裹
		byte[] container = new byte[1024 * 60];
		DatagramPacket packet = new DatagramPacket(container, 0, container.length);
		// 阻塞式接收包裹Receive(DatagramPacket)
		server.receive(packet);// 阻塞式
		// 分析数据
		byte[] datas = packet.getData();
		Data data = IOUtils.toObject(datas);

		System.out.println("Id：" + data.getId());
		System.out.println("Name：" + data.getName());
		System.out.println("Message：" + data.getMessage());
		System.out.println("Note：" + data.getNote());
		server.close();
	}

}
