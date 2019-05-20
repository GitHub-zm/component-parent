package com.zm.common.core.biz.validator;

import java.text.MessageFormat;

public class ValidatorException extends RuntimeException {
	private static final long serialVersionUID = 8811722410395209530L;

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(String pattern, Object... arguments) {
		super(MessageFormat.format(pattern, arguments));
	}

	public ValidatorException(Throwable cause, String pattern, Object... arguments) {
		super(MessageFormat.format(pattern, arguments), cause);
	}

}
