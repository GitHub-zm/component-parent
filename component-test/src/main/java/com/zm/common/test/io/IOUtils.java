package com.zm.common.test.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtils {

	public static <T> byte[] toBytes(T obj) throws IOException {
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(new BufferedOutputStream(baos));
			oos.writeObject(obj);
			oos.flush();
			return baos.toByteArray();
		} finally {
			if (oos != null) {
				oos.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> T toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInputStream ios = null;
		try {
			ios = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
			Object object = ios.readObject();
			return (T) object;
		} finally {
			if (ios != null) {
				ios.close();
			}
		}

	}

}
