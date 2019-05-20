package com.zm.common.ds.multi.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zm.common.ds.multi.DsRouterRec;

/**
 * Json文件配置源，默认配置文件"/dsRouter.json"。
 * 
 * 
 */
public class JsonFileConfigSource implements ConfigSource, InitializingBean {

	/** 配置文件位置，默认{@value} */
	private String location = "/dsRouter.json";
	private List<DsRouterRec> records = new ArrayList<DsRouterRec>();

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		Assert.notNull(location, "location");
		this.location = location;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream src = getClass().getResourceAsStream(getLocation());
		if (src == null) {
			throw new FileNotFoundException(getLocation());
		}
		MappingIterator<DsRouterRec> iter = new ObjectMapper().reader(DsRouterRec.class).readValues(src);
		iter.readAll(records);
	}

	@Override
	public List<DsRouterRec> getConfigs() {
		return records;
	}

}
