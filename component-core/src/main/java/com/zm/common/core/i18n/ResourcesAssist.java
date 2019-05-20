package com.zm.common.core.i18n;

import java.util.ResourceBundle;

/**
 * 资源实现类的基类。
 *
 */
public class ResourcesAssist implements BaseResources{

	public ResourcesAssist() {
		// Do Nothing;
	}

	private ResourceBundle resourceBundle = null;

	@Override
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	@Override
	public String getResource(String key) {
		return ensureValue(resourceBundle, key);
	}

	private static String ensureValue(ResourceBundle bundle, String key) {
		if (bundle == null) {
			return key;
		}

		if (bundle.containsKey(key) == false) {
			return key;
		}

		// 将“"”转换为“\\"”
		String value = bundle.getString(key);
		StringBuffer sb = new StringBuffer();
		char last = 0;
		for (int index = 0; index < value.length(); index++) {
			char ch = value.charAt(index);
			if (ch == '"') {
				if (index != 0 && last == '\\') {
					sb.deleteCharAt(index - 1);
				}
			}
			sb.append(ch);
			last = ch;
		}

		return sb.toString();
	}

}
