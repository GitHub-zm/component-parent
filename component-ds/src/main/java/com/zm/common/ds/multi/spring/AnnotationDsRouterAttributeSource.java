package com.zm.common.ds.multi.spring;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.ObjectUtils;

import com.zm.common.ds.multi.annotation.DsRouterMgr;

public class AnnotationDsRouterAttributeSource {
	private DsRouterMgrAnnotationParser annotationParser;

	final Map<Object, DsRouterMgrDefinition> attributeCache = new ConcurrentHashMap<Object, DsRouterMgrDefinition>(
			1024);
	public final static DsRouterMgrDefinition NULL_DEFINITION = new DsRouterMgrDefinition();

	/**
	 * 
	 */
	public AnnotationDsRouterAttributeSource() {
		annotationParser = new DsRouterMgrAnnotationParser();
	}

	/**
	 * 如果方法上有{@link DsRouterMgr}注解，则取方法。否则，取类上的{@link DsRouterMgr}注解。均无，则返回null。
	 */
	public DsRouterMgrDefinition getDsRouterMgrDefinition(Method method, Class<?> targetClass) {
		Object cacheKey = getCacheKey(method, targetClass);
		Object cached = this.attributeCache.get(cacheKey);
		if (cached != null) {
			if (cached == NULL_DEFINITION) {
				return null;
			} else {
				return (DsRouterMgrDefinition) cached;
			}
		} else {
			DsRouterMgrDefinition def = determineDsRouterMgrDefinition(method);

			if (null == def) {
				this.attributeCache.put(cacheKey, NULL_DEFINITION);
			} else {
				this.attributeCache.put(cacheKey, def);
			}

			return def;
		}
	}

	protected DsRouterMgrDefinition determineDsRouterMgrDefinition(Method method) {
		return annotationParser.parseDefinition(method);
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
