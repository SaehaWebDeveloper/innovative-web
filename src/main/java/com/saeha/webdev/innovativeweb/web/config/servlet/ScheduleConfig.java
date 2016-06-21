package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.util.Properties;

import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.simpl.SimpleThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.bikeemotion.quartz.jobstore.hazelcast.HazelcastJobStore;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class ScheduleConfig {
	
	@Value("${schedule.quarts.instanceName:webdevScheduler}")
	private String instanceName;
	
	@Value("${schedule.quarts.jmx.enable:true}")
	private String jmxEnable;
	
	@Value("${schedule.quarts.clustered:true}")
	private boolean clustered;
	
	@Autowired HazelcastInstance hazelcastInstance;
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(){
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		
		// Register ApplicationContext in Scheduler context.
		schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		
		Properties quartzProperties = new Properties();
		// Main Configuration
		quartzProperties.setProperty(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, instanceName);
		quartzProperties.setProperty(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, "AUTO");
		quartzProperties.setProperty(StdSchedulerFactory.PROP_SCHED_JOB_FACTORY_CLASS, SimpleJobFactory.class.getName());
		// Configuration of ThreadPool
		quartzProperties.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, SimpleThreadPool.class.getName());
		quartzProperties.setProperty("org.quartz.threadPool.threadCount", "10");
		quartzProperties.setProperty("org.quartz.threadPool.threadPriority", "5");
		// JMX 설정
		quartzProperties.setProperty(StdSchedulerFactory.PROP_SCHED_JMX_EXPORT, jmxEnable);
		quartzProperties.setProperty(StdSchedulerFactory.PROP_SCHED_JMX_OBJECT_NAME, "quartz:type=QuartzScheduler,name="+instanceName);
		
		if(clustered){
			// Configure HazelcastJobStore
			HazelcastJobStore.setHazelcastClient(hazelcastInstance);
			quartzProperties.setProperty(StdSchedulerFactory.PROP_JOB_STORE_CLASS, HazelcastJobStore.class.getName());
		}else{
			// Configure RAMJobStore
			quartzProperties.setProperty("org.quartz.jobStore.misfireThreshold", "60000");
		}
		
		schedulerFactoryBean.setQuartzProperties(quartzProperties);
		return schedulerFactoryBean;
	}
}
