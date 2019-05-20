package com.zm.common.test.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpiderTets2 {
	public static void main(String[] args) throws IOException {

		URL url = new URL("https://www.dianping.com");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
		String msg = null;
		while (null != (msg = bufferedReader.readLine())) {
			System.out.println(msg);
		}

	}
}
