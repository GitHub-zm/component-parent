package com.zm.common.customize.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.zm.common.customize.annotation.FunctionProfile;

/**
 * functions配置为“xxx.version=1”
 *
 */
@Configuration
@ImportResource("classpath:META-INF/xxx/beans1.xml")
@FunctionProfile(name = "xxx.version", value = "1")
public class XxxFunctionProfileConfig {

}
