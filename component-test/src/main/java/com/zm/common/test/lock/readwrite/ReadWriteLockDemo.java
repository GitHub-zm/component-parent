package com.zm.common.test.lock.readwrite;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
	public static void main(String[] args) {
		// 死锁-锁升级，ReentrantReadWriteLock是不支持的
		// ReadWriteLock rtLock = new ReentrantReadWriteLock();
		// rtLock.readLock().lock();
		// System.out.println("get readLock.");
		// rtLock.writeLock().lock();
		// System.out.println("blocking");

		// ReentrantReadWriteLock支持锁降级
		ReadWriteLock rtLock = new ReentrantReadWriteLock();
		rtLock.writeLock().lock();
		System.out.println("writeLock");

		rtLock.readLock().lock();
		System.out.println("get read lock");
	}
}
