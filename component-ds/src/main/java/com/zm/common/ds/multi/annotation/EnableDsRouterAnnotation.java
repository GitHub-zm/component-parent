package com.zm.common.ds.multi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.zm.common.ds.multi.DsRouterAnnotationConfiguration;

/**
 * 激活DsRouter相关注解。
 * 
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DsRouterAnnotationConfiguration.class)
public @interface EnableDsRouterAnnotation {

}
