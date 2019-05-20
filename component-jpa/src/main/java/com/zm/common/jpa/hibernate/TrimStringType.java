package com.zm.common.jpa.hibernate;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

/**
 * A type that maps between {@link java.sql.Types#VARCHAR VARCHAR} and
 * {@link String} <br>
 * 旨在解决数据类型char时，字符串空格问题。
 * 
 * <p>
 * 用法： 在字段 or 方法上标注 <code>
 * 
 * @Type(type = TrimStringType.TYPE_NAME) </code>
 * 
 *            </p>
 * 
 * @see org.hibernate.type.StringType
 */
public class TrimStringType extends AbstractSingleColumnStandardBasicType<String> implements DiscriminatorType<String> {

	public static final String TYPE_NAME = "com.hd123.orchid.jpa.hibernate.TrimStringType";

	public static final TrimStringType INSTANCE = new TrimStringType();

	/**
	 * 
	 */
	private static final long serialVersionUID = 5418440477160834320L;

	public TrimStringType() {
		super(VarcharTypeDescriptor.INSTANCE, TrimStringTypeDescriptor.INSTANCE);
	}

	public String getName() {
		return "string";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}

	@Override
	public String objectToSQLString(String value, Dialect dialect) throws Exception {
		return '\'' + value + '\'';
	}

	public String stringToObject(String xml) throws Exception {
		return xml;
	}

	public String toString(String value) {
		return value;
	}
}
