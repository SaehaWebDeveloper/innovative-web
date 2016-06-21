package com.saeha.webdev.innovativeweb.web.config.servlet;

import javax.management.MBeanServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

/**
 * JMX MBean Server
 * 
 * @author Pure
 *
 */
@Configuration
@EnableMBeanExport(server="mbeanServer")
public class JmxConfig {
	
	@Value("${jmx.server.ip:localhost}")
	private String serverIp;
	
	@Value("${jmx.server.port:54120}")
	private int serverPort;
	
	@Value("${jmx.connect.port:54121}")
	private int connectPort;
	
	/**
	 * MBean Server
	 * @return MBeanServer
	 */
	@Bean
	public MBeanServer mbeanServer(){
		MBeanServerFactoryBean mBeanServerFactoryBean = new MBeanServerFactoryBean();
		mBeanServerFactoryBean.setLocateExistingServerIfPossible(true);
		mBeanServerFactoryBean.afterPropertiesSet();
		return mBeanServerFactoryBean.getObject();
	}
	
	/**
	 * Connector Server
	 * 
	 * @return ConnectorServerFactoryBean
	 */
	@Bean @DependsOn(value="rmiRegistry")
	public ConnectorServerFactoryBean connectorServer(){
		String serviceUrl = "service:jmx:rmi://"+serverIp+":"+connectPort+"/jndi/rmi://"+serverIp+":"+serverPort+"/jmxrmi";
		
		ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
		connectorServerFactoryBean.setServiceUrl(serviceUrl);
		connectorServerFactoryBean.setThreaded(true);
		connectorServerFactoryBean.setDaemon(true);
		
		return connectorServerFactoryBean;
	}
	
	/**
	 * MBean 등록
	 * 
	 * @return RmiRegistryFactoryBean
	 */
	@Bean
	public RmiRegistryFactoryBean rmiRegistry(){
		RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
		rmiRegistryFactoryBean.setPort(serverPort);
		rmiRegistryFactoryBean.setAlwaysCreate(true);
		return rmiRegistryFactoryBean;
	}
}
