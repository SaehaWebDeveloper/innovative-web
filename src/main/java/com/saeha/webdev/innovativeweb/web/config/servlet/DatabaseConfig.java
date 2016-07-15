package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

/**
 * DB 설정
 * 
 * @author Pure
 *
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="${config.spring.jpa.repository.basePackages}")
public class DatabaseConfig {
	/**
	 * Repository base package
	 */
	@Value("${config.spring.jpa.entiry.packagesToScan:}")
	private String entityPackagesToSacn;
	
	/**
	 * DB 구분 with JdbcInfoGenerator
	 */
	@Value("#{'${db.type:MARIA}'.toUpperCase()}")
	private String dbType;
	
	/**
	 * 접속 정보 암호화 여부
	 */
	@Value("${db.encode:false}")
	private boolean encode;
	
	/**
	 * JDBC Driver Class Name
	 */
	@Value("${db.driverClassName:}")
	private String driverClassName;
	
	/**
	 * JDBC URL
	 */
	@Value("${db.url:}")
	private String jdbcUrl;
	
	/**
	 * DB IP
	 */
	@Value("${db.ip:}")
	private String dbIp;
	
	/**
	 * DB Port
	 */
	@Value("${db.port:}")
	private String dbPort;
	
	/**
	 * DB Name
	 */
	@Value("${db.dbname:}")
	private String dbName;
	
	/**
	 * JDBC URL의 추가 설정 값
	 */
	@Value("${db.extra:}")
	private String jdbcUrlExtra;
	
	/**
	 * DB 접속 사용자
	 */
	@Value("${db.username:}")
	private String username;
	
	/**
	 * DB 접속 사용자 패스워드
	 */
	@Value("${db.password:}")
	private String password;
	
	/**
	 * DB에 따른 Hibernate Dialect Class 이름
	 */
	@Value("${db.hibernate.dialect:}")
	private String hibernateDialect;
	
	/**
	 * HikariDataSource Pool 이름
	 */
	@Value("${db.hcp.poolName:connectionPool}")
	private String hcpPoolName;
	
	/**
	 * HikariDataSource Pool 크기
	 */
	@Value("${db.hcp.maximumPoolSize:10}")
	private int hcpMaximumPoolSize;
	
	/**
	 * HikariDataSource idle 제한 시간
	 */
	@Value("${db.hcp.idleTimeout:60000}")
	private long hcpIdleTimeout;
	
	/**
	 * HikariDataSource 접속 제한 시간
	 */
	@Value("${db.hcp.connectionTimeout:300000}")
	private long hcpConnectionTimeout;
	
	/**
	 * Hibernate SQL 출력 여부
	 */
	@Value("${db.hibernate.show_sql:true}")
	private String hibernateShowSql;
	
	/**
	 * Hibernate SQL 형식 변경 여부
	 */
	@Value("${db.hibernate.format_sql:true}")
	private String hibernateFormatSql;
	
	/**
	 * JDBC 접속 정보 생성기
	 */
	private static JdbcInfoGenerator jdbcInfo;
	
	/**
	 * JDBC 접속 설정 생성기
	 * 
	 * @return JdbcInfoGenerator
	 */
	private JdbcInfoGenerator getJdbcInfo(){
		if(jdbcInfo == null){
			jdbcInfo = JdbcInfoGenerator.valueOf(dbType);
			
			jdbcInfo.setInfo(driverClassName, jdbcUrl, dbIp, dbPort, dbName, jdbcUrlExtra, username, password, hibernateDialect);
			log.info("dbType: {}, Jdbc Information: {}", dbType, jdbcInfo);
		}
		
		return jdbcInfo;
	}
	
	/**
	 * DataSource 생성
	 * 
	 * @return DataSource
	 */
	@Bean(destroyMethod="close")
	public DataSource dataSource(){
		JdbcInfoGenerator jdbcInfo = getJdbcInfo();
		
		HikariDataSource dataSource = new HikariDataSource();
		// 접속 설정
		dataSource.setDriverClassName(jdbcInfo.getDriverClassName());
		dataSource.setJdbcUrl(jdbcInfo.getJdbcUrl());
		dataSource.setUsername(jdbcInfo.getUsername());
		dataSource.setPassword(jdbcInfo.getPassword());
		// hcp 설정
		dataSource.setPoolName(hcpPoolName);
		dataSource.setMaximumPoolSize(hcpMaximumPoolSize);
		dataSource.setIdleTimeout(hcpIdleTimeout);
		dataSource.setConnectionTimeout(hcpConnectionTimeout);
		
		dataSource.setAutoCommit(false);
		//dataSource.setRegisterMbeans(true);
		return dataSource;
	}
	
	/**
	 * JPA Vendor For Hibernate
	 * 
	 * @return JpaVendorAdapter
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		JdbcInfoGenerator jdbcInfo = getJdbcInfo();
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabasePlatform(jdbcInfo.getHibernateDialect());
		return jpaVendorAdapter;
	}
	
	/**
	 * Entity Manager 생성
	 * 
	 * @return EntityManagerFactory
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(entityPackagesToSacn);
		entityManagerFactoryBean.setJpaProperties(hibernateProperties());
		entityManagerFactoryBean.afterPropertiesSet();
		
		return entityManagerFactoryBean.getObject();
	}
	
	/**
	 * hibernate property 생성
	 * 
	 * @return Properties
	 */
	private Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.setProperty(AvailableSettings.SHOW_SQL, hibernateShowSql);
		properties.setProperty(AvailableSettings.FORMAT_SQL, hibernateFormatSql);
		
		return properties;
	}
	
	/**
	 * Transaction Manager 생성
	 * 
	 * @return PlatformTransactionManager
	 * @throws Exception
	 */
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory());
		return transactionManager;
	}
	
	/**
	 * ExceptionTranslator
	 * 
	 * @return HibernateExceptionTranslator
	 */
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator(){
		return new HibernateExceptionTranslator();
	}
}
