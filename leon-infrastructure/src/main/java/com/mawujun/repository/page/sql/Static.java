package com.mawujun.repository.page.sql;

import org.hibernate.persister.entity.AbstractEntityPersister;



/**
 * 
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class Static implements SqlExpression {// extends NoParamsPItem

	private String str;

	public Static(String str) {
		this.str = str;
	}

	public SqlExpression setNot(boolean not) {
		return this;
	}

	public String toString() {
		return ' ' + str + ' ';
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		sb.append(' ').append(str).append(' ');
//	}


	@Override
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append(' ').append(str).append(' ');
	}

	@Override
	public int joinParams(AbstractEntityPersister classMetadata, Object obj,
			Object[] params, int off) {
		// TODO Auto-generated method stub
		return off;
	}

	@Override
	public int paramCount(AbstractEntityPersister classMetadata) {
		// TODO Auto-generated method stub
		return 0;
	}

}