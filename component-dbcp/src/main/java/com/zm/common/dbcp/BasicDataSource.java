package com.zm.common.dbcp;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.pool.KeyedObjectPoolFactory;

/**
 * 解决一下问题：
 * <ul>
 * <li>抛出异常时，增加url信息。
 * <li>数据库无法正常连接时，重试连接后类实例增多。创建DataSource失败时，调用AbandonedObjectPool的close进行关闭。
 * </ul>
 * 
 */
public class BasicDataSource extends org.apache.commons.dbcp.BasicDataSource {

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(this.getClass().getName());
	}

	@Override
	protected synchronized DataSource createDataSource() throws SQLException {
		try {
			return super.createDataSource();
		} catch (SQLException e) {
			if (null != connectionPool) {
				try {
					connectionPool.close();
				} catch (Exception e1) {
					// DoNothing
				}
				connectionPool = null;
			}
			throw e;
		}
	}

	@Override
	protected void createPoolableConnectionFactory(ConnectionFactory driverConnectionFactory,
			KeyedObjectPoolFactory statementPoolFactory, AbandonedConfig configuration) throws SQLException {
		try {
			super.createPoolableConnectionFactory(driverConnectionFactory, statementPoolFactory, configuration);
		} catch (RuntimeException e) {
			throw new RuntimeException(getUrl(), e);
		} catch (Exception e) {
			throw new SQLException(getUrl(), e);
		}
	}

}
