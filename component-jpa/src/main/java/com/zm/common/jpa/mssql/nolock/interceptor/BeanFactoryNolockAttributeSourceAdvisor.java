package com.zm.common.jpa.mssql.nolock.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

import com.zm.common.jpa.mssql.nolock.NolockAttributeSource;

/**
 * 
 */
@SuppressWarnings("serial")
public class BeanFactoryNolockAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

	private NolockAttributeSource nolockAttributeSource;

	private final NolockAttributeSourcePointcut pointcut = new NolockAttributeSourcePointcut() {
		@Override
		protected NolockAttributeSource getNolockAttributeSource() {
			return nolockAttributeSource;
		}
	};

	public void setNolockAttributeSource(NolockAttributeSource nolockAttributeSource) {
		this.nolockAttributeSource = nolockAttributeSource;
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

}
