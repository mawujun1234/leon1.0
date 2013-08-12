package com.mawujun.generator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyColumn {
	private String column;//列名
	private String property;//属性名称
	
	private String basepackage;//包名
	private String javaType;
	private String javaTypeClassName;//其实不用定义这个属性，其实是可以直接写get方法，下同
	private String jsType;
	
	//private String joinColumn;//在多对一关联的时候，的关联列名是什么
	
	private Boolean isIdProperty=false;//是不是属于id的列
	private Boolean isComponentType=false;
	private Boolean isAssociationType=false;
	private Boolean isBaseType=false;
	private Boolean isCollectionType=false;
	private Boolean isConstantType=false;//判断是不是常数
	
	List<PropertyColumn> propertyColumns=new ArrayList<PropertyColumn>();
	
	private static Map<Class,String> jsJavaMapper=new HashMap<Class,String>();
	static {
		jsJavaMapper.put(String.class, "string");
		jsJavaMapper.put(Charset.class, "string");
		jsJavaMapper.put(char.class, "string");
		
		jsJavaMapper.put(boolean.class, "bool");
		jsJavaMapper.put(Boolean.class, "bool");
		
		jsJavaMapper.put(byte.class, "int");
		jsJavaMapper.put(Byte.class, "int");
		jsJavaMapper.put(short.class, "int");
		jsJavaMapper.put(Short.class, "int");
		jsJavaMapper.put(int.class, "int");
		jsJavaMapper.put(Integer.class, "int");
		jsJavaMapper.put(long.class, "int");
		jsJavaMapper.put(Long.class, "int");
		jsJavaMapper.put(BigInteger.class, "int");
		
		jsJavaMapper.put(float.class, "float");
		jsJavaMapper.put(Float.class, "float");
		jsJavaMapper.put(double.class, "float");
		jsJavaMapper.put(Double.class, "float");
		jsJavaMapper.put(BigDecimal.class, "float");
		
		jsJavaMapper.put(java.util.Date.class, "date");
		jsJavaMapper.put(java.sql.Date.class, "date");
		//jsJavaMapper.put(, "");
		
		//jsJavaMapper.put(, "");
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(Class clazz) {
		this.javaType = clazz.getName();
		this.basepackage=clazz.getPackage().getName();
		//System.out.println(javaType);
		this.javaTypeClassName=javaType.substring(javaType.lastIndexOf('.')+1);
		//System.out.println(this.javaTypeClassName);
		if(jsJavaMapper.get(clazz)==null){
			this.jsType="auto";
		} else {
			this.jsType=jsJavaMapper.get(clazz);//映射好后就是替换 自己写的类然后测试
		}
		
		//System.out.println(this.jsType);
	}

	public Boolean getIsComponentType() {
		return isComponentType;
	}

	public void setIsComponentType(Boolean isComponentType) {
		this.isComponentType = isComponentType;
	}

	public Boolean getIsAssociationType() {
		return isAssociationType;
	}

	public void setIsAssociationType(Boolean isAssociationType) {
		this.isAssociationType = isAssociationType;
	}

	public Boolean getIsBaseType() {
		return isBaseType;
	}

	public void setIsBaseType(Boolean isBaseType) {
		this.isBaseType = isBaseType;
	}

	public List<PropertyColumn> getPropertyColumns() {
		return propertyColumns;
	}
	
	//private List<PropertyColumn> baseTypePropertyColumns=new ArrayList<PropertyColumn>();
	public List<PropertyColumn> getBaseTypePropertyColumns() {
		List<PropertyColumn> baseTypePropertyColumns=new ArrayList<PropertyColumn>();
		//baseTypePropertyColumns.clear();
		for(PropertyColumn propertyColumn:propertyColumns){
			if(propertyColumn.isBaseType){
				baseTypePropertyColumns.add(propertyColumn);
			}
		}
		return baseTypePropertyColumns;
	}

	public void setPropertyColumns(List<PropertyColumn> propertyColumns) {
		this.propertyColumns = propertyColumns;
	}
	
	public PropertyColumn addBaseTypePropertyColumn(String property,String column,Class clazz) {
		PropertyColumn aa=new PropertyColumn();
		aa.setColumn(column);
		aa.setProperty(property);
		aa.setIsBaseType(true);
		aa.setJavaType(clazz);
		this.propertyColumns.add(aa);
		return aa;
	}
	public PropertyColumn addEntityTypePropertyColumn(String property,String column,Class clazz) {
		PropertyColumn aa=new PropertyColumn();
		aa.setColumn(column);
		aa.setProperty(property);
		aa.setIsAssociationType(true);
		aa.setJavaType(clazz);
		this.propertyColumns.add(aa);
		return aa;
	}

	public Boolean getIsIdProperty() {
		return isIdProperty;
	}

	public void setIsIdProperty(Boolean isIdProperty) {
		this.isIdProperty = isIdProperty;
	}

	public Boolean getIsCollectionType() {
		return isCollectionType;
	}

	public void setIsCollectionType(Boolean isCollectionType) {
		this.isCollectionType = isCollectionType;
	}

	public String getJsType() {
		return jsType;
	}

	public void setJsType(String jsType) {
		this.jsType = jsType;
	}

	public String getJavaTypeClassName() {
		return javaTypeClassName;
	}

	public void setJavaTypeClassName(String javaTypeClassName) {
		this.javaTypeClassName = javaTypeClassName;
	}

	public String getBasepackage() {
		return basepackage;
	}

	public void setBasepackage(String basepackage) {
		this.basepackage = basepackage;
	}

	public Boolean getIsConstantType() {
		return isConstantType;
	}

	public void setIsConstantType(Boolean isConstantType) {
		this.isConstantType = isConstantType;
	}
	
}
