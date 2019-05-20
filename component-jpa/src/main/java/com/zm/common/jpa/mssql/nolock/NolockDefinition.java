package com.zm.common.jpa.mssql.nolock;

import java.util.List;

/**
 * nolock 定义
 * 
 */
public interface NolockDefinition {
	/**
	 * 需要加nolock的表名的List。
	 * 
	 * @return
	 */
	List<String> getTableNames();
}
