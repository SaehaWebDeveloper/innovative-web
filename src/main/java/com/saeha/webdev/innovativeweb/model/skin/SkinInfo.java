package com.saeha.webdev.innovativeweb.model.skin;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
public class SkinInfo {
	@Id
	@Getter@Setter
	private String skinCode = "default";
	
	@Getter@Setter
	private String skinName;
}
