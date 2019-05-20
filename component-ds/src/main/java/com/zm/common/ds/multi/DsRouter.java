package com.zm.common.ds.multi;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.zm.common.ds.multi.config.ConfigSource;
import com.zm.common.ds.multi.factory.DataSourceFactory;
import com.zm.common.ds.multi.factory.DataSourceObject;

/**
 * DataSource Router,提供多数据源切换支持。
 * 
 */
public class DsRouter implements IRoutingDataSource {

	/** 默认分组 */
	private String defaultCat;
	private ConfigSource configSource;
	private DataSourceFactory dataSourceFactory;

	public void setDefaultCat(String defaultCat) {
		this.defaultCat = defaultCat;
	}

	/**
	 * 默认cat
	 * 
	 * @return
	 */
	public String getDefaultCat() {
		Assert.notNull(defaultCat, "defaultCat");
		return defaultCat;
	}

	public ConfigSource getConfigSource() {
		Assert.notNull(configSource, "configSource");
		return configSource;
	}

	public void setConfigSource(ConfigSource configSource) {
		this.configSource = configSource;
	}

	public DataSourceFactory getDataSourceFactory() {
		Assert.notNull(dataSourceFactory, "dataSourceFactory");
		return dataSourceFactory;
	}

	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

	/** key: cat */
	private Map<String, Map<String, DataSource>> dataSourceCache;
	private Map<String, Map<String, String>> dataSourceUrlCache;

	private Map<String, Set<String>> storeCodeCache;
	/** 别名与门店代码映射关系 */
	private Map<String, Map<String, String>> aliasCodeMap;

	private Map<DataSource, JdbcTemplate> jdbcTemplateCache;
	private Map<DataSource, TransactionTemplate> transactionTemplateCache;

	@PostConstruct
	public void postConstruct() throws Exception {
		dataSourceCache = new HashMap<String, Map<String, DataSource>>();
		dataSourceUrlCache = new HashMap<String, Map<String, String>>();
		storeCodeCache = new HashMap<String, Set<String>>();
		aliasCodeMap = new HashMap<String, Map<String, String>>();

		jdbcTemplateCache = new HashMap<DataSource, JdbcTemplate>();
		transactionTemplateCache = new HashMap<DataSource, TransactionTemplate>();

		for (DsRouterRec rec : getConfigSource().getConfigs()) {
			DataSourceObject dsObject = getDataSourceFactory().getDataSource(rec);
			DataSource ds = dsObject.getDataSource();

			if (rec.getStorecodes() != null) {
				Map<String, DataSource> dsCache = getDataSourceCache(rec.getCat());
				Map<String, String> dsUrlCache = getDataSourceUrlCache(rec.getCat());
				Set<String> storeCodeSet = getStoreCodeCache(rec.getCat());

				for (String storecode : rec.getStorecodes()) {
					dsCache.put(storecode, ds);
					dsUrlCache.put(storecode, dsObject.getUrl());
					storeCodeSet.add(storecode);
				}

				Map<String, String> aliasCodeCache = getAliasCodeMap(rec.getCat());
				if (rec.getAliascodes() != null && !rec.getAliascodes().isEmpty()) {
					if (rec.getAliascodes().size() != rec.getStorecodes().size()) {
						throw new Exception("门店代码与别名需要一一对应！");
					}

					for (int i = 0; i < rec.getStorecodes().size(); i++) {
						String storeCode = rec.getStorecodes().get(i);
						String aliasCode = rec.getAliascodes().get(i);

						String oldAliasCode = null;
						if (StringUtils.isBlank(aliasCode)) {
							oldAliasCode = aliasCodeCache.put(storeCode, storeCode);
						} else {
							oldAliasCode = aliasCodeCache.put(aliasCode, storeCode);
						}
						if (oldAliasCode != null) {
							throw new Exception(MessageFormat.format("存在两个相同的别名“{0}”", oldAliasCode));
						}
					}
				} else {
					for (int i = 0; i < rec.getStorecodes().size(); i++) {
						String storeCode = rec.getStorecodes().get(i);
						aliasCodeCache.put(storeCode, storeCode);
					}
				}
			}

			jdbcTemplateCache.put(ds, new JdbcTemplate(ds));

			DataSourceTransactionManager tm = new DataSourceTransactionManager(ds);
			DefaultTransactionDefinition td = new DefaultTransactionDefinition();
			td.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			td.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
			transactionTemplateCache.put(ds, new TransactionTemplate(tm, td));
		}
	}

	private Map<String, DataSource> getDataSourceCache(String cat) {
		Map<String, DataSource> dsCache = dataSourceCache.get(cat);

		if (null == dsCache) {
			dsCache = new HashMap<String, DataSource>();
			dataSourceCache.put(cat, dsCache);
		}
		return dsCache;
	}

	private Map<String, String> getDataSourceUrlCache(String cat) {
		Map<String, String> dsUrlCache = dataSourceUrlCache.get(cat);
		if (null == dsUrlCache) {
			dsUrlCache = new HashMap<String, String>();
			dataSourceUrlCache.put(cat, dsUrlCache);
		}
		return dsUrlCache;
	}

	private Set<String> getStoreCodeCache(String cat) {
		Set<String> storeCodeSet = storeCodeCache.get(cat);
		if (null == storeCodeSet) {
			storeCodeSet = new HashSet<String>();
			storeCodeCache.put(cat, storeCodeSet);
		}
		return storeCodeSet;
	}

	private Map<String, String> getAliasCodeMap(String cat) {
		Map<String, String> aliasCodeCache = aliasCodeMap.get(cat);
		if (null == aliasCodeCache) {
			aliasCodeCache = new HashMap<String, String>();
			aliasCodeMap.put(cat, aliasCodeCache);
		}
		return aliasCodeCache;
	}

	public DataSource getDataSource(String cat, String storeCode) throws RuntimeException {
		Map<String, DataSource> dsCache = dataSourceCache.get(cat);
		DataSource ds = null;
		if (dsCache != null) {
			ds = dsCache.get(storeCode);
		}

		if (ds == null) {
			throw new RuntimeException(String.format("No datasource found for cat=%s, storeCode=%s", cat, storeCode));
		}
		return ds;
	}

	public JdbcTemplate getJdbcTemplate(String cat, String storeCode) throws RuntimeException {
		DataSource dataSource = getDataSource(cat, storeCode);
		return jdbcTemplateCache.get(dataSource);
	}

	public TransactionTemplate getTransactionTemplate(String cat, String storeCode) throws RuntimeException {
		DataSource dataSource = getDataSource(cat, storeCode);
		return transactionTemplateCache.get(dataSource);
	}

	// ------------------------------------------------------------------------
	// DataSource interface
	// ------------------------------------------------------------------------

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException("getLogWriter");
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException("setLogWriter");
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException("setLoginTimeout");
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		return determineTargetDataSource().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
	}

	@Override
	public Connection getConnection() throws SQLException {
		return determineTargetDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return determineTargetDataSource().getConnection(username, password);
	}

	private DataSource determineTargetDataSource() throws SQLException {
		DataSourceKey key = keyHolder.get();
		if (key == null) {
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
				if ("org.hibernate.tool.hbm2ddl.SchemaUpdate".equals(ste.getClassName())
						&& "execute".equals(ste.getMethodName()) //
						|| ("org.hibernate.cfg.SettingsFactory".equals(ste.getClassName())
								&& "buildSettings".equals(ste.getMethodName()))) {

					Iterator<Entry<String, DataSource>> iter = getTargetDataSources().entrySet().iterator();
					return iter.next().getValue();
				}
			}
			throw new RuntimeException("No DataSourceKey in current thread, call setKey() first.");
		}
		return getDataSource(key.cat, key.storeCode);
	}

	private final ThreadLocal<DataSourceKey> keyHolder = new ThreadLocal<DataSourceKey>();

	@Override
	public Map<String, DataSource> getTargetDataSources() {
		return dataSourceCache.get(getDefaultCat());
	}

	/**
	 * 默认Cat的门店代码集合。
	 * 
	 * @return
	 */
	public Set<String> getDefaultStoreCodes() {
		return getStoreCodes(getDefaultCat());
	}

	/**
	 * 指定Cat的门店代码集合。
	 * 
	 * @param cat
	 * @return
	 */
	public Set<String> getStoreCodes(String cat) {
		return storeCodeCache.get(cat);
	}

	/**
	 * 获取别名对应的门店代码。
	 * 
	 * @param cat
	 * @param alias
	 *            别名
	 * @return
	 */
	public String getStoreCode(String cat, String alias) {
		Map<String, String> map = aliasCodeMap.get(cat);
		if (null == map) {
			return null;
		}

		return map.get(alias);
	}

	@Override
	public void setDataSourceKey(String dsKey) {
		throw new RuntimeException("系统不支持该方法，请使用setKey");
	}

	@Override
	public String getDataSourceKey() {
		throw new RuntimeException("系统不支持该方法，请使用getKey");
	}

	public static class DataSourceKey {
		public String cat, storeCode;

		public DataSourceKey(String cat, String storeCode) {
			super();
			this.cat = cat;
			this.storeCode = storeCode;
		}
	}

	// ------------------------------------------------------------------------
	// DataSourceKey methods
	// ------------------------------------------------------------------------

	public DataSource setKey(String cat, String storeCode) {
		keyHolder.set(new DataSourceKey(cat, storeCode));
		return this;
	}

	public DataSourceKey getKey() {
		return keyHolder.get();
	}

	public void clearKey() {
		keyHolder.remove();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(this.getClass().getName());
	}

	@Override
	public String getTargetDataSourceUrl(String dsKey) {
		return dataSourceUrlCache.get(getDefaultCat()).get(dsKey);
	}
}
