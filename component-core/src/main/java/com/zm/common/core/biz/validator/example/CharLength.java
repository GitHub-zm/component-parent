package com.zm.common.core.biz.validator.example;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zm.common.core.biz.validator.ValidatorClass;

/**
 * 标记字符串的字节长度在指定范围内。
 * 
 * <li>GBK-一个字符占用两个字节
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(CharLengthValidator.class)
@Documented
public @interface CharLength {

	/**
	 * 最大值。
	 * 
	 * @return 最大值。
	 */
	long max();

	/**
	 * 最小值。
	 * 
	 * @return 最小值。
	 */
	long min() default 0;
}
