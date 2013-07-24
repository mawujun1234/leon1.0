package com.mawujun.repository.page.sql;

import java.util.Collections;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;

import com.mawujun.utils.BeanPropertiesCopy;
import com.mawujun.utils.ReflectionUtils;





/**
 * 用来存放where和orderby的数据
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class Cnd implements PItem{
	protected SqlExpressionGroup where;

	private OrderBySet orderBy;
	
	public Cnd() {
		where = new SqlExpressionGroup();
		orderBy = new OrderBySet();
	}
	
	public static SqlExpression exp(String name, String op, Object value) {
		return Exps.create(name, op, value);
	}
	public static SqlExpressionGroup exps(String name, String op, Object value) {
		return exps(exp(name, op, value));
	}
	public static SqlExpressionGroup exps(SqlExpression exp) {
		return new SqlExpressionGroup().and(exp);
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
	//==================================
	往这里加上 addEQ等方法
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//========================================================
	

//	@Override
//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		// TODO Auto-generated method stub
//		where.joinSql(classMetadata, sb);
//	}

	@Override
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		// TODO Auto-generated method stub
		where.joinHql(classMetadata, sb);
		//orderBy.joinSql(classMetadata, sb);
	}

	@Override
	public int joinParams(AbstractEntityPersister classMetadata, Object obj,
			Object[] params, int off) {
		return where.joinParams(classMetadata, obj, params, off);
	}

	@Override
	public int paramCount(AbstractEntityPersister classMetadata) {
		return where.paramCount(classMetadata);
	}
	
//	public String toSql(AbstractEntityPersister classMetadata) {
//		Object[] params = new Object[this.paramCount(classMetadata)];
//		int i = where.joinParams(classMetadata, null, params, 0);
//		//orderBy.joinParams(classMetadata, null, params, i);
//
//		StringBuilder sb = new StringBuilder();
//		this.joinSql(classMetadata, sb);
//		String[] ss = sb.toString().split("[?]");
//
//		sb = new StringBuilder();
//		for (i = 0; i < params.length; i++) {
//			sb.append(ss[i]);
//			sb.append(formatFieldValue(params[i]));
//		}
//		if (i < ss.length)
//			sb.append(ss[i]);
//
//		return sb.toString();
//	}
	
	public String toSql(AbstractEntityPersister classMetadata) {
		String hql=toHql(classMetadata);
		//http://fungo.me/java-2/retrieve-sql-query-from-criteria-or-hql.html
		//http://www.heiqu.com/show-81243-1.html
		if (hql != null && hql.trim().length() > 0)
	    {
	        final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
	        final SessionFactoryImplementor factory =  classMetadata.getFactory();
	        final QueryTranslator translator = translatorFactory.createQueryTranslator(hql, hql,
	            Collections.EMPTY_MAP, factory);
	        translator.compile(Collections.EMPTY_MAP, false);
	        return translator.getSQLString();
	    }
	    return null;
	}

	
	public String toHql(AbstractEntityPersister classMetadata) {
		Object[] params = new Object[this.paramCount(classMetadata)];
		int i = where.joinParams(classMetadata, null, params, 0);
		//orderBy.joinParams(classMetadata, null, params, i);

		StringBuilder sb = new StringBuilder("from "+classMetadata.getEntityName());
		//StringBuilder sb = new StringBuilder();
		this.joinHql(classMetadata, sb);
		String[] ss = sb.toString().split("[?]");

		sb = new StringBuilder();
		for (i = 0; i < params.length; i++) {
			sb.append(ss[i]);
			sb.append(formatFieldValue(params[i]));
		}
		if (i < ss.length)
			sb.append(ss[i]);

		return sb.toString();
	}
	
	public  CharSequence formatFieldValue(Object v) {
		if (null == v)
			return "NULL";
		else if (isNotNeedQuote(v))
			return escapeFieldValue(v.toString());
		else
			return new StringBuilder("'").append(escapeFieldValue(BeanPropertiesCopy.copyOrCast(v, String.class))).append('\'');
	}
	public  boolean isNotNeedQuote(Object v) {
		
		return ReflectionUtils.isBoolean(v) || ReflectionUtils.isPrimitiveNumber(v);
	}
	/**
	 * 将 SQL 的字段值进行转意，可以用来防止 SQL 注入攻击
	 * 
	 * @param s
	 *            字段值
	 * @return 格式化后的 Sql 字段值，可以直接拼装在 SQL 里面
	 */
	public  CharSequence escapeFieldValue(CharSequence s) {
//		if (null == s)
//			return null;
//		return ES_FLD_VAL.escape(s);
		return s;
	}
	

}
