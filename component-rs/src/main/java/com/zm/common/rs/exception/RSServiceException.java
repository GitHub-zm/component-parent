package com.zm.common.rs.exception;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSServiceException {

	private String code;
	private String message;

	public RSServiceException() {
	}

	public RSServiceException(String message) {
		this.message = message;
	}

	public RSServiceException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@XmlElement(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@XmlElement(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
