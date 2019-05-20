package com.zm.common.ds.multi;

import java.util.Map;

import javax.sql.DataSource;

public interface IRoutingDataSource extends DataSource {
	/**
	 * 取得目标数据源map, key=dataSourceKey, value=目标dataSource
	 * 
	 * @return
	 */
	Map<String, DataSource> getTargetDataSources();

	String getTargetDataSourceUrl(String dsKey);

	/**
	 * 在当前线程中设置dataSourceKey
	 */
	void setDataSourceKey(String dsKey);

	/**
	 * 获得当前线程中设定的dataSourkey
	 */
	String getDataSourceKey();
}
