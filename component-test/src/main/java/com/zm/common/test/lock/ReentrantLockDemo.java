package com.zm.common.test.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 */
public class ReentrantLockDemo {

	private int value = 0;
	private Lock lock = new ReentrantLock(); // 注意这个地方，声明为类的属性

	public static void main(String[] args) {
		final ReentrantLockDemo demo = new ReentrantLockDemo();

		new Thread() {
			public void run() {
				demo.add(Thread.currentThread());
			};
		}.start();
		// 可以用Java箭头函数特性改写上述冗余代码：
		// new Thread(){()->Thread.currentThread}.start();
		new Thread() {
			public void run() {
				demo.otherAdd(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				demo.otherAdd(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				demo.otherAdd(Thread.currentThread());
			};
		}.start();
	}

	public void add(Thread thread) {
		lock.lock();
		try {
			System.out.println(thread.getName() + "得到了锁");
			for (int i = 0; i < 5; i++) {
				value++;
				System.out.println(value);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			System.out.println(thread.getName() + "释放了锁");
			lock.unlock();
		}
	}

	public void otherAdd(Thread thread) {
		lock.lock();
		try {
			System.out.println(thread.getName() + "得到了锁");
			for (int i = 0; i < 5; i++) {
				value++;
				System.out.println(value);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			System.out.println(thread.getName() + "释放了锁");
			lock.unlock();
		}
	}

}
