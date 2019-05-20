package com.zm.common.customize;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.zm.common.customize.annotation.FunctionProfile;
import com.zm.common.customize.annotation.FunctionSwitch;

/**
 * 
 * 提供功能清单加载，配合{@linkplain FunctionSwitch}或{@linkplain FunctionProfile}进行使用。<br>
 * 配置文件：<strong>classpath:functions.properties</strong> <br>
 * 
 * <pre>
 * 配置方式，在web.xml中增加配置项，如下：
 * 
 * &lt;context-param&gt;
 *   &lt;param-name&gt;contextInitializerClasses&lt;/param-name&gt;
 *   &lt;param-value&gt;com.hd123.orchid.customize.FunctionApplicationContextInitializer&lt;/param-value&gt;
 * &lt;/context-param&gt;
 * </pre>
 * 
 * @see FunctionSwitch
 * @see FunctionProfile
 */
public class FunctionApplicationContextInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	protected final Log logger = LogFactory.getLog(getClass());

	private static final String DEFAULT_FUNCTIONS_PROPERTIES = "classpath:functions.properties";

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		try {
			ResourcePropertySource functionPropertySource = new ResourcePropertySource("functionPropertySource",
					DEFAULT_FUNCTIONS_PROPERTIES);
			environment.getPropertySources().addFirst(functionPropertySource);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
