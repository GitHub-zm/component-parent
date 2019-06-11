package com.zm.common.test.spring.mvc;

@CustomService("MyServiceImpl")
public class MyServiceImpl implements MyService {

	@Override
	public String query(String name, String age) {
		return "name:" + name + ";age:" + age;
	}

}
