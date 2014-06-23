package com.yummynoodlebar.config.persistence;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.yummynoodlebar.config.persistence.dataconfig.DevDataConfig;
import com.yummynoodlebar.config.persistence.dataconfig.ProductionDataConfig;
import com.yummynoodlebar.persistence.repository.OrdersRepository;

@Configuration
@Import({ DevDataConfig.class, ProductionDataConfig.class })
@EnableJpaRepositories(basePackages = "com.yummynoodlebar.persistence.repository", includeFilters = @ComponentScan.Filter(value = { OrdersRepository.class }, type = FilterType.ASSIGNABLE_TYPE))
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class JPAConfiguration {

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_PACKAGES_TO_SCAN = "packages.to.scan";

	@Resource
	private Environment env;

	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties jpaProperties) throws SQLException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_PACKAGES_TO_SCAN));
		factory.setDataSource(dataSource);
		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		return properties;
	}

	@Bean(name = "entityManager")
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws SQLException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
}
