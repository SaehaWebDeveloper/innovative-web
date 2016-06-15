package com.saeha.webdev.innovativeweb.web.config.servlet;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.saeha.webdev.innovativeweb.common.jdbc.JdbcInfoGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableTransactionManagement()
@PropertySource(name="dbConfig", value="classpath:/properties/db.properties", ignoreResourceNotFound=true)
@MapperScan(basePackages="com.saeha.webdev.innovativeweb", annotationClass=Repository.class)
public class DatabaseConfig {
	
	@Autowired private Environment env;
	
	@Bean
	public DataSource dataSource(){
		String dbType = env.getProperty("db.type", JdbcInfoGenerator.MARIA.name()).toUpperCase();
		JdbcInfoGenerator jdbcInfo = JdbcInfoGenerator.valueOf(dbType);
		
		jdbcInfo.setInfo(env.getProperty("db.driverClassName")
				, env.getProperty("db.url")
				, env.getProperty("db.ip")
				, env.getProperty("db.port")
				, env.getProperty("db.dbname")
				, env.getProperty("db.extra")
				, env.getProperty("db.username")
				, env.getProperty("db.password"));
		
		log.info("dbType: {}, Jdbc Information: {}", dbType, jdbcInfo);
		return makeDataSource(jdbcInfo);
	}
	
	private DataSource makeDataSource(JdbcInfoGenerator jdbcInfo){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(jdbcInfo.getDriverClassName());
		dataSource.setUrl(jdbcInfo.getUrl());
		dataSource.setUsername(jdbcInfo.getUsername());
		dataSource.setPassword(jdbcInfo.getPassword());
		dataSource.setDefaultAutoCommit(false);
//		
//		dataSource.setMaxActive(maxActive);
//		dataSource.setMaxIdle(maxIdle);
//		dataSource.setMinIdle(minIdle);
//		dataSource.setMaxWait(maxWait);
//		dataSource.setDefaultAutoCommit(defaultAutoCommit);
//		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//		dataSource.setDefaultReadOnly(defaultReadOnly);
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory SqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setTypeAliasesPackage("com.saeha.webdev.innovativeweb.model");
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/sql/"+env.getProperty("db.type")+"/**/*Mapper.xml"));
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/config/mybatis/mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sessionTemplate() throws Exception {
		return new SqlSessionTemplate(SqlSessionFactory());
	}
	
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(){
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public TransactionTemplate transactionTemplate(){
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(dataSourceTransactionManager());
		return transactionTemplate;
	}
}
