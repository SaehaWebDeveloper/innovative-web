package com.saeha.webdev.innovativeweb.controller;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.saeha.webdev.innovativeweb.service.ConferenceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Controller
@RequestMapping(value="/conference")
public class ConferenceController {
	@Autowired private ConferenceService conferenceService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listPage(Model model, HttpSession session) throws Exception {
		model.addAttribute("user", session.getAttribute("SESSION_USER"));
		
		return "conference/list";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public Callable<String> createPage(Model model){
		Callable<String> callableLambda = () -> {
			return "conference/create";
		};
		
		return callableLambda;
	}
}
