package com.zm.common.test;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Service3 {

	private Logger logger = Logger.getLogger(this.getClass());

	public void method() {
		logger.info("Service3服务调用方法method！");
		// do nothing
	}

}
