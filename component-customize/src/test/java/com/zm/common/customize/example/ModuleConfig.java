package com.zm.common.customize.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.zm.common.customize.annotation.FunctionSwitch;

/**
 * functions配置为“xxx.moudle=true”或者“xxx.moudle=false”
 *
 */
@Configuration
@ImportResource("classpath:xxx.xml")
@Import({ XxxConfig.class })
@FunctionSwitch(value = "xxx.moudle", description = "xxx功能模块")
public class ModuleConfig {

}
