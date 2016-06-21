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
	MARIA("org.mariadb.jdbc.Driver", 
			"jdbc:mariadb://{ip}:{port}/{dbname}", 
			"3306", 
			"org.hibernate.dialect.MySQLDialect"),
	/**
	 * MS-SQL(SQL Server) 
	 */
	MSSQL("com.microsoft.sqlserver.jdbc.SQLServerDriver", 
			"jdbc:sqlserver://{ip}:{port};DataBaseName={dbname}", 
			"1443", 
			"org.hibernate.dialect.SQLServerDialect");
	
	@Getter private String driverClassName;
	@Getter private String jdbcUrl;
	private String ip = "127.0.0.1";
	private String port = "";
	private String dbname = "mv61";
	private String extra = "";
	@Getter private String username = "root";
	@Getter private String password = "saeha";
	@Getter private String hibernateDialect;
	
	private JdbcInfoGenerator(String driverClassName, String url, String port, String hibernateDialect){
		this.driverClassName = driverClassName;
		this.jdbcUrl = url;
		this.port = port;
		this.hibernateDialect = hibernateDialect;
	}
	
	/**
	 * Jdbc 접속 정보 설정
	 * @param driverClassName
	 * @param jdbcUrl
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
			, String jdbcUrl
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
		if(jdbcUrl != null && !jdbcUrl.isEmpty())
			this.jdbcUrl = jdbcUrl;
		else
			this.jdbcUrl = makeJdbcUrl();
		
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
		return jdbcUrl.replace("{ip}", ip)
			.replace("{port}", port)
			.replace("{dbname}", dbname) + extra;
	}
}
