package com.zm.common.test.io;

import com.zm.common.test.udp.Data;

public class Test {

	public static void main(String[] args) throws Exception {
		Data data = new Data();
		data.setId("id1");
		data.setName("name1");
		data.setMessage("message1");
		data.setNote("note1");
		byte[] datas = IOUtils.toBytes(data);
		System.out.println(datas.length);
		Data returnData = IOUtils.toObject(datas);

		System.out.println("返回数据：" + returnData.getId());
		System.out.println("返回数据：" + returnData.getName());
		System.out.println("返回数据：" + returnData.getMessage());
		System.out.println("返回数据：" + returnData.getNote());

		// 基本类型
		// byte[] datas = ByteUtils.toBytes(1);
		// System.out.println(datas);
		// int returnData = ByteUtils.toObject(datas);
		// System.out.println(returnData);

	}

}
