package com.zm.common.ds.multi;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

/**
 * DsRouter 数据源切换切面。
 * 
 */
public class DsRouterAspect implements Ordered {

	/** 默认顺序：{@value} */
	private static final int DEFAULT_ORDER = 1;

	@Autowired
	private DsRouter dsRouter;
	/**
	 * 分组
	 */
	private String cat;
	/**
	 * 切面顺序
	 */
	private int order = DEFAULT_ORDER;

	public String getCat() {
		Assert.notNull(cat, "cat");
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public Object selectDataSource(ProceedingJoinPoint jp, String storeCode) throws Throwable {
		dsRouter.setKey(getCat(), storeCode);
		return jp.proceed();
	}

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
