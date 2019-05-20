package com.zm.common.test.volatiledemo;

public class MainTest1 {

	// public volatile static Boolean flag = false;
	public static Boolean flag = false;

	public static void main(String[] args) throws InterruptedException {

		new Thread(() -> {
			int i = 0;
			while (!flag) {
				i = i + 1;
				// System.out.println(i);
			}

		}).start();
		Thread.sleep(1000);
		flag = true;
	}

}
