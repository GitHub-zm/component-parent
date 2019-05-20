package com.zm.common.jpa.hibernate;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.CharacterStream;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.CharacterStreamImpl;
import org.hibernate.type.descriptor.java.DataHelper;

public class TrimStringTypeDescriptor extends AbstractTypeDescriptor<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1151315196478946828L;

	public static final TrimStringTypeDescriptor INSTANCE = new TrimStringTypeDescriptor();

	public TrimStringTypeDescriptor() {
		super(String.class);
	}

	public String toString(String value) {
		return value;
	}

	public String fromString(String string) {
		return string;
	}

	@SuppressWarnings({ "unchecked" })
	public <X> X unwrap(String value, Class<X> type, WrapperOptions options) {
		if (value == null) {
			return null;
		}
		if (String.class.isAssignableFrom(type)) {
			return (X) value;
		}
		if (Reader.class.isAssignableFrom(type)) {
			return (X) new StringReader((String) value);
		}
		if (CharacterStream.class.isAssignableFrom(type)) {
			return (X) new CharacterStreamImpl((String) value);
		}
		if (Clob.class.isAssignableFrom(type)) {
			return (X) options.getLobCreator().createClob(value);
		}
		if (DataHelper.isNClob(type)) {
			return (X) options.getLobCreator().createNClob(value);
		}

		throw unknownUnwrap(type);
	}

	public <X> String wrap(X value, WrapperOptions options) {
		if (value == null) {
			return null;
		}
		if (String.class.isInstance(value)) {
			return ((String) value).trim();
		}
		if (Reader.class.isInstance(value)) {
			return DataHelper.extractString((Reader) value);
		}
		if (Clob.class.isInstance(value) || DataHelper.isNClob(value.getClass())) {
			try {
				return DataHelper.extractString(((Clob) value).getCharacterStream());
			} catch (SQLException e) {
				throw new HibernateException("Unable to access lob stream", e);
			}
		}

		throw unknownWrap(value.getClass());
	}
}
