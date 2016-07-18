package com.saeha.webdev.innovativeweb.model.skin;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
public class SkinInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4478544305948938803L;

	@Id
	@Getter@Setter
	private String skinCode = "default";
	
	@Getter@Setter
	private String skinName;
}
