package com.mawujun.utils.page.sql;





/**
 * 用来存放where和orderby的数据
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class Cnd {
	protected SqlExpressionGroup where;

	private OrderBySet orderBy;
	
	public Cnd() {
		where = new SqlExpressionGroup();
		orderBy = new OrderBySet();
	}
	
	public static SqlExpression exp(String name, String op, Object value) {
		return Exps.create(name, op, value);
	}
	

	public static Cnd where() {
		return new Cnd();
	}
	
	public SqlExpressionGroup getWhere() {
		return where;
	}

	public Cnd and(SqlExpression exp) {
		this.getWhere().and(exp);
		return this;
	}

	public Cnd and(String name, String op, Object value) {
		return and(Cnd.exp(name, op, value));
	}

	public Cnd or(SqlExpression exp) {
		this.getWhere().or(exp);
		return this;
	}

	public Cnd or(String name, String op, Object value) {
		return or(Cnd.exp(name, op, value));
	}

	public Cnd andNot(SqlExpression exp) {
		this.getWhere().and(exp.setNot(true));
		return this;
	}

	public Cnd andNot(String name, String op, Object value) {
		return andNot(Cnd.exp(name, op, value));
	}

	public Cnd orNot(SqlExpression exp) {
		this.getWhere().or(exp.setNot(true));
		return this;
	}

	public Cnd orNot(String name, String op, Object value) {
		return orNot(Cnd.exp(name, op, value));
	}
	
	

}
