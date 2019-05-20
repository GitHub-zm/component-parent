package com.zm.common.test.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SpiderTets {
	public static void main(String[] args) throws IOException {

		URL url = new URL("https://www.jd.com");
		// 点评没有权限
		// URL url = new URL("https://www.dianping.com");
		InputStream is = url.openStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String msg = null;
		while (null != (msg = bufferedReader.readLine())) {
			System.out.println(msg);
		}

	}
}
