package com.zm.common.rs.trace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

/**
 *
 */
public class TraceOutInterceptor extends AbstractLoggingInterceptor {
	private static final Logger LOG = LogUtils.getLogger(TraceOutInterceptor.class);

	/**
	 * @param phase
	 */
	public TraceOutInterceptor() {
		super(Phase.USER_LOGICAL);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));
		List<String> traceIds = headers.get(TraceConstants.LOG_TRACE_ID);
		if (traceIds == null) {
			traceIds = new ArrayList<String>();
			traceIds.add(TraceContext.getCurrentTraceId());
			headers.put(TraceConstants.LOG_TRACE_ID, traceIds);
		} else if (traceIds.isEmpty()) {
			traceIds.add(TraceContext.getCurrentTraceId());
		}
	}

	@Override
	protected Logger getLogger() {
		return LOG;
	}

}
