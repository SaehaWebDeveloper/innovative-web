package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

import javax.management.MBeanServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * JMX MBean Server
 * 
 * @author Pure
 *
 */
@Slf4j
@Configuration
@EnableMBeanExport(server="mbeanServer")
public class JmxConfig {
	
	/**
	 * jmx remote file path
	 * base classpath
	 */
	private final static String REMOTE_FILE_PATH = "jmx";
	
	/**
	 * access file path.
	 * base classpath
	 */
	private final static String REMOTE_ACCESS_FILE_PATH = REMOTE_FILE_PATH + "/jmxremote.access";
	/**
	 * password file path.
	 * base classpath
	 */
	private final static String REMOTE_PASSWORD_FILE_PATH = REMOTE_FILE_PATH + "/jmxremote.password";
	
	/**
	 * JMX Remote 접속 사용자 인증 및 권한
	 * 
	 * @author Pure
	 *
	 */
	@AllArgsConstructor
	private enum JmxRemoteUserNAuth{
		/**
		 * 관리자 - 읽기 및 쓰기
		 */
		ADMIN("admin", "admin123", "readwrite"),
		/**
		 * 모니터링 - 읽기
		 */
		MONITOR("monitor", "monitor123", "readonly");
		
		/**
		 * 사용자
		 */
		@Getter private String username;
		/**
		 * 패스워드
		 */
		@Getter private String password;
		/**
		 * 권한(readwrite, readonly)
		 */
		@Getter private String authentication;
		
		/**
		 * access 정보
		 * @return username + authentication
		 */
		public String getAccessContent(){
			return username + " " + authentication;
		}
		
		/**
		 * password 정보
		 * @return username + password
		 */
		public String getPasswordContent(){
			return username + " " + password;
		}
		
		/**
		 * access 정보
		 * @return username + authentication
		 */
		public static String getAccessContents(){
			StringBuilder sb = new StringBuilder();
			for(JmxRemoteUserNAuth jmxRemote : JmxRemoteUserNAuth.values()){
				sb.append(jmxRemote.getAccessContent()).append("\r\n");
			}
			return sb.toString();
		}
		
		/**
		 * password 정보
		 * @return username + password
		 */
		public static String getPasswordContents(){
			StringBuilder sb = new StringBuilder();
			for(JmxRemoteUserNAuth jmxRemote : JmxRemoteUserNAuth.values()){
				sb.append(jmxRemote.getPasswordContent()).append("\r\n");
			}
			return sb.toString();
		}
	}
	
	/**
	 * JMX Server host
	 */
	@Value("${jmx.server.host:localhost}")
	private String serverHost;
	
	/**
	 * JMX Server port
	 */
	@Value("${jmx.server.port:54120}")
	private int serverPort;
	
	/**
	 * JMX Connector port
	 */
	@Value("${jmx.connect.port:54121}")
	private int connectPort;
	
	/**
	 * Remote Access 사용 여부
	 */
	@Value("${jmx.remote.enable:true}")
	private boolean remoteEnable;
	
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
		String serviceUrl = "service:jmx:rmi://"+serverHost+":"+connectPort+"/jndi/rmi://"+serverHost+":"+serverPort+"/jmxrmi";
		
		ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
		connectorServerFactoryBean.setServiceUrl(serviceUrl);
		connectorServerFactoryBean.setThreaded(true);
		connectorServerFactoryBean.setDaemon(true);
		
		if(remoteEnable){
			remoteSetting(connectorServerFactoryBean);
		}
		log.info("JMX server url:{}", serviceUrl);
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
	
	/**
	 * Remote Access 설정
	 * 
	 * @param connectorServerFactoryBean
	 */
	private void remoteSetting(ConnectorServerFactoryBean connectorServerFactoryBean){
		// access, password 파일 생성 및 변경
		File remoteAccessFile = makeFile(REMOTE_ACCESS_FILE_PATH, JmxRemoteUserNAuth.getAccessContents());
		File remotePasswordFile = makeFile(REMOTE_PASSWORD_FILE_PATH, JmxRemoteUserNAuth.getPasswordContents());
		
		// access, password 파일 확인
		if(remoteAccessFile == null || !remoteAccessFile.exists() || !remoteAccessFile.isFile()){
			log.warn("JMX Remote Access File not maked.");
			return;
		}
		if(remotePasswordFile == null || !remotePasswordFile.exists() || !remotePasswordFile.isFile()){
			log.warn("JMX Remote Password File not maked.");
			return;
		}
		
		// jmx remote 설정
		Properties environment = new Properties();
		environment.setProperty("jmx.remote.x.access.file", remoteAccessFile.getAbsolutePath());
		environment.setProperty("jmx.remote.x.password.file", remotePasswordFile.getAbsolutePath());
		connectorServerFactoryBean.setEnvironment(environment);
		log.info("JMX Remote Setting. path:{}", remoteAccessFile.getParent());
	}
	
	/**
	 * Remote 파일 생성
	 * 
	 * @param filePath
	 * @param content
	 * @return RemoteFile
	 */
	private File makeFile(String filePath, String content){
		File realFile = null;
		FileWriter fileWriter = null;
		try{
			realFile = new File(getClasspath(), filePath);
			if(!realFile.getParentFile().exists()){
				realFile.getParentFile().mkdirs();
			}else{
				realFile.delete();
			}
			
			fileWriter = new FileWriter(realFile);
			fileWriter.write(content);
			
			realFile.setReadOnly();
			log.debug("JMX Remote File Maked. file:{}", realFile);
		}catch(Exception e){
			log.warn("JMX Remote File Make Error.", e);
		}finally {
			if(fileWriter != null){
				try{fileWriter.close();}catch(Exception e){}
			}
		}
		
		return realFile;
	}
	
	/**
	 * ClassPath 경로 반환
	 * @return ClassPath
	 */
	private String getClasspath(){
		return getClass().getClassLoader().getResource(".").getPath();
	}
}
