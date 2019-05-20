package com.zm.common.quartz;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Trigger;

/**
 * Job Trigger 自动注册配置
 * 
 */
public class JobTriggerAutoRegisterConfigure implements JobTriggerAutoRegisterable {

	private List<Trigger> triggers = new ArrayList<Trigger>();

	@Override
	public List<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers.addAll(triggers);
	}
}
