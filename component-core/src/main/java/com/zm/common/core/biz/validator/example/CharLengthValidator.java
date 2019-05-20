package com.zm.common.core.biz.validator.example;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import com.zm.common.core.biz.validator.R;
import com.zm.common.core.biz.validator.Validator;

/**
 * 标记字符串的字节长度在指定范围内。
 * 
 * @author zengming
 *
 */
public class CharLengthValidator implements Validator<CharLength> {

	@Override
	public String validate(CharLength annotation, Object value) throws RuntimeException {
		if (annotation == null || value == null)
			return null;

		if (value instanceof String == false)
			return null;

		int length = 0;
		try {
			length = ((String) value).getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		}

		if (length < annotation.min())
			return MessageFormat.format(R.R.validatorLengthMin(), annotation.min());
		else if (length > annotation.max())
			return MessageFormat.format(R.R.validatorLengthMax(), annotation.max());

		return null;
	}

}
