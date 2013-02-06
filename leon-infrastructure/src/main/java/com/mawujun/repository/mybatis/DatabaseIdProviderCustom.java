package com.mawujun.repository.mybatis;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;

public class DatabaseIdProviderCustom implements DatabaseIdProvider {
	
	private String databaseId;

	public void setProperties(Properties p) {
		// TODO Auto-generated method stub

	}

	public String getDatabaseId(DataSource dataSource) throws SQLException {
		// TODO Auto-generated method stub
		return databaseId;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

}
