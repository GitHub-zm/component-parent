package com.zm.common.jpa.h3;

import com.zm.common.jpa.hibernate.TrimStringType;

/**
 * 旨在解决数据类型char时，字符串空格问题。
 * 
 */
public class SQLServerDialect extends org.hibernate.dialect.SQLServerDialect {

	@Override
	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
			String[] primaryKey, boolean referencesPrimaryKey) {
		// 返回一句无聊的话，目的是不产生ForiegnKey。
		// 尚未测试 -- lxm 2008-9-24
		return " enable trigger all";
	}

	@Override
	public boolean supportsUniqueConstraintInCreateAlterTable() {
		return false;
	}

	public SQLServerDialect() {
		this.addTypeOverride(TrimStringType.INSTANCE);
	}

}
