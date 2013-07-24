package com.mawujun.repository.page.sql;

import org.hibernate.persister.entity.AbstractEntityPersister;



public class SqlRange extends NoParamsSqlExpression implements SqlExpression {

	private String sql;

	public SqlRange(String name, String fmt, Object... args) {
		super(name);
		this.not = false;
		this.sql = String.format(fmt, args);
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		sb.append(String.format("%s%s IN (%s)", (not ? " NOT " : ""), _fmtcol(classMetadata), sql));
//	}

	@Override
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append(String.format("%s%s IN (%s)", (not ? " NOT " : ""), this.getName(), sql));
	}



}
