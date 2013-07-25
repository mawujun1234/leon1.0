package com.mawujun.repository.cnd;

import org.hibernate.persister.entity.AbstractEntityPersister;



public class IsNull extends NoParamsSqlExpression {

	public IsNull(String name) {
		super(name);
		this.not = false;
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		sb.append(_fmtcol(classMetadata));
//		sb.append(" IS ");
//		if (not)
//			sb.append("NOT ");
//		sb.append("NULL ");
//	}
	
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		sb.append(this.getName());
		sb.append(" IS ");
		if (not)
			sb.append("NOT ");
		sb.append("NULL ");
	}

}
