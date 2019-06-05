package com.zm.common.test.volatiledemo;

/**
 * 保证可见性，并不保证原子性（即保证读取的数据为最新，并不保证操作的原子性）
 *
 */
public class VolatileDemo {

	public volatile int inc = 0;

	public void increase() {
		inc++;
	}

	public static void main(String[] args) {
		final VolatileDemo test = new VolatileDemo();
		for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					for (int j = 0; j < 1000; j++)
						test.increase();
				};
			}.start();
		}
		while (Thread.activeCount() > 1) // 保证前面的线程都执行完
			Thread.yield();
		System.out.println(test.inc);
	}

}
