package com.zm.common.jpa.mssql.nolock.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.ObjectUtils;

import com.zm.common.jpa.annotation.MSSQLNolock;
import com.zm.common.jpa.mssql.nolock.DefaultNolockDefinition;
import com.zm.common.jpa.mssql.nolock.NolockAttributeSource;
import com.zm.common.jpa.mssql.nolock.NolockDefinition;

/**
 * 支持注解。
 * 
 */
public class AnnotationNolockAttributeSource implements NolockAttributeSource {

	private NolockAnnotationParser annotationParser;

	final Map<Object, NolockDefinition> attributeCache = new ConcurrentHashMap<Object, NolockDefinition>(1024);
	private final static NolockDefinition NULL_NOLOCK_DEFINITION = new DefaultNolockDefinition();

	/**
	 * 
	 */
	public AnnotationNolockAttributeSource() {
		annotationParser = new NolockAnnotationParser();
	}

	/**
	 * 如果方法上有{@link MSSQLNolock}注解，则取方法。否则，取类上的{@link MSSQLNolock}注解。均无，则返回null。
	 */
	@Override
	public NolockDefinition getNolockDefinition(Method method, Class<?> targetClass) {
		Object cacheKey = getCacheKey(method, targetClass);
		Object cached = this.attributeCache.get(cacheKey);
		if (cached != null) {
			if (cached == NULL_NOLOCK_DEFINITION) {
				return null;
			} else {
				return (NolockDefinition) cached;
			}
		} else {
			NolockDefinition def = determineNolockDefinition(method);
			if (null == def) {
				def = determineNolockDefinition(targetClass);
			}

			if (null == def) {
				this.attributeCache.put(cacheKey, NULL_NOLOCK_DEFINITION);
			} else {
				this.attributeCache.put(cacheKey, def);
			}

			return def;
		}
	}

	protected NolockDefinition determineNolockDefinition(AnnotatedElement ae) {
		return annotationParser.parseNolockDefinition(ae);
	}

	protected Object getCacheKey(Method method, Class<?> targetClass) {
		return new DefaultCacheKey(method, targetClass);
	}

	private static class DefaultCacheKey {

		private final Method method;

		private final Class<?> targetClass;

		public DefaultCacheKey(Method method, Class<?> targetClass) {
			this.method = method;
			this.targetClass = targetClass;
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof DefaultCacheKey)) {
				return false;
			}
			DefaultCacheKey otherKey = (DefaultCacheKey) other;
			return (this.method.equals(otherKey.method)
					&& ObjectUtils.nullSafeEquals(this.targetClass, otherKey.targetClass));
		}

		@Override
		public int hashCode() {
			return this.method.hashCode();
		}
	}
}
