package com.mawujun.repository.cnd;

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

import com.mawujun.utils.AssertUtils;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.ReflectUtils;






/**
 * 参数类型转换还没有在Pitem的子类中做过
 * BaseRepository中根据 Cnd进行查询。还有就是增，删，改，把WhereInfo的改成Cnd的
 * 还有就是如何接收前台的参数，
 * DAO 是使用一个好呢，还是继承好，建立一个独立的DAO，他用来快速访问
 * 用来存放where和orderby的数据
 * 
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class Cnd implements PItem{
	protected SqlExpressionGroup where;

	private OrderBySet orderBy;
	
	private SelectItems selectItems;
	
	private UpdateItems updateItems;
	
	private InsertItems insertItems;
	
	private SqlType sqlType;
	
	public Cnd() {
		where = new SqlExpressionGroup();
		orderBy = new OrderBySet();
		selectItems=new SelectItems();
		updateItems=new UpdateItems();
		insertItems=new InsertItems();
	}
	public Cnd(SqlType sqlType) {
		this();
		this.sqlType=sqlType;
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
	/**
	 * Cnd.select().addSelect("id","name").distinct().andEquals("name", "1");
	 * Cnd.where()只提供where语句
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public static Cnd where() {
		return new Cnd();
	}
	/**
	 * Cnd.select().addSelect("id","name").distinct().andEquals("name", "1");
	 * Cnd.select()设置SqlType为SELECT
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public static Cnd select() {
		Cnd cnd=new Cnd(SqlType.SELECT).addSelect();
		
		return cnd;
	}
	/**
	 * Cnd.select("id","name").distinct().andEquals("name", "1");
	 * Cnd.select()设置SqlType为SELECT
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public static Cnd select(String... selNames) {
		Cnd cnd=new Cnd(SqlType.SELECT).addSelect();
		if(selNames!=null){
			cnd.addSelect(selNames);
		}
		return cnd;
	}
	/**
	 * 是用于对象模型
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public static Cnd delete() {
		return new Cnd(SqlType.DELETE);
	}
	/**
	 * 是用于对象模型 Cnd.update().set().set()
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public static Cnd update() {
		return new Cnd(SqlType.UPDATE);
	}
	/**
	 * 注意，这里即使获取hql，最终要获取到的也是sql，因为hibernate不支持insert(....) value(....)这种形式
	 * 是用于对象模型Cnd.insert().set().set()
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public static Cnd insert() {
		return new Cnd(SqlType.INSERT);
	}
	//===============================================
	
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

//	public Cnd orIn(String name, int... ids) {
//		this.getWhere().orIn(name,ids);
//		return this;
//	}

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

//	public Cnd orNotIn(String name, int... ids) {
//		this.getWhere().orNotIn(name,ids);
//		return this;
//	}

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
	
	
	
//================================================
	public Cnd asc(String name) {
		orderBy.asc(name);
		return this;
	}

	public Cnd desc(String name) {
		orderBy.desc(name);
		return this;
	}
	
///////////////////////////////////////////////////	
	/**
	 * Cnd.select().addSelect("id","name").distinct().andEquals("name", "1");
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param names
	 * @return
	 */
	public Cnd addSelect(String... names) {
		selectItems.addNames(names);
		return this;
	}
	/**
	 * Cnd.select().addSelect("id","name").distinct().andEquals("name", "1");
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public Cnd distinct() {
		
		selectItems.setDistinct(true);
		return this;
	}
	
	
	///////////////////////的字段
	/**
	 * update和insert的时候设置值得
	 * @author mawujun 16064988@qq.com 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public Cnd set(String fieldName,Object value) {
		if(this.getSqlType()==SqlType.UPDATE){
			this.updateItems.set(fieldName, value);
		} else if(this.getSqlType()==SqlType.INSERT){
			this.insertItems.set(fieldName, value);
		} else {
			throw new RuntimeException("该方法只能用于insert和update语句");
		}
		
		return this;
	}
	//删除的字段用的
	
	
	
	
	
	
	
	
	
	
	
	
	
	//========================================================
	

//	@Override
//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		// TODO Auto-generated method stub
//		where.joinSql(classMetadata, sb);
//	}

	//AbstractEntityPersister classMetadata;
	private String from; //要更新的类名或表名
	/**
	 * 默认识不用设置的，当设置了后，会覆盖默认设置
	 * @author mawujun 16064988@qq.com 
	 * @param from
	 */
	public void setFrom(String from){
		this.from=from;
	}
	public String getFrom(){
		return this.from;
	}
	@Override
	public void joinHql( StringBuilder sb) {
		// TODO Auto-generated method stub
		AssertUtils.notNull(from);
		//sb.append("from "+classMetadata.getEntityName());
		
		if(this.getSqlType()==SqlType.SELECT){
			selectItems.joinHql(sb);
			sb.append("from "+from);
			
			where.joinHql(sb);
			orderBy.joinHql(sb);
		} else if(this.getSqlType()==SqlType.DELETE){
			sb.append("delete from "+from);
			
			where.joinHql(sb);
		} else if(this.getSqlType()==SqlType.UPDATE && updateItems.size()>0){//updateItems.size()>0防止用T进行更新的时候出现hql重复
			//int versionIndex=classMetadata.getVersionProperty();
			//if(versionIndex!=-66){//如果有version字段就更新他
			//	sb.append("update versioned  " + classMetadata.getEntityName()+ " set ");
			//} else {
				sb.append("update  " + from+ " set ");
			//}
			updateItems.joinHql(sb);
			where.joinHql(sb);
			
		}else if(this.getSqlType()==SqlType.INSERT && insertItems.size()>0){
			//因为hibernate不能支持insert into com.mawujun.repository.EntityTest(firstName,lastName,email,id)  values(?,?,?,?)这种方式
			//只能支持insert into com.mawujun.repository.EntityTest(firstName,lastName,email,id) select  * from .....
			sb.append("insert into "+ from);//这里是表明
			insertItems.joinHql(sb);
		}
		System.out.println(sb);
		
//		//sb.append("from "+classMetadata.getEntityName());
//		if(this.getSqlType()==SqlType.SELECT){
//			selectItems.joinHql(sb);
//			sb.append("from "+classMetadata.getEntityName());
//			
//			where.joinHql(sb);
//			orderBy.joinHql(classMetadata, sb);
//		} else if(this.getSqlType()==SqlType.DELETE){
//			sb.append("delete from "+classMetadata.getEntityName());
//			
//			where.joinHql(sb);
//		} else if(this.getSqlType()==SqlType.UPDATE && updateItems.size()>0){//updateItems.size()>0防止用T进行更新的时候出现hql重复
//			//sb.append("delete from "+classMetadata.getEntityName());
//			int versionIndex=classMetadata.getVersionProperty();
//			if(versionIndex!=-66){//如果有version字段就更新他
//				sb.append("update versioned  " + classMetadata.getEntityName()+ " set ");
//			} else {
//				sb.append("update  " + classMetadata.getEntityName()+ " set ");
//			}
//			updateItems.joinHql(sb);
//			where.joinHql(sb);
//			
//		}else if(this.getSqlType()==SqlType.INSERT && insertItems.size()>0){
//			//因为hibernate不能支持insert into com.mawujun.repository.EntityTest(firstName,lastName,email,id)  values(?,?,?,?)这种方式
//			//只能支持insert into com.mawujun.repository.EntityTest(firstName,lastName,email,id) select  * from .....
//			sb.append("insert into "+ classMetadata.getTableName());
//			insertItems.joinHql(sb);
//		}
//		System.out.println(sb);

	}

	@Override
	public int joinParams(Object obj,
			Object[] params, int off) {
		//int ret=off;
		 if(this.getSqlType()==SqlType.UPDATE){
			 off=updateItems.joinParams(obj, params, off);
			 off=where.joinParams(obj, params, off);
		 } else if(this.getSqlType()==SqlType.INSERT){
			 off=insertItems.joinParams(obj, params, off);
		 } else {
			 off=where.joinParams(obj, params, off);
		 }
		return off;
	}

	@Override
	public int paramCount() {
		int count=0;
		 if(this.getSqlType()==SqlType.UPDATE){
			 count=updateItems.paramCount();
			 count+=where.paramCount();
		 } else if (this.getSqlType()==SqlType.INSERT){
			 count=insertItems.paramCount();
		 } else {
			 count+=where.paramCount();
		 }
		
		return count;
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

	/**
	 * 完整的hql，不带有占位符
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param classMetadata
	 * @return
	 */
	public String toHql(AbstractEntityPersister classMetadata) {
		Object[] params = new Object[this.paramCount()];
		this.joinParams(null, params, 0);
		
//		if(this.getSqlType()==SqlType.INSERT){t
//			insertItems.joinParams(classMetadata, null, params, 0);
//		} else if(this.getSqlType()==SqlType.UPDATE){
//			updateItems.joinParams(classMetadata, null, params, 0);
//			where.joinParams(classMetadata, null, params, 0);
//		} else {
//			 where.joinParams(classMetadata, null, params, 0);
//		}
		


		if(this.sqlType==SqlType.INSERT){
			this.setFrom(classMetadata.getTableName());
		} else {
			this.setFrom(classMetadata.getEntityName());
		}
		//StringBuilder sb = new StringBuilder("from "+classMetadata.getEntityName());
		StringBuilder sb = new StringBuilder();
		this.joinHql(sb);
		String[] ss = sb.toString().split("[?]");

		sb = new StringBuilder();
		int i = 0;
		for ( i = 0; i < params.length; i++) {
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
			return new StringBuilder("'").append(escapeFieldValue(BeanUtils.copyOrCast(v, String.class))).append('\'');
	}
	public  boolean isNotNeedQuote(Object v) {
		
		return ReflectUtils.isBoolean(v) || ReflectUtils.isPrimitiveNumber(v);
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
	/**
	 * 获取语句的类型，是delete，select还是update等等
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param sqlType
	 */
	public SqlType getSqlType() {
		return sqlType;
	}
	/**
	 * 更改语句的类型，是delete，select还是update等等
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param sqlType
	 */
	public void setSqlType(SqlType sqlType) {
		this.sqlType = sqlType;
	}
	public SelectItems getSelectItems() {
		return selectItems;
	}
	public UpdateItems getUpdateItems() {
		if(updateItems==null){
			return null;
		}
		if(updateItems.size()==0){
			return null;
		}
		return updateItems;
	}
	public InsertItems getInsertItems() {
		if(insertItems==null){
			return null;
		}
		if(insertItems.size()==0){
			return null;
		}
		return insertItems;
	}
	
	

}
