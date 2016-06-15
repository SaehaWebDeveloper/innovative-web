package com.saeha.webdev.innovativeweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeha.webdev.innovativeweb.model.UserLogin;
import com.saeha.webdev.innovativeweb.model.UserSession;
import com.saeha.webdev.innovativeweb.repository.UserinfoDao;

@Service
public class LoginService{
	@Autowired private UserinfoDao userinfoDao;

	@Transactional(readOnly=true, rollbackFor=Exception.class)
	public UserSession checkUser(UserLogin userLogin) throws Exception{
		return userinfoDao.selectUser(userLogin);
	}
	
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public void updateUser(String userid, String password) throws Exception{
		userinfoDao.updateUser(userid, password);
	}
}
