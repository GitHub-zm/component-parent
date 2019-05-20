package com.zm.common.jpa.mssql.nolock.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;

import com.zm.common.jpa.mssql.nolock.NolockAttributeSource;
import com.zm.common.jpa.mssql.nolock.NolockDefinition;
import com.zm.common.jpa.mssql.nolock.SQLServerNoLockInterceptor;

/**
 * nolock方法拦截器
 * 
 */
public class NolockInterceptor implements MethodInterceptor, InitializingBean {

	private NolockAttributeSource nolockAttributeSource;

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

		return invokeWithinNolock(invocation.getMethod(), targetClass, invocation);
	}

	public NolockAttributeSource getNolockAttributeSource() {
		return nolockAttributeSource;
	}

	public void setNolockAttributeSource(NolockAttributeSource nolockAttributeSource) {
		this.nolockAttributeSource = nolockAttributeSource;
	}

	protected Object invokeWithinNolock(Method method, Class<?> targetClass, MethodInvocation invocation)
			throws Throwable {
		NolockDefinition def = this.getNolockAttributeSource().getNolockDefinition(method, targetClass);

		List<String> tableNames = def.getTableNames();

		Object retVal = null;

		String[] oldTableNames = SQLServerNoLockInterceptor.getTableNames();
		if (!tableNames.isEmpty()) {
			SQLServerNoLockInterceptor.setTableNames(tableNames.toArray(new String[] {}));
		}
		try {
			retVal = invocation.proceed();
		} finally {
			SQLServerNoLockInterceptor.setTableNames();
			// 恢复调用方nolock表。
			if (null != oldTableNames)
				SQLServerNoLockInterceptor.setTableNames(oldTableNames);
		}

		return retVal;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.nolockAttributeSource == null) {
			throw new IllegalStateException("Either 'nolockAttributeSource' is required: "
					+ "If there are no nolock methods, then don't use a nolock aspect.");
		}
	}

}
