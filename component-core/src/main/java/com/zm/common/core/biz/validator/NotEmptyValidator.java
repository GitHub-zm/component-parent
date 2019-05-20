package com.zm.common.core.biz.validator;

/**
 * 验证不能为null或空字符串的实现。
 * 
 */
public class NotEmptyValidator implements Validator<NotEmpty> {

	@Override
	public String validate(NotEmpty annotation, Object value) throws RuntimeException {
		if (annotation == null)
			return null;

		if (value == null || "".equals(value))
			return R.R.validatorNotEmpty();

		return null;
	}

}