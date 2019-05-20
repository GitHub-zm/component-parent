package com.zm.common.ds.multi.spring;

import java.lang.reflect.Method;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.ObjectUtils;

public abstract class DsRouterAttributeSourcePointcut extends StaticMethodMatcherPointcut {
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		AnnotationDsRouterAttributeSource tas = getAttributeSource();
		return (tas == null || tas.getDsRouterMgrDefinition(method, targetClass) != null);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DsRouterAttributeSourcePointcut)) {
			return false;
		}
		DsRouterAttributeSourcePointcut otherPc = (DsRouterAttributeSourcePointcut) other;
		return ObjectUtils.nullSafeEquals(getAttributeSource(), otherPc.getAttributeSource());
	}

	@Override
	public int hashCode() {
		return DsRouterAttributeSourcePointcut.class.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getName() + ": " + getAttributeSource();
	}

	protected abstract AnnotationDsRouterAttributeSource getAttributeSource();
}
