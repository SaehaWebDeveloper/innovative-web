package com.saeha.webdev.innovativeweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.saeha.webdev.innovativeweb.model.Conference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConferenceService {
	
	@Async
	public Future<List<Conference>> getList(){
		List<Conference> list = new ArrayList<>();
		for(int i=0;i<=100;i++){
			Conference conference = new Conference();
			conference.setConfcode("confcode_" + i);
			conference.setTitle("회의실_" + 1);
			list.add(conference);
		}
		
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new AsyncResult<List<Conference>>(list);
	}
	
	public List<Conference> getList2(){
		List<Conference> list = new ArrayList<>();
		for(int i=0;i<=100;i++){
			Conference conference = new Conference();
			conference.setConfcode("confcode_" + i);
			conference.setTitle("회의실_" + i);
			list.add(conference);
		}
		
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
