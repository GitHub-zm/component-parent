package com.zm.common.customize.annotation;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 功能profile条件。
 * 
 */
public class FunctionProfileCondition implements Condition {
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (metadata.isAnnotated(FunctionProfile.class.getName())) {
			AnnotationAttributes annotationAttributes = AnnotationAttributes
					.fromMap(metadata.getAnnotationAttributes(FunctionProfile.class.getName()));

			String name = annotationAttributes.getString("name");
			String value = annotationAttributes.getString("value");

			boolean enabled = isEnabled(context, name, value);
			logger.info(MessageFormat.format("功能：{0}:{1} {2}", name, value, enabled ? "启用" : "关闭"));
			return enabled;
		}

		return false;
	}

	private boolean isEnabled(ConditionContext context, String property, String selectFunction) {
		String spe = context.getEnvironment().getProperty(property, String.class, "");
		String[] values = spe.split(",");
		for (String value : values) {
			if (value.equals(selectFunction)) {
				return true;
			}
		}
		return false;
	}
}
