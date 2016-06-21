package com.saeha.webdev.innovativeweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saeha.webdev.innovativeweb.model.conference.ConferenceInfo;
import com.saeha.webdev.innovativeweb.repository.ConferenceInfoRespository;

@Service
public class ConferenceService {
	@Autowired private ConferenceInfoRespository conferenceInfoRespository;
	
	public Page<ConferenceInfo> getConferecneInfoList(Pageable pageable){
		return conferenceInfoRespository.findAll(pageable);
	}
}
