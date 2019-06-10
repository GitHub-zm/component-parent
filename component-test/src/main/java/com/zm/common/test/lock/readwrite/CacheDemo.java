package com.zm.common.test.lock.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemo {
	/**
	 * 缓存器,这里假设需要存储1000左右个缓存对象，按照默认的负载因子0.75，则容量=750，大概估计每一个节点链表长度为5个
	 * 那么数组长度大概为：150,又有雨设置map大小一般为2的指数，则最近的数字为：128
	 */
	private Map<String, Object> map = new HashMap<>(128);
	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	public static void main(String[] args) {

	}

	public Object get(String id) {
		Object value = null;
		rwl.readLock().lock();// 首先开启读锁，从缓存中去取
		try {
			if (map.get(id) == null) { // 如果缓存中没有释放读锁，上写锁
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					if (value == null) { // 防止多写线程重复查询赋值
						value = "redis-value"; // 此时可以去数据库中查找，这里简单的模拟一下
					}
					rwl.readLock().lock(); // 加读锁降级写锁,不明白的可以查看上面锁降级的原理与保持读取数据原子性的讲解
				} finally {
					rwl.writeLock().unlock(); // 释放写锁
				}
			} else {
				value = map.get(id);
			}
		} finally {
			rwl.readLock().unlock(); // 最后释放读锁
		}
		return value;
	}
}