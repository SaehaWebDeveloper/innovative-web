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

<link rel="stylesheet" type="text/css" href="resources/librarys/bootstrap/${webjars-bootstrap-version}/css/bootstrap.min.css">

<title>InnovativeWeb - ${SESSION_APP_CONFIG.projectVersion}</title>
</head> 
<body style="height: 100%; background-image: url('resources/image/login_background.jpg');">
<tiles:insertAttribute name="body" />

<script type="text/javascript" src="resources/librarys/jquery/${webjars-jquery-version}/jquery.min.js"></script>
<script type="text/javascript" src="resources/librarys/bootstrap/${webjars-bootstrap-version}/js/bootstrap.min.js"></script>

<script type="text/javascript" src="resources/librarys/jquery-i18n-properties/${webjars-jquery-i18n-properties-version}/jquery.i18n.properties.min.js"></script>
<script type="text/javascript" src="resources/js/util/i18nUtil.js"></script>

<script type="text/javascript" src="resources/librarys/jquery-validation/${webjars-jquery-validation-version}/jquery.validate.min.js"></script>
<script type="text/javascript" src="resources/librarys/jquery-validation/localization/messages_${language}.js"></script>

<script type="text/javascript" src="resources/librarys/stomp-websocket/${webjars-stomp-websocket-version}/stomp.min.js"></script>
<script type="text/javascript" src="resources/js/util/websocketUtil.js"></script>

<script type="text/javascript">
	i18nUtil.load({language:'${language}'});
	$('[data-toggle="tooltip"]').tooltip();
</script>

<script type="text/javascript" src="resources/js/auth/login.js"></script>
</body>
</html>