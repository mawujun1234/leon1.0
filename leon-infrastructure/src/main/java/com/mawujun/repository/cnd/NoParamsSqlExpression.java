package com.mawujun.repository.cnd;



public abstract class NoParamsSqlExpression extends AbstractSqlExpression {

	protected NoParamsSqlExpression(String name) {
		super(name);
	}

//	public int joinAdaptor(AbstractEntityPersister classMetadata, ValueAdaptor[] adaptors, int off) {
//		return off;
//	}
	@Override
	public int joinParams( Object obj, Object[] params, int off) {
		return off;
	}
	@Override
	public int paramCount() {
		return 0;
	}

}
