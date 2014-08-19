package com.mawujun.repository.cnd;

import java.util.ArrayList;
import java.util.List;

public class SelectItems  implements SqlExpression{

	private List<String> names=new ArrayList<String>();
	
	private boolean isDistinct=false;
//	private boolean isCount=false;
//	private boolean isSum=false;
	
	private SelectAggreFun selectAggreFun;


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
			if(SelectAggreFun.count==selectAggreFun){
				sb.append(" select ");
				sb.append("count(");
				if(this.isDistinct){
					sb.append("distinct ");
				}
				for (String obi : names) {
					//sb.append(obi+" as "+obi+",");
					sb.append(obi+",");
				}
				sb.setCharAt(sb.length() - 1, ' ');
				sb.append(") ");
			} else if(SelectAggreFun.sum==selectAggreFun){
				sb.append(" select sum("+names.get(0)+") ");
			} else if(SelectAggreFun.avg==selectAggreFun){
				sb.append(" select avg("+names.get(0)+") ");
			} else if(SelectAggreFun.max==selectAggreFun){
				sb.append(" select max("+names.get(0)+") ");
			} else if(SelectAggreFun.min==selectAggreFun){
				sb.append(" select min("+names.get(0)+") ");
			} else {
				sb.append(" select ");
				if(this.isDistinct){
					sb.append("distinct ");
				}
				for (String obi : names) {
					//sb.append(obi+" as "+obi+",");
					sb.append(obi+",");
				}
				sb.setCharAt(sb.length() - 1, ' ');
			}
			
		} else {
			if(SelectAggreFun.count==selectAggreFun){
				sb.append(" select count(*) ");
			}  
		}
		
//		if (!names.isEmpty()) {
//			sb.append(" select ");
//			if(isCount){
//				sb.append("count(");
//			}
//			if(this.isDistinct){
//				sb.append("distinct ");
//			}
//			//sb.append(" new map(");
//			for (String obi : names) {
//				//sb.append(obi+" as "+obi+",");
//				sb.append(obi+",");
//			}
//			sb.setCharAt(sb.length() - 1, ' ');
//			//sb.append(") "); 
//			if(isCount){
//				sb.append(") ");
//			}
//		} else {
//			if(isCount){
//				sb.append(" select count(*) ");
//			}  else if(isSum){
//				sb.append(" select sum("+names.get(0)+") ");
//			}
//		}
			
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

	public SelectAggreFun getSelectAggreFun() {
		return selectAggreFun;
	}

	public void setSelectAggreFun(SelectAggreFun selectAggreFun) {
		this.selectAggreFun = selectAggreFun;
	}
}
