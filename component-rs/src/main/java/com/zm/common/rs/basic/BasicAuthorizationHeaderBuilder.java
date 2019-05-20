package com.zm.common.rs.basic;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Assert;

/**
 * Basic认证实现
 * 
 */
public class BasicAuthorizationHeaderBuilder {

	/**
	 * 构建认证字符串。
	 * 
	 * @param userName
	 *            用户名，禁止传入null。
	 * @param password
	 *            密码，禁止传入null。
	 * @return
	 */
	public static String build(String userName, String password) {
		Assert.notNull(userName);
		Assert.notNull(password);

		String auth = userName + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		return authHeader;
	}
}
