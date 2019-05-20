package com.zm.common.jpa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记实体表是视图<br>
 * 应用启动更新表结构时，表结构不升级，需要结合{@link ViewSupportableEntityManagerFactoryBean}
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

}
