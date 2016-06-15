package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PreDestroy;

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
	
	private Config config(){
		// FIXME Properties 설정
		Config config = new Config();
		config.setGroupConfig(new GroupConfig("webdev", "webdev"));
		config.setNetworkConfig(networkConfig());
		config.setMapConfigs(mapConfigs());
		config.setProperties(properties());
		return config;
	}
	
	private NetworkConfig networkConfig(){
		MulticastConfig multicastConfig = new MulticastConfig();
		multicastConfig.setEnabled(false);
		
		// FIXME Properties 설정
		List<String> members = new ArrayList<>();
		members.add("172.16.34.*");
		TcpIpConfig tcpIpConfig = new TcpIpConfig();
		tcpIpConfig.setEnabled(true);
		tcpIpConfig.setMembers(members);
		
		JoinConfig joinConfig = new JoinConfig();
		joinConfig.setMulticastConfig(multicastConfig);
		joinConfig.setTcpIpConfig(tcpIpConfig);
		
		// FIXME Properties 설정
		NetworkConfig networkConfig = new NetworkConfig();
		networkConfig.setPort(54110);
		networkConfig.setPortAutoIncrement(false);
		networkConfig.setPortCount(1);
		networkConfig.setJoin(joinConfig);
		
		return networkConfig;
	}
	
	private MapConfig mapConfigForIdle30(){
		MapConfig mapConfig = new MapConfig();
		mapConfig.setMaxIdleSeconds(30);
		mapConfig.setEvictionPolicy(EvictionPolicy.LFU);
		mapConfig.setMaxSizeConfig(new MaxSizeConfig(10, MaxSizePolicy.PER_NODE));
		return mapConfig;
	}
	
	private Map<String, MapConfig> mapConfigs(){
		// FIXME Properties 설정??
		Map<String, MapConfig> mapConfigs = new HashMap<>();
		mapConfigs.put("messageProperty", mapConfigForIdle30());
		return mapConfigs;
	}
	
	private Properties properties(){
		Properties properties = new Properties();
		properties.setProperty("hazelcast.jmx", "true"); // JMX 사용
		return properties;
	}
}
