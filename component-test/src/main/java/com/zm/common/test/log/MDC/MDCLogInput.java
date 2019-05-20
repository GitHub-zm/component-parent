package com.zm.common.test.log.MDC;

import java.util.UUID;

import org.apache.log4j.MDC;

/**
 * 
 * MDC 实现原理---ThreadLocal
 *
 */

public class MDCLogInput {

	public void service() {

		/**
		 * 
		 */
		MDC.put("tranceId", UUID.randomUUID().toString());

		/**
		 * 用于分布式中tranceId传递，filter实现，减少代码的耦合
		 */
		MDC.get("tranceId");

	}

}
