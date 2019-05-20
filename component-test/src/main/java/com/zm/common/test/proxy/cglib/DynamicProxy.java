package com.zm.common.test.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

//CGLIB动态代理
//1. 首先实现一个MethodInterceptor，方法调用会被转发到该类的intercept()方法。
public class DynamicProxy implements MethodInterceptor {

	/**
	 * sub：cglib生成的代理对象 method：被代理对象方法 objects：方法入参 methodProxy: 代理方法
	 */
	@Override
	public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("======插入前置通知======");
		Object object = methodProxy.invokeSuper(sub, objects);
		System.out.println("======插入后者通知======");
		return object;
	}

}
