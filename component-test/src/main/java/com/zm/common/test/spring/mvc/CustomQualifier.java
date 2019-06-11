package com.zm.common.test.spring.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD }) // 代表注解的注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomQualifier {
	String value() default "";
}
