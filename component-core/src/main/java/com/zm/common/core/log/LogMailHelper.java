package com.zm.common.core.log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;

/**
 * 
 */
public class LogMailHelper {

	private static LogMailHelper default_ = null;
	private String cache;

	public static LogMailHelper getDefault() {
		return default_;
	}

	public LogMailHelper() {
		default_ = this;
	}

	private JavaMailSender mailSender;
	private String subjectPrefix;
	private String from;
	private String to;
	private String cc;
	private String bcc;

	private JavaMailSender getMailSender() {
		Assert.notNull(mailSender, "mailSender");
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/** 邮件标题前缀。 */
	public void setSubjectPrefix(String subjectPrefix) {
		this.subjectPrefix = subjectPrefix;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public void send(LoggingEvent event) throws Exception {
		Assert.notNull(event, "event");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(from);
		mail.setTo(decodeAddresses(to));
		mail.setCc(decodeAddresses(cc));
		mail.setBcc(decodeAddresses(bcc));
		mail.setSubject(buildSubject(event));
		mail.setText(buildText(event));

		if (StringUtils.equals(cache, mail.getSubject())) {
			return;
		}
		getMailSender().send(mail);
		cache = mail.getSubject();
	}

	private String[] decodeAddresses(String addrs) {
		if (!StringUtils.isBlank(addrs)) {
			return split(addrs, ";, ", '\\', 0);
		}
		return null;
	}

	private String buildSubject(LoggingEvent event) {
		assert event != null;
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(subjectPrefix) == false) {
			sb.append(subjectPrefix);
		}
		sb.append('[');
		sb.append(event.getLevel().toString());
		sb.append(']');
		sb.append(event.getMessage());
		return sb.toString();
	}

	private String buildText(LoggingEvent event) {
		assert event != null;
		StringBuffer sb = new StringBuffer();
		sb.append(printLogEvent(event));
		return sb.toString();
	}

	private String printLogEvent(LoggingEvent event) {
		assert event != null;
		StringBuffer sb = new StringBuffer();
		sb.append(event.getMessage().toString());
		if (event.getThrowableStrRep() != null) {
			for (String str : event.getThrowableStrRep()) {
				sb.append('\n');
				sb.append(str);
			}
		}
		return sb.toString();
	}

	/**
	 * 支持转义的字符串拆分。
	 * 
	 * @param input
	 *            输入经过转义的字符串。传入null导致返回null。
	 * @param separators
	 *            包含所有分隔字符的字符串。
	 * @param escape
	 *            语法中的转义字符。
	 * @param limit
	 *            模式应用次数上限，也就是说返回数组的最大长度。传入小于等于0的数值时，表示不限制。
	 * @return 返回拆分后的字符串数组。
	 * @throws IllegalArgumentException
	 *             当input中字符串违反语法要求时抛出，例如转义字符之后没有其它字符。
	 * @since 1.4
	 */
	private String[] split(String input, String separators, char escape, int limit) throws IllegalArgumentException {
		if (input == null) {
			return null;
		}
		int ripeLimit = limit <= 0 ? Integer.MAX_VALUE : limit;
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		boolean escapeOn = false;
		for (char c : input.toCharArray()) {
			if (escapeOn) {
				sb.append(c);
				escapeOn = false;
			} else {
				if (separators.indexOf(c) >= 0) {
					if (list.size() >= ripeLimit - 1) {
						sb.append(c);
					} else {
						list.add(sb.toString());
						sb = new StringBuffer();
					}
				} else if (c == escape) {
					sb.append(c);
					escapeOn = true;
				} else {
					sb.append(c);
				}
			}
		}
		if (escapeOn) {
			throw new IllegalArgumentException(MessageFormat.format("转义字符“{0}”之后缺少字符", escape));
		}
		list.add(sb.toString());
		return list.toArray(new String[] {});
	}

}
