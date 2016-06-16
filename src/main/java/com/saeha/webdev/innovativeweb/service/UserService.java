package com.saeha.webdev.innovativeweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeha.webdev.innovativeweb.model.user.Userinfo;
import com.saeha.webdev.innovativeweb.model.user.UserinfoFunc.FunctionType;
import com.saeha.webdev.innovativeweb.repository.UserinfoDao;

import ch.qos.logback.core.recovery.ResilientFileOutputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService{
	@Autowired private UserinfoDao userinfoDao;

	@Transactional(readOnly=true, rollbackFor=Exception.class)
	public Userinfo checkUser(Userinfo userinfo) throws Exception{
		Userinfo result = userinfoDao.findByUserRealId(userinfo.getUserRealId());
		
		
		log.debug("{}", result.getUserinfoFuncList());
		
		return result;
	}
	
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public void updateUser(String userid, String password) throws Exception{
	}
	
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public void save(Userinfo userinfo){
		Userinfo result = userinfoDao.save(userinfo);
		log.debug("User Insert:{}", result);
	}
}
