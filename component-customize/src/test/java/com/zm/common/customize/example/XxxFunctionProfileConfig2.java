package com.zm.common.customize.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.zm.common.customize.annotation.FunctionProfile;

/**
 * functions配置为“xxx.version=2”
 */
@Configuration
@ImportResource("classpath:META-INF/xxx/beans2.xml")
@FunctionProfile(name = "xxx.version", value = "2")
public class XxxFunctionProfileConfig2 {

}
