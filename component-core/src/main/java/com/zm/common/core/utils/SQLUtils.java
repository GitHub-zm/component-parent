/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2016，所有权利保留。
 * 
 * 项目名：	orchid-commons-core
 * 文件名：	SQLUtils.java
 * 模块说明：	
 * 修改历史：
 * 2016年7月4日 - xietao - 创建。
 */
package com.zm.common.core.utils;

import java.util.Iterator;
import java.util.Objects;

/**
 * SQL工具类
 * 
 */
public class SQLUtils {
	private SQLUtils() {
	}

	public static final String EMPTY = "";
	public static final String SQL_QUOTE = "'";

	/**
	 * 
	 * @param iterable
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String joinStr(Iterable<String> iterable, String separator) {
		if (iterable == null) {
			return null;
		}
		return joinStr(iterable.iterator(), separator);
	}

	public static String joinStr(Iterator<?> iterator, String separator) {
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return SQL_QUOTE + Objects.toString(first, "") + SQL_QUOTE;
		}

		StringBuilder buf = new StringBuilder(256);
		if (first != null) {
			buf.append(SQL_QUOTE).append(first).append(SQL_QUOTE);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(SQL_QUOTE).append(obj).append(SQL_QUOTE);
			}
		}
		return buf.toString();
	}

	public static String joinStr(String[] array, String separator) {
		if (array == null) {
			return null;
		}
		return joinStr(array, separator, 0, array.length);
	}

	public static String joinStr(String[] array, String separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}

		StringBuilder buf = new StringBuilder(noOfItems * 16);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(SQL_QUOTE).append(array[i]).append(SQL_QUOTE);
			}
		}
		return buf.toString();
	}
}
