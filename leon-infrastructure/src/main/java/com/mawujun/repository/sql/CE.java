package com.mawujun.repository.sql;

import java.util.ArrayList;
import java.util.HashMap;

public class CE<E> extends SubSQL
{
	protected ArrayList<SE> ses=new ArrayList<SE>();
	protected ArrayList<SQLKeyword> logics=new ArrayList<SQLKeyword>();
	
	protected SQLKeyword getKeyword()
	{
		return SQLKeyword.SPACER;
	}
	
	
	@SuppressWarnings("unchecked")
	public E or(SE se)
	{
		ses.add(se);
		se.setParent(this);
		logics.add(SQLKeyword.OR);
		return (E)this;
	}
	
	@SuppressWarnings("unchecked")
	public E orIf(SE se)
	{
		if(se.isAllParamsEmpty(true)) return (E)this;
		return or(se);
	}
	
	@SuppressWarnings("unchecked")
	public E and(SE se)
	{
		ses.add(se);
		se.setParent(this);
		logics.add(SQLKeyword.AND);
		return (E)this;
	}
	
	@SuppressWarnings("unchecked")
	public E andIf(SE se)
	{
		if(se.isAllParamsEmpty(true)) return (E)this;
		return and(se);
	}

	public E and(String se,Object...ps)
	{
		return and(new SE(se,ps));
	}
	
	public E andIf(String se,Object...ps)
	{
		SE _se=new SE(se,ps);
		if(_se.isAllParamsEmpty(true)) return (E)this;
		return and(_se);
	}
	
	public E or(String se,Object...ps)
	{
		return or(new SE(se,ps));
	}
	
	public E orIf(String se,Object...ps)
	{
		SE _se=new SE(se,ps);
		if(_se.isAllParamsEmpty(true)) return (E)this;
		return or(new SE(se,ps));
	}

	
	public String getSQL() {
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		sql.append(this.getKeyword().toString()+SQLKeyword.SPACER);
		for(int i=0;i<ses.size();i++)
		{
			if(i==0)
			{
				sql.append(join(ses.get(i).getSQL()));
			}
			else
			{
				sql.append(join(logics.get(i).toString(),ses.get(i).getSQL()));
			}
		}
		return sql.toString();
	}

	
	public String getParamedSQL() {
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		sql.append(this.getKeyword().toString()+SQLKeyword.SPACER);
		for(int i=0;i<ses.size();i++)
		{
			if(i==0)
			{
				sql.append(join(ses.get(i).getParamedSQL()));
			}
			else
			{
				sql.append(join(logics.get(i).toString(),ses.get(i).getParamedSQL()));
			}
		}
		return sql.toString();
	}

	
	public Object[] getParams() {
		if(this.isEmpty()) return new Object[]{};
		ArrayList<Object> ps=new ArrayList<Object>();
		for(int i=0;i<ses.size();i++)
		{
			ps.addAll(Utils.toArrayList(ses.get(i).getParams()));
		}
		return ps.toArray(new Object[ps.size()]);
	}

	
	public String getParamNamedSQL() {
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		sql.append(this.getKeyword().toString()+SQLKeyword.SPACER);
		this.beginParamNameSQL();
		
		for(int i=0;i<ses.size();i++)
		{
			if(i==0)
			{
				sql.append(join(ses.get(i).getParamNamedSQL()));
			}
			else
			{
				sql.append(join(logics.get(i).toString(),ses.get(i).getParamNamedSQL()));
			}
		}
		
		this.endParamNameSQL();
		return sql.toString();
	}

	
	public HashMap<String, Object> getNamedParams() {
		HashMap<String, Object> ps=new HashMap<String, Object>();
		if(this.isEmpty()) return ps;
		this.beginParamNameSQL();
		for(int i=0;i<ses.size();i++)
		{
			ps.putAll(ses.get(i).getNamedParams());
		}
		this.endParamNameSQL();
		return ps;
	}

	
	public boolean isEmpty() {
		return ses.size()==0;
	}
	
	/**
	 * 默认为CE模式 CE=true
	 * */
	
	public boolean isAllParamsEmpty() {
		return isAllParamsEmpty(true);
	}
	
	
	public boolean isAllParamsEmpty(boolean isCE) {
		if(this.isEmpty()) return false;
		for(int i=0;i<ses.size();i++)
		{
			if(!ses.get(i).isAllParamsEmpty(isCE)) return false;
		}
		return true;
	}
	
	 
	
}
