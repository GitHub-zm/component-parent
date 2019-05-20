package com.zm.common.ds.multi.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.zm.common.ds.multi.DsRouterRec;

/**
 * 基于数据库的配置源。<br>
 * 其中查询语句中，必须包含{@value #KEY_CAT}、{@value #KEY_STORE_CODE}、
 * {@value #KEY_ALIAS_CODE}、{@value #KEY_URL}、{@value #KEY_USERNAME}、
 * {@value #KEY_PASSWORD}字段。 <br>
 * 配置样例如下：
 * 
 * <pre>
 *   &lt;bean id=&quot;dbConfigSource&quot; class=&quot;com.hd123.orchid.ds.multi.config.DbConfigSource&quot;&gt;
 *     &lt;property name=&quot;dataSource&quot; ref=&quot;config.dataSource&quot; /&gt;
 *     &lt;property name=&quot;querySql&quot;
 *       value=&quot;SELECT cat,stcode as storecode,aliascode,url,username,password FROM DBCONFIG&quot; /&gt;
 *   &lt;/bean&gt;
 * </pre>
 * 
 */
public class DbConfigSource implements ConfigSource, InitializingBean {
	/** 分组 */
	private static final String KEY_CAT = "cat";
	/** 门店代码 */
	private static final String KEY_STORE_CODE = "storecode";
	/** 门店别名代码 */
	private static final String KEY_ALIAS_CODE = "aliascode";
	/** 数据库URL */
	private static final String KEY_URL = "url";
	/** 数据库用户名 */
	private static final String KEY_USERNAME = "username";
	/** 数据库密码 */
	private static final String KEY_PASSWORD = "password";

	/** 配置源 */
	private DataSource dataSource;
	/** 查询SQL */
	private String querySql;

	private List<DsRouterRec> records = new ArrayList<DsRouterRec>();

	public DataSource getDataSource() {
		Assert.notNull(dataSource, "dataSource");
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getQuerySql() {
		Assert.notNull(querySql, "querySql");
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try (Connection conn = getDataSource().getConnection();
				ResultSet rs = conn.createStatement().executeQuery(getQuerySql()); //
		) {
			Map<DsRouterRecKey, DsRouterRec> map = new HashMap<>();
			while (rs.next()) {
				String cat = StringUtils.trim(rs.getString(KEY_CAT));
				String storeCode = StringUtils.trim(rs.getString(KEY_STORE_CODE));
				String aliasCode = StringUtils.trim(rs.getString(KEY_ALIAS_CODE));
				String url = StringUtils.trim(rs.getString(KEY_URL));
				String username = StringUtils.trim(rs.getString(KEY_USERNAME));
				String password = StringUtils.trim(rs.getString(KEY_PASSWORD));

				DsRouterRecKey key = new DsRouterRecKey(cat, url, username, password);
				DsRouterRec rec = map.get(key);
				if (rec == null) {
					rec = new DsRouterRec();
					rec.setCat(cat);
					rec.setUrl(url);
					rec.setUsername(username);
					rec.setPassword(password);
					rec.setStorecodes(new ArrayList<String>());
					rec.setAliascodes(new ArrayList<String>());
					map.put(key, rec);
				}
				rec.getStorecodes().add(storeCode);
				rec.getAliascodes().add(aliasCode);
			}

			records.addAll(map.values());
		}
	}

	@Override
	public List<DsRouterRec> getConfigs() {
		return records;
	}

	class DsRouterRecKey {
		private String cat;
		private String url;
		private String username;
		private String password;

		public DsRouterRecKey(String cat, String url, String username, String password) {
			super();
			this.cat = cat;
			this.url = url;
			this.username = username;
			this.password = password;
		}

		public String getCat() {
			return cat;
		}

		public void setCat(String cat) {
			this.cat = cat;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cat == null) ? 0 : cat.hashCode());
			result = prime * result + ((password == null) ? 0 : password.hashCode());
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			result = prime * result + ((username == null) ? 0 : username.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DsRouterRecKey other = (DsRouterRecKey) obj;
			if (cat == null) {
				if (other.cat != null)
					return false;
			} else if (!cat.equals(other.cat))
				return false;
			if (password == null) {
				if (other.password != null)
					return false;
			} else if (!password.equals(other.password))
				return false;
			if (url == null) {
				if (other.url != null)
					return false;
			} else if (!url.equals(other.url))
				return false;
			if (username == null) {
				if (other.username != null)
					return false;
			} else if (!username.equals(other.username))
				return false;
			return true;
		}
	}
}
