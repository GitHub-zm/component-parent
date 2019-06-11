package com.zm.common.test.designpatterns.singleton;

/**
 * 一种更好的单例模式实现方式
 * <li>懒加载
 * <li>线程安全（由JVM保证线程的安全）
 */
public class SingletonByHolder {

	private SingletonByHolder() {
	}

	public static SingletonByHolder getSingleton() {
		return SingletonHolder.singleton;
	}

	static class SingletonHolder {
		private static SingletonByHolder singleton = new SingletonByHolder();
	}

}
