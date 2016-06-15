package com.saeha.webdev.innovativeweb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Conference {
	@Getter@Setter private String confcode;
	@Getter@Setter private String title;
}
