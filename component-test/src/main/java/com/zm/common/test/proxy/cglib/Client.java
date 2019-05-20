package com.zm.common.test.proxy.cglib;

import com.zm.common.test.proxy.RealSubject;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class Client {
	public static void main(String[] args) {
		// 代理类class文件存入本地磁盘方便我们反编译查看源码
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
		// 通过CGLIB动态代理获取代理对象的过程
		Enhancer enhancer = new Enhancer();
		// 设置enhancer对象的父类
		enhancer.setSuperclass(RealSubject.class);
		// 设置enhancer的回s调对象
		enhancer.setCallback(new DynamicProxy());
		// 创建代理对象
		RealSubject subject = (RealSubject) enhancer.create();
		// 通过代理对象调用目标方法

		subject.rent();
		subject.hello("world");
		String value = subject.get();
		System.out.println("get返回值：" + value);
	}

}
