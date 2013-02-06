package com.mawujun.repository.sql;

import java.util.ArrayList;
import java.util.HashMap;

public class Insert extends DML implements ExecutableSQL  {

	private ArrayList<SQL> values=new ArrayList<SQL>();
	private ArrayList<String> fields=new ArrayList<String>();
	private String table=null;
	
	public Insert()
	{}
	
	public Insert(String table)
	{
		this.table=table;
	}

	public Insert into(String table)
	{
		this.table=table;
		return this;
	}
	
	public Insert set(String fld,Object val)
	{
		if(val instanceof SQL)
		{
			set(fld,(SQL)val);
		}
		else
		{
			set(fld,new SE(SQLKeyword.QUESTION.toString(),val));
		}
		return this;
	}
	
	public Insert setIf(String fld,Object val)
	{
		if(val instanceof SQL)
		{
			setIf(fld,(SQL)val);
		}
		else
		{
			setIf(fld,new SE(SQLKeyword.QUESTION.toString(),val));
		}
		return this;
	}
	
	public Insert set(String fld,SQL se)
	{
		values.add(se);
		se.setParent(this);
		fields.add(fld);
		return this;
	}
	
	public Insert setIf(String fld,SQL se)
	{
		if(se.isAllParamsEmpty()) return this;
		return set(fld,se);
	}
	
	public Insert setSE(String fld,SE se)
	{
		return set(fld,se);
	}
	
	public Insert setSEIf(String fld,SE se)
	{
		if(se.isAllParamsEmpty()) return this;
		return setSE(fld, se);
	}
	
	public Insert setSE(String fld,String se,Object... ps)
	{
		return set(fld,new SE(se,ps));
	}
	
	public Insert setSEIf(String fld,String se,Object... ps)
	{
		return setIf(fld,new SE(se,ps));
	}
	
	
	
	
	
	
	
	
	public String getSQL() {
		
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		sql.append(SQLKeyword.INSERT.toString()+SQLKeyword.SPACER.toString()+SQLKeyword.INSERT$INTO);
		sql.append(SQLKeyword.SPACER+this.table+SQLKeyword.SPACER.toString()+SQLKeyword.LEFT_BRACKET.toString());
		for(int i=0;i<fields.size();i++)
		{
			sql.append(fields.get(i));
			if(i<fields.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		sql.append(SQLKeyword.RIGHT_BRACKET.toString());
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.VALUES.toString()+SQLKeyword.LEFT_BRACKET);
		for(int i=0;i<values.size();i++)
		{
			sql.append(values.get(i).getSQL());
			if(i<values.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		sql.append(SQLKeyword.RIGHT_BRACKET.toString());
		return sql.toString();
		
	}

	
	public String getParamedSQL() {
		
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		sql.append(SQLKeyword.INSERT.toString()+SQLKeyword.SPACER.toString()+SQLKeyword.INSERT$INTO);
		sql.append(SQLKeyword.SPACER+this.table+SQLKeyword.SPACER.toString()+SQLKeyword.LEFT_BRACKET.toString());
		for(int i=0;i<fields.size();i++)
		{
			sql.append(fields.get(i));
			if(i<fields.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		sql.append(SQLKeyword.RIGHT_BRACKET.toString());
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.VALUES.toString()+SQLKeyword.LEFT_BRACKET);
		for(int i=0;i<values.size();i++)
		{
			sql.append(values.get(i).getParamedSQL());
			if(i<values.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		sql.append(SQLKeyword.RIGHT_BRACKET.toString());
		return sql.toString();
		
	}

	
	public Object[] getParams() {
		if(this.isEmpty()) return new Object[]{};
		ArrayList<Object> ps=new ArrayList<Object>();
		for(SQL val:values)
		{
			ps.addAll(Utils.toArrayList(val.getParams()));
		}
		return ps.toArray(new Object[ps.size()]);
	}

	
	public String getParamNamedSQL() {
		if(this.isEmpty()) return "";
		this.beginParamNameSQL();
		StringBuffer sql=new StringBuffer();
		sql.append(SQLKeyword.INSERT.toString()+SQLKeyword.SPACER.toString()+SQLKeyword.INSERT$INTO);
		sql.append(SQLKeyword.SPACER+this.table+SQLKeyword.SPACER.toString()+SQLKeyword.LEFT_BRACKET.toString());
		for(int i=0;i<fields.size();i++)
		{
			sql.append(fields.get(i));
			if(i<fields.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		sql.append(SQLKeyword.RIGHT_BRACKET.toString());
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.VALUES.toString()+SQLKeyword.LEFT_BRACKET);
		for(int i=0;i<values.size();i++)
		{
			sql.append(values.get(i).getParamNamedSQL());
			if(i<values.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		sql.append(SQLKeyword.RIGHT_BRACKET.toString());
		this.endParamNameSQL();
		return sql.toString();
	}

	
	public HashMap<String, Object> getNamedParams() {
		
		HashMap<String, Object> ps=new HashMap<String, Object>();
		if(this.isEmpty()) return ps;
		this.beginParamNameSQL();
		for(SQL val:values)
		{
			ps.putAll(val.getNamedParams());
		}
		this.endParamNameSQL();
		return ps;
	}

	
	public boolean isEmpty() {
		return fields.size()==0;
	}

	
	public boolean isAllParamsEmpty() {
		if(this.isEmpty()) return false;
		for(SQL val:values)
		{
			if(!val.isAllParamsEmpty()) return false;
		}
		return true;
	}
	
	
	public boolean isAllParamsEmpty(boolean isCE)
	{
		return isAllParamsEmpty();
	}
	
	
	private DAO dao=new DAO();
	
 
	public Integer execute()
	{
		return dao.execute(this);
	}

}
