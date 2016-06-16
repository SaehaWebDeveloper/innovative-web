package com.saeha.webdev.innovativeweb.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.saeha.webdev.innovativeweb.model.user.pk.UserinfoFuncPk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="TB_USERINFO_FUNC") 
@IdClass(UserinfoFuncPk.class)
@ToString
public class UserinfoFunc {
	@AllArgsConstructor
	public enum FunctionType{
		CREATE(0), JOIN(1), REMOTE(3), QUESTION(3), OBSVER(4);
		
		@Getter private int functionType;
		
		@Override
		public String toString(){
			return String.valueOf(this.getFunctionType());
		}
	}
	
	@Id
	@Column(name="USERID")
	@Getter@Setter
	private int userid;
	
	@Id
	@Column(name="FUNC_TYPE")
	@Getter@Setter
	private FunctionType functionType;
	
	@Column(name="USE_YN")
	@Getter@Setter
	private boolean use;
}
