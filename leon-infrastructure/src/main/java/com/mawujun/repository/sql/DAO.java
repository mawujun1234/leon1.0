package com.mawujun.repository.sql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mawujun.repository.mybatis.DataParser;
import com.mawujun.repository.mybatis.Record;

public class DAO {

	private DataParser dp=new DataParser();
	
	public Connection getConnection() 
	{
		try {
			return TxUnit.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private NamedStatement createNamedStatement(String sql)
	{
		Connection db=getConnection();
		NamedStatement st=null;
		try {
			st = new NamedStatement(db);
			st.prepare(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	private void setNamedParameters(NamedStatement st,
			HashMap<String, Object> ps) throws SQLException {
		for(String fld:ps.keySet())
		{
			Object val=ps.get(fld);
			if(val==null)
			{
				throw new SQLException(fld +" = null is not allowed !");
			}
			if(val instanceof String)
			{
				st.setString(fld, (String)val);
			}
			else if(val instanceof Character)
			{
				st.setString(fld, ((Character)val)+"");
			}
			else if(val instanceof Byte)
			{
				st.setByte(fld, (Byte)val);
			}
			else if(val instanceof Short)
			{
				st.setShort(fld, (Short)val);
			}
			else if(val instanceof Integer)
			{
				st.setInt(fld, (Integer)val);
			}
			else if(val instanceof Long)
			{
				st.setLong(fld, (Long)val);
			}
			else if(val instanceof Float)
			{
				st.setFloat(fld, (Float)val);
			}
			else if(val instanceof Double)
			{
				st.setDouble(fld, (Double)val);
			}
			else if(val instanceof BigDecimal)
			{
				st.setBigDecimal(fld, (BigDecimal)val);
			}
			else if(val instanceof java.sql.Date)
			{
				st.setDate(fld, (java.sql.Date)val);
			}
			else if(val instanceof java.util.Date)
			{
				st.setDate(fld, (java.sql.Date)val);
			}
			else
			{
				throw new SQLException("不支持的值类型！"+val.getClass().getName());
			}
		}
	}
	
	private PreparedStatement createPreparedStatement(String sql)
	{
		Connection db=getConnection();
		PreparedStatement st=null;
		try {
			st = db.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}
	
	private void setPreparedParameters(PreparedStatement st,	Object... ps) throws SQLException {
		int fld=0;
		for(Object val:ps)
		{
			fld++;
			if(val==null)
			{
				throw new SQLException(fld +" = null is not allowed !");
			}
			if(val instanceof String)
			{
				st.setString(fld, (String)val);
			}
			else if(val instanceof Character)
			{
				st.setString(fld, ((Character)val)+"");
			}
			else if(val instanceof Byte)
			{
				st.setByte(fld, (Byte)val);
			}
			else if(val instanceof Short)
			{
				st.setShort(fld, (Short)val);
			}
			else if(val instanceof Integer)
			{
				st.setInt(fld, (Integer)val);
			}
			else if(val instanceof Long)
			{
				st.setLong(fld, (Long)val);
			}
			else if(val instanceof Float)
			{
				st.setFloat(fld, (Float)val);
			}
			else if(val instanceof Double)
			{
				st.setDouble(fld, (Double)val);
			}
			else if(val instanceof BigDecimal)
			{
				st.setBigDecimal(fld, (BigDecimal)val);
			}
			else if(val instanceof java.sql.Date)
			{
				st.setDate(fld, (java.sql.Date)val);
			}
			else if(val instanceof java.util.Date)
			{
				st.setDate(fld, (java.sql.Date)val);
			}
			else
			{
				throw new SQLException("不支持的值类型！"+val.getClass().getName());
			}
			
		}
	}
	
	private void close(PreparedStatement pst, NamedStatement nst,ResultSet rs)
	{
		if(rs!=null)
		try {
			rs.close();
		} catch (SQLException e) {
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(pst!=null)
		try {
			pst.close();
		} catch (SQLException e) {
			try {
				pst.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(nst!=null)
		try {
			nst.close();
		} catch (SQLException e) {
			try {
				nst.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	
	/**
	 * 查询
	 * */
	public RcdSet quiz(SQL sql) {
		NamedStatement st=createNamedStatement(sql.getParamNamedSQL());
		RcdSet set=null;
		ResultSet rs=null;
		try {
			setNamedParameters(st, sql.getNamedParams());
			 rs= st.executeQuery();
			set=new RcdSet(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(null, st, rs);
		return set;
	}
	
	public RcdSet quiz(String sql,Object... ps) {
		return quiz(new SE(sql,ps));
	}
	
	/**
	 * 查询
	 * */
	public RecordSet query(SQL sql,RecordSet ds) {
		if(ds==null) ds = new RecordSet();
		NamedStatement st=createNamedStatement(sql.getParamNamedSQL());
		ResultSet rs=null;
		try {
			setNamedParameters(st, sql.getNamedParams());
			rs = st.executeQuery();
			RecordSetHelper.loadFromResultSet(rs, ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		close(null, st, rs);
		return ds;
	}
	
	public RecordSet query(SQL sql)  {
		return query(sql, null);
	}
	/**
	 * 查询
	 * */
	public RecordSet query(String sql,HashMap<String,Object> ps,RecordSet ds)  {
		if(ds==null)  ds = new RecordSet();
		NamedStatement st=createNamedStatement(sql);
		ResultSet rs=null;
		try {
			setNamedParameters(st,ps);
			rs = st.executeQuery();
			RecordSetHelper.loadFromResultSet(rs, ds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(null, st, rs);
		return ds;
	}
	
	public RecordSet query(String sql,HashMap<String,Object> ps)  {
			return query(sql, ps, null);
	}
		
	/**
	 * 查询
	 * */
	public RecordSet query(String sql,RecordSet ds,Object... ps) {
		SE se=new SE(sql,ps);
		return query(se,ds);
	}
	
	/**
	 * 查询
	 * */
	public RecordSet query(String sql,Object... ps)  {
		SE se=new SE(sql,ps);
		return query(se,null);
	}


	public Record uniqueRecord(SQL sql)  {
		 RecordSet set=query(sql);
		 if(set.recordCount()>0)
		 {
			 return set.getRecord(0);
		 }
		 else
		 {
			 return null;
		 }
	}
	
	public Object uniqueValue(SQL sql)  {
		Record r=uniqueRecord(sql);
		if(r==null)
		{
			return null;
		}
		return r.getField(0).getAsObject();
	}

	public Integer uniqueInteger(SQL sql)  {
		 Object val=uniqueValue(sql);
		 if(val==null) return null;
		 else return dp.parseInteger(val);
	}

	public String uniqueString(String sql,Object... ps)  {
		return uniqueString(new SE(sql,ps));
	}
	public String uniqueString(SQL sql)  {
		 Object val=uniqueValue(sql);
		 if(val==null) return null;
		 else return dp.parseString(val);
	}

	public Long uniqueLong(SQL sql)  {
		 Object val=uniqueValue(sql);
		 if(val==null) return null;
		 else return dp.parseLong(val);
	}

	public Date uniqueDate(SQL sql)  {
		 Object val=uniqueValue(sql);
		 if(val==null) return null;
		 else return dp.parseDate(val);
	}

	public BigDecimal uniqueDecimal(SQL sql)  {
		 Object val=uniqueValue(sql);
		 if(val==null) return null;
		 else return dp.parseBigDecimal(val);
	}

	/**
	 * 执行SQL语句
	 * */
	public Integer execute(SQL sql)  {
		NamedStatement st=createNamedStatement(sql.getParamNamedSQL());
		int i =-1;
		try {
			setNamedParameters(st, sql.getNamedParams());
			i= st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(null, st, null);
		return i;
	}
	
	
	/**
	 * 执行SQL语句
	 * */
	public Integer execute(String sql,HashMap<String, Object> ps)  {
		NamedStatement st=createNamedStatement(sql);
		int i=0;
		try {
			setNamedParameters(st, ps);
			i = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(null, st, null);
		return i;
	}
	
	/**
	 * 执行SQL语句
	 * */
	public Integer execute(String sql,Object... ps) {
		return execute(new SE(sql,ps));
	}
	
	/**
	 * 使用命名参数的SQL进行批量执行，效率略低与executeBatch
	 * */
	public int[] executeBatchAsNamedSQL(String namedsql,ArrayList<Map<String,Object>> pslist)  {
		if(pslist.size()==0) return new int[0];
		SE se=new SE(namedsql,pslist.get(0));
		PreparedStatement st=createPreparedStatement(se.getParamedSQL());
		int[] ret=null;
		try {
			for(Map<String,Object> ps : pslist)
			{
				SE se0=new SE(namedsql,ps);
				setPreparedParameters(st, se0.getParams());
				st.addBatch();
			}
			ret = st.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close( st,null,null);
		return ret;
	}
	
	
	/**
	 * 批量执行
	 * */
	public int[] executeBatchAsUnnamedSQL(String sql,ArrayList<Object[]> pslist)  {
			return executeBatch(sql,pslist);
	}
	
	/**
	 * 批量执行
	 * */
	public int[] executeBatch(String sql,ArrayList<Object[]> pslist)  {
		if(pslist.size()==0) return new int[0];
		PreparedStatement st=createPreparedStatement(sql);
		int[] ret=null;
		try {
			for(Object[] ps : pslist)
			{
				setPreparedParameters(st,ps);
				st.addBatch();
			}
			ret = st.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close( st,null,null);
		return ret;
	}

	public Record uniqueRecord(String sql,Object... ps)  {
		return uniqueRecord(new SE(sql,ps));
	}

	public Rcd uniqueRcd(SQL se) { 
		return quiz(se).get(0);
	}
 
}
