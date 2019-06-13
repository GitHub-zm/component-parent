package com.zm.common.core.timer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 计时器，用于记录执行次数以及执行时间。
 * 
 */
public class ElapsedTimer {

	private static final ThreadLocal<ElapsedTimer> threadInstances = new ThreadLocal<ElapsedTimer>() {
		@Override
		protected ElapsedTimer initialValue() {
			return new ElapsedTimer();
		}
	};

	/**
	 * 取得当前线程的计时器实例。
	 */
	public static ElapsedTimer getThreadInstance() {
		return threadInstances.get();
	}

	/**
	 * 累加上另一个计时器的数据。
	 * 
	 * @param another
	 *            传入null将导致操作被忽略。
	 */
	public void accumulate(ElapsedTimer another) {
		if (another == null) {
			return;
		}
		for (Map.Entry<String, Record> e : another.watch.entrySet()) {
			Record rec = watch.get(e.getKey());
			if (rec == null) {
				watch.put(e.getKey(), e.getValue().clone());
			} else {
				rec.accumulate(e.getValue());
			}
		}
	}

	/**
	 * 清空所有数据。
	 */
	public void clear() {
		watch.clear();
	}

	/**
	 * 对指定操作开始计时。
	 */
	public void start(String action) {
		Record r = watch.get(action);
		if (r == null) {
			r = new Record();
			watch.put(action, r);
		}
		r.start = System.currentTimeMillis();
	}

	/**
	 * 对指定操作停止计时，并返回操作执行的毫秒数。
	 */
	public long stop(String action) {
		Record r = watch.get(action);
		long time = 0;
		if (r != null) {
			time = System.currentTimeMillis() - r.start;
			r.total += time;
			r.max = Math.max(r.max, time);
			r.listMin.add(time);
			r.min = r.getMinRec();
			r.count++;
		}
		return time;
	}

	/**
	 * 对指定操作停止计时，并累计本次操作处理的资源数。
	 * 
	 * @param action
	 *            描述操作的字符串。
	 * @param processMessagePattern
	 *            字符串模版，用于构造描述操作处理的资源数的字符串。<br>
	 *            格式为：动词+通配符“{0}”+量词+名词，比如“查出{0}条记录”，再比如“修改了{0}条记录”。
	 * @param resourceCount
	 *            本次操作处理的资源数。
	 * @return 返回操作执行的毫秒数。
	 */
	public long stop(String action, String processMessagePattern, int resourceCount) {
		Record r = watch.get(action);
		long time = 0;
		if (r != null) {
			time = System.currentTimeMillis() - r.start;
			r.total += time;
			r.count++;
			r.processMessagePattern = processMessagePattern;
			r.resourceCount += resourceCount;
			if (r.max < time) {
				r.max = time;
				r.maxResourceCount = resourceCount;
			}
			if (r.getMinRec() != null) {
				long min = r.getMinRec();
				if (min > time) {
					r.min = time;
					r.minResourceCount = resourceCount;
				}
			} else {
				r.min = time;
				r.minResourceCount = resourceCount;
			}
			r.listMin.add(time);
		}
		return time;
	}

	@Override
	public ElapsedTimer clone() {
		ElapsedTimer ret = new ElapsedTimer();
		for (Map.Entry<String, Record> e : watch.entrySet()) {
			ret.watch.put(e.getKey(), e.getValue().clone());
		}
		return ret;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Record> e : watch.entrySet()) {
			sb.append("“");
			sb.append(e.getKey());
			sb.append("”");
			sb.append(e.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}

	private Map<String, Record> watch = new LinkedHashMap<String, Record>();

	private class Record {
		long start;
		long total = 0;
		long max = 0;
		long min = 0;
		List<Long> listMin = new ArrayList<Long>();
		int count = 0;
		String processMessagePattern;
		int resourceCount = 0;
		int maxResourceCount = 0;
		int minResourceCount = 0;

		public void accumulate(Record another) {
			if (another == null) {
				return;
			}
			total += another.total;
			count += another.count;
			resourceCount += another.resourceCount;
			if (max < another.max) {
				max = another.max;
				maxResourceCount = another.maxResourceCount;
			}
			if (min > another.min) {
				min = another.min;
				minResourceCount = another.minResourceCount;
				listMin.clear();
				listMin.addAll(another.listMin);
			}
		}

		private Long getMinRec() {
			if (listMin.isEmpty()) {
				return null;
			}
			Collections.sort(listMin);
			return listMin.get(0);
		}

		@Override
		public Record clone() {
			Record ret = new Record();
			ret.start = start;
			ret.total = total;
			ret.max = max;
			ret.min = min;
			ret.listMin = listMin;
			ret.count = count;
			ret.processMessagePattern = processMessagePattern;
			ret.resourceCount = resourceCount;
			ret.maxResourceCount = maxResourceCount;
			ret.minResourceCount = minResourceCount;
			return ret;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("执行");
			sb.append(count);
			sb.append("次共");
			sb.append(toSeconds(total));
			sb.append("秒");
			sb.append(formatProcessMessage(resourceCount));
			if (count > 1) {
				sb.append("，最久一次执行");
				sb.append(toSeconds(max));
				sb.append("秒");
				sb.append(formatProcessMessage(maxResourceCount));

				sb.append("，最快一次执行");
				sb.append(toSeconds(min));
				sb.append("秒");
				sb.append(formatProcessMessage(minResourceCount));
			}
			return sb.toString();
		}

		private String formatProcessMessage(int resourceCount) {
			if (processMessagePattern == null) {
				return "";
			}
			return MessageFormat.format(processMessagePattern, Integer.valueOf(resourceCount));
		}
	}

	private static final BigDecimal MILLISECONDS_PER_SECOND = new BigDecimal(1000);

	private static BigDecimal toSeconds(long milliseconds) {
		return new BigDecimal(milliseconds).divide(MILLISECONDS_PER_SECOND, 3, RoundingMode.HALF_DOWN);
	}

}
