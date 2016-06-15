package com.saeha.webdev.innovativeweb.model;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserLogin {
	@NotBlank
	@Getter@Setter private String userid;
	@NotBlank
	@Getter@Setter private String password;
}
