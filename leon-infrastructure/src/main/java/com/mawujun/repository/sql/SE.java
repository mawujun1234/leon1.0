package com.mawujun.repository.sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mawujun.repository.mybatis.Record;
 

 

/**
 * SimpleExpression ����д
 * */
public class SE extends SubSQL implements ExecutableSQL,QueryableSQL
{
	private static final String PARAM_NAME_SUFFIX_CHARS="(+-*/ ><=,)";
	
	private int paramIndex=-1;
	private ArrayList<Object> paramValues=new ArrayList<Object>();
	 
	private String originalSQL=null;
	private ArrayList<String> splitParts=new ArrayList<String>();
	private String lastSqlPart="";
	
	 
	
	
	private void init(String sql,Map<String,Object> map,Object... ps)
	{
		this.originalSQL=sql;
		analyse(this.originalSQL,map,ps);
		splitParts.add(this.lastSqlPart);
		for(Object val:paramValues)
		{
			if(val instanceof SQL)
			{
				SQL se=(SQL)val;
				se.setParent(this);
			}
		}
	}
	
	 
	
	public SE(String sql,Object... ps)
	{
		this.init(sql,new HashMap<String, Object>(), ps);
	}
	
	public SE(String sql,Map<String, Object> map,Object... ps)
	{
		this.init(sql,map, ps);
	}
	
	private void err(String msg)
	{
		(new Exception(msg)).printStackTrace();
	}
	
	 
	
	
	private void analyse(String sql,Map<String,Object> map,Object... ps)
	{
		sql=" "+sql+" ";
		char[] chars=sql.toCharArray();
		String part1="";
		String part2=sql;
		int i=-1;
		int matchCount=0;
		while(true)
		{
			
			i++;
			if(i>=chars.length) break;
			
			char c=chars[i];
			int z=jumpIf(sql,i);
			if(z==-1)
			{
				err("���"+sql+"���ڵ�"+i+"���ַ�,û���ҵ���֮��Ӧ�Ľ�β�ַ�,���ܴ����﷨����!");
				return;
			}
			else
			{
				if(z!=i)
				{
					i=z;
					continue;
				}
			}
			
			if(c=='?')
			{
				matchCount++;
				part1=part2.substring(0,i).trim();
				this.splitParts.add(part1);
				part2=part2.substring(i+1,part2.length()).trim();
				lastSqlPart=part2;
				paramIndex++;
				if(paramIndex>=ps.length)
				{
					err(part1+"? �����������");
					return;
				}
				this.paramValues.add(ps[paramIndex]);
				if(part2.length()>0)
				{
					analyse(part2,map,ps);
					return;
				}
			}
			else if(c==':')
			{
				matchCount++;
				part1=part2.substring(0,i).trim();
				this.splitParts.add(part1);
				int end=chars.length;
				for(int j=i+1;j<chars.length;j++)
				{
					char ic=chars[j];
					if(PARAM_NAME_SUFFIX_CHARS.indexOf(ic)>-1)
					{
						end=j;
						break;
					}
				}
				String pname=part2.substring(i+1,end).trim();
				 
				if(!map.containsKey(pname))
				{
					err(part2+":"+pname+" ��û��ָ������ֵ");
					return;
				}
				paramValues.add(map.get(pname));
				part2=part2.substring(end+1,part2.length()).trim();
				lastSqlPart=part2;
				if(part2.length()>0)
				{
					analyse(part2,map,ps);
					return;
				}
			}
			
			if(matchCount==0)
			{
				this.lastSqlPart=part2.trim();
			}
		}
	}
	
	
	private int jumpIf(String sql,int i) {
		String s1=null;
		if(i<sql.length()-1)
		{
			s1=sql.substring(i,i+1);
		}
		String s2=null;
		if(i<sql.length()-2)
		{
			s2=sql.substring(i,i+2);
		}
		
		if(s1!=null)
		{
			if(s1.equals(SQLKeyword.SINGLE_QUATE.toString()))
			{
				return jumpSingleQuateIf(sql,i);
			}
			else if(s1.equals(SQLKeyword.LEFT_DOUBLE_QUATE.toString()))
			{
				return jumpDoubleQuateIf(sql,i);
			}
		}
		if(s2!=null)
		{
			if(s2.equals(SQLKeyword.SINGLE_REMARK.toString()))
			{
				return jumpSingleLineRemarkIf(sql, i);
			}
			else if(s2.equals(SQLKeyword.LEFT_REMARK.toString()))
			{
				return jumpMulityLineRemarkIf(sql, i);
			}
		}
		return i;
	}



	private int jumpSingleQuateIf(String sql, int i) {
		boolean matched=false;
		while(true)
		{
			i++;
			if(i>=sql.length()) break;
			String c1=sql.substring(i,i+1);
			String c2=null;
			if(i+1<=sql.length()-1)
			{
				c2=sql.substring(i+1,i+2);
			}
			if(c1.equals(SQLKeyword.SINGLE_QUATE.toString()))
			{
				if(c2!=null)
				{
					if(c2.equals(SQLKeyword.SINGLE_QUATE.toString()))
					{
						i++;
					}
					else
					{
						matched=true;
						break;
					}
				}
				else
				{
					matched=true;
					break;
				}
			}
		}
		return matched?i:-1;
	}
	
	
	private int jumpDoubleQuateIf(String sql, int i) {
		boolean matched=false;
		while(true)
		{
			i++;
			if(i>=sql.length()) break;
			String c1=sql.substring(i,i+1);
			String c2=null;
			if(i+1<=sql.length()-1)
			{
				c2=sql.substring(i+1,i+2);
			}
			if(c1.equals(SQLKeyword.DOUBLE_QUATE.toString()))
			{
				if(c2!=null)
				{
					if(c2.equals(SQLKeyword.DOUBLE_QUATE.toString()))
					{
						i++;
					}
					else
					{
						matched=true;
						break;
					}
				}
				else
				{
					matched=true;
					break;
				}
			}
		}
		return matched?i:-1;
	}
	
	
	private int jumpMulityLineRemarkIf(String sql, int i) {
		boolean matched=false;
		while(true)
		{
			i++;
			if(i>=sql.length()-1) break;
			String c1=sql.substring(i,i+2);
			if(c1.equals(SQLKeyword.RIGHT_REMARK.toString()))
			{
				matched=true;
				break;
			}
		}
		return matched?i:-1;
	}
	
	private int jumpSingleLineRemarkIf(String sql, int i) {
		boolean matched=false;
		while(true)
		{
			i++;
			if(i>=sql.length()) break;
			String c1=sql.substring(i,i+1);
			if(c1.equals(SQLKeyword.LN.toString()))
			{
				matched=true;
				break;
			}
		}
		
		if(i==sql.length())  matched=true;
		
		return matched?i:-1;
	}



	public String getOriginalSQL()
	{
		return originalSQL;
	}
	
	 
	public String getSQL()
	{
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		for(int i=0;i<splitParts.size()-1;i++)
		{
			String part=splitParts.get(i);
			Object param=this.paramValues.get(i);
			if(param instanceof SQL)
			{
				SQL se=(SQL)param;
				sql.append(" "+part+se.getSQL()+" ");
			}
			else 
			{
				String val=Utils.castValue(param);
				sql.append(" "+part+val+" ");
			}
		}
		sql.append(this.splitParts.get(splitParts.size()-1));
		return sql.toString().trim();
	}
	
	public Object[] getParams()
	{
		if(isEmpty()) return new Object[]{};
		else
		{
			ArrayList<Object> list=new ArrayList<Object>();
			for(Object o:this.paramValues)
			{
				if(o==null) continue;
				
				if(o instanceof SQL)
				{
					SQL se=(SQL)o;
					list.addAll(Utils.toArrayList(se.getParams()));
				}
				else
				{
					list.add(o);
				}
			}
			return list.toArray();
		}
	}
	
	public String getParamedSQL()
	{
		if(this.isEmpty()) return "";
		StringBuffer sql=new StringBuffer();
		for(int i=0;i<splitParts.size()-1;i++)
		{
			String part=splitParts.get(i);
			Object param=this.paramValues.get(i);
			if(param!=null)
			{
				if(param instanceof SE)
				{
					SE se=(SE)param;
					sql.append(" "+part+se.getParamedSQL()+" ");
				}
				else
				{
					sql.append(part+" ? ");
				}
			}
			else
			{
				sql.append(part+" null ");
			}
		}
		sql.append(this.splitParts.get(splitParts.size()-1));
		return sql.toString().trim();
	}
	
	public String getParamNamedSQL()
	{
		if(this.isEmpty()) return "";
		this.beginParamNameSQL();
		StringBuffer sql=new StringBuffer();
		for(int i=0;i<splitParts.size()-1;i++)
		{
			String part=splitParts.get(i);
			Object param=this.paramValues.get(i);
			if(param!=null)
			{
				if(param instanceof SE)
				{
					SE se=(SE)param;
					sql.append(" "+part+se.getParamNamedSQL()+" ");
				}
				else
				{
					sql.append(part+" "+this.getNextParamName(true)+" ");
				}
			}
			else
			{
				sql.append(part+" null ");
			}
		}
		sql.append(this.splitParts.get(splitParts.size()-1));
		this.endParamNameSQL();
		return sql.toString().trim();
	}
	
	public HashMap<String, Object> getNamedParams()
	{
		HashMap<String, Object> ps=new HashMap<String, Object>();
		if(isEmpty()) return ps;
 		this.beginParamNameSQL();
		for(Object o:this.paramValues)
		{
			if(o==null) continue;
			if(o instanceof SQL)
			{
				HashMap<String, Object> map=((SQL) o).getNamedParams();
				ps.putAll(map);
			}
			else
			{
				ps.put(this.getNextParamName(false), o);
			}
		}
		this.endParamNameSQL();
		return ps;
	}
	
	public boolean isEmpty()
	{
		return this.originalSQL==null || this.originalSQL.length()==0;
	}

	public boolean isAllParamsEmpty(boolean isCE)
	{
		if(this.isEmpty()) return false;
		
		for(int i=0;i<this.paramValues.size();i++)
		{
			Object o=this.paramValues.get(i);
			if(o instanceof SQL)
			{
				SQL se=(SQL)o;
				if(!se.isAllParamsEmpty(isCE)) return false;
			}
			else
			{
				if(o!=null)
				{
					if( isCE && (o instanceof String) )
					{
						String str=(String)o;
						String sql=splitParts.get(i).trim().toUpperCase();
						if(sql.indexOf("LIKE")>-1) //�˴���������Ϊ���ӵ��ж� 
						{
							str=str.replace("%","");
							str=str.replace("_","");
						}
						if(!str.equals("") && !str.equals("null")) return false; 
					}
					else
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	 
	public SE append(String se,Object... ps)
	{
		return append(new SE(se,ps));
	}
	
	public SE append(SE... ses)
	{
		StringBuffer sql=new StringBuffer();
		ArrayList<Object> ps=new ArrayList<Object>();
		
		sql.append(this.getParamedSQL());
		ps.addAll(Utils.toArrayList(this.getParams()));
		
		for(SE se:ses)
		{
		  sql.append(SQLKeyword.SPACER.toString()+se.getParamedSQL());
		  ps.addAll(Utils.toArrayList(se.getParams()));
		}
		return new SE(sql.toString(),ps.toArray(new Object[ps.size()]));
	}
	
	public SE appendIf(SE... ses)
	{
		StringBuffer sql=new StringBuffer();
		ArrayList<Object> ps=new ArrayList<Object>();
		
		sql.append(this.getParamedSQL());
		ps.addAll(Utils.toArrayList(this.getParams()));
		
		for(SE se:ses)
		{
			if(se.isAllParamsEmpty()) continue;
			sql.append(SQLKeyword.SPACER.toString()+se.getParamedSQL());
			ps.addAll(Utils.toArrayList(se.getParams()));
		}
		return new SE(sql.toString(),ps.toArray(new Object[ps.size()]));
	}
	
	
	
	private DAO dao=new DAO();
 
//	public RecordSet query() 
//	{
//		return dao.query(this);
//	}
//	
	public RcdSet quiz() 
	{
		return dao.quiz(this);
	}
	
//	public RecordSet query(RecordSet ds) 
//	{
//		return dao.query(this,ds);
//	}
 
	public Record record() 
	{
		return null;//dao.uniqueRecord(this);
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
	
	public Integer execute()
	{
		return dao.execute(this);
	}
	
	public static void main(String[] args) {
		
		
		/*SE se1=new SE("'?");
		System.out.println(se1.getSQL());
		SE se=new SE("drp_xiaos_huoh '?' to_date(sysdate,'hh:mi:ss'))");
		System.out.println(se.getSQL());*/
		
		SE se2=new SE("select --asd? :asd\n *  from /*sd ? : */ dual --AS?A:SD");
		System.out.println(se2.getSQL());
	}



	 
	
}
