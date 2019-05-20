package com.zm.common.rs.jackson2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectMapper. 忽略不存在字段
 *
 */
public class ObjectMapperBean extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public ObjectMapperBean() {
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
