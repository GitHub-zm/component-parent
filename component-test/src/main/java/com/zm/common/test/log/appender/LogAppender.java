package com.zm.common.test.log.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

public class LogAppender extends AppenderSkeleton {

	@Override
	protected void append(LoggingEvent event) {
		try {
			String message = event.getMessage().toString();

			// bean初始化完成,执行initLifecycleProcessor
			if (message.startsWith(MessageCollector.BEAN_FINISHED_FLAG)) {
				MessageCollector.getDefault().addMessage(MessageCollector.BEAN_FINISHED_FLAG);
			}

			// 执行SmartLifecycle的start之前，执行了isAutoStartup()\getPhase()
			if (message.startsWith(MessageCollector.SMARTLIFECYCLE_START_FLAG)) {
				MessageCollector.getDefault().addMessage(MessageCollector.SMARTLIFECYCLE_START_FLAG);
			}
		} catch (Exception e) {
			errorHandler.error("", e, ErrorCode.GENERIC_FAILURE, event);
		}
	}

	@Override
	public void close() {
		closed = true;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}
