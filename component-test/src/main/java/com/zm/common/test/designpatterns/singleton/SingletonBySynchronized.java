package com.zm.common.test.designpatterns.singleton;

/**
 * <li>懒加载
 * <li>线程安全（使用双重校验锁方式实现单例）
 */
public class SingletonBySynchronized {
	private volatile static SingletonBySynchronized singleton;

	private SingletonBySynchronized() {
	}

	public static SingletonBySynchronized getSingleton() {
		if (singleton == null) {
			synchronized (SingletonBySynchronized.class) {
				if (singleton == null) {
					singleton = new SingletonBySynchronized();
				}
			}
		}
		return singleton;
	}

}
