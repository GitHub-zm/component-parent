package com.zm.common.ds.multi.spring;

/**
 * DsRouterMgr 注解定义
 */
public class DsRouterMgrDefinition {

	private String type;

	private int index;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSwitchFactorParameterIndex() {
		return index;
	}

	public void setSwitchFactorParameterIndex(int index) {
		this.index = index;
	}
}
