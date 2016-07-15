package com.saeha.webdev.innovativeweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeha.webdev.innovativeweb.model.user.UserInfo;
import com.saeha.webdev.innovativeweb.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService{
	@Autowired private UserInfoRepository userInfoRepository;

	@Transactional(readOnly=true, rollbackFor=Exception.class)
	public UserInfo checkUser(UserInfo userInfo) throws Exception{
		UserInfo result = userInfoRepository.findByUserRealIdAndGroupcode(userInfo.getUserRealId(), userInfo.getGroupcode());
		return result;
	}
	
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public void updateUser(String userid, String password) throws Exception{
	}
	
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public void save(UserInfo userinfo){
		UserInfo result = userInfoRepository.save(userinfo);
		log.debug("User Insert:{}", result);
	}
}
