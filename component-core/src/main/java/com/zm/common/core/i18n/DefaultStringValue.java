package com.zm.common.core.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 默认资源文件标记接口，使用该接口可以不写默认的properties文件
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DefaultStringValue {
	/** 默认字符串内容 */
	String value();
}
