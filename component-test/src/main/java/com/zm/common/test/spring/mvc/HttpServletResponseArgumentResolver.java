package com.zm.common.test.spring.mvc;

import java.lang.reflect.Method;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CustomService("httpServletResponseArgumentResolver")
public class HttpServletResponseArgumentResolver implements ArgumentResolver {

	@Override
	public boolean support(Class<?> type, int paramIndex, Method method) {
		return ServletResponse.class.isAssignableFrom(type);
	}

	@Override
	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type,
			int paramIndex, Method method) {
		return response;
	}

}
