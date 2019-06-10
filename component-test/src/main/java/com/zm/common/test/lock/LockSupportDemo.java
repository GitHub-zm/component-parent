package com.zm.common.test.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("进入线程1");
				LockSupport.park(this);
				System.out.println("线程1：park结束");
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("进入线程2");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				LockSupport.unpark(t1);
				System.out.println("线程2：unpark结束");
			}
		});
		t1.start();
		t2.start();
	}
}
