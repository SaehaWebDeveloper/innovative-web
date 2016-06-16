package com.saeha.webdev.innovativeweb.model.user.pk;

import java.io.Serializable;

import com.saeha.webdev.innovativeweb.model.user.UserinfoFunc.FunctionType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserinfoFuncPk implements Serializable{
	private static final long serialVersionUID = -7103178677782623347L;
	
	@Getter@Setter private int userid;
	@Getter@Setter private FunctionType functionType;
}
