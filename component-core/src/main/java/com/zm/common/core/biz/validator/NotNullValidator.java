package com.zm.common.core.biz.validator;

/**
 * 验证不能为null的实现
 * 
 */
public class NotNullValidator implements Validator<NotNull> {

	@Override
	public String validate(NotNull annotation, Object value) throws RuntimeException {
		if (annotation == null)
			return null;

		if (value == null)
			return R.R.validatorNotNull();

		return null;
	}

}