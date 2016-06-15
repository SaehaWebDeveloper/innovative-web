package com.saeha.webdev.innovativeweb.common.jdbc;

import lombok.Getter;
import lombok.ToString;

/**
 * JDBC 접속 정보 생성
 * @author Pure
 *
 */
@ToString
public enum JdbcInfoGenerator {
	/**
	 * Maria DB or MySQL
	 */
	MARIA("org.mariadb.jdbc.Driver", "jdbc:mariadb://{ip}:{port}/{dbname}", "3306"),
	/**
	 * MS-SQL(SQL Server) 
	 */
	MSSQL("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://{ip}:{port};DataBaseName={dbname}", "1443");
	
	@Getter private String driverClassName;
	@Getter private String url;
	private String ip = "127.0.0.1";
	private String port = "";
	private String dbname = "mv61";
	private String extra = "";
	@Getter private String username = "root";
	@Getter private String password = "saeha";
	
	private JdbcInfoGenerator(String driverClassName, String url, String port){
		this.driverClassName = driverClassName;
		this.url = url;
		this.port = port;
	}
	
	/**
	 * Jdbc 접속 정보 설정
	 * @param driverClassName
	 * @param url
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param extra
	 * @param username
	 * @param password
	 * @return
	 */
	public JdbcInfoGenerator setInfo(
			String driverClassName
			, String url
			, String ip
			, String port
			, String dbname
			, String extra
			, String username
			, String password){
		
		if(driverClassName != null && !driverClassName.isEmpty())
			this.driverClassName = driverClassName;
		
		if(ip != null && !ip.isEmpty())
			this.ip = ip;
		if(port != null && !port.isEmpty()) 
			this.port = port;
		if(dbname != null && !dbname.isEmpty())
			this.dbname = dbname;
		if(extra != null && !extra.isEmpty())
			this.extra = extra;
		if(url != null && !url.isEmpty())
			this.url = url;
		else
			this.url = makeJdbcUrl();
		
		if(username != null && !username.isEmpty())
			this.username = username;
		if(password != null && !password.isEmpty())
			this.password = password;
		
		return this;
	}
	
	/**
	 * url 생성
	 * @return
	 */
	private String makeJdbcUrl(){
		return url.replace("{ip}", ip)
			.replace("{port}", port)
			.replace("{dbname}", dbname) + extra;
	}
}
