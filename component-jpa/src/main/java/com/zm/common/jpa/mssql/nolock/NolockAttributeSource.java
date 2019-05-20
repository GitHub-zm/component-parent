package com.zm.common.jpa.mssql.nolock;

import java.lang.reflect.Method;

/**
 * nolock属性源，供
 * {@link com.hd123.orchid.jpa.mssql.nolock.interceptor.NolockInterceptor}
 * 查询nolock属性元数据。
 * 
 */
public interface NolockAttributeSource {

	/**
	 * 返回给定方法的nolock属性。如果无nolock，则返回null。
	 * 
	 * @param method
	 *            方法
	 * @param targetClass
	 *            目标类.
	 * @return NolockDefinition 。
	 */
	NolockDefinition getNolockDefinition(Method method, Class<?> targetClass);
}
