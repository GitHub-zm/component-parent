package com.zm.common.test.udp;

import java.io.Serializable;

public class Data implements Serializable {

	private static final long serialVersionUID = 1667294016885123654L;

	private String id;

	private String name;

	private String message;

	private transient String note; // 不需要序列化

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
