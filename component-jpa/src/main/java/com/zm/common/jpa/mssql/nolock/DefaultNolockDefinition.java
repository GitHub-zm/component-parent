package com.zm.common.jpa.mssql.nolock;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class DefaultNolockDefinition implements NolockDefinition {

	private List<String> tableNames = new ArrayList<String>();

	@Override
	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

}
