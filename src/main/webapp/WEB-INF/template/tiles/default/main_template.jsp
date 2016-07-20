<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:set var="req" value="${pageContext.request}"/>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<base href="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/">

<link rel="stylesheet" type="text/css" href="resources/librarys/bootstrap/3.3.6/dist/css/bootstrap.min.css">

<title>Insert title here</title>
</head>
<body>

	<tiles:insertAttribute name="header" ignore="true" />
	<tiles:insertAttribute name="body" />

<script type="text/javascript" src="resources/librarys/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="resources/librarys/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<script type="text/javascript" src="resources/librarys/jquery-i18n-properties/1.2.2/jquery.i18n.properties.min.js"></script>
<script type="text/javascript" src="resources/js/util/i18nUtil.js"></script>

<script type="text/javascript" src="resources/js/<tiles:getAsString name="location" ignore="true" />.js"></script>

<script type="text/javascript">
	i18nUtil.load({language:'${language}'});
</script>
</body>
</html>