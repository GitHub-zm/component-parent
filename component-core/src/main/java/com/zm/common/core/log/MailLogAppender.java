package com.zm.common.core.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 */
public class MailLogAppender extends AppenderSkeleton {

	@Override
	public void close() {
		closed = true;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		try {
			LogMailHelper.getDefault().send(event);
		} catch (Exception e) {
			errorHandler.error("", e, ErrorCode.GENERIC_FAILURE, event);
		}
	}

}
