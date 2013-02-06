package com.mawujun.repository.sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.mawujun.repository.mybatis.Record;

public class Select extends DML implements QueryableSQL
{
	 

	private ArrayList<SQL> tables =new ArrayList<SQL>();
	private ArrayList<String> tableAliases=new ArrayList<String>();
	
	
	private ArrayList<SQL> fields=new ArrayList<SQL>();
	private ArrayList<String> fieldsAliases=new ArrayList<String>();
	
	private ArrayList<String> fieldsPrefix=new ArrayList<String>();
	private String currentFieldPrefix=null;
	
	
	
	private SelectWhere where=new SelectWhere();
	private OrderBy orderBy=new OrderBy();
	private GroupBy groupBy=new GroupBy();
	
	 
	public SelectWhere where()
	{
		return where;
	}
	
	public GroupBy groupBy()
	{
		return groupBy;
	}
	
	public OrderBy orderBy()
	{
		return orderBy;
	}
	
	public static Select init()
	{
		return new Select();
	}
	
	 
	
	
	public Select()
	{
		this.where.setParent(this);
		this.orderBy.setParent(this);
		this.groupBy.setParent(this);
	}

	public Select from(SE table,String alias)
	{
		this.tables.add(table);
		table.setParent(this);
		this.tableAliases.add(alias);
		currentFieldPrefix=null;
		return this;
	}
	
	public Select from(String table,String alias,Object... ps)
	{
		currentFieldPrefix=null;
		return from(new SE(table,ps),alias);
	}
	
	public Select from(Select table,String alias)
	{
		this.tables.add(table);
		table.setParent(this);
		this.tableAliases.add(alias);
		currentFieldPrefix=null;
		return this;
	}
	
	public Select from(String table)
	{
		currentFieldPrefix=null;
		return from(new SE(table),null);
	}
	
	public Select froms(String... table)
	{
		for(String tab:table)
		{
			from(new SE(tab),null);
		}
		currentFieldPrefix=null;
		return this;
	}
	
	public Select fromAs(String... tableOrAlias)
	{
		String[] tabs;
		if(tableOrAlias.length%2==1)
		{
			tabs=new String[tableOrAlias.length+1];
			for(int i=0;i<tableOrAlias.length;i++) tabs[i]=tableOrAlias[i];
			tabs[tabs.length-1]=null;
		}
		else tabs=tableOrAlias;
		
		for(int i=0;i<tabs.length;i++)
		{
			String table=tabs[i];
			i++;
			String alias=tabs[i];
			
			from(new SE(table),alias); 
		}
		currentFieldPrefix=null;
		return this;
	}
	
	public Select prefix()
	{
		int last=tableAliases.size()-1;
		String pfx=tableAliases.get(last);
		if(pfx==null || pfx.length()==0)
			pfx=tables.get(last).getSQL();
		return prefix(pfx);
	}
	
	public Select prefix(String pfx)
	{
		currentFieldPrefix=pfx;
		return this;
	}
	
	public Select select(SE se,String alias)
	{
		this.fields.add(se);
		se.setParent(this);
		this.fieldsAliases.add(alias);
		this.fieldsPrefix.add(currentFieldPrefix);
		return this;
	}
	
	public Select select(Select se,String alias)
	{
		this.fields.add(se);
		se.setParent(this);
		this.fieldsAliases.add(alias);
		this.fieldsPrefix.add(currentFieldPrefix);
		return this;
	}
	
	public Select select(String fld,Object... ps)
	{
		 return this.select(new SE(fld,ps), "");
	}

	public Select select(String fld,String alias,Object... ps)
	{
		 return this.select(new SE(fld,ps), alias);
	}
	public Select selects(SE... se)
	{
		for(SE s:se)
		{
			this.select(s, null);
		}
		return this;
	}
	
	public Select selects(String... fld)
	{
		 for(String s:fld)
		 {
			 this.select(s,null);
		 }
		 return this;
	}

	public Select selects(Select... se)
	{
		for(Select s:se)
		{
			this.select(s,null);
		}
		return this;
	}
	
	public Select selectAs(String... fldOrAlias)
	{
		String[] fields;
		if(fldOrAlias.length%2==1)
		{
			fields=new String[fldOrAlias.length+1];
			for(int i=0;i<fldOrAlias.length;i++) fields[i]=fldOrAlias[i];
			fields[fields.length-1]=null;
		}
		else fields=fldOrAlias;
		
		for(int i=0;i<fields.length;i++)
		{
			String field=fields[i];
			i++;
			String alias=fields[i];
			this.select(field,alias);
		}
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
	public String getSQL() {
		StringBuffer sql=new StringBuffer();
		if(this.isEmpty()) return "";
		sql.append(SQLKeyword.SELECT.toString()+SQLKeyword.SPACER.toString());
		if(this.fields.size()==0) sql.append(SQLKeyword.SELECT_STAR.toString()+SQLKeyword.SPACER);
		else
		{
			for(int i=0;i<this.fields.size();i++)
			{
				sql.append(join((this.fieldsPrefix.get(i)==null?"":this.fieldsPrefix.get(i)+SQLKeyword.DOT.toString())+this.fields.get(i).getSQL(),(this.fieldsAliases.get(i)==null?"":this.fieldsAliases.get(i))));
				if(i<this.fields.size()-1) sql.append(SQLKeyword.COMMA);
			}
		}
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.FROM+SQLKeyword.SPACER);
		for(int i=0;i<this.tables.size();i++)
		{
			String sub=this.tables.get(i).getSQL();
			sql.append(join(sub,(this.tableAliases.get(i)==null?"":this.tableAliases.get(i))));
			if(i<this.tables.size()-1) sql.append(SQLKeyword.COMMA);
		}
		if(!this.where().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+where().getSQL());
		}
		
		if(!this.groupBy().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+groupBy().getSQL());
		}
		if(!this.orderBy().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+orderBy().getSQL());
		}
		return sql.toString();
	}

	
	public String getParamedSQL() {
		StringBuffer sql=new StringBuffer();
		if(this.isEmpty()) return "";
		sql.append(SQLKeyword.SELECT.toString()+SQLKeyword.SPACER.toString());
		if(this.fields.size()==0) sql.append(SQLKeyword.SELECT_STAR.toString()+SQLKeyword.SPACER);
		else
		{
			for(int i=0;i<this.fields.size();i++)
			{
				sql.append(join((this.fieldsPrefix.get(i)==null?"":this.fieldsPrefix.get(i)+SQLKeyword.DOT.toString())+this.fields.get(i).getParamedSQL(),(this.fieldsAliases.get(i)==null?"":this.fieldsAliases.get(i))));
				if(i<this.fields.size()-1) sql.append(SQLKeyword.COMMA);
			}
		}
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.FROM+SQLKeyword.SPACER);
		for(int i=0;i<this.tables.size();i++)
		{
			String sub=this.tables.get(i).getParamedSQL();
			sql.append(join(sub,(this.tableAliases.get(i)==null?"":this.tableAliases.get(i))));
			if(i<this.tables.size()-1) sql.append(SQLKeyword.COMMA);
		}
		if(!this.where().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+where().getParamedSQL());
		}
		if(!this.groupBy().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+groupBy().getParamedSQL());
		}
		if(!this.orderBy().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+orderBy().getParamedSQL());
		}
		return sql.toString();
	}

	
	public Object[] getParams() {
		if(this.isEmpty()) return new Object[]{};
		ArrayList<Object> ps=new ArrayList<Object>();
		for(int i=0;i<this.fields.size();i++)
		{
			ps.addAll(Utils.toArrayList(this.fields.get(i).getParams()));
		}
		for(int i=0;i<this.tables.size();i++)
		{
			ps.addAll(Utils.toArrayList(this.tables.get(i).getParams()));
		}
		if(!this.where().isEmpty())
		{
			ps.addAll(Utils.toArrayList(this.where().getParams()));
		}
		if(!this.groupBy().isEmpty())
		{
			ps.addAll(Utils.toArrayList(this.groupBy().getParams()));
		}
		if(!this.orderBy().isEmpty())
		{
			ps.addAll(Utils.toArrayList(this.orderBy().getParams()));
		}
		return ps.toArray(new Object[ps.size()]);
	}

	
	public String getParamNamedSQL() {
		StringBuffer sql=new StringBuffer();
		if(this.isEmpty()) return "";
		this.beginParamNameSQL();
		sql.append(SQLKeyword.SELECT.toString()+SQLKeyword.SPACER.toString());
		if(this.fields.size()==0) sql.append(SQLKeyword.SELECT_STAR.toString()+SQLKeyword.SPACER);
		else
		{
			for(int i=0;i<this.fields.size();i++)
			{
				sql.append(join((this.fieldsPrefix.get(i)==null?"":this.fieldsPrefix.get(i)+SQLKeyword.DOT.toString())+this.fields.get(i).getParamNamedSQL(),(this.fieldsAliases.get(i)==null?"":this.fieldsAliases.get(i))));
				if(i<this.fields.size()-1) sql.append(SQLKeyword.COMMA);
			}
		}
		sql.append(SQLKeyword.SPACER.toString()+SQLKeyword.FROM+SQLKeyword.SPACER);
		for(int i=0;i<this.tables.size();i++)
		{
			String sub=this.tables.get(i).getParamNamedSQL();
			sql.append(join(sub,(this.tableAliases.get(i)==null?"":this.tableAliases.get(i))));
			if(i<this.tables.size()-1) sql.append(SQLKeyword.COMMA);
		}
		if(!this.where().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+where().getParamNamedSQL());
		}
		if(!this.groupBy().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+groupBy().getParamNamedSQL());
		}
		if(!this.orderBy().isEmpty())
		{
			sql.append(SQLKeyword.SPACER.toString()+orderBy().getParamNamedSQL());
		}
		this.endParamNameSQL();
		return sql.toString();
	}

	
	public HashMap<String, Object> getNamedParams() {
		HashMap<String, Object> ps=new HashMap<String, Object>();
		if(this.isEmpty()) return ps;
		this.beginParamNameSQL();
		for(int i=0;i<this.fields.size();i++)
		{
			ps.putAll(this.fields.get(i).getNamedParams());
		}
		for(int i=0;i<this.tables.size();i++)
		{
			ps.putAll(this.tables.get(i).getNamedParams());
		}
		if(!this.where().isEmpty())
		{
			ps.putAll(this.where().getNamedParams());
		}
		if(!this.groupBy().isEmpty())
		{
			ps.putAll(this.groupBy().getNamedParams());
		}
		if(!this.orderBy().isEmpty())
		{
			ps.putAll(this.orderBy().getNamedParams());
		}
		this.endParamNameSQL();
		return ps;
	}

	
	public boolean isEmpty() {
		return this.tables.size()==0;
	}

	
	public boolean isAllParamsEmpty() {
		if(this.isEmpty()) return false;
		 
		for(int i=0;i<this.fields.size();i++)
		{
			if(!this.fields.get(i).isAllParamsEmpty()) return false;
		}
		for(int i=0;i<this.tables.size();i++)
		{
			if(!this.tables.get(i).isAllParamsEmpty()) return false;
		}
		if(!this.where().isEmpty())
		{
			if(!this.where().isAllParamsEmpty()) return false;
		}
		if(!this.groupBy().isEmpty())
		{
			if(!this.groupBy().isAllParamsEmpty()) return false;
		}
		if(!this.orderBy().isEmpty())
		{
			if(!this.orderBy().isAllParamsEmpty()) return false;
		}
		return true;
	}
	
	
	public boolean isAllParamsEmpty(boolean isCE)
	{
		return isAllParamsEmpty();
	}
	
	
	
	private DAO dao= new DAO();
	

//	public RecordSet query() 
//	{
//		return dao.query(this);
//	}

	public RcdSet quiz() 
	{
		return dao.quiz(this);
	}

	public Record record() 
	{
		return dao.uniqueRecord(this);
	}
	
	public Rcd rcd() 
	{
		return dao.uniqueRcd(this);
	}
	
	 

	
	public Integer intValue() 
	{
		return dao.uniqueInteger(this);
	}

	
	public String stringValue() 
	{
		return dao.uniqueString(this);
	}

	
	public Long longValue() 
	{
		return dao.uniqueLong(this);
	}

	public Date dateValue() 
	{
		return dao.uniqueDate(this);
	}
	
	public BigDecimal decimalValue() 
	{
		return dao.uniqueDecimal(this);
	}
	

}
