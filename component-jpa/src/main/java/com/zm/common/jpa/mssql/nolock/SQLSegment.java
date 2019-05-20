package com.zm.common.jpa.mssql.nolock;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * SQL语句的片段。
 * 
 * @author zengyun
 * @since 1.0.2
 * 
 */
public class SQLSegment implements Comparable<SQLSegment> {

	private int start;
	private int end;

	public SQLSegment(int start, int end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * 开始位置。
	 */
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 结束位置。
	 */
	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return MessageFormat.format("[{0},{1}]", start, end);
	}

	@Override
	public int compareTo(SQLSegment o) {
		return (start < o.start ? -1 : (start == o.start ? 0 : 1));
	}

	/**
	 * 从指定的SQL片段中，找出第一个单词的片段。<br>
	 * 如果传入的单词中有“(”或“)”，只会匹配第一个不成对的括号。
	 * 
	 * @param sql
	 *            SQL语句。禁止传入null。
	 * @param start
	 *            SQL片段的开始位置。
	 * @param end
	 *            SQL片段的结束位置。
	 * @param words
	 *            包含单词的数组，大小写不敏感。忽略所有null元素和空字符串。
	 * @return 若找不到将返回null。
	 * @throws IllegalArgumentException
	 *             当参数sql或words为null时抛出。
	 */
	public static SQLSegment getFirstWordSegment(String sql, int start, int end, String[] words)
			throws IllegalArgumentException {
		Assert.notNull(sql);
		Assert.notNull(words);

		SQLSegment ret = null;
		String lowerCaseSql = sql.substring(start, end).toLowerCase();
		for (String word : words) {
			if (StringUtils.isBlank(word)) {
				continue;
			}
			String lowerCaseWord = word.toLowerCase();
			int index = getIndexOfWord(lowerCaseSql, lowerCaseWord);
			if (index < 0) {
				continue;
			}
			if (ret == null || start + index < ret.getStart()) {
				ret = new SQLSegment(start + index, start + index + lowerCaseWord.length());
			}
		}
		return ret;
	}

	/**
	 * 从指定的SQL片段中，找出介于左右边界单词之间的所有片段，并且片段内容不包括左右边界单词。<br>
	 * 如果匹配了左边界单词但是匹配不上右边界单词，则将左边界单词之后的所有内容作为一个片段返回。<br>
	 * 如果传入的单词中有“(”或“)”，只会匹配第一个不成对的括号。
	 * 
	 * @param sql
	 *            SQL语句。禁止传入null。
	 * @param segment
	 *            传入null意味着要分析整句sql语句。
	 * @param leftWords
	 *            包含左边界单词的数组，大小写不敏感。
	 * @param rightWords
	 *            包含右边界单词的数组，大小写不敏感。
	 * @return 正常情况下不会返回null。
	 * @throws IllegalArgumentException
	 *             当参数sql、leftWords或rightWords为null时抛出。
	 */
	public static List<SQLSegment> getSegmentsBetween(String sql, SQLSegment segment, String[] leftWords,
			String[] rightWords) throws IllegalArgumentException {
		Assert.notNull(sql);
		Assert.notNull(leftWords);
		Assert.notNull(rightWords);

		List<SQLSegment> retSegments = new ArrayList<SQLSegment>();
		int start = segment == null ? 0 : segment.getStart();
		final int end = segment == null ? sql.length() : segment.getEnd();
		while (start < end) {
			SQLSegment leftSegment = getFirstWordSegment(sql, start, end, leftWords);
			if (leftSegment == null) {
				break;
			}
			SQLSegment rightSegment = getFirstWordSegment(sql, leftSegment.getEnd(), end, rightWords);
			if (rightSegment == null) {
				retSegments.add(new SQLSegment(leftSegment.getEnd(), end));
				break;
			}
			retSegments.add(new SQLSegment(leftSegment.getEnd(), rightSegment.getStart()));
			start = rightSegment.getEnd();
		}
		return retSegments;
	}

	/**
	 * @param str
	 * @param word
	 *            如果传入“(”或“)”，只会匹配第一个不成对的括号。
	 */
	private static int getIndexOfWord(String str, String word) {
		assert str != null;
		assert word != null;

		if (word.length() != 1) {
			return str.indexOf(word);
		}
		char ch = word.charAt(0);
		if (ch != '(' && ch != ')') {
			return str.indexOf(ch);
		}

		int aloneLeftBracketCount = 0; // 不成对的左括号计数
		for (int index = 0; index < str.length(); index++) {
			char c = str.charAt(index);
			if (c == ch && aloneLeftBracketCount == 0) {
				return index;
			}
			if (c == '(') {
				aloneLeftBracketCount++;
			} else if (c == ')' && aloneLeftBracketCount > 0) {
				aloneLeftBracketCount--;
			}
		}
		return -1;
	}

}
