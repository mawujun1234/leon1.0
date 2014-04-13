package com.mawujun.repository.cnd;




public class SqlRange extends NoParamsSqlExpression implements SqlExpression {

	private String sql;
	protected Object[] args;

	public SqlRange(String name, String fmt, Object... args) {
		super(name);
		this.not = false;
		this.sql = String.format(fmt, args);
		this.args = args;
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		sb.append(String.format("%s%s IN (%s)", (not ? " NOT " : ""), _fmtcol(classMetadata), sql));
//	}

	@Override
	 public int joinParams(Object obj, Object[] params, int off) {
	   for (Object arg : args)
	           params[off++] = arg;
	       return off;
	 }
	
	 @Override
	 public int paramCount() {
	    return args.length;
	 }
	
	@Override
	public void joinHql(StringBuilder sb) {
		// TODO Auto-generated method stub
		//sb.append(String.format("%s%s IN (%s)", (not ? " NOT " : ""), this.getName(), sql));
		sb.append(this.getName());
		if (not)
			sb.append(" NOT");
		sb.append(" IN (");
		sb.append(sql);
		sb.append(")");
	}



}
