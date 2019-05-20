package com.zm.common.test.volatiledemo;

public class MainTest2 {

	public volatile static Boolean flag = false;
	// public static Boolean flag = false;

	public static void main(String[] args) throws InterruptedException {
		(new TestThread()).start();
		Thread.sleep(1000);
		flag = true;
	}

}
