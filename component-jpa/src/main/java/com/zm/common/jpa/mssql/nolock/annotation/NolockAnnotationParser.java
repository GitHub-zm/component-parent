package com.zm.common.jpa.mssql.nolock.annotation;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

import org.springframework.core.annotation.AnnotationUtils;

import com.zm.common.jpa.annotation.MSSQLNolock;
import com.zm.common.jpa.mssql.nolock.DefaultNolockDefinition;
import com.zm.common.jpa.mssql.nolock.NolockDefinition;

/**
 * nolock注解解析器
 * 
 * @author xietao
 * 
 */
public class NolockAnnotationParser {

	public NolockDefinition parseNolockDefinition(AnnotatedElement ae) {
		MSSQLNolock ann = AnnotationUtils.getAnnotation(ae, MSSQLNolock.class);
		if (ann != null) {
			return parseTransactionAnnotation(ann);
		} else {
			return null;
		}
	}

	public NolockDefinition parseTransactionAnnotation(MSSQLNolock ann) {
		DefaultNolockDefinition def = new DefaultNolockDefinition();
		def.getTableNames().addAll(Arrays.asList(ann.value()));

		return def;
	}
}
