package com.zm.common.ds.multi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解方法参数，作为数据源切换的key。
 * 
 * 
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface DsRouterParameter {
	/**
	 * 参数名
	 * 
	 * @return
	 */
	String value() default "";
}
