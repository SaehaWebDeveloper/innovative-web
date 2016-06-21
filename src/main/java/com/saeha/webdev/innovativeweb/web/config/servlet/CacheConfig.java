package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

import lombok.extern.slf4j.Slf4j;

/**
 * Cache 설정 (dependency: Hazelcast 3.6.3)
 * 
 * @author Pure
 *
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {
	/**
	 * 그룹 이름
	 */
	@Value("${cache.hazelcast.group.name:webdev}")
	private String groupName;
	
	/**
	 * 그룹 패스워드
	 */
	@Value("${cache.hazelcast.group.password:webdev}")
	private String groupPassword;
	
	/**
	 * 그룹 맴버 설정
	 */
	@Value("${cache.hazelcast.tcpip.members:127.0.0.1}")
	private String tcpipMembers;
	
	/**
	 * 포트
	 */
	@Value("${cache.hazelcast.network.port:54110}")
	private int networkPort;
	
	/**
	 * 포트 자동 증가 여부
	 */
	@Value("${cache.hazelcast.network.autoIncrement:false}")
	private boolean networkAutoIncrement;
	
	/**
	 * 포트 증가 수
	 */
	@Value("${cache.hazelcast.network.portCount:1}")
	private int networkPortCount;
	
	/**
	 * i18n.properties의 메시지 파일
	 */
	public final static String CACHE_MESSAGE_PROPERTY = "messageProperty";
	
	@Bean
	public CacheManager cacheManager(){
		return new HazelcastCacheManager(hazelcastInstance());
	}
	
	@Bean @Lazy
	public HazelcastInstance hazelcastInstance(){
		HazelcastInstance  hazelcastInstance = Hazelcast.newHazelcastInstance(config());
		return hazelcastInstance;
	}
	@PreDestroy
	public void destroy(){
		hazelcastInstance().shutdown();
	}
	
	/**
	 * Hazelcast Config
	 * 
	 * @return Config
	 */
	private Config config(){
		Config config = new Config();
		config.setGroupConfig(new GroupConfig(groupName, groupPassword));
		config.setNetworkConfig(networkConfig());
		config.setMapConfigs(mapConfigs());
		config.setProperties(properties());
		return config;
	}
	
	/**
	 * Hazelcast Network Config
	 * 
	 * @return NetworkConfig
	 */
	private NetworkConfig networkConfig(){
		// MulticastConfig 사용하지 않는다.
		MulticastConfig multicastConfig = new MulticastConfig();
		multicastConfig.setEnabled(false);
		
		// TcpIpConfig 사용 설정
		TcpIpConfig tcpIpConfig = new TcpIpConfig();
		tcpIpConfig.setEnabled(true);
		tcpIpConfig.setMembers(Arrays.asList(tcpipMembers));
		
		JoinConfig joinConfig = new JoinConfig();
		joinConfig.setMulticastConfig(multicastConfig);
		joinConfig.setTcpIpConfig(tcpIpConfig);
		
		// 포트 설정
		NetworkConfig networkConfig = new NetworkConfig();
		networkConfig.setPort(networkPort);
		networkConfig.setPortAutoIncrement(networkAutoIncrement);
		networkConfig.setPortCount(networkPortCount);
		networkConfig.setJoin(joinConfig);
		
		return networkConfig;
	}
	
	/**
	 * Hazelcast Cache Map Config
	 * 
	 * @return mapConfigs
	 */
	private Map<String, MapConfig> mapConfigs(){
		// Cache 이름 및 설정
		Map<String, MapConfig> mapConfigs = new HashMap<>();
		mapConfigs.put(CACHE_MESSAGE_PROPERTY, mapConfigForIdleAndSize(CACHE_MESSAGE_PROPERTY, 30, 10));
		// Cache Add
		
		log.debug("Cache Map:{}", mapConfigs);
		return mapConfigs;
	}
	
	/**
	 * Hazelcast Properties
	 * @return
	 */
	private Properties properties(){
		Properties properties = new Properties();
		properties.setProperty("hazelcast.jmx", "true"); // JMX 사용
		return properties;
	}
	
	/**
	 * Cache Map Default Config idle and size
	 * Default LFU, PRE_NODE
	 * 
	 * @param name
	 * @param idle
	 * @param size
	 * @return MapConfig
	 */
	private MapConfig mapConfigForIdleAndSize(String name, int idle, int size){
		MapConfig mapConfig = new MapConfig();
		mapConfig.setName(name);
		mapConfig.setMaxIdleSeconds(idle);
		mapConfig.setEvictionPolicy(EvictionPolicy.LFU);
		mapConfig.setMaxSizeConfig(new MaxSizeConfig(size, MaxSizePolicy.PER_NODE));
		return mapConfig;
	}
}
