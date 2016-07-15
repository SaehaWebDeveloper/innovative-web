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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="TB_USERINFO")
@ToString
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	@Getter@Setter
	private int userId;
	
	@Column(name="USER_REAL_ID")
	@Getter@Setter
	private String userRealId;
	
	@Column(name="PASSWORD")
	@Getter@Setter
	private String password;
	
	@Column(name="USER_NAME")
	@Getter@Setter
	private String userName;
	
	@Column(name="GROUPCODE")
	@Getter@Setter
	private int groupcode;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	@Getter@Setter
	private List<UserInfoFunc> UserinfoFuncList;
}
