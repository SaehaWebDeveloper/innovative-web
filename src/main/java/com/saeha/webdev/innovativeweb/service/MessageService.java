package com.saeha.webdev.innovativeweb.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageService {
	
	@Cacheable(cacheNames="messageProperty", key="#messageFileName")
	public String readMessagePropertyFile(String messageFileName) throws IOException{
		log.debug("Message Properties File Read. {}", messageFileName);
		
		Resource resource = new ClassPathResource(File.separator + "message" + File.separator + messageFileName + ".properties");
		if(!resource.exists() || resource.getFile() != null || !resource.getFile().isFile()){
			log.debug("[{}] message properties 파일이 존재하지 않습니다.", resource.getFilename());
			return null;
		}
		
		InputStream inputStream = resource.getInputStream();
		List<?> readLines = IOUtils.readLines(inputStream);
		IOUtils.closeQuietly(inputStream);
		
		StringBuilder sb = new StringBuilder();
		for(Object msg : readLines) {
			sb.append(msg).append("\n");
		}
		
		return sb.toString();
	}
	
	
	public void settingLocale(){
		Locale locale = LocaleContextHolder.getLocale();
		Locale[] supportLocales = {Locale.KOREA, Locale.ENGLISH, Locale.JAPAN};
		Locale defaultLocale = Locale.KOREA;
		boolean isSupport = false;
		
		for(Locale supprotLocale : supportLocales){
			if(supprotLocale.getLanguage().equals(locale.getLanguage())){
				isSupport = true;
				break;
			}
		}
		
		if(!isSupport){
			LocaleContextHolder.setLocale(defaultLocale);
		}
	}
}
