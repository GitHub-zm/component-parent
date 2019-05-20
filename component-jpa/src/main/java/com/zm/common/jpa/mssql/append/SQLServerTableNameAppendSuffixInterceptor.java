package com.zm.common.jpa.mssql.append;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * SQL Server数据库的拦截器。
 * <p>
 * 
 * 提供以下功能：
 * <ol>
 * <li>在Hibernate查询语句中对指定表名增加后缀，仅对当前线程有效。</li>
 * </ol>
 * 
 */
public class SQLServerTableNameAppendSuffixInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 6024260780501119066L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final ThreadLocal<Map<String, String>> TABLE_NAMES_AND_SUFFIX_MAP = new ThreadLocal<Map<String, String>>();

	/**
	 * 在接下来执行的Hibernate查询语句中，对指定表名增加指定后缀，仅对当前线程有效。
	 * 
	 * @param tableNameAndSuffixMap
	 *            Map<表名,后缀>对象,传入null或传入空Map等价于清空当前线程设值。
	 */
	public static void setTableNameAndSuffixMap(Map<String, String> tableNameAndSuffixMap) {
		if (CollectionUtils.isEmpty(tableNameAndSuffixMap)) {
			TABLE_NAMES_AND_SUFFIX_MAP.set(null);
		} else {
			TABLE_NAMES_AND_SUFFIX_MAP.set(tableNameAndSuffixMap);
		}
	}

	@Override
	public String onPrepareStatement(String sql) {
		return processTableNames(sql);
	}

	private String processTableNames(String sql) {
		Map<String, String> map = TABLE_NAMES_AND_SUFFIX_MAP.get();
		if (sql == null || CollectionUtils.isEmpty(map)) {
			return sql;
		}

		for (String tableName : map.keySet()) {
			String suffix = map.get(tableName);
			if (StringUtils.indexOf(sql, tableName) != -1 && StringUtils.indexOf(sql, tableName.concat(suffix)) == -1) {
				sql = StringUtils.replaceOnce(sql, tableName, tableName.concat(suffix));
			}
		}
		logger.info("interceptor sql:{}", sql);
		return sql;
	}
}
