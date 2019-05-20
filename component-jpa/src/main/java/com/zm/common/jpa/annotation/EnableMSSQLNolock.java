package com.zm.common.jpa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.zm.common.jpa.mssql.nolock.ProxyNolockManagementConfiguration;

/**
 * 激活@MSSQLNolock。
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ProxyNolockManagementConfiguration.class)
public @interface EnableMSSQLNolock {

}
