package com.zm.common.core.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.Assert;

/**
 * 常用文件工具类
 * 
 */
public class FileUtils {

	/**
	 * 构建文件名
	 * 
	 * @param path
	 *            文件路径。
	 * @param fileName
	 *            文件名，禁止传入null。
	 * @return
	 */
	public static String buildFileName(String path, String fileName) {
		Assert.notNull(fileName);

		if (path.endsWith("/") || path.endsWith("\\")) {
			return path + fileName;
		} else {
			return path + File.separator + fileName;
		}
	}

	/**
	 * 目录不存在时创建目录。
	 * 
	 * @param path
	 *            文件路径，禁止传入null。
	 */
	public static void mkdir(String path) {
		Assert.notNull(path);

		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
	}

	/**
	 * 获取指定类所在位置。<br>
	 * 当类来自jar包时，则返回这个jar的真实文件位置;<br>
	 * 否则是以WEB-INF/classes方式部署的, 则返回这个目录的真实位置。
	 * 
	 * @param bassClazz
	 *            用于定位的类，禁止传入null。
	 * @throws FileNotFoundException
	 */
	public static File getLocation(Class<?> bassClazz) throws FileNotFoundException {
		Assert.notNull(bassClazz);

		String codeLocation = bassClazz.getProtectionDomain().getCodeSource().getLocation().toString();

		// suppose it is a file!
		String location = codeLocation;
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			location = codeLocation.substring("file:/".length());
		} else {
			location = codeLocation.substring("file:".length());
		}

		File codeFile = null;
		if (location.endsWith(".class")) {
			// WEB-INF/classes
			int classLen = bassClazz.getName().length() + ".class".length();
			codeFile = new File(location.substring(0, location.length() - classLen));
		} else if (location.endsWith(".jar")) {
			// file
			codeFile = new File(location);
		} else {
			codeFile = new File(location);
		}

		// if (codeFile == null) {
		// throw new FileNotFoundException("Cannot handle code location: " +
		// codeLocation);
		// }
		return codeFile;
	}
}
