package com.zm.common.ds.multi.factory;

import com.zm.common.ds.multi.DsRouterRec;

/**
 * 数据源工厂
 * 
 */
public interface DataSourceFactory {

	/**
	 * 取数据源。
	 * 
	 * @param record
	 *            配置记录，禁止传入null.
	 * @return
	 * @throws Exception
	 */
	DataSourceObject getDataSource(DsRouterRec record) throws Exception;
}
