package com.mawujun.repository.cnd;



public class SimpleExpression extends AbstractSqlExpression {

	private String op;
	private Object value;

	public SimpleExpression(String name, String op, Object val) {
		super(name);
		this.op = op;
		this.value = val;
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		if (not)
//			sb.append(" NOT ");
//		if ("=".equals(op) || ">".equals(op) || "<".equals(op) || "!=".equals(op))
//			sb.append(_fmtcol(classMetadata)).append(op).append('?');
//		else
//			sb.append(_fmtcol(classMetadata)).append(' ').append(op).append(' ').append('?');
//	}
	@Override
	public void joinHql( StringBuilder sb) {
		if (not)
			sb.append(" NOT ");
		if ("=".equals(op) || ">".equals(op) || "<".equals(op) || "!=".equals(op))
			sb.append(this.getName()).append(op).append('?');
		else
			sb.append(this.getName()).append(' ').append(op).append(' ').append('?');
	}

//	public int joinAdaptor(Entity<?> en, ValueAdaptor[] adaptors, int off) {
//		MappingField mf = _field(en);
//		if (null != mf) {
//			adaptors[off++] = mf.getAdaptor();
//		} else {
//			adaptors[off++] = Jdbcs.getAdaptorBy(value);
//		}
//		return off;
//	}

	@Override
	public int joinParams(Object obj, Object[] params, int off) {
		params[off++] = value;
		return off;
	}
//	@Override
//	public int joinEntitys(AbstractEntityPersister classMetadata, Object obj,
//			List<String> entitys) {
//		// TODO Auto-generated method stubd
//		dd
//		return 0;
//	}
	@Override
	public int paramCount() {
		return 1;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	

}
