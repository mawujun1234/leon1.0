package com.mawujun.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectRoot {
	private String dbName;
	private String tableName;
	private String simpleClassName;
	private String basepackage;
	private String idType;
	private String idColumnName;
	private String idPropertyName;
	private String idGeneratorStrategy;
	private String sequenceName;
	private boolean hasResultMap;//是组件关联的时候
	
	//private String jsPackage;//用于用表生成的时候指定的
	//private Map<Object,Object> extenConfig=new HashMap<Object,Object>();
	private Object extenConfig=new Object();
	
	

	
	List<PropertyColumn> propertyColumns=new ArrayList<PropertyColumn>();
	List<PropertyColumn> baseTypePropertyColumns=new ArrayList<PropertyColumn>();
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSimpleClassName() {
		return simpleClassName;
	}
	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}
	public String getBasepackage() {
		return basepackage;
	}
	public void setBasepackage(String basepackage) {
		this.basepackage = basepackage;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdColumnName() {
		return idColumnName;
	}
	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}
	public String getIdPropertyName() {
		return idPropertyName;
	}
	public void setIdPropertyName(String idPropertyName) {
		this.idPropertyName = idPropertyName;
	}
	public String getIdGeneratorStrategy() {
		return idGeneratorStrategy;
	}
	public void setIdGeneratorStrategy(String idGeneratorStrategy) {
		this.idGeneratorStrategy = idGeneratorStrategy;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public boolean isHasResultMap() {
		return hasResultMap;
	}
	public void setHasResultMap(boolean hasResultMap) {
		this.hasResultMap = hasResultMap;
	}
	public List<PropertyColumn> getPropertyColumns() {
		return propertyColumns;
	}
	public void setPropertyColumns(List<PropertyColumn> propertyColumns) {
		this.propertyColumns = propertyColumns;
	}
	public List<PropertyColumn> getBaseTypePropertyColumns() {
		return baseTypePropertyColumns;
	}
	public void setBaseTypePropertyColumns(
			List<PropertyColumn> baseTypePropertyColumns) {
		this.baseTypePropertyColumns = baseTypePropertyColumns;
	}
//	public Map<Object, Object> getExtenConfig() {
//		return extenConfig;
//	}
//	public void setExtenConfig(Map<Object, Object> extenConfig) {
//		this.extenConfig = extenConfig;
//	}
//	public void addExtenConfig(Object key, Object value) {
//		this.extenConfig.put(key, value);
//	}
	public Object getExtenConfig() {
		return extenConfig;
	}
	public void setExtenConfig(Object extenConfig) {
		this.extenConfig = extenConfig;
	}
	
//	public String getJsPackage() {
////		if(jsPackage==null){
////			return "Leon"+StringUtils.uncapitalize(simpleClassName);
////		}
//		return jsPackage;
//	}
//	public void setJsPackage(String jsPackage) {
//		this.jsPackage = jsPackage;
//	}
}
