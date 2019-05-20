package com.zm.common.ds.utils;

/**
 * JDBC工具类
 * 
 */
public class JdbcUtils {

	public static String driverClassName(String url) throws Exception {
		String driverClassName = null;
		if (url.startsWith("jdbc:mysql:"))
			driverClassName = "com.mysql.jdbc.Driver";
		else if (url.startsWith("jdbc:sqlserver:"))
			driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		else if (url.startsWith("jdbc:jtds:sqlserver:"))
			driverClassName = "net.sourceforge.jtds.jdbc.Driver";
		else if (url.startsWith("jdbc:oracle:thin:"))
			driverClassName = "oracle.jdbc.driver.OracleDriver";
		else if (url.startsWith("jdbc:postgresql:"))
			driverClassName = "org.postgresql.Driver";
		else
			throw new Exception("从url中不能确定jdbc driver类型: " + url);
		return driverClassName;
	}

	/**
	 * 确定验证查询语句
	 * 
	 * @param url
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String determineValidationQuery(String url) throws Exception {
		String validationQuery = null;
		if (url.startsWith("jdbc:mysql:"))
			validationQuery = "SELECT 1 ";
		else if (url.startsWith("jdbc:sqlserver:") //
				|| url.startsWith("jdbc:jtds:sqlserver:"))
			validationQuery = "SELECT 1 ";
		else if (url.startsWith("jdbc:oracle:thin:"))
			validationQuery = "SELECT 1 FROM DUAL";
		else if (url.startsWith("jdbc:postgresql:"))
			validationQuery = "SELECT version()";
		else
			throw new Exception("从url中不能确定数据库验证语句: " + url);

		return validationQuery;
	}
}
