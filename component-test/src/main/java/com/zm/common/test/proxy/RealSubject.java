package com.zm.common.test.proxy;

public class RealSubject implements Subject {
	@Override
	public void rent() {
		System.out.println("I want to rent my house");
	}

	@Override
	public void hello(String str) {
		System.out.println("hello: " + str);
	}

	@Override
	public String get() {
		return "hello world!";
	}
}
