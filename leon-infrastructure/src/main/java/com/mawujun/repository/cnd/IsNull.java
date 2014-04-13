package com.mawujun.repository.cnd;




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
	@Override
	public void joinHql(StringBuilder sb) {
		sb.append(this.getName());
		sb.append(" IS ");
		if (not)
			sb.append("NOT ");
		sb.append("NULL ");
	}

}
