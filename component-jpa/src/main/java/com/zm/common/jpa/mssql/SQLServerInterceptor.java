package com.zm.common.jpa.mssql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;

import com.zm.common.jpa.mssql.append.SQLServerTableNameAppendSuffixInterceptor;
import com.zm.common.jpa.mssql.nolock.SQLServerNoLockInterceptor;

/**
 * 
 * SQL Server数据库的拦截器。
 * 
 */
public class SQLServerInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 5666912333246135732L;

	private List<Interceptor> interceptors = new ArrayList<>();

	public SQLServerInterceptor() {
		super();
		interceptors.add(new SQLServerNoLockInterceptor());
		interceptors.add(new SQLServerTableNameAppendSuffixInterceptor());
	}

	@Override
	public String onPrepareStatement(final String sql) {
		String prepareSql = sql;
		for (Interceptor interceptor : interceptors) {
			prepareSql = interceptor.onPrepareStatement(prepareSql);
		}
		return prepareSql;
	}

}
