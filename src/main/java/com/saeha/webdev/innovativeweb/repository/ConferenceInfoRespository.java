package com.saeha.webdev.innovativeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saeha.webdev.innovativeweb.model.conference.ConferenceInfo;

public interface ConferenceInfoRespository extends JpaRepository<ConferenceInfo, String> {
}
