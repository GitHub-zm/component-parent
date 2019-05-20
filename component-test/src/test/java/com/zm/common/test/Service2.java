package com.zm.common.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service2 {
	@Autowired
	private Service3 service3;
	private Logger logger = Logger.getLogger(this.getClass());

	public void method() {
		logger.info("Service2服务调用方法method！");

		service3.method();
		// do nothing
	}

}
