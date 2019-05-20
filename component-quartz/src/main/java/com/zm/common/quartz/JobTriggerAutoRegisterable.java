package com.zm.common.quartz;

import java.util.List;

import org.quartz.Trigger;

/**
 * Job Trigger 自动注册接口，指示实现类将自动注册触发器。
 * 
 */
public interface JobTriggerAutoRegisterable {

	List<Trigger> getTriggers();
}
