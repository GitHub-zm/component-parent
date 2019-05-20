package com.zm.common.core.biz.validator.example;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zm.common.core.biz.validator.ValidatorClass;

/**
 * 标记集合属性不能为null或空集合
 * 
 * @author q
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(NotEmptyCollectionValidator.class)
@Documented
public @interface NotEmptyCollection {
	String message() default "不能为空集合";
}
