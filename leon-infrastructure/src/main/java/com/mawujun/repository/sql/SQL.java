package com.mawujun.repository.sql;

import java.util.HashMap;

public interface SQL
{
	public static final String PNAME_PREFIX = "PARAM";
	
	/**
	 * 完整的SQL语句
	 * */
	public String getSQL();
	
	/**
	 * 以?形式代入变量的SQL语句
	 * */
	public String getParamedSQL();
	/**
	 * 返回以?形式代入变量的SQL语句的参数列表
	 * */
	public Object[] getParams();
	
	/**
	 * 以:PARAM_n形式代入变量的SQL语句
	 * */
	public String getParamNamedSQL();
	/**
	 * 以:PARAM_n形式代入变量的SQL语句参数列表
	 * */
	public HashMap<String, Object> getNamedParams();
	/**
	 * 是否空语句
	 * */
	public boolean isEmpty();
	
	public boolean isAllParamsEmpty(); //自动识别模式
	public boolean isAllParamsEmpty(boolean isCE); //指定模式
	
	/**
	 * 得到顶层的SQL
	 * */
	public SQL top();
	/**
	 * 得到上级SQL
	 * */
	public SQL parent();
	/**
	 * 设置上级SQL
	 * */
	public void setParent(SQL sql);
	
	
	/*
	 * 以下实现方法必须考虑并发
	 * */
	public void beginParamNameSQL();
	public void endParamNameSQL();
	public String getNextParamName(boolean withColon);
	
	
}
