package com.zm.common.ds.multi.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;

import com.zm.common.ds.multi.annotation.DsRouterMgr;
import com.zm.common.ds.multi.annotation.DsRouterParameter;

/**
 * DsRouterMgr 注解解析器
 * 
 */
public class DsRouterMgrAnnotationParser {
	public DsRouterMgrDefinition parseDefinition(Method method) {
		DsRouterMgr ann = AnnotationUtils.getAnnotation(method, DsRouterMgr.class);

		if (ann != null) {
			DsRouterMgrDefinition def = parseAnnotation(ann);
			parseParameterAnnotation(method, def);
			return def;
		} else {
			return null;
		}
	}

	public DsRouterMgrDefinition parseAnnotation(DsRouterMgr ann) {
		DsRouterMgrDefinition def = new DsRouterMgrDefinition();
		def.setType(ann.cat());
		return def;
	}

	public void parseParameterAnnotation(Method method, DsRouterMgrDefinition def) {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		if (parameterAnnotations == null || parameterAnnotations.length == 0) {
			throw new IllegalStateException("@DsRouterMgr需要配合@DsRouterParameter使用。 ");
		}

		boolean exists = false;
		for (int i = 0; i < parameterAnnotations.length; i++) {
			Annotation[] parameterAnnotation = parameterAnnotations[i];
			for (Annotation annotation : parameterAnnotation) {
				if (annotation instanceof DsRouterParameter) {
					// DsRouterParameter param = (DsRouterParameter) annotation;
					def.setSwitchFactorParameterIndex(i);
					exists = true;
					break;
				}
			}
		}

		if (!exists) {
			throw new IllegalStateException("DsRouterMgr注解需要配合DsRouterParameter注解使用。 ");
		}
	}
}
