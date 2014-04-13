package com.mawujun.repository.cnd;

import org.hibernate.persister.entity.AbstractEntityPersister;


public abstract class NumberRange extends AbstractSqlExpression {

	protected long[] ids;

	protected NumberRange(String name) {
		super(name);
	}

	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		if (ids.length > 0) {
			sb.append(_fmtcol(classMetadata));
			if (not)
				sb.append(" NOT");
			sb.append(" IN (");
			for (int i = 0; i < ids.length; i++)
				sb.append("?,");
			sb.setCharAt(sb.length() - 1, ')');
		} else
			;//OK,无需添加.
	}
	@Override
	public void joinHql( StringBuilder sb) {
		if (ids.length > 0) {
			sb.append(this.getName());
			if (not)
				sb.append(" NOT");
			sb.append(" IN (");
			for (int i = 0; i < ids.length; i++)
				sb.append("?,");
			sb.setCharAt(sb.length() - 1, ')');
		} else
			;//OK,无需添加.
		
	}
//
//	public int joinAdaptor(AbstractEntityPersister classMetadata, ValueAdaptor[] adaptors, int off) {
//		for (int i = 0; i < ids.length; i++)
//			adaptors[off++] = Jdbcs.Adaptor.asLong;
//		return off;
//	}
	@Override
	public int joinParams(Object obj, Object[] params, int off) {
		for (long id : ids)
			params[off++] = id;
		return off;
	}
	@Override
	public int paramCount() {
		return ids.length;
	}

}
