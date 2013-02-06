package com.mawujun.repository.sql;

import java.util.ArrayList;
import java.util.HashMap;

public class Update extends DML implements ExecutableSQL {

	private ArrayList<SQL> values=new ArrayList<SQL>();
	private ArrayList<String> fields=new ArrayList<String>();
	private String table=null;
	private String tableAlias=null;
	private UpdateWhere where=new UpdateWhere();
	
	public static Update init()
	{
		return new Update();
	}

	public UpdateWhere where()
	{
		return this.where;
	}
	
	public Update()
	{
		this.where.setParent(this);
	}
	
	public Update update(String table,String alias)
	{
		this.table=table;
		this.tableAlias=alias;
		return this;
	}
	
	public Update update(String table)
	{
		this.table=table;
		this.tableAlias=null;
		return this;
	}
	
	public Update set(String fld,Object val)
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
	
	public Update setIf(String fld,Object val)
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
	
	
	public Update set(String fld,SQL se)
	{
		values.add(se);
		se.setParent(this);
		fields.add(fld);
		return this;
	}
	
	public Update setIf(String fld,SQL se)
	{
		if(se.isAllParamsEmpty()) return this;
		return set(fld,se);
	}
	
	public Update setSE(String fld,SE se)
	{
		return set(fld,se);
	}
	public Update setSEIf(String fld,SE se)
	{
		return setIf(fld,se);
	}
	
	public Update setSE(String fld,String se,Object... ps)
	{
		return set(fld,new SE(se,ps));
	}
	
	public Update setSEIf(String fld,String se,Object... ps)
	{
		return setIf(fld,new SE(se,ps));
	}
	
	
	
	
	public String getSQL() {
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		sql.append(SQLKeyword.UPDATE.toString()+SQLKeyword.SPACER.toString()+this.table);
		if(this.tableAlias!=null)
		{
			sql.append(SQLKeyword.SPACER.toString()+this.tableAlias+SQLKeyword.SPACER.toString());
		}
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.UPDATE$SET.toString()+SQLKeyword.SPACER.toString());
		for(int i=0;i<fields.size();i++)
		{
			sql.append(fields.get(i)+SQLKeyword.OP$EAUALS+values.get(i).getSQL());
			if(i<fields.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		if(!this.where().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+this.where().getSQL());
		}
		return sql.toString();
	}

	
	public String getParamedSQL() {
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		sql.append(SQLKeyword.UPDATE.toString()+SQLKeyword.SPACER.toString()+this.table);
		if(this.tableAlias!=null)
		{
			sql.append(SQLKeyword.SPACER.toString()+this.tableAlias+SQLKeyword.SPACER.toString());
		}
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.UPDATE$SET.toString()+SQLKeyword.SPACER.toString());
		for(int i=0;i<fields.size();i++)
		{
			sql.append(fields.get(i)+SQLKeyword.OP$EAUALS+values.get(i).getParamedSQL());
			if(i<fields.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		if(!this.where().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+this.where().getParamedSQL());
		}
		return sql.toString();
	}

	
	public Object[] getParams() {
		if(this.isEmpty()) return new Object[]{};
		ArrayList<Object> ps=new ArrayList<Object>();
		for(SQL val:values)
		{
			ps.addAll(Utils.toArrayList(val.getParams()));
		}
		if(!this.where().isEmpty())
		{
			ps.addAll(Utils.toArrayList(this.where().getParams()));
		}
		return ps.toArray(new Object[ps.size()]);
	}

	
	public String getParamNamedSQL() {
		if(this.isEmpty()) return "";
		this.beginParamNameSQL();
		StringBuffer sql=new StringBuffer();
		sql.append(SQLKeyword.UPDATE.toString()+SQLKeyword.SPACER.toString()+this.table);
		if(this.tableAlias!=null)
		{
			sql.append(SQLKeyword.SPACER.toString()+this.tableAlias+SQLKeyword.SPACER.toString());
		}
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.UPDATE$SET.toString()+SQLKeyword.SPACER.toString());
		for(int i=0;i<fields.size();i++)
		{
			sql.append(fields.get(i)+SQLKeyword.OP$EAUALS+values.get(i).getParamNamedSQL());
			if(i<fields.size()-1) sql.append(SQLKeyword.COMMA.toString());	
		}
		if(!this.where().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+this.where().getParamNamedSQL());
		}
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
		if(!this.where().isEmpty())
		{
			ps.putAll(this.where().getNamedParams());
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
		if(!this.where().isEmpty())
		{
			if(!this.where().isAllParamsEmpty()) return false;
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
