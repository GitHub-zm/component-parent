package com.zm.common.test.spring.mvc;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CustomService("httpServletRequestArgumentResolver")
public class HttpServletRequestArgumentResolver implements ArgumentResolver {

	@Override
	public boolean support(Class<?> type, int paramIndex, Method method) {
		return ServletRequest.class.isAssignableFrom(type);
	}

	@Override
	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type,
			int paramIndex, Method method) {
		return request;
	}

}
