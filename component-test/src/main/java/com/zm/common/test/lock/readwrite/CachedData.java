package com.zm.common.test.lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CachedData {
	Object data;
	volatile boolean cacheValid;
	final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	public void processCachedData() {
		rwl.readLock().lock();
		if (!cacheValid) {
			// Must release read lock before acquiring write lock
			rwl.readLock().unlock();
			rwl.writeLock().lock();
			try {
				// Recheck state because another thread might have,acquired write lock and
				// changed state before we did.
				if (!cacheValid) {
					data = new Object();
					cacheValid = true;
				}
				// 在释放写锁之前通过获取读锁降级写锁(注意此时还没有释放写锁)
				rwl.readLock().lock();
			} finally {
				rwl.writeLock().unlock(); // 释放写锁而此时已经持有读锁
			}
		}

		try {
			use(data);
		} finally {
			rwl.readLock().unlock();
		}
	}

	private void use(Object data2) {
		// Do Nothing

	}

}