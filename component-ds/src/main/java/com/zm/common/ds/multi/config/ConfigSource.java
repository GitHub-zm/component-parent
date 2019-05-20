package com.zm.common.ds.multi.config;

import java.util.List;

import com.zm.common.ds.multi.DsRouterRec;

/**
 * DsRouter配置源
 * 
 * 
 */
public interface ConfigSource {

	List<DsRouterRec> getConfigs();
}
