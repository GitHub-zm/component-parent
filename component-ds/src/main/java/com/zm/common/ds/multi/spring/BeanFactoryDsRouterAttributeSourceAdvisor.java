package com.zm.common.ds.multi.spring;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

@SuppressWarnings("serial")
public class BeanFactoryDsRouterAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

	private AnnotationDsRouterAttributeSource attributeSource;

	private final DsRouterAttributeSourcePointcut pointcut = new DsRouterAttributeSourcePointcut() {
		@Override
		protected AnnotationDsRouterAttributeSource getAttributeSource() {
			return attributeSource;
		}
	};

	public void setAttributeSource(AnnotationDsRouterAttributeSource attributeSource) {
		this.attributeSource = attributeSource;
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

}
