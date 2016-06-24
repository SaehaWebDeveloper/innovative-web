<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>

	<s:message code="password" arguments="Pure" htmlEscape="true" />


<a href="logout">로그아웃</a>






<c:forEach var="conference" items="${conferenceList}">
	<div>${conference.confcode} - ${conference.title}</div>
</c:forEach>
