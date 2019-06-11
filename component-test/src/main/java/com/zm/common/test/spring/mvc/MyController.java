package com.zm.common.test.spring.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CustomController
@CustomRequestMapping("")
public class MyController {
	@CustomQualifier("MyServiceImpl")
	private MyService myService;

	@CustomRequestMapping("/query")
	public void query(HttpServletRequest request, HttpServletResponse response, @CustomRequestParam("name") String name,
			@CustomRequestParam("age") String age) {
		try {
			PrintWriter writer = response.getWriter();
			String result = myService.query(name, age);
			writer.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
