package com.zm.common.core.i18n;

import java.util.ResourceBundle;

/**
 * 提供所有资源接口类的基类。
 * 
 */
public interface BaseResources {

	/**
	 * 取得对应的ResourceBundle对象。
	 */
	ResourceBundle getResourceBundle();

	/**
	 * 根据key取得指定资源。
	 * 
	 * @param key
	 */
	String getResource(String key);
}