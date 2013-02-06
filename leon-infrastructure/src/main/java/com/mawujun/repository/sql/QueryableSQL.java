package com.mawujun.repository.sql;

import java.math.BigDecimal;
import java.util.Date;

import com.mawujun.repository.mybatis.Record;

public interface QueryableSQL 
{
	
	//public RecordSet query() ;
	
	public RcdSet quiz() ;

	public Record record() ;
	
	public Rcd rcd() ;
	
	public Integer intValue() ;
	
	public String stringValue() ;
	
	public Long longValue() ;
	
	public Date dateValue() ;
	
	public BigDecimal decimalValue() ;

	
}
