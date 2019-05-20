package com.zm.common.core.collections;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.Assert;

/**
 * 提供{@link Collection}的工具方法。
 */
public class CollectionUtils {

	/**
	 * 移除指定集合中的所有null值。
	 *
	 * @param source
	 *            待移除null的集合,禁止传入null。
	 * @return 新的结果集合。
	 */
	public static <T> Collection<T> removeNull(final Collection<T> source) {
		Assert.notNull(source);

		ArrayList<T> list = new ArrayList<>(source.size());

		for (T value : source) {
			if (value != null) {
				list.add(value);
			}
		}
		return list;
	}
}
