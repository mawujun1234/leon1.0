package com.mawujun.repository.cnd;

import java.util.ArrayList;
import java.util.List;

public class SelectItems  implements SqlExpression{

	private List<String> names=new ArrayList<String>();
	
	private boolean isDistinct=false;
	private boolean isCount=false;


	public List<String> getNames() {
		return names;
	}

	public void addNames(String... names) {
		if(names!=null && names.length>0){
			for(String name:names){
				this.names.add(name);
			}
		}
	}
	@Override
	public void joinHql(StringBuilder sb) {
		if (!names.isEmpty()) {
			sb.append(" select ");
			if(isCount){
				sb.append("count(");
			}
			if(this.isDistinct){
				sb.append("distinct ");
			}
			//sb.append(" new map(");
			for (String obi : names) {
				//sb.append(obi+" as "+obi+",");
				sb.append(obi+",");
			}
			sb.setCharAt(sb.length() - 1, ' ');
			//sb.append(") "); 
			if(isCount){
				sb.append(") ");
			}
		} else {
			if(isCount){
				sb.append(" select count(*) ");
			} 
//			else { 这块以后要改，联合Repository
//				
//				if(this.isDistinct){
//					sb.append(" select distinct * ");
//				} else {
//					sb.append(" select * ");
//				}
//			}
		}
			
	}

	public boolean isDistinct() {
		return isDistinct;
	}

	public void setDistinct(boolean isDistinct) {
		this.isDistinct = isDistinct;
	}

	@Override
	public int joinParams(Object obj,
			Object[] params, int off) {
		// TODO Auto-generated method stub
		return off;
	}

	@Override
	public int paramCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SqlExpression setNot(boolean not) {
		// TODO Auto-generated method stub
		return this;
	}

	public boolean isCount() {
		return isCount;
	}

	public void setCount(boolean isCount) {
		this.isCount = isCount;
	}

}
