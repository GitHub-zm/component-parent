package com.zm.common.jpa.support;

/**
 * JPA persistence.xml 文件位置接口，用于指示持久化对象配置文件位置。
 * 
 */
public interface PersistenceXmlLocation {

	/**
	 * 获取persistence.xml的位置。
	 */
	String[] getPersistenceXmlLocations();
}
