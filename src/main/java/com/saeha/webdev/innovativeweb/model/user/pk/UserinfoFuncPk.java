package com.saeha.webdev.innovativeweb.model.user.pk;

import java.io.Serializable;

import com.saeha.webdev.innovativeweb.model.user.UserInfoFunc.FunctionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString @AllArgsConstructor @NoArgsConstructor
public class UserinfoFuncPk implements Serializable{
	private static final long serialVersionUID = -7103178677782623347L;
	
	@Getter@Setter private int userId;
	@Getter@Setter private FunctionType functionType;
}
