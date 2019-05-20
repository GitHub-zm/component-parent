package com.zm.common.rs.jackson2;

import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class Jackson2JsonProviderFactoryBean implements FactoryBean<JacksonJsonProvider> {

	private ObjectMapper mapper;

	private static final boolean DEFAULT_FAIL_ON_UNKNOWN_PROPERTIES = false;

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public JacksonJsonProvider getObject() throws Exception {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}

		// 忽略不存在的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, DEFAULT_FAIL_ON_UNKNOWN_PROPERTIES);
		return new JacksonJsonProvider(mapper);
	}

	@Override
	public Class<? extends JacksonJsonProvider> getObjectType() {
		return JacksonJsonProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
