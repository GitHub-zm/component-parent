package com.zm.common.core.biz.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记字符串的长度在指定范围内。
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(LengthValidator.class)
@Documented
public @interface Length {

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