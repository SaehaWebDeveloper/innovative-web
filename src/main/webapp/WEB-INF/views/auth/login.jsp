<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>

${language}
<div class="container" style="text-align: center; height: 100%;">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default" style="margin: 5px">
				<div class="panel-heading" style="padding-top: 30px; padding-bottom: 20px; border-color: #fff; background-color: #fff;">
					<img alt="Multiview" src="resources/image/login_logo.gif">
				</div><!-- panel-heading -->
				<div class="panel-body" style="padding: 30px">
					<c:if test="${!empty checkResult}">
					<div class="alert alert-warning" role="alert">
						${checkResult}
					</div>
					</c:if>
					
					<form id="loginForm" action="auth/login" method="post">
						<!-- 아이디 -->
						<div class="form-group form-group-lg">
							<div class="input-group">
								<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
								<input type="text" class="form-control" id="userid" name="userid" placeholder="<s:message code='userid'/>" value="${userLogin.userid}">
							</div>
						</div>
						<!-- 패스워드 -->
						<div class="form-group form-group-lg">
							<div class="input-group">
								<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
								<input type="password" class="form-control" id="password" name="password" placeholder="<s:message code='password'/>">
							</div>
						</div>
						<!-- 로그인 -->
						<div class="form-group">
							<button type="submit" class="btn btn-success btn-lg" style="width: 100%"><s:message code="login"/></button>
						</div>
						<!-- 아이디 저장 -->
						<div class="form-group checkbox-inline">
							<label><input type="checkbox" id="saveUserid" name="saveUserid"><s:message code="saveUserid"/> </label>&nbsp;&nbsp;
						</div>
						<!-- 자동 로그인 -->
						<div class="form-group checkbox-inline">
							<label><input type="checkbox" id="autoLogin"><s:message code="autoLogin"/> </label>
						</div>
					</form>
				</div><!-- panel-body -->
				<div class="panel-footer">
					<div style="text-align: right;">
						<s:message code="findIdPassword"/>
					</div>
				</div><!-- panel-footer -->
			</div><!-- panel -->
		</div><!-- col -->
	</div><!-- row -->
</div><!-- container -->