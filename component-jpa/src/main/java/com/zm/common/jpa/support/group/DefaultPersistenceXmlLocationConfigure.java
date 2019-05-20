package com.zm.common.jpa.support.group;

/**
 * 默认persistence.xml位置配置
 * 
 */
public class DefaultPersistenceXmlLocationConfigure implements PersistenceXmlLocation {

	private String group;
	private String[] persistenceXmlLocations = new String[] {};

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String getGroup() {
		return group;
	}

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
