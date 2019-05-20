package com.zm.common.jpa.view;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

/**
 * 支持视图的EntityManagerFactory工厂类。
 */
public class ViewSupportableEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
	private static final Logger logger = Logger.getLogger(ViewSupportableEntityManagerFactoryBean.class.getName());
	private boolean ddlGenerated = false;

	private static final String VIEW_CLASS_NAME = "View";
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
		if (ddlGenerated) {
			map.remove(Environment.HBM2DDL_AUTO);
		}
		return map;
	}

	@Override
	protected EntityManagerFactory createNativeEntityManagerFactory() throws PersistenceException {

		if (ddlGenerated == false) {
			EntityManagerFactory emf = super.createNativeEntityManagerFactory();
			try {
				emf.close();
			} catch (Exception e) {
				logger.log(Level.WARNING, "emf.close()", e);
			}
			ddlGenerated = true;
		}

		if (this.persistenceUnitManager != null //
				&& this.persistenceUnitManager instanceof InitializingBean) {
			try {
				((InitializingBean) persistenceUnitManager).afterPropertiesSet();
			} catch (Exception e) {
				throw new PersistenceException(e);
			}
		}

		return super.createNativeEntityManagerFactory();
	}

	@Override
	protected PersistenceUnitInfo determinePersistenceUnitInfo(PersistenceUnitManager persistenceUnitManager) {
		PersistenceUnitInfo pui = super.determinePersistenceUnitInfo(persistenceUnitManager);
		if (ddlGenerated == false) {
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
