package com.saeha.webdev.innovativeweb.web.config.servlet;

import javax.management.MBeanServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

@Configuration
@EnableMBeanExport(server="mbeanServer")
public class JmxConfig {
	
	@Bean
	public MBeanServer mbeanServer(){
		MBeanServerFactoryBean mBeanServerFactoryBean = new MBeanServerFactoryBean();
		mBeanServerFactoryBean.setLocateExistingServerIfPossible(true);
		mBeanServerFactoryBean.afterPropertiesSet();
		return mBeanServerFactoryBean.getObject();
	}
	
	@Bean @DependsOn(value="rmiRegistry")
	public ConnectorServerFactoryBean connectorServer(){
		ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
		connectorServerFactoryBean.setServiceUrl("service:jmx:rmi://localhost:54021/jndi/rmi://localhost:54020/jmxrmi");
		connectorServerFactoryBean.setThreaded(true);
		connectorServerFactoryBean.setDaemon(true);
		
		return connectorServerFactoryBean;
	}
	
	@Bean
	public RmiRegistryFactoryBean rmiRegistry(){
		RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
		rmiRegistryFactoryBean.setPort(54020);
		rmiRegistryFactoryBean.setAlwaysCreate(true);
		return rmiRegistryFactoryBean;
	}
}
