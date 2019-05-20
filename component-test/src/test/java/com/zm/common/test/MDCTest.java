package com.zm.common.test;

import java.util.UUID;

import org.apache.log4j.MDC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@ContextConfiguration(classes = xxxTestConfiguration.class)
@ContextConfiguration("classpath:test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MDCTest {

	@Autowired
	private Service1 service1;

	@Test
	public void test() {
		// 实现
		MDC.put("tranceId", "tranceId:" + UUID.randomUUID().toString());
		service1.method();
		MDC.get("tranceId");
		System.out.println("CONSOLE输出tranceId：" + MDC.get("tranceId"));
	}

}
