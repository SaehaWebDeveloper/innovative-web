package com.saeha.webdev.innovativeweb.schedule;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduleManager {
	
	@Autowired private SchedulerFactoryBean schedulerFactoryBean;
	
	private Scheduler scheduler;
	
	@PostConstruct
	private void init(){
		scheduler = schedulerFactoryBean.getScheduler();
		addSchedule();
	}
	@PreDestroy
	private void stop() throws SchedulerException {
		scheduler.shutdown(true);
	}
	
	/**
	 * 
	 */
	private void addSchedule(){
		log.debug("Schedule 추가.");
	}
}
