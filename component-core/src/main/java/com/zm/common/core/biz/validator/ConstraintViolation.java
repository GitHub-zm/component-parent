package com.zm.common.core.biz.validator;

import java.io.Serializable;

/**
 * 验证冲突结果
 * 
 */
public class ConstraintViolation implements Serializable {

	private static final long serialVersionUID = 4531995183407407588L;

	private String propertyName;
	private String message;
	private Object invalidValue;

	public ConstraintViolation() {
	}

	public ConstraintViolation(String propertyName, String message, Object invalidValue) {
		this.propertyName = propertyName;
		this.message = message;
		this.invalidValue = invalidValue;
	}

	/**
	 * 取得属性的名称。
	 * 
	 * @return 属性名称。
	 */
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * 取得冲突信息。
	 * 
	 * @return 冲突信息。
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 取得
	 * 
	 * @return 取得不合法的属性值。
	 */
	public Object getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}

}
