package com.zm.common.rs.trace;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * 追溯上下文。
 * 
 */
public class TraceContext {
	/**
	 * 产生一个新的traceId。
	 * 
	 * @return
	 */
	public static String getNewTraceId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 取当前线程TraceId，如果已存在，直接返回；否则，产生新的traceId，并返回。
	 * 
	 * @return
	 */
	public static String getCurrentTraceId() {
		Object traceId = MDC.get(TraceConstants.LOG_TRACE_ID);
		if (traceId != null) {
			return (String) traceId;
		}
		return getNewTraceId();
	}

	/**
	 * 设置当前线程traceId。
	 * 
	 * @param traceId
	 *            跟踪Id，如果传入null或空字符串，则产生新traceId，并设值；否则，直接设值。
	 */
	public static void setThreadTraceId(String traceId) {
		if (StringUtils.isEmpty(traceId)) {
			traceId = TraceContext.getNewTraceId();
		}
		MDC.put(TraceConstants.LOG_TRACE_ID, traceId);
	}
}
