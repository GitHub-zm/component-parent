package com.zm.common.jpa.spring4.h3;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.util.Assert;

/**
 * 支持 Hibernate 3.6-4.2 org.hibernate.ejb package
 * 
 */
public class HibernateJpaVendorAdapter implements JpaVendorAdapter {

	private final PersistenceProvider persistenceProvider;
	private org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter actual;

	public org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter getActual() {
		Assert.notNull(actual, "actual");
		return actual;
	}

	public void setActual(org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter actual) {
		this.actual = actual;
	}

	public HibernateJpaVendorAdapter() {
		this.persistenceProvider = new SpringHibernateEjbPersistenceProvider();
	}

	@Override
	public PersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	@Override
	public String getPersistenceProviderRootPackage() {
		return actual.getPersistenceProviderRootPackage();
	}

	@Override
	public Map<String, ?> getJpaPropertyMap(PersistenceUnitInfo pui) {
		return actual.getJpaPropertyMap(pui);
	}

	@Override
	public Map<String, ?> getJpaPropertyMap() {
		return actual.getJpaPropertyMap();
	}

	@Override
	public JpaDialect getJpaDialect() {
		return actual.getJpaDialect();
	}

	@Override
	public Class<? extends EntityManagerFactory> getEntityManagerFactoryInterface() {
		return actual.getEntityManagerFactoryInterface();
	}

	@Override
	public Class<? extends EntityManager> getEntityManagerInterface() {
		return actual.getEntityManagerInterface();
	}

	@Override
	public void postProcessEntityManagerFactory(EntityManagerFactory emf) {
		actual.postProcessEntityManagerFactory(emf);
	}

}
