package com.mawujun.repository.cnd;



public class NameRange extends AbstractSqlExpression {

	private String[] names;

	NameRange(String name, String... names) {
		super(name);
		this.names = names;
		this.not = false;
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		if (names.length > 0) {
//			sb.append(_fmtcol(classMetadata));
//			if (not)
//				sb.append(" NOT");
//			sb.append(" IN (");
//			for (int i = 0; i < names.length; i++)
//				sb.append("?,");
//			sb.setCharAt(sb.length() - 1, ')');
//		} else
//			;//OK,无需添加.
//	}
	@Override
	public void joinHql(StringBuilder sb) {
		if (names.length > 0) {
			sb.append(this.getName());
			if (not)
				sb.append(" NOT");
			sb.append(" IN (");
			for (int i = 0; i < names.length; i++)
				sb.append("?,");
			sb.setCharAt(sb.length() - 1, ')');
		} else
			;//OK,无需添加.
		
	}
//
//	public int joinAdaptor(AbstractEntityPersister classMetadata,ValueAdaptor[] adaptors, int off) {
//		for (int i = 0; i < names.length; i++)
//			adaptors[off++] = Jdbcs.Adaptor.asString;
//		return off;
//	}
	@Override
	public int joinParams(Object obj, Object[] params, int off) {
		for (String name : names)
			params[off++] = name;
		return off;
	}
	@Override
	public int paramCount() {
		return names.length;
	}

}
