package com.saeha.webdev.innovativeweb.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.ToString;

@Entity
@Table(name="TB_COMPANY_INFO")
@ToString
public class CompanyInfo {
	
	public enum LicenseType{
		FREE, PAY;
	}
	
	@Id
	@Column(name="GROUPCODE")
	private int groupcode;
	
	@Column(name="COMPANY_ID")
	private String companyId;
	
	@Column(name="COMPANY_NAME")
	private String companyName;
	
	@Column(name="MEMO")
	private String memo;
	
	@Column(name="CONTRACT_DATE")
	private String contractDate;
	
	@Column(name="LICENSE_TYPE")
	private String licenseType;
}
