package com.mawujun.repository.cnd;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UpdateItems implements SqlExpression{

	private Map<String,Object> sets=new LinkedHashMap<String,Object>();
	
	public void set(String fieldName,Object value) {
		sets.put(fieldName, value);
	}
	@Override
	public void joinHql(StringBuilder sb) {
		if (!sets.isEmpty()) {
			//sb.append(" new map(");
			int flag=0;
			for (Entry<String,Object> entry : sets.entrySet()) {
				//sb.append(obi+" as "+obi+",");
				sb.append(entry.getKey()+"=?");
				if(flag!=sets.size()-1){
					sb.append(",");
				}
				flag++;
			}
		} 
	}
	
	public int size(){
		return this.sets.size();
	}

	@Override
	public int joinParams(Object obj,Object[] params, int off) {
		for (Entry<String,Object> entry : sets.entrySet()) {
			//params[off++] = BeanUtils.convert(entry.getValue(), type.getReturnedClass());
			if(entry.getValue()==null){
				params[off++] = null;
			} else {
				//在设置参数的时候，很多时候都是设置为String的，如果有指定类型，就转换为指定类型
				params[off++] = entry.getValue();//BeanUtils.convert(entry.getValue(), type.getReturnedClass());
			}
			
//			//把数据抓换为正确的类型
//			Type type=classMetadata.getPropertyType(entry.getKey());
//			//params[off++] = BeanUtils.convert(entry.getValue(), type.getReturnedClass());
//			if(entry.getValue()==null){
//				params[off++] = null;
//			} else {
//				//在设置参数的时候，很多时候都是设置为String的，如果有指定类型，就转换为指定类型
//				params[off++] = BeanUtils.convert(entry.getValue(), type.getReturnedClass());
//			}
		}

		return off;
	}

	@Override
	public int paramCount() {
		// TODO Auto-generated method stub
		return this.size();
	}

	@Override
	public SqlExpression setNot(boolean not) {
		return this;
	}
	
	public void clearSets(){
		this.sets.clear();
	}

}
