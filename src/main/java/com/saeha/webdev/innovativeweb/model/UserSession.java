package com.saeha.webdev.innovativeweb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserSession {
	@Getter@Setter private int userid;
	@Getter@Setter private String userrealid;
	@Getter@Setter private String password;
	@Getter@Setter private String username;
	@Getter@Setter private String phoneNumber;
}
