package com.mawujun.repository.cnd;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.persister.entity.AbstractEntityPersister;

public class SelectItems {

	private List<String> names=new ArrayList<String>();
	
	private boolean isDistinct=false;


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
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		if (!names.isEmpty()) {
			sb.append(" select ");
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
		} else
			;// OK,无需添加.
	}

	public boolean isDistinct() {
		return isDistinct;
	}

	public void setDistinct(boolean isDistinct) {
		this.isDistinct = isDistinct;
	}

}
