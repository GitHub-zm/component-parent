package com.zm.common.core.biz.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记字符串符合指定模式。
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(PatternValidator.class)
@Documented
public @interface Pattern {

	/**
	 * 正则表达式。
	 * 
	 * @return 正则表达式。
	 */
	String regex();

	/**
	 * 对正则表达式所表达的含义的友好提示信息。
	 * 
	 * @return 友好提示信息。
	 */
	String prompt();
}