package com.zm.common.jpa.support;

/**
 * 默认persistence.xml位置配置
 * 
 */
public class DefaultPersistenceXmlLocationConfigure implements PersistenceXmlLocation {

	private String[] persistenceXmlLocations = new String[] {};

	@Override
	public String[] getPersistenceXmlLocations() {
		return persistenceXmlLocations;
	}

	public void setPersistenceXmlLocation(String persistenceXmlLocation) {
		this.persistenceXmlLocations = new String[] { persistenceXmlLocation };
	}

	public void setPersistenceXmlLocations(String... persistenceXmlLocations) {
		this.persistenceXmlLocations = persistenceXmlLocations;
	}

}
