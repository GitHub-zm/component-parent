package com.zm.common.ds.multi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DsRouterMgr {
	/**
	 * 数据源组
	 * 
	 * @return
	 */
	String cat();
}
