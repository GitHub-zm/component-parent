package com.zm.common.core.biz.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用自定义的验证器验证。
 * 
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorClass {

	/**
	 * 验证类，必须实现{@link com.hd123.rumba.commons.biz.validator.Validator}接口。
	 * 
	 * @return 验证类。
	 */
	Class<? extends Validator> value();
}