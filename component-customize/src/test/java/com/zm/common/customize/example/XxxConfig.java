package com.zm.common.customize.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.zm.common.customize.annotation.FunctionSwitch;

@Configuration
@ImportResource("classpath:META-INF/xxx/*.xml")
@FunctionSwitch(value = "xxx", description = "xxx功能")
public class XxxConfig {

}
