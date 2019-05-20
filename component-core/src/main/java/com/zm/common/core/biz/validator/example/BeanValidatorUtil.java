package com.zm.common.core.biz.validator.example;

import java.util.List;

import com.zm.common.core.biz.validator.BeanValidator;
import com.zm.common.core.biz.validator.ConstraintViolation;

public class BeanValidatorUtil {

	/**
	 * 校验bean
	 * 
	 * @param bean
	 */
	public static void beanValidator(Object bean) {
		StringBuilder sb = new StringBuilder();
		BeanValidator validator = new BeanValidator();
		List<ConstraintViolation> results = validator.validate(bean);
		if (results.size() > 0) {
			for (ConstraintViolation item : results) {
				sb.append(String.format("%s:%s", item.getPropertyName(), item.getMessage()));
				sb.append(";");
			}
			throw new RuntimeException(sb.toString());
		}
	}
}
