package com.zm.common.jpa.mssql.nolock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.EmptyInterceptor;

import com.zm.common.jpa.annotation.MSSQLNolock;

/**
 * SQL Server数据库的拦截器。注解{@link MSSQLNolock}实现增加“(nolock)”表名及清理。
 * <p>
 * 
 * 提供以下功能：
 * <ol>
 * <li>在Hibernate查询语句中对指定表名增加“(nolock)”提示，仅对当前线程有效。</li>
 * </ol>
 * 
 * @see com.hd123.orchid.jpa.annotation.MSSQLNolock
 * @since 2.2
 */
public class SQLServerNoLockInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -5285959129691120011L;
	private static final ThreadLocal<Set<String>> TABLE_NAMES_WITH_NOLOCK = new ThreadLocal<Set<String>>();

	/**
	 * 在接下来执行的Hibernate查询语句中，对指定表名增加“(nolock)”提示，仅对当前线程有效。<br>
	 * <strong>为防止线程复用时，当前线程对后续执行的其他任务的影响，需开发者显式的调用
	 * {@link SQLServerNoLockInterceptor#setTableNames(String...)}
	 * 移除增加“(nolock)”的指定表名。</strong>
	 * 
	 * @param tableNames
	 *            包含数据表名的数组，大小写不敏感。
	 */
	public static void setTableNames(String... tableNames) {
		Set<String> ripeTableNames = getRipeValues(Arrays.asList(tableNames));
		if (ripeTableNames.isEmpty()) {
			TABLE_NAMES_WITH_NOLOCK.set(null);
		} else {
			TABLE_NAMES_WITH_NOLOCK.set(ripeTableNames);
		}
	}

	/**
	 * 取当前线程指定增加“(nolock)”的表名。
	 * 
	 * @return 包含数据表名的数组，大小写不敏感。不存在表名时，返回null。
	 */
	public static String[] getTableNames() {
		Set<String> ripeTableNames = TABLE_NAMES_WITH_NOLOCK.get();
		if (null == ripeTableNames) {
			return null;
		}
		return ripeTableNames.toArray(new String[] {});
	}

	@Override
	public String onPrepareStatement(String sql) {
		return processTableNames(sql);
	}

	private String processTableNames(String sql) {
		Set<String> tableNames = TABLE_NAMES_WITH_NOLOCK.get();
		if (sql == null || tableNames == null || tableNames.isEmpty() || !isSelectStatement(sql)) {
			return sql;
		}

		String[] tblNames = tableNames.toArray(new String[0]);
		List<SQLSegment> tblSegments = new ArrayList<SQLSegment>();
		List<SQLSegment> fromSegments = SQLSegment.getSegmentsBetween(sql, null, new String[] { " from " },
				new String[] { " where ", ")", " order " });
		for (SQLSegment fromSegment : fromSegments) {
			tblSegments.addAll(SQLSegment.getSegmentsBetween(sql, fromSegment, tblNames,
					new String[] { ",", " cross ", " inner ", " left ", " right ", " full ", " on " }));
		}

		StringBuffer buffer = new StringBuffer(sql);
		Collections.sort(tblSegments);
		for (int i = tblSegments.size() - 1; i >= 0; i--) {
			int position = tblSegments.get(i).getEnd();
			buffer.replace(position, position, " (nolock)");
		}
		return buffer.toString();
	}

	private boolean isSelectStatement(String sql) {
		return sql.trim().toLowerCase().startsWith("select");
	}

	/**
	 * 从指定集合中取出所有非null的值并去重。
	 * 
	 * @param values
	 *            传入null等价于传入空集合。
	 * @return 返回值集合将保持输入集合的顺序。正常情况下不会返回null。
	 */
	private static <V> LinkedHashSet<V> getRipeValues(Collection<V> values) {
		LinkedHashSet<V> ripeValues = new LinkedHashSet<V>();
		if (values != null) {
			ripeValues.addAll(values);
			ripeValues.remove(null);
		}
		return ripeValues;
	}
}
