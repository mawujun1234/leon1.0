package com.mawujun.repository.cnd;




public class Like extends AbstractSqlExpression {

	static Like create(String name, String value, boolean ignoreCase) {
		Like like = new Like(name);
		like.value = value;
		like.ignoreCase = ignoreCase;
		like.left = "%";
		like.right = "%";
		return like;
	}

	private String value;

	private boolean ignoreCase=true;

	private String left;

	private String right;

	private Like(String name) {
		super(name);
	}

//	/**
//	 * 
//	 */
//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		String colName = _fmtcol(classMetadata);
//		if (not)
//			sb.append(" NOT ");
//		if (ignoreCase)
//			sb.append("LOWER(").append(colName).append(") LIKE LOWER(?)");
//		else
//			sb.append(colName).append(" LIKE ?");
//
//	}
	
	@Override
	public void joinHql( StringBuilder sb) {
		String colName = this.getName();
		if (not)
			sb.append(" NOT ");
		if (ignoreCase)
			sb.append("LOWER(").append(colName).append(") LIKE LOWER(?)");
		else
			sb.append(colName).append(" LIKE ?");
		
	}

//	public int joinAdaptor(Entity<?> en, ValueAdaptor[] adaptors, int off) {
//		adaptors[off++] = Jdbcs.Adaptor.asString;
//		return off;
//	}
	@Override
	public int joinParams(Object obj, Object[] params, int off) {
		params[off++] = (null == left ? "" : left) + value + (null == right ? "" : right);
		return off;
	}
	@Override
	public int paramCount() {
		return 1;
	}

	public Like left(String left) {
		this.left = left;
		return this;
	}

	public Like right(String right) {
		this.right = right;
		return this;
	}

	public Like ignoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
		return this;
	}

	

//	@Override
//	public void setPojo(Pojo pojo) {
//		// TODO Auto-generated method stub
//		
//	}


}
