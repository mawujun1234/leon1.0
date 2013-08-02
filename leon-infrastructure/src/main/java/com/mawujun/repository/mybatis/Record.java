package com.mawujun.repository.mybatis;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.ibatis.type.Alias;

import com.mawujun.utils.BeanUtils;

/**
 * 因为不能新建一个类，代码中写死了，所以使用继承
 * @author mawujun
 *
 */
@Alias("record")
public class Record extends LinkedHashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DataParser dataParser=new DataParser();
	
	private String[] columns=null;
	
	public Set<String> getColumns(){
		if(columns==null){
			columns=super.keySet().toArray(new String[super.keySet().size()]);
		}
		
		return super.keySet();
	}
	
	
	public Object getValue(String name)
	{	
		return this.get(name);
	}
	
	public Object getValue(int i)
	{	
		if(columns==null){
			this.getColumns();
		}
		if(i>columns.length-1){
			return null;
		}
		return this.get(columns[i]);
	}
	public Character getChar(String name)
	{
		return dataParser.parseChar(getValue(name));
	}
	public Character getChar(int i)
	{
		return dataParser.parseChar(getValue(i));
	}
	public String getString(String name)
	{
		return dataParser.parseString(getValue(name));
	}
	public String getString(int i)
	{
		return dataParser.parseString(getValue(i));
	}
	
	
	public Boolean getBoolean(String name)
	{
		return dataParser.parseBoolean(getValue(name));
	}
	public Boolean getBoolean(int i)
	{
		return dataParser.parseBoolean(getValue(i));
	}
	public Byte getByte(String name)
	{
		return dataParser.parseByte(getValue(name));
	}
	public Byte getByte(int i)
	{
		return dataParser.parseByte(getValue(i));
	}
	public Short getShort(String name)
	{
		return dataParser.parseShort(getValue(name));
	}
	public Short getShort(int i)
	{
		return dataParser.parseShort(getValue(i));
	}
	public Integer getInteger(String name)
	{
		return dataParser.parseInteger(getValue(name));
	}
	public Integer getInteger(int i)
	{
		return dataParser.parseInteger(getValue(i));
	}
	public Long getLong(String name)
	{
		return dataParser.parseLong(getValue(name));
	}
	public Long getLong(int i)
	{
		return dataParser.parseLong(getValue(i));
	}
	public BigInteger getBigInteger(String name)
	{
		return dataParser.parseBigInteger(getValue(name));
	}
	public BigInteger getBigInteger(int i)
	{
		return dataParser.parseBigInteger(getValue(i));
	}
	public Float getFloat(String name)
	{
		return dataParser.parseFloat(getValue(name));
	}
	public Float getFloat(int i)
	{
		return dataParser.parseFloat(getValue(i));
	}
	public Double getDouble(String name)
	{
		return dataParser.parseDouble(getValue(name));
	}
	public Double getDouble(int i)
	{
		return dataParser.parseDouble(getValue(i));
	}
	public BigDecimal getBigDecimal(String name)
	{
		return dataParser.parseBigDecimal(getValue(name));
	}
	public BigDecimal getBigDecimal(int i)
	{
		return dataParser.parseBigDecimal(getValue(i));
	}
	public Date getDate(String name)
	{
		return dataParser.parseDate(getValue(name));
	}
	public Date getDate(int i)
	{
		return dataParser.parseDate(getValue(i));
	}
	public static Record toRecord(Object obj){
		return BeanUtils.copyOrCast(obj, Record.class);
	}
//
//	
//	public JSONObject toJSONObject(){
//		return JSONObject.fromObject(this);
//	}
}
