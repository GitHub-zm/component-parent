package com.zm.common.ds.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import com.zm.common.ds.multi.spring.AnnotationDsRouterAttributeSource;
import com.zm.common.ds.multi.spring.BeanFactoryDsRouterAttributeSourceAdvisor;
import com.zm.common.ds.multi.spring.DsRouterInterceptor;

/**
 * DsRouterMgr 注解配置入口
 * 
 */
@Configuration
public class DsRouterAnnotationConfiguration {

	@Autowired
	private DsRouter dsRouter;

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryDsRouterAttributeSourceAdvisor dsRouterMgrAdvisor() {
		BeanFactoryDsRouterAttributeSourceAdvisor advisor = new BeanFactoryDsRouterAttributeSourceAdvisor();
		advisor.setAttributeSource(dsRouterAttributeSource());
		advisor.setAdvice(dsRouterInterceptor());
		advisor.setOrder(1);
		return advisor;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AnnotationDsRouterAttributeSource dsRouterAttributeSource() {
		return new AnnotationDsRouterAttributeSource();
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public DsRouterInterceptor dsRouterInterceptor() {
		DsRouterInterceptor interceptor = new DsRouterInterceptor();
		interceptor.setAttributeSource(dsRouterAttributeSource());
		interceptor.setDsRouter(dsRouter);
		return interceptor;
	}
}
