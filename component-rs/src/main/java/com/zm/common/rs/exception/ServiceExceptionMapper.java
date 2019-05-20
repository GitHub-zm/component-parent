package com.zm.common.rs.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 报错自定义处理
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		RSServiceException r = null;
		// 通常为自定义异常提供特殊处理
		if (e instanceof NullPointerException) {
			r = new RSServiceException(ExceptionUtils.getMessage(e));
		} else {
			r = new RSServiceException(ExceptionUtils.getMessage(e));
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(r).build();
	}

}
