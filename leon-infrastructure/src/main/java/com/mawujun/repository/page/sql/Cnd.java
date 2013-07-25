package com.mawujun.repository.page.sql;

//import static com.mawujun.repository.page.sql.Exps.gt;
//import static com.mawujun.repository.page.sql.Exps.gte;
//import static com.mawujun.repository.page.sql.Exps.inInt;
//import static com.mawujun.repository.page.sql.Exps.inLong;
//import static com.mawujun.repository.page.sql.Exps.inSql;
//import static com.mawujun.repository.page.sql.Exps.inStr;
//import static com.mawujun.repository.page.sql.Exps.like;
//import static com.mawujun.repository.page.sql.Exps.lt;
//import static com.mawujun.repository.page.sql.Exps.lte;

import java.util.Collections;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;

import com.mawujun.utils.BeanPropertiesCopy;
import com.mawujun.utils.ReflectionUtils;






/**
 * DAO 是使用一个好呢，还是继承好，还有就是如何接收前台的参数，BaseRepository中根据 Cnd进行查询。还有就是增，删，改，把WhereInfo的改成Cnd的
 * 建立一个独立的DAO，他用来快速访问
 * 用来存放where和orderby的数据
 * 
 * updateIgnoreNull和deleteBatch(Cnd cnd)还没有测试
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
	
	public Cnd andIsNull(String name) {
		this.getWhere().andIsNull(name);
		return this;
	}

	public Cnd andNotIsNull(String name) {
		this.getWhere().andNotIsNull(name);
		return this;
	}
	
	public Cnd andEquals(String name, Object val) {
		if (null == val)
			return andIsNull(name);
		this.getWhere().andEquals(name, val);
		return this;
	}

	public Cnd andNotEquals(String name, Object val) {
		if (null == val)
			return andNotIsNull(name);
		this.getWhere().andNotEquals(name, val);
		return this;

	}
	
	public Cnd andGT(String name, long val) {
		this.getWhere().andGT(name, val);
		return this;
	}

	public Cnd andGTE(String name, long val) {
		this.getWhere().andGTE(name, val);
		return this;
	}

	public Cnd andLT(String name, long val) {
		this.getWhere().andLT(name, val);
		return this;
	}

	public Cnd andLTE(String name, long val) {
		this.getWhere().andLTE(name, val);
		return this;
	}
	
	public Cnd andIn(String name, long... ids) {
		this.getWhere().andIn(name, ids);
		return this;
	}

	public Cnd andInInt(String name, int... ids) {
		this.getWhere().andInIntArray(name, ids);
		return this;
	}

	public Cnd andIn(String name, String... names) {
		this.getWhere().andIn(name, names);
		return this;
	}

	public Cnd andInByHql(String name, String subSql, Object... args) {
		this.getWhere().andInBySql(name, subSql,args);
		return this;
	}

	public Cnd andNotInByHql(String name, String subSql, Object... args) {
		this.getWhere().andNotInBySql(name,subSql, args);
		return this;
	}

	public Cnd andNotIn(String name, long... ids) {
		this.getWhere().andNotIn(name, ids);
		return this;
	}

	public Cnd andNotInInt(String name, int... ids) {
		this.getWhere().andNotIn(name, ids);
		return this;
	}

	public Cnd andNotIn(String name, String... names) {
		this.getWhere().andNotIn(name, names);
		return this;
	}

	public Cnd andLike(String name, String value) {
		this.getWhere().andLike(name, value);
		return this;
	}

	public Cnd andNotLike(String name, String value) {
		this.getWhere().andNotLike(name, value);
		return this;
	}

	public Cnd andLike(String name, String value, boolean ignoreCase) {
		this.getWhere().andLike(name, value,ignoreCase);
		return this;
	}

	public Cnd andNotLike(String name, String value, boolean ignoreCase) {
		this.getWhere().andNotLike(name, value,ignoreCase);
		return this;
	}
	
	public Cnd orEquals(String name, Object val) {
		this.getWhere().orEquals(name, val);
		return this;
	}

	public Cnd orNotEquals(String name, Object val) {
		this.getWhere().orNotEquals(name, val);
		return this;

	}

	public Cnd orIsNull(String name) {
		this.getWhere().orIsNull(name);
		return this;
	}

	public Cnd orNotIsNull(String name) {
		this.getWhere().orNotIsNull(name);
		return this;
	}

	public Cnd orGT(String name, long val) {
		this.getWhere().orGT(name,val);
		return this;
	}

	public Cnd orGTE(String name, long val) {
		this.getWhere().orGTE(name,val);
		return this;
	}

	public Cnd orLT(String name, long val) {
		this.getWhere().orLT(name,val);
		return this;
	}

	public Cnd orLTE(String name, long val) {
		this.getWhere().orLTE(name,val);
		return this;
	}

	public Cnd orIn(String name, long... ids) {
		this.getWhere().orIn(name,ids);
		return this;
	}

	public Cnd orIn(String name, int... ids) {
		this.getWhere().orIn(name,ids);
		return this;
	}

	public Cnd orIn(String name, String... names) {
		this.getWhere().orIn(name,names);
		return this;
	}

	public Cnd orInBySql(String name, String subSql, Object... args) {
		this.getWhere().orInBySql(name,subSql,args);
		return this;
	}

	public Cnd orNotInBySql(String name, String subSql, Object... args) {
		this.getWhere().orNotInBySql(name,subSql,args);
		return this;
	}

	public Cnd orNotIn(String name, long... ids) {
		this.getWhere().orNotIn(name,ids);
		return this;
	}

	public Cnd orNotIn(String name, int... ids) {
		this.getWhere().orNotIn(name,ids);
		return this;
	}

	public Cnd orNotIn(String name, String... names) {
		this.getWhere().orNotIn(name,names);
		return this;
	}

	public Cnd orLike(String name, String value) {
		this.getWhere().orLike(name,value);
		return this;
	}

	public Cnd orNotLike(String name, String value) {
		this.getWhere().orNotLike(name,value);
		return this;
	}

	public Cnd orLike(String name, String value, boolean ignoreCase) {
		this.getWhere().orLike(name,value,ignoreCase);
		return this;
	}

	public Cnd orNotLike(String name, String value, boolean ignoreCase) {
		this.getWhere().orNotLike(name,value,ignoreCase);
		return this;
	}
	
	
	

	public Cnd asc(String name) {
		orderBy.asc(name);
		return this;
	}

	public Cnd desc(String name) {
		orderBy.desc(name);
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//========================================================
	

//	@Override
//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		// TODO Auto-generated method stub
//		where.joinSql(classMetadata, sb);
//	}

	@Override
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		// TODO Auto-generated method stub
		
		//sb.append("from "+classMetadata.getEntityName());
		where.joinHql(classMetadata, sb);
		orderBy.joinHql(classMetadata, sb);
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
