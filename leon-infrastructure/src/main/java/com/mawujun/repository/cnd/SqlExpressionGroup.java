package com.mawujun.repository.cnd;

import static com.mawujun.repository.cnd.Exps.eq;
import static com.mawujun.repository.cnd.Exps.gt;
import static com.mawujun.repository.cnd.Exps.gte;
import static com.mawujun.repository.cnd.Exps.inInt;
import static com.mawujun.repository.cnd.Exps.inLong;
import static com.mawujun.repository.cnd.Exps.inSql;
import static com.mawujun.repository.cnd.Exps.inStr;
import static com.mawujun.repository.cnd.Exps.isNull;
import static com.mawujun.repository.cnd.Exps.like;
import static com.mawujun.repository.cnd.Exps.lt;
import static com.mawujun.repository.cnd.Exps.lte;

import java.util.ArrayList;
import java.util.List;



/**
 * 组合一组表达式，只能增加，不能减少
 */
public class SqlExpressionGroup implements SqlExpression {// extends AbstractPItem

	private List<SqlExpression> exps;

	private boolean top;

	public SqlExpressionGroup() {
		exps = new ArrayList<SqlExpression>(); // 默认就是10个，能放5个条件，够了吧
		top = true;
		
	}

	public SqlExpressionGroup and(String name, String op, Object value) {
		return and(Exps.create(name, op, value));
	}

	public SqlExpressionGroup and(SqlExpression exp) {
		if (!exps.isEmpty())
			_add(new Static("AND"));
		return _add(exp);
	}

	public SqlExpressionGroup andEquals(String name, Object val) {
		if (null == val)
			return andIsNull(name);
		return and(eq(name, val));
	}

	public SqlExpressionGroup andNotEquals(String name, Object val) {
		if (null == val)
			return andNotIsNull(name);
		return and(eq(name, val).not());

	}

	public SqlExpressionGroup andIsNull(String name) {
		return and(isNull(name));
	}

	public SqlExpressionGroup andNotIsNull(String name) {
		return and(isNull(name).not());
	}

	public SqlExpressionGroup andGT(String name, long val) {
		return and(gt(name, val));
	}

	public SqlExpressionGroup andGTE(String name, long val) {
		return and(gte(name, val));
	}

	public SqlExpressionGroup andLT(String name, long val) {
		return and(lt(name, val));
	}

	public SqlExpressionGroup andLTE(String name, long val) {
		return and(lte(name, val));
	}

	public SqlExpressionGroup andIn(String name, long... ids) {
		return and(inLong(name, ids));
	}

	public SqlExpressionGroup andInIntArray(String name, int... ids) {
		return and(inInt(name, ids));
	}

	public SqlExpressionGroup andIn(String name, String... names) {
		return and(inStr(name, names));
	}

	public SqlExpressionGroup andInBySql(String name, String subSql, Object... args) {
		return and(inSql(name, subSql, args));
	}

	public SqlExpressionGroup andNotInBySql(String name, String subSql, Object... args) {
		return and(inSql(name, subSql, args).not());
	}

	public SqlExpressionGroup andNotIn(String name, long... ids) {
		return and(inLong(name, ids).not());
	}

	public SqlExpressionGroup andNotIn(String name, int... ids) {
		return and(inInt(name, ids).not());
	}

	public SqlExpressionGroup andNotIn(String name, String... names) {
		return and(inStr(name, names).not());
	}

	public SqlExpressionGroup andLike(String name, String value) {
		return and(like(name, value));
	}

	public SqlExpressionGroup andNotLike(String name, String value) {
		return and(like(name, value).not());
	}

	public SqlExpressionGroup andLike(String name, String value, boolean ignoreCase) {
		return and(like(name, value, ignoreCase));
	}

	public SqlExpressionGroup andNotLike(String name, String value, boolean ignoreCase) {
		return and(like(name, value, ignoreCase).not());
	}

	public SqlExpressionGroup or(String name, String op, Object value) {
		return or(Exps.create(name, op, value));
	}

	public SqlExpressionGroup or(SqlExpression exp) {
		if (!exps.isEmpty())
			_add(new Static("OR"));
		return _add(exp);
	}

	public SqlExpressionGroup orEquals(String name, Object val) {
		return or(eq(name, val));
	}

	public SqlExpressionGroup orNotEquals(String name, Object val) {
		return or(eq(name, val).not());

	}

	public SqlExpressionGroup orIsNull(String name) {
		return or(isNull(name));
	}

	public SqlExpressionGroup orNotIsNull(String name) {
		return or(isNull(name).not());
	}

	public SqlExpressionGroup orGT(String name, long val) {
		return or(gt(name, val));
	}

	public SqlExpressionGroup orGTE(String name, long val) {
		return or(gte(name, val));
	}

	public SqlExpressionGroup orLT(String name, long val) {
		return or(lt(name, val));
	}

	public SqlExpressionGroup orLTE(String name, long val) {
		return or(lte(name, val));
	}

	public SqlExpressionGroup orIn(String name, long... ids) {
		return or(inLong(name, ids));
	}

	public SqlExpressionGroup orIn(String name, int... ids) {
		return or(inInt(name, ids));
	}

	public SqlExpressionGroup orIn(String name, String... names) {
		return or(inStr(name, names));
	}

	public SqlExpressionGroup orInBySql(String name, String subSql, Object... args) {
		return or(inSql(name, subSql, args));
	}

	public SqlExpressionGroup orNotInBySql(String name, String subSql, Object... args) {
		return or(inSql(name, subSql, args).not());
	}

	public SqlExpressionGroup orNotIn(String name, long... ids) {
		return or(inLong(name, ids).not());
	}

	public SqlExpressionGroup orNotIn(String name, int... ids) {
		return or(inInt(name, ids).not());
	}

	public SqlExpressionGroup orNotIn(String name, String... names) {
		return or(inStr(name, names).not());
	}

	public SqlExpressionGroup orLike(String name, String value) {
		return or(like(name, value));
	}

	public SqlExpressionGroup orNotLike(String name, String value) {
		return or(like(name, value).not());
	}

	public SqlExpressionGroup orLike(String name, String value, boolean ignoreCase) {
		return or(like(name, value, ignoreCase));
	}

	public SqlExpressionGroup orNotLike(String name, String value, boolean ignoreCase) {
		return or(like(name, value, ignoreCase).not());
	}

//	@Override
//	public void setPojo(Pojo pojo) {
//		super.setPojo(pojo);
//		for (SqlExpression exp : exps)
//			exp.setPojo(pojo);
//	}
	


	private SqlExpressionGroup _add(SqlExpression exp) {
		if (null != exp) {
			exps.add(exp);
			//exp.setPojo(pojo);
			if (exp instanceof SqlExpressionGroup)
				((SqlExpressionGroup) exp).top = false;
		}
		return this;
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		if (!exps.isEmpty()) {
//			if (top) {
//				sb.append(" WHERE ");
//				for (SqlExpression exp : exps)
//					exp.joinSql(classMetadata, sb);
//			} else {
//				sb.append('(');
//				for (SqlExpression exp : exps)
//					exp.joinSql(classMetadata, sb);
//				sb.append(')');
//			}
//		}
//	}
	@Override
	public void joinHql( StringBuilder sb) {
		if (!exps.isEmpty()) {
			if (top) {
				sb.append(" WHERE ");
				for (SqlExpression exp : exps)
					exp.joinHql( sb);
			} else {
				sb.append('(');
				for (SqlExpression exp : exps)
					exp.joinHql(sb);
				sb.append(')');
			}
		}
	}

//	public int joinAdaptor(AbstractEntityPersister classMetadata, ValueAdaptor[] adaptors, int off) {
//		for (SqlExpression exp : exps)
//			off = exp.joinAdaptor(en, adaptors, off);
//		return off;
//	}

	@Override
	public int joinParams(Object obj, Object[] params, int off) {
		for (SqlExpression exp : exps)
			off = exp.joinParams(obj, params, off);
		return off;
	}
	@Override
	public int paramCount() {
		int re = 0;
		for (SqlExpression exp : exps)
			re += exp.paramCount();
		return re;
	}

	public SqlExpression setNot(boolean not) {
		return this;
	}

	public boolean isEmpty() {
		return exps.isEmpty();
	}

	public List<SqlExpression> cloneExps() {
		return new ArrayList<SqlExpression>(exps);
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}
}
