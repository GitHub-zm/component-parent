package com.zm.common.ds.multi.factory;

import javax.sql.DataSource;

/**
 * 数据源对象。
 * 
 * 
 */
public class DataSourceObject {
	/** 数据源 */
	private DataSource dataSource;
	/** 数据源URL */
	private String url;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
