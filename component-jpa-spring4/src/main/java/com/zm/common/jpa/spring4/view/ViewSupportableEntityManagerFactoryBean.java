package com.zm.common.jpa.spring4.view;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

/**
 * 
 */
public class ViewSupportableEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {

	private static final Logger logger = LoggerFactory.getLogger(ViewSupportableEntityManagerFactoryBean.class);
	private boolean ddlGenerated = false;

	private static final String VIEW_CLASS_NAME = "View";
	private static String[] HBM2DDL_AUTO = { "update", "create", "create-drop", "validate" };

	private boolean autoUpdateSchema = false;

	private PersistenceUnitManager persistenceUnitManager;

	public PersistenceUnitManager getPersistenceUnitManager() {
		return persistenceUnitManager;
	}

	public void setPersistenceUnitManager(PersistenceUnitManager persistenceUnitManager) {
		super.setPersistenceUnitManager(persistenceUnitManager);
		this.persistenceUnitManager = persistenceUnitManager;
	}

	@Override
	public Map<String, Object> getJpaPropertyMap() {
		Map<String, Object> map = super.getJpaPropertyMap();
		if (ddlGenerated && autoUpdateSchema) {
			map.remove(Environment.HBM2DDL_AUTO);
		}
		return map;
	}

	private boolean isAutoUpdateSchema() {
		Object obj = super.getJpaPropertyMap().get(Environment.HBM2DDL_AUTO);
		if (obj != null) {
			for (String optionValue : HBM2DDL_AUTO) {
				if (optionValue.equalsIgnoreCase((String) obj)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void afterPropertiesSet() throws PersistenceException {
		autoUpdateSchema = isAutoUpdateSchema();
		if (ddlGenerated == false && autoUpdateSchema) {
			super.afterPropertiesSet();
			try {
				this.getNativeEntityManagerFactory().close();
			} catch (Exception e) {
				logger.warn("emf.close()", e);
			}

			ddlGenerated = true;
		}

		super.afterPropertiesSet();
	}

	@Override
	protected PersistenceUnitInfo determinePersistenceUnitInfo(PersistenceUnitManager persistenceUnitManager) {
		PersistenceUnitInfo pui = super.determinePersistenceUnitInfo(persistenceUnitManager);
		if (ddlGenerated == false && autoUpdateSchema) {
			for (String className : pui.getManagedClassNames().toArray(new String[] {})) {
				try {
					Class<?> clazz = Class.forName(className);
					if (hasViewAnnotation(clazz)) {
						pui.getManagedClassNames().remove(className);
					}
				} catch (Exception e) {
					// Do Nothing
				}
			}
		}
		return pui;
	}

	private boolean hasViewAnnotation(Class<?> clazz) {
		assert clazz != null;

		for (Annotation annotation : clazz.getAnnotations()) {
			if (VIEW_CLASS_NAME.equals(annotation.annotationType().getSimpleName())) {
				return true;
			}
		}
		return false;
	}
}
