package com.saeha.webdev.innovativeweb.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeha.webdev.innovativeweb.service.MessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 다국어 관련
 * 
 * @author Pure
 *
 */
@Slf4j
@Controller
@RequestMapping(value="/message")
public class MessageController {
	@Autowired private MessageService messageService;
	
	/**
	 * 다국어 Message 파일을 읽어서 보내준다
	 * @param messageFileName Message 파일명
	 * @param response HttpServletResponse
	 * @throws IOException
	 */
	@RequestMapping(value="/{messageFileName}", produces={"text/plain"})
	public @ResponseBody Object getMessageProperties(
			@PathVariable String messageFileName
			, Model model) throws IOException{
		String messageContent = messageService.readMessagePropertyFile(messageFileName);
		return messageContent;
	}
	
	/**
	 * 사용가능한 다국어 정보 
	 * (jquery.i18n.properties에서 checkAvailableLanguages true 설정 시 호출됨)
	 * 
	 * @param model 
	 * @return 사용가능한 다국어 정보 목록
	 */
	@RequestMapping(value="/languages", produces={"application/json; charset=UTF-8"})
	public @ResponseBody Model checkAvailableLanguages(Model model){
		log.debug("Supported Language:{}", Arrays.asList(""));
		model.addAttribute("languages", new String[]{"", "ko", "en", "ja"});
		return model;
	}
}
