package com.saeha.webdev.innovativeweb.model.conference;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saeha.webdev.innovativeweb.model.company.CompanyInfo;
import com.saeha.webdev.innovativeweb.model.user.UserInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="TB_CONFERENCE_INFO")
@ToString(exclude={"userInfo", "companyInfo"})
public class ConferenceInfo {
	@Id
	@Column(name="CONFCODE")
	@Getter@Setter
	private String confcode;
	
	@Column(name="GROUPCODE")
	@Getter@Setter
	private int groupcode;
	
	@Column(name="TITLE")
	@Getter@Setter
	private String title;
	
	@Column(name="CREATE_USER_ID")
	@Getter@Setter
	private int createUserId;
	
	@Column(name="CREATE_USER_REAL_ID")
	@Getter@Setter
	private String createUserRealId;
	
	@Column(name="CREATE_USER_NAME")
	@Getter@Setter
	private String createUserName;
	
	@Column(name="REG_DATE")
	@Getter@Setter
	private Date regDate;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="GROUPCODE", referencedColumnName="GROUPCODE", insertable=false, updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	@Getter@Setter
	private CompanyInfo companyInfo;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CREATE_USER_ID", referencedColumnName="USER_ID", insertable=false, updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	@Getter@Setter
	private UserInfo userInfo;
}
