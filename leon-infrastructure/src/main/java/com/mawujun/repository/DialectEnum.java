package com.mawujun.repository;

/**
 * DB2	org.hibernate.dialect.DB2Dialect
DB2 AS/400	org.hibernate.dialect.DB2400Dialect
DB2 OS390	org.hibernate.dialect.DB2390Dialect
Firebird	org.hibernate.dialect.FirebirdDialect
FrontBase	org.hibernate.dialect.FrontbaseDialect
HypersonicSQL	org.hibernate.dialect.HSQLDialect
Informix	org.hibernate.dialect.InformixDialect
Interbase	org.hibernate.dialect.InterbaseDialect
Ingres	org.hibernate.dialect.IngresDialect
Microsoft SQL Server 2005	org.hibernate.dialect.SQLServer2005Dialect
Microsoft SQL Server 2008	org.hibernate.dialect.SQLServer2008Dialect
Mckoi SQL	org.hibernate.dialect.MckoiDialect
MySQL	org.hibernate.dialect.MySQLDialect
MySQL with InnoDB	org.hibernate.dialect.MySQL5InnoDBDialect
MySQL with MyISAM	org.hibernate.dialect.MySQLMyISAMDialect
Oracle 8i	org.hibernate.dialect.Oracle8iDialect
Oracle 9i	org.hibernate.dialect.Oracle9iDialect
Oracle 10g	org.hibernate.dialect.Oracle10gDialect
Pointbase	org.hibernate.dialect.PointbaseDialect
PostgreSQL 8.1	org.hibernate.dialect.PostgreSQL81Dialect
PostgreSQL 8.2 and later	org.hibernate.dialect.PostgreSQL82Dialect
Progress	org.hibernate.dialect.ProgressDialect
SAP DB	org.hibernate.dialect.SAPDBDialect
Sybase ASE 15.5	org.hibernate.dialect.SybaseASE15Dialect
Sybase ASE 15.7	org.hibernate.dialect.SybaseASE157Dialect
Sybase Anywhere	org.hibernate.dialect.SybaseAnywhereDialect
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public enum DialectEnum {
	MySQL5InnoDBDialect("org.hibernate.dialect.MySQL5InnoDBDialect"), 
	MySQLDialect_mybatis("com.mawujun.repository.mybatis.dialect.MySQLDialect"),
	
	Oracle10gDialect("org.hibernate.dialect.Oracle10gDialect"), 
	OracleDialect_mybatis("com.mawujun.repository.mybatis.dialect.OracleDialect"),
	
	H2Dialect("org.hibernate.dialect.H2Dialect"), 
	H2Dialect_mybatis("com.mawujun.repository.mybatis.dialect.H2Dialect"),
	
	DB2Dialect("org.hibernate.dialect.DB2Dialect"), 
	DB2Dialect_mybatis("com.mawujun.repository.mybatis.dialect.DB2Dialect"),
	
	DerbyTenSevenDialect("org.hibernate.dialect.DerbyTenSevenDialect"), 
	DerbyDialect_mybatis("com.mawujun.repository.mybatis.dialect.DerbyDialect"),
	
	HSQLDialect("org.hibernate.dialect.HSQLDialect"), 
	HSQLDialect_mybatis("com.mawujun.repository.mybatis.dialect.HSQLDialect"),
	
	PostgresPlusDialect("org.hibernate.dialect.PostgresPlusDialect"), 
	PostgreSQLDialect_mybatis("com.mawujun.repository.mybatis.dialect.PostgreSQLDialect"),
	
	SQLServer2008Dialect("org.hibernate.dialect.SQLServer2008Dialect"), 
	SQLServer2005Dialect_mybatis("com.mawujun.repository.mybatis.dialect.SQLServer2005Dialect"),
	
	SybaseDialect("org.hibernate.dialect.SybaseDialect"), 
	SybaseDialect_mybatis("com.mawujun.repository.mybatis.dialect.SybaseDialect");

	private String className;

	private DialectEnum(String className) {

		this.className = className;

	}

	public String getClassName() {

		return className;

	}
}
