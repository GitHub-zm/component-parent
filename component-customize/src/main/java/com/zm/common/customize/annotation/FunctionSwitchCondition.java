package com.zm.common.customize.annotation;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 功能开关条件。
 * 
 */
public class FunctionSwitchCondition implements Condition {
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (metadata.isAnnotated(FunctionSwitch.class.getName())) {
			AnnotationAttributes annotationAttributes = AnnotationAttributes
					.fromMap(metadata.getAnnotationAttributes(FunctionSwitch.class.getName()));
			String property = annotationAttributes.getString("value");

			boolean enabled = isEnabled(context, property);
			logger.info(MessageFormat.format("功能：{0} {1}", property, enabled ? "启用" : "关闭"));
			return enabled;
		}

		return false;
	}

	private boolean isEnabled(ConditionContext context, String property) {
		return context.getEnvironment().getProperty(property, Boolean.class, false);
	}
}
