package com.saeha.webdev.innovativeweb.model.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="TB_USERINFO")
@ToString
public class Userinfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USERID")
	@Getter@Setter
	private int userid;
	
	@Column(name="USER_REAL_ID")
	@Getter@Setter
	private String userRealId;
	
	@Column(name="PASSWORD")
	@Getter@Setter
	private String password;
	
	@Column(name="USER_NAME")
	@Getter@Setter
	private String userName;
	
	@OneToMany( fetch=FetchType.LAZY)
	@JoinColumn(name="USERID", referencedColumnName="USERID")
	@Getter@Setter
	private List<UserinfoFunc> UserinfoFuncList;
}
