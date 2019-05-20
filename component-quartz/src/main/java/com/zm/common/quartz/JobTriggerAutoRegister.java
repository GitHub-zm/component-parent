package com.zm.common.quartz;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * Job Trigger 自动注册器
 * 
 */
public class JobTriggerAutoRegister implements InitializingBean, ApplicationContextAware {
	private final Log logger = LogFactory.getLog(getClass());

	private ApplicationContext appCxt;
	private Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Scheduler getScheduler() {
		Assert.notNull(scheduler);
		return scheduler;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appCxt = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, JobTriggerAutoRegisterable> map = appCxt.getBeansOfType(JobTriggerAutoRegisterable.class);

		logger.info("开始自动注册Job Triggers...");
		for (JobTriggerAutoRegisterable item : map.values()) {
			for (Trigger trigger : item.getTriggers()) {
				logger.info("注册Job Triggers:" + trigger.getKey());
				this.addTriggerToScheduler(trigger);
			}
		}
		logger.info("自动注册Job Triggers完成");
	}

	private boolean addTriggerToScheduler(Trigger trigger) throws SchedulerException {
		boolean triggerExists = getScheduler().checkExists(trigger.getKey());
		if (!triggerExists) {
			JobDetail jobDetail = findJobDetail(trigger);
			if (jobDetail != null && getScheduler().getJobDetail(jobDetail.getKey()) == null) {
				getScheduler().scheduleJob(jobDetail, trigger);
			} else {
				getScheduler().scheduleJob(trigger);
			}

			return true;
		} else {
			return false;
		}
	}

	private JobDetail findJobDetail(Trigger trigger) {
		return (JobDetail) trigger.getJobDataMap().remove("jobDetail");
	}

}
