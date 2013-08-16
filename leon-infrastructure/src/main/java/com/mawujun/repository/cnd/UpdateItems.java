package com.mawujun.repository.cnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;

import com.mawujun.utils.BeanUtils;

public class UpdateItems implements SqlExpression{

	private Map<String,Object> sets=new HashMap<String,Object>();
	
	public void set(String fieldName,Object value) {
		sets.put(fieldName, value);
	}
	
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		if (!sets.isEmpty()) {
			//sb.append(" new map(");
			int flag=0;
			for (Entry<String,Object> entry : sets.entrySet()) {
				//sb.append(obi+" as "+obi+",");
				sb.append(" "+entry.getKey()+"=? ");
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
	public int joinParams(AbstractEntityPersister classMetadata, Object obj,Object[] params, int off) {
//		
//		//ClassUtils.isPrimitiveOrWrapper(type)
//		query.setParameter(whereInfo.getPropTrans(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
		for (Entry<String,Object> entry : sets.entrySet()) {
			//把数据抓换为正确的类型
			Type type=classMetadata.getPropertyType(entry.getKey());
			params[off++] = BeanUtils.convert(entry.getValue(), type.getReturnedClass());
		}

		return off;
	}

	@Override
	public int paramCount(AbstractEntityPersister classMetadata) {
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
