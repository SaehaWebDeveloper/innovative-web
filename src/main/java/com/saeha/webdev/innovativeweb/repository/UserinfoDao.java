package com.saeha.webdev.innovativeweb.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.saeha.webdev.innovativeweb.model.UserLogin;
import com.saeha.webdev.innovativeweb.model.UserSession;

@Repository
public interface UserinfoDao{

	public UserSession selectUser(UserLogin userLogin);
	public void updateUser(@Param("userid") String userid, @Param("password") String password);
}
