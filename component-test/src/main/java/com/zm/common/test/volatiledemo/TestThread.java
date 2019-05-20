package com.zm.common.test.volatiledemo;

public class TestThread extends Thread {

	public TestThread() {
		super();
	}

	@Override
	public void run() {
		int i = 0;
		while (!MainTest2.flag) {
			i = i + 1;
			// System.out.println(i);
		}
	}

}
