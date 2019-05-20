package com.zm.common.core.biz.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记属性不能为空或空字符串。
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(NotEmptyValidator.class)
@Documented
public @interface NotEmpty {

}