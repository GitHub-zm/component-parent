package com.zm.common.jpa.mssql.nolock;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import com.zm.common.jpa.mssql.nolock.annotation.AnnotationNolockAttributeSource;
import com.zm.common.jpa.mssql.nolock.interceptor.BeanFactoryNolockAttributeSourceAdvisor;
import com.zm.common.jpa.mssql.nolock.interceptor.NolockInterceptor;

/**
 * NoLock 注解配置入口
 * 
 */
@Configuration
public class ProxyNolockManagementConfiguration {

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryNolockAttributeSourceAdvisor transactionAdvisor() {
		BeanFactoryNolockAttributeSourceAdvisor advisor = new BeanFactoryNolockAttributeSourceAdvisor();
		advisor.setNolockAttributeSource(nolockAttributeSource());
		advisor.setAdvice(nolockInterceptor());
		return advisor;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AnnotationNolockAttributeSource nolockAttributeSource() {
		return new AnnotationNolockAttributeSource();
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public NolockInterceptor nolockInterceptor() {
		NolockInterceptor interceptor = new NolockInterceptor();
		interceptor.setNolockAttributeSource(nolockAttributeSource());
		return interceptor;
	}

}
