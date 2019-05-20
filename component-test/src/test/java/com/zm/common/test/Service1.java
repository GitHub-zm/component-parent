package com.zm.common.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service1 {

	@Autowired
	private Service2 service2;

	private Logger logger = Logger.getLogger(this.getClass());

	public void method() {
		logger.info("Service1服务调用方法method！");

		service2.method();
		// do nothing
	}

}
