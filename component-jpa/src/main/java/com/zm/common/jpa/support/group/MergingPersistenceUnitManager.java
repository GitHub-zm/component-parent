package com.zm.common.jpa.support.group;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

/**
 * <ul>
 * <li>支持将多个persistence.xml文件进行合并的{@link PersistenceUnitManager}，扩展自
 * {@link DefaultPersistenceUnitManager}。
 * 
 * <p>
 * 用于spring容器（定义在集成模块），参见如下：
 * 
 * <pre>
 *     &lt;bean id="emf" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean"&gt;
 *         &lt;property name="persistenceUnitManager"&gt;
 *             &lt;bean class="com.hd123.hdpos.sdk.jpa.support.MergingPersistenceUnitManager"&gt;
 *                 &lt;!-- 数据源配置在这里 --&gt;
 *                 &lt;property name="defaultDataSource" ref="dataSource" /&gt;
 *                 &lt;!-- 这个是最终的persistenceUnitName, 覆盖下列中定义的persistenceUnitName --&gt;
 *                 &lt;property name="persistenceUnitName" value="puName" /&gt; 
 *                 &lt;!-- 指定扫描的group，以逗号分隔。 --&gt;
 *                 &lt;property name="groups" value="store,share" /&gt; 
 *                 &lt;property name="persistenceXmlLocations"&gt;
 *                     &lt;list&gt;
 *                         &lt;value&gt;classpath:META-INF/jar1/persistence1.xml&lt;/value&gt;
 *                         &lt;value&gt;classpath:META-INF/jar1/persistence2.xml&lt;/value&gt;
 *                         &lt;value&gt;classpath:META-INF/jar2/persistence1.xml&lt;/value&gt;
 *                         &lt;value&gt;classpath:META-INF/jar2/persistence2.xml&lt;/value&gt;
 *                     &lt;/list&gt;
 *                 &lt;/property&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *         &lt;property name="jpaProperties"&gt;...&lt;/property&gt;
 *         &lt;property name="jpaVendorAdapter"&gt;...&lt;/property&gt;
 *     &lt;/bean&gt;
 * </pre>
 * 
 * <li>支持模块化定制persistence.xml，并自动合并。
 * <p>
 * 用于spring容器（定义在模块内），参见如下：
 * 
 * <pre>
 * &lt;bean class=&quot;com.hd123.hdpos.sdk.jpa.support.DefaultPersistenceXmlLocationConfigure&quot;&gt;
 *   &lt;property name=&quot;persistenceXmlLocations&quot; value=&quot;classpath:/META-INF/jar1/persistence1.xml&quot;/&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * </ul>
 * 
 */
public class MergingPersistenceUnitManager extends DefaultPersistenceUnitManager implements ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(MergingPersistenceUnitManager.class);

	private List<String> groups = new ArrayList<>();
	private String persistenceUnitName = ORIGINAL_DEFAULT_PERSISTENCE_UNIT_NAME;

	private static final String PREFIX_CLASSPATH_ALL = "classpath*:";
	private static final String PREFIX_CLASSPATH = "classpath:";
	private Map<String, String> map = new HashMap<String, String>();

	private ApplicationContext appCxt;

	/**
	 * @since 0.0.9
	 */
	public List<String> getGroups() {
		return groups;
	}

	/**
	 * 指定聚合哪些group的persistence.xml。
	 * 
	 * @since 0.0.9
	 */
	public void setGroups(String... groups) {
		this.groups.addAll(Arrays.asList(groups));
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appCxt = applicationContext;
	}

	@Override
	public void setPersistenceXmlLocation(String persistenceXmlLocation) {
		super.setPersistenceXmlLocation(persistenceXmlLocation);
		this.addPersistenceXmlLocation(persistenceXmlLocation);
	}

	@Override
	public void setPersistenceXmlLocations(String... persistenceXmlLocations) {
		super.setPersistenceXmlLocations(persistenceXmlLocations);
		this.addPersistenceXmlLocations(persistenceXmlLocations);
	}

	@Override
	protected boolean isPersistenceUnitOverrideAllowed() {
		return true;
	}

	@Override
	public void afterPropertiesSet() {
		loadModulePersistenceXmlLocation();
		super.setPersistenceXmlLocations(map.values().toArray(new String[] {}));

		super.afterPropertiesSet();
	}

	private void loadModulePersistenceXmlLocation() {
		if (!getGroups().isEmpty()) {
			Map<String, PersistenceXmlLocation> map = appCxt.getBeansOfType(PersistenceXmlLocation.class);
			for (PersistenceXmlLocation location : map.values()) {
				if (getGroups().contains(location.getGroup())) {
					addPersistenceXmlLocations(location.getPersistenceXmlLocations());
				}
			}
		}
	}

	private void addPersistenceXmlLocation(String location) {
		LOG.info("新增persistence文件路径：{}", location);
		String key = location;
		if (location.startsWith(PREFIX_CLASSPATH_ALL)) {
			key = location.substring(PREFIX_CLASSPATH_ALL.length());
			map.put(key, location);
		} else if (location.startsWith(PREFIX_CLASSPATH)) {
			key = location.substring(PREFIX_CLASSPATH.length());
			String value = map.get(key);
			if (value == null) {
				map.put(key, location);
			}
		} else {
			map.put(key, location);
		}
	}

	private void addPersistenceXmlLocations(String[] locations) {
		for (String location : locations) {
			addPersistenceXmlLocation(location);
		}
	}

	@Override
	protected void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		// Invoke normal post processing
		super.postProcessPersistenceUnitInfo(pui);
		pui.setPersistenceUnitName(this.persistenceUnitName);

		PersistenceUnitInfo oldPui = getPersistenceUnitInfo(((PersistenceUnitInfo) pui).getPersistenceUnitName());

		if (oldPui != null) {
			postProcessPersistenceUnitInfo(pui, oldPui);
		}
	}

	void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui, PersistenceUnitInfo oldPui) {

		String persistenceUnitName = pui.getPersistenceUnitName();

		for (URL url : oldPui.getJarFileUrls()) {
			if (!pui.getJarFileUrls().contains(url)) {
				LOG.debug("Adding JAR file URL {} to persistence unit {}.", url, persistenceUnitName);
				pui.addJarFileUrl(url);
			}
		}

		for (String className : oldPui.getManagedClassNames()) {
			if (!pui.getManagedClassNames().contains(className)) {
				LOG.debug("Adding class {} to PersistenceUnit {}", className, persistenceUnitName);
				pui.addManagedClassName(className);
			}
		}

		for (String mappingFileName : oldPui.getMappingFileNames()) {
			if (!pui.getMappingFileNames().contains(mappingFileName)) {
				LOG.debug("Adding mapping file to persistence unit {}.", mappingFileName, persistenceUnitName);
				pui.addMappingFileName(mappingFileName);
			}
		}

		URL newUrl = pui.getPersistenceUnitRootUrl();
		URL oldUrl = oldPui.getPersistenceUnitRootUrl();

		if (oldUrl == null || newUrl == null) {
			return;
		}

		try {
			boolean rootUrlsDiffer = !newUrl.toURI().equals(oldUrl.toURI());
			boolean urlNotInJarUrls = !pui.getJarFileUrls().contains(oldUrl);

			if (rootUrlsDiffer && urlNotInJarUrls) {
				pui.addJarFileUrl(oldUrl);
			}
		} catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}
}
