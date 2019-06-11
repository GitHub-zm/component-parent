package com.zm.common.test.spring.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE }) // 在方法上的注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomRequestMapping {
	String value() default "";
}
