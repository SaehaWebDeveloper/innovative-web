package com.saeha.webdev.innovativeweb.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PureTestJob extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext content) throws JobExecutionException {
		log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! {}", content);
	}

}
