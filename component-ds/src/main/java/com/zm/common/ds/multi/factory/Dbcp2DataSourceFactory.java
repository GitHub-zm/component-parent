package com.zm.common.ds.multi.factory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import com.zm.common.ds.multi.DsRouterRec;
import com.zm.common.ds.utils.JdbcUtils;

/**
 * 基于dbcp 2.x实现的数据源工厂类。
 * 
 * 
 */
public class Dbcp2DataSourceFactory implements DataSourceFactory, ApplicationContextAware {
	private ApplicationContext applicationContext;
	private String dataSourceTemplateBeanName;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setDataSourceTemplateBeanName(String dataSourceTemplateBeanName) {
		this.dataSourceTemplateBeanName = dataSourceTemplateBeanName;
	}

	public String getDataSourceTemplateBeanName() {
		Assert.notNull(dataSourceTemplateBeanName, "dataSourceTemplateBeanName");
		return dataSourceTemplateBeanName;
	}

	@Override
	public DataSourceObject getDataSource(DsRouterRec record) throws Exception {
		BasicDataSource ds = (BasicDataSource) applicationContext.getBean(getDataSourceTemplateBeanName());
		ds.setUrl(record.getUrl());
		ds.setUsername(record.getUsername());
		ds.setPassword(record.getPassword());
		ds.setDriverClassName(JdbcUtils.driverClassName(record.getUrl()));

		DataSourceObject dsObject = new DataSourceObject();
		dsObject.setDataSource(ds);
		dsObject.setUrl(ds.getUrl());
		return dsObject;
	}

}
