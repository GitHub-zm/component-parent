package com.zm.common.core.biz.validator.example;

import java.util.Collection;

import com.zm.common.core.biz.validator.R;
import com.zm.common.core.biz.validator.Validator;

/**
 * 验证集合属性不能为null或空集合
 * 
 */
public class NotEmptyCollectionValidator implements Validator<NotEmptyCollection> {

	@Override
	public String validate(NotEmptyCollection annotation, Object value) throws RuntimeException {
		if (annotation == null) {
			return null;
		}

		if (value == null || "".equals(value)) {
			return R.R.validatorNotEmpty();
		}

		if (value instanceof Collection) {
			Collection col = (Collection) value;
			if (col.isEmpty()) {
				return annotation.message();
			}
		}
		return null;
	}
}
