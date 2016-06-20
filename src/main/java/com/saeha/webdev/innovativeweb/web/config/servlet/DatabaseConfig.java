package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.saeha.webdev.innovativeweb.common.jdbc.JdbcInfoGenerator;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@PropertySource(name="dbConfig", value="classpath:/properties/db.properties", ignoreResourceNotFound=true)
@EnableJpaRepositories(basePackages="com.saeha.webdev.innovativeweb.repository")
@EnableTransactionManagement
public class DatabaseConfig {
	@Autowired private Environment env;
	
	private JdbcInfoGenerator jdbcInfo;
	private JdbcInfoGenerator getJdbcInfo(){
		if(jdbcInfo == null){
			String dbType = env.getProperty("db.type", JdbcInfoGenerator.MARIA.name()).toUpperCase();
			jdbcInfo = JdbcInfoGenerator.valueOf(dbType);
			
			jdbcInfo.setInfo(env.getProperty("db.driverClassName")
					, env.getProperty("db.url")
					, env.getProperty("db.ip")
					, env.getProperty("db.port")
					, env.getProperty("db.dbname")
					, env.getProperty("db.extra")
					, env.getProperty("db.username")
					, env.getProperty("db.password"));
			log.info("dbType: {}, Jdbc Information: {}", dbType, jdbcInfo);
		}
		
		return jdbcInfo;
	}
	
	@Bean(destroyMethod="close")
	public DataSource dataSource(){
		JdbcInfoGenerator jdbcInfo = getJdbcInfo();
		
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(jdbcInfo.getDriverClassName());
		dataSource.setJdbcUrl(jdbcInfo.getUrl());
		dataSource.setUsername(jdbcInfo.getUsername());
		dataSource.setPassword(jdbcInfo.getPassword());
		
		dataSource.setPoolName("saeha");
		dataSource.setMaximumPoolSize(10);
		
		dataSource.setAutoCommit(false);
		//dataSource.setRegisterMbeans(true);
		return dataSource;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		JdbcInfoGenerator jdbcInfo = getJdbcInfo();
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabasePlatform(jdbcInfo.getHibernateDialect());
		jpaVendorAdapter.setShowSql(true);
		//jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.saeha.**.model");
		entityManagerFactoryBean.setJpaProperties(hibernateProperties());
		entityManagerFactoryBean.afterPropertiesSet();
		
		return entityManagerFactoryBean.getObject();
	}
	private Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.setProperty(AvailableSettings.FORMAT_SQL, "true");
		
		return properties;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory());
		return transactionManager;
	}
	
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator(){
		return new HibernateExceptionTranslator();
	}
}
