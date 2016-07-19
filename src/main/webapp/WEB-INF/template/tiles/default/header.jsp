<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<header>
	<div>
		${SESSION_USER.userName}(${SESSION_USER.userRealId}) <a href="logout">로그아웃</a>
	</div>
	<a href="conference/list">회의실 목록</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="conference/create">회의실 개설</a>
</header>
<br>