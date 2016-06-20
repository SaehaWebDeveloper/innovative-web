package com.saeha.webdev.innovativeweb.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.saeha.webdev.innovativeweb.model.user.UserInfo;

@Repository
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Integer>{
	/**
	 * 사용자 아이디 조회
	 * @param userRealId
	 * @return
	 */
	public UserInfo findByUserRealIdAndGroupcode(String userRealId, int groupcode);
}
