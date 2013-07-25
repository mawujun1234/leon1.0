package com.mawujun.repository.cnd;

import org.hibernate.persister.entity.AbstractEntityPersister;


public abstract class NoParamsSqlExpression extends AbstractSqlExpression {

	protected NoParamsSqlExpression(String name) {
		super(name);
	}

//	public int joinAdaptor(AbstractEntityPersister classMetadata, ValueAdaptor[] adaptors, int off) {
//		return off;
//	}

	public int joinParams(AbstractEntityPersister classMetadata, Object obj, Object[] params, int off) {
		return off;
	}

	public int paramCount(AbstractEntityPersister classMetadata) {
		return 0;
	}

}
