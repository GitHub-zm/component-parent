package com.zm.common.core.biz.validator;

import java.text.MessageFormat;

/**
 * 验证模式是否匹配的实现
 * 
 */
public class PatternValidator implements Validator<Pattern> {

	@Override
	public String validate(Pattern annotation, Object value) throws RuntimeException {
		if (annotation == null || value == null)
			return null;
		if (value instanceof String == false)
			return null;

		if (java.util.regex.Pattern.matches(annotation.regex(), (String) value) == false)
			return MessageFormat.format(R.R.validatorPattern(), annotation.prompt());

		return null;
	}

}