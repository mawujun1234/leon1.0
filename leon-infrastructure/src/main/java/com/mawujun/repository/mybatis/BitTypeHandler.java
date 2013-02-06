package com.mawujun.repository.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BitTypeHandler  extends BaseTypeHandler<Boolean>{
	private static final String YES="1";
	private static final String NO="0";
	
	private Boolean stringToBoolean(String s){
		return toBoolean(s);
	}
	/**
	 * 供外部使用的
	 * @param s
	 * @return
	 */
	public Boolean toBoolean(String s){
		if(YES.equals(s)){
			return Boolean.TRUE;
		} else if(NO.equals(s)){
			return Boolean.FALSE;
		} else {
			return null;
		}
	}
	@Override
	public Boolean getNullableResult(ResultSet arg0, String arg1)
			throws SQLException {
		return stringToBoolean(arg0.getString(arg1));
	}

	@Override
	public Boolean getNullableResult(ResultSet arg0, int arg1)
			throws SQLException {
		return stringToBoolean(arg0.getString(arg1));
	}

	@Override
	public Boolean getNullableResult(CallableStatement arg0, int arg1)
			throws SQLException {
		return stringToBoolean(arg0.getString(arg1));
	}

	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1,
			Boolean arg2, JdbcType arg3) throws SQLException {
		if(arg2==true){
			arg0.setString(arg1, YES);
		} else if(arg2==false){
			arg0.setString(arg1, NO);
		} else {
			arg0.setString(arg1, null);
		}
		
		
	}
}
