package com.mawujun.repository.cnd;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class InsertItems implements SqlExpression {
	private Map<String,Object> sets=new LinkedHashMap<String,Object>();
	
	public int size(){
		return this.sets.size();
	}
	
	public void set(String fieldName,Object value) {
		sets.put(fieldName, value);
	}

	@Override
	public void joinHql(StringBuilder sb) {
		// TODO Auto-generated method stub
		if (!sets.isEmpty()) {
			sb.append("(");
			StringBuilder values=new StringBuilder(" values(");
			int flag=0;
			for (Entry<String,Object> entry : sets.entrySet()) {
				//sb.append(obi+" as "+obi+",");
				sb.append(entry.getKey());
				values.append("?");
				if(flag!=sets.size()-1){
					sb.append(",");
					values.append(",");
				}
				flag++;
			}
			sb.append(") ");
			values.append(")");
			sb.append(values);
		} 
	}

	@Override
	public int joinParams(Object obj,Object[] params, int off) {
		for (Entry<String,Object> entry : sets.entrySet()) {
			if(entry.getValue()==null){
				params[off++] = null;
			} else {
				params[off++] = entry.getValue();//BeanUtils.convert(entry.getValue(), type.getReturnedClass());
			}
			
		}
		
//		for (Entry<String,Object> entry : sets.entrySet()) {
//			//把数据抓换为正确的类型
//			Type type=classMetadata.getPropertyType(entry.getKey());
//			if(entry.getValue()==null){
//				params[off++] = null;
//			} else {
//				params[off++] = BeanUtils.convert(entry.getValue(), type.getReturnedClass());
//			}
//			
//		}

		return off;
	}

	@Override
	public int paramCount() {
		// TODO Auto-generated method stub
		return this.size();
	}

	@Override
	public SqlExpression setNot(boolean not) {
		// TODO Auto-generated method stub
		return this;
	}
	public void clearSets(){
		this.sets.clear();
	}

}
