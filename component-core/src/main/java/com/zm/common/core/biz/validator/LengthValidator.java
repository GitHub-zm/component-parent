package com.zm.common.core.biz.validator;

import java.text.MessageFormat;

/**
 * 验证字符串长度
 * 
 */
public class LengthValidator implements Validator<Length> {

	@Override
	public String validate(Length annotation, Object value) throws RuntimeException {
		if (annotation == null || value == null)
			return null;

		if (value instanceof String == false)
			return null;

		int length = ((String) value).length();
		if (length < annotation.min())
			return MessageFormat.format(R.R.validatorLengthMin(), annotation.min());
		else if (length > annotation.max())
			return MessageFormat.format(R.R.validatorLengthMax(), annotation.max());

		return null;
	}

}
