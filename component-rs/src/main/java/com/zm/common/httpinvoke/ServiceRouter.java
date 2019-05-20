package com.zm.common.httpinvoke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;

public class ServiceRouter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private int connectTimeout = 10000, readTimeout = 60000;

	private StaticApplicationContext context;

	@SuppressWarnings("unchecked")
	synchronized public <ServiceInterface> ServiceInterface getRemoteService(String path, String exportBeanId,
			Class<ServiceInterface> serviceInterface) throws Exception {
		String beanId = path + ":" + exportBeanId;
		logger.debug("获取远程服务:" + beanId);
		Object bean = null;
		try {
			bean = context.getBean(beanId);
		} catch (BeansException e) {
			// 首次执行
			String serviceUrl = path + exportBeanId;

			SimpleHttpInvokerRequestExecutor executor = new SimpleHttpInvokerRequestExecutor();
			executor.setConnectTimeout(connectTimeout);
			executor.setReadTimeout(readTimeout);

			MutablePropertyValues pvs = new MutablePropertyValues();
			pvs.add("serviceUrl", serviceUrl);
			pvs.add("serviceInterface", serviceInterface);
			pvs.add("httpInvokerRequestExecutor", executor);
			context.registerSingleton(beanId, HttpInvokerProxyFactoryBean.class, pvs);

			bean = context.getBean(beanId);
		}
		return (ServiceInterface) bean;
	}
}
