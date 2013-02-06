package com.mawujun.repository.mybatis;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;


public class DataParser
{
	public String parseString(Object val)
	{
		if(val==null) return null;
		if(val instanceof String)
		{
			return (String)val;
		}
		else
		{
			return val.toString();
		}
	}
	
	YesNoTypeHandler yn=new YesNoTypeHandler();
	TrueFalseTypeHandler tf=new TrueFalseTypeHandler();
	BitTypeHandler bit=new BitTypeHandler();
	public Boolean parseBoolean(Object val)
	{
		if(val==null) return null;
		if(val instanceof Boolean)
		{
			return (Boolean)val;
		}
		else
		{
			String strval=parseString(val);
			
			//YesNoTypeHandler yn=YesNoTypeHandler.parse(strval); 
			//if(yn!=YNBoolean.NULL) return yn.getValue();
			Boolean result=null;
			
			result=(yn.toBoolean(strval));
			if(result!=null){
				return result;
			}
			result=(tf.toBoolean(strval));
			if(result!=null){
				return result;
			}
			
			result=(bit.toBoolean(strval));
			if(result!=null){
				return result;
			}
			
			return null;
		}
	}
	
	public Byte parseByte(Object val)
	{
		if(val==null) return null;
		if(isNumberType(val))
		{
			return (Byte)val;
		}
		else if(val instanceof BigDecimal)
		{
			return ((BigDecimal)val).byteValue();
		}
		else if(val instanceof BigInteger)
		{
			return ((BigInteger)val).byteValue();
		}
		else
		{
			return Byte.parseByte(parseString(val));
		}
	}
	
	public Integer parseInteger(Object val)
	{
		if(val==null) return null;
		if(val instanceof Long)
		{
			return Integer.parseInt(parseString(val));
		}
		else if(isNumberType(val))
		{
			return (Integer)val;
		}
		else if(val instanceof BigDecimal)
		{
			return ((BigDecimal)val).intValue();
		}
		else if(val instanceof BigInteger)
		{
			return ((BigInteger)val).intValue();
		}
		else
		{
			return Integer.parseInt(parseString(val));
		}
	}
	
	 
	
	public BigInteger parseBigInteger(Object val)
	{
		if(val==null) return null;
		if(val instanceof BigInteger)
		{
			return (BigInteger)val;
		}
		else if(isNumberType(val))
		{
			return new BigInteger(val.toString());
		}
		else if(val instanceof BigDecimal)
		{
			return ((BigDecimal)val).toBigInteger();
		}
		
		else
		{
			return new BigInteger(parseString(val));
		}
	}
	
	public Float parseFloat(Object val)
	{
		if(val==null) return null;
		if(isNumberType(val))
		{
			return (Float)val;
		}
		else if(val instanceof BigDecimal)
		{
			return ((BigDecimal)val).floatValue();
		}
		else if(val instanceof BigInteger)
		{
			return ((BigInteger)val).floatValue();
		}
		else
		{
			return null;
		}
	}
	public Double parseDouble(Object val)
	{
		if(val==null) return null;
		if(isNumberType(val))
		{
			return (Double)val;
		}
		else if(val instanceof BigDecimal)
		{
			return ((BigDecimal)val).doubleValue();
		}
		else if(val instanceof BigInteger)
		{
			return ((BigInteger)val).doubleValue();
		}
		else
		{
			return null;
		}
	}
	
	public BigDecimal parseBigDecimal(Object val)
	{
		if(val==null) return null;
		 if(val instanceof BigDecimal)
		{
			return (BigDecimal)val;
		}
		else if(isNumberType(val))
		{
			return new BigDecimal(val.toString());
		}
		
		else if(val instanceof BigInteger)
		{
			return new BigDecimal(((BigInteger)val).toString());
		}
		else
		{
			return null;
		}
	}
	
	public Date parseDate(Object val)
	{
		if(val==null) return null;
		if(val instanceof Date)
		{
			return (Date)val;
		}
		else if(val instanceof java.sql.Date)
		{
			return (Date)val;
		}
		else if(val instanceof java.sql.Timestamp)
		{
			return (Date)val;
		}
		else if(val instanceof String)
		{
			try {
				return DateUtils.parseDate(val+"", "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public Character parseChar(Object val)
	{
		if(val==null) return null;
		if(val instanceof String)
		{
			return ((String)val).charAt(0);
		}
		else
		{
			return val.toString().charAt(0);
		}
	}
	
	public boolean isNumberType(Object valueNotNull)
	{
		if(valueNotNull instanceof Integer)
		{
			return true;
		}
		else if(valueNotNull instanceof Long)
		{
			return true;
		}
		else if(valueNotNull instanceof Short)
		{
			return true;
		}
		else if(valueNotNull instanceof Byte)
		{
			return true;
		}
		else if(valueNotNull instanceof Float)
		{
			return true;
		}
		else if(valueNotNull instanceof Double)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Short parseShort(Object val)
	{
		if(val==null) return null;
		if(isNumberType(val))
		{
			return (Short)val;
		}
		else if(val instanceof BigDecimal)
		{
			return ((BigDecimal)val).shortValue();
		}
		else if(val instanceof BigInteger)
		{
			return ((BigInteger)val).shortValue();
		}
		else
		{
			return Short.parseShort(parseString(val));
		}
	}
	
	public Long parseLong(Object val)
	{
		if(val==null) return null;
		if(isNumberType(val))
		{
			return (Long)val;
		}
		else if(val instanceof BigDecimal)
		{
			return ((BigDecimal)val).longValue();
		}
		else if(val instanceof BigInteger)
		{
			return ((BigInteger)val).longValue();
		}
		else
		{
			return Long.parseLong(parseString(val));
		}
	}
}
