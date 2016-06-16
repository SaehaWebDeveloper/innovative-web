package com.saeha.webdev.innovativeweb.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.saeha.webdev.innovativeweb.model.user.Userinfo;

@Repository
public interface UserinfoDao extends PagingAndSortingRepository<Userinfo, Integer>{
	public Userinfo findByUserRealId(String userRealId);
}
