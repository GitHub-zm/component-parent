package com.zm.common.ds.multi.spring;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;

import com.zm.common.ds.multi.DsRouter;

public class DsRouterInterceptor implements MethodInterceptor, InitializingBean {

	private AnnotationDsRouterAttributeSource attributeSource;

	private DsRouter dsRouter;

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

		return switchDataSource(invocation.getMethod(), targetClass, invocation);
	}

	public AnnotationDsRouterAttributeSource getAttributeSource() {
		return attributeSource;
	}

	public void setAttributeSource(AnnotationDsRouterAttributeSource attributeSource) {
		this.attributeSource = attributeSource;
	}

	public DsRouter getDsRouter() {
		return dsRouter;
	}

	public void setDsRouter(DsRouter dsRouter) {
		this.dsRouter = dsRouter;
	}

	protected Object switchDataSource(Method method, Class<?> targetClass, MethodInvocation invocation)
			throws Throwable {
		DsRouterMgrDefinition def = this.getAttributeSource().getDsRouterMgrDefinition(method, targetClass);

		if (!AnnotationDsRouterAttributeSource.NULL_DEFINITION.equals(def)) {
			Object[] args = invocation.getArguments();

			Object factor = args[def.getSwitchFactorParameterIndex()];
			String storeCode = null;
			if (factor instanceof String) {
				storeCode = (String) factor;
			} else {
				throw new RuntimeException(MessageFormat.format("参数类型{0}不支持。", factor.getClass()));
			}
			/**
			 * 切换数据源
			 */
			getDsRouter().setKey(def.getType(), storeCode);
		}

		Object retVal = null;
		try {
			retVal = invocation.proceed();
		} finally {
			// Do Nothing
		}
		return retVal;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.attributeSource == null) {
			throw new IllegalStateException("attributeSource属性必须设置。");
		}
	}

}
