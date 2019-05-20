package com.zm.common.test.log.appender;

/**
 * 测试SmartLifecycle
 * 
 */
public class MessageCollector {

	private static MessageCollector default_ = null;

	// bean初始化完成,执行initLifecycleProcessor
	public static String BEAN_FINISHED_FLAG = "Unable to locate LifecycleProcessor with name";
	// 开始执行SmartLifecycle的start，执行了isAutoStartup()\getPhase()
	public static String SMARTLIFECYCLE_START_FLAG = "Starting beans in phase";

	public static MessageCollector getDefault() {
		return default_;
	}

	public MessageCollector() {
		default_ = this;
	}

	public void addMessage(String message) {
		if (message.startsWith(SMARTLIFECYCLE_START_FLAG)) {

		}

	}

}
