package com.zm.common.jpa.mssql.nolock.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.ObjectUtils;

import com.zm.common.jpa.mssql.nolock.NolockAttributeSource;

@SuppressWarnings("serial")
public abstract class NolockAttributeSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		NolockAttributeSource tas = getNolockAttributeSource();
		return (tas == null || tas.getNolockDefinition(method, targetClass) != null);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NolockAttributeSourcePointcut)) {
			return false;
		}
		NolockAttributeSourcePointcut otherPc = (NolockAttributeSourcePointcut) other;
		return ObjectUtils.nullSafeEquals(getNolockAttributeSource(), otherPc.getNolockAttributeSource());
	}

	@Override
	public int hashCode() {
		return NolockAttributeSourcePointcut.class.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getName() + ": " + getNolockAttributeSource();
	}

	protected abstract NolockAttributeSource getNolockAttributeSource();
}
