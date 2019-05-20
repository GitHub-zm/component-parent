package com.zm.common.core.biz.validator;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 验证过去时间实现
 * 
 */
public class PastValidator implements Validator<Past> {

	@Override
	public String validate(Past annotation, Object value) throws RuntimeException {
		if (annotation == null || value == null)
			return null;
		if (value instanceof Date == false)
			return null;

		int result = DateUtils.truncatedCompareTo((Date) value, new Date(), Calendar.DATE);
		if (annotation.includeToday()) {
			if (result > 0)
				return R.R.validatorPastIncludeToday();
		} else {
			if (result >= 0)
				return R.R.validatorPast();
		}

		return null;
	}

}