package com.zm.common.rs.springclient;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.zm.common.rs.trace.TraceConstants;
import com.zm.common.rs.trace.TraceContext;

/**
 * 客户端请求Trace拦截器。
 * 
 */
public class ClientHttpRequestTraceInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		request.getHeaders().add(TraceConstants.LOG_TRACE_ID, TraceContext.getCurrentTraceId());

		return execution.execute(request, body);
	}

}
