package com.mawujun.repository.sql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.mawujun.repository.mybatis.DataParser;


public class Rcd  implements Serializable{
	private static final long serialVersionUID = 860047827010027202L;
	private Object[] data=null;
	private RcdSet ownerSet;
	protected Rcd(RcdSet set,Object[] data)
	{
		this.ownerSet=set;
		this.data=data;
	}
	private Object getValue(int i) {
		return data[i];
	}
	public Object getValue(String name)
	{
		int i=ownerSet.getColumnIndex(name);
		return i==-1?name+" error":this.getValue(i);
	}
	public Character getChar(String name)
	{
		return DataParser.parseChar(getValue(name));
	}
	public Character getChar(int i)
	{
		return DataParser.parseChar(getValue(i));
	}
	public String getString(String name)
	{
		return DataParser.parseString(getValue(name));
	}
	public String getString(int i)
	{
		return DataParser.parseString(getValue(i));
	}
	public Boolean getBoolean(String name)
	{
		return DataParser.parseBoolean(getValue(name));
	}
	public Boolean getBoolean(int i)
	{
		return DataParser.parseBoolean(getValue(i));
	}
	public Byte getByte(String name)
	{
		return DataParser.parseByte(getValue(name));
	}
	public Byte getByte(int i)
	{
		return DataParser.parseByte(getValue(i));
	}
	public Short getShort(String name)
	{
		return DataParser.parseShort(getValue(name));
	}
	public Short getShort(int i)
	{
		return DataParser.parseShort(getValue(i));
	}
	public Integer getInteger(String name)
	{
		return DataParser.parseInteger(getValue(name));
	}
	public Integer getInteger(int i)
	{
		return DataParser.parseInteger(getValue(i));
	}
	public Long getLong(String name)
	{
		return DataParser.parseLong(getValue(name));
	}
	public Long getLong(int i)
	{
		return DataParser.parseLong(getValue(i));
	}
	public BigInteger getBigInteger(String name)
	{
		return DataParser.parseBigInteger(getValue(name));
	}
	public BigInteger getBigInteger(int i)
	{
		return DataParser.parseBigInteger(getValue(i));
	}
	public Float getFloat(String name)
	{
		return DataParser.parseFloat(getValue(name));
	}
	public Float getFloat(int i)
	{
		return DataParser.parseFloat(getValue(i));
	}
	public Double getDouble(String name)
	{
		return DataParser.parseDouble(getValue(name));
	}
	public Double getDouble(int i)
	{
		return DataParser.parseDouble(getValue(i));
	}
	public BigDecimal getBigDecimal(String name)
	{
		return DataParser.parseBigDecimal(getValue(name));
	}
	public BigDecimal getBigDecimal(int i)
	{
		return DataParser.parseBigDecimal(getValue(i));
	}
	public Date getDate(String name)
	{
		return DataParser.parseDate(getValue(name));
	}
	public Date getDate(int i)
	{
		return DataParser.parseDate(getValue(i));
	}
	
public void setValue(int fld, Object val) {
		data[fld]=val;
	}

	public void setValue(String fld, Object val) {
		int i=ownerSet.getColumnIndex(fld);
		setValue(i, val);
	}
}
