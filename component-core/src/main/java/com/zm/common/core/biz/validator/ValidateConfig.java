package com.zm.common.core.biz.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * 验证Bean时的配置参数
 * 
 */
public class ValidateConfig {

	/** 特性名，特性是一类包含TRUE/FALSE值的开关 */
	public static final String USE_JAXB_PROPERTY_NAME = "USE_JAXB_PROPERTY_NAME";

	/**
	 * 启用某个特性。
	 * 
	 * @param featureName
	 *            特性名，取值是{@link ValidateConfig}中的常量，not null
	 */
	public void enable(String featureName) {
		Assert.notNull(featureName, "featureName");
		map.put(featureName, Boolean.TRUE);
	}

	/**
	 * 禁用某个特性。
	 * 
	 * @param featureName
	 *            特性名，取值是{@link ValidateConfig}中的常量，not null
	 */
	public void disable(String featureName) {
		Assert.notNull(featureName, "featureName");
		map.remove(featureName);
	}

	/**
	 * 判断是否启用了某个特性。
	 * 
	 * @param featureName
	 *            特性名，not null
	 * @return 启用了则返回true，否则返回false。
	 */
	public boolean isEnabled(String featureName) {
		Assert.notNull(featureName, "featureName");
		Object v = map.get(featureName);
		return v != null && Boolean.TRUE.equals(v);
	}

	private Map<String, Object> map = new HashMap();
}
