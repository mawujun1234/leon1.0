package com.mawujun.repository.mybatis;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.mawujun.repository.mybatis.dialect.Dialect;
import com.mawujun.utils.PropertiesUtils;
import com.mawujun.utils.ReflectUtils;
import com.mawujun.utils.page.Page;

/**
 * 为ibatis3提供基于方言(Dialect)的分页查询的插件
 * 
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 
 * 配置文件内容:
 * <pre>
 * 	<plugins>
 *		<plugin interceptor="com.mawujun.mybatis.PageInterceptor">
 *			<property name="dialectClass" value="com.mawujun.jdbc.dialect.MySQLDialect"/>
 *		</plugin>
 *	</plugins>
 * </pre>
 * 
 * @author badqiu
 *
 */
/** 
 * 
 * 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 
 * 利用拦截器实现Mybatis分页的原理： 
 * 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句 
 * 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。在Mybatis中Statement语句是通过RoutingStatementHandler对象的 
 * prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用 
 * StatementHandler对象的prepare方法，即调用invocation.proceed()。 
 * 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设 
 * 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。 
 * 
 */  
//@Intercepts({@Signature(
//		type= Executor.class,
//		method = "query",
//		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Intercepts( {  
@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class}) })  
public class PageInterceptor implements Interceptor{
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	
	Dialect dialect;
	private String dialectClass;
	
	 /** 
     * 拦截后要执行的方法 
     */  
    public Object intercept(Invocation invocation) throws Throwable {  
       //对于StatementHandler其实只有两个实现类，一个是RoutingStatementHandler，另一个是抽象类BaseStatementHandler，  
       //BaseStatementHandler有三个子类，分别是SimpleStatementHandler，PreparedStatementHandler和CallableStatementHandler，  
       //SimpleStatementHandler是用于处理Statement的，PreparedStatementHandler是处理PreparedStatement的，而CallableStatementHandler是  
       //处理CallableStatement的。Mybatis在进行Sql语句处理的时候都是建立的RoutingStatementHandler，而在RoutingStatementHandler里面拥有一个  
       //StatementHandler类型的delegate属性，RoutingStatementHandler会依据Statement的不同建立对应的BaseStatementHandler，即SimpleStatementHandler、  
       //PreparedStatementHandler或CallableStatementHandler，在RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法。  
       //我们在PageInterceptor类上已经用@Signature标记了该Interceptor只拦截StatementHandler接口的prepare方法，又因为Mybatis只有在建立RoutingStatementHandler的时候  
       //是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。  
       RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();  
       //通过反射获取到当前RoutingStatementHandler对象的delegate属性  
       StatementHandler delegate = (StatementHandler)ReflectUtils.getFieldValue(handler, "delegate");  
       //获取到当前StatementHandler的 boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了  
       //RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。  
       BoundSql boundSql = delegate.getBoundSql();  
       //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象  
       Object obj = boundSql.getParameterObject();  
       //这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。  
       if (obj instanceof Page) {  
           Page page = (Page) obj;  
           //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性  
           MappedStatement mappedStatement = (MappedStatement)ReflectUtils.getFieldValue(delegate, "mappedStatement");  
           //拦截到的prepare方法参数是一个Connection对象  
           Connection connection = (Connection)invocation.getArgs()[0];  
           //获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句  
           String sql = boundSql.getSql();  
           //给当前的page参数对象设置总记录数  
           this.setTotalRecord(page,  mappedStatement, connection);  
           //获取分页Sql语句  
           String pageSql = this.getPageSql(page, sql);  
           //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句  
           ReflectUtils.setFieldValue(boundSql, "sql", pageSql);  
       }  
       return invocation.proceed();  
    }  
   
   
    /** 
     * 拦截器对应的封装原始对象的方法 
     */  
    public Object plugin(Object target) {  
       return Plugin.wrap(target, this);  
    }  
    
    public void setDialectClass(String dialectClass) {
		this.dialectClass = dialectClass;
		try {
			dialect = (Dialect) Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("cannot create dialect instance by dialectClass:"+ dialectClass, e);
		}
	}
	public void setProperties(Properties properties) {
		String dialectClass = new PropertiesUtils(properties).getRequiredString("dialectClass");
//		try {
//			dialect = (Dialect) Class.forName(dialectClass).newInstance();
//		} catch (Exception e) {
//			throw new RuntimeException("cannot create dialect instance by dialectClass:"+ dialectClass, e);
//		}
//		// System.out.println(OffsetLimitInterceptor.class.getSimpleName()+".dialect="+dialectClass);
		setDialectClass(dialectClass);
	}
//   
//    /** 
//     * 设置注册拦截器时设定的属性 
//     */  
//    public void setProperties(Properties properties) {  
//       this.databaseType = properties.getProperty("databaseType");  
//    }  
     
    /** 
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 
     * 其它的数据库都 没有进行分页 
     * 
     * @param page 分页对象 
     * @param sql 原sql语句 
     * @return 
     */  
    private String getPageSql(Page page, String sql) {  
//       StringBuffer sqlBuffer = new StringBuffer(sql);  
//       if ("mysql".equalsIgnoreCase(databaseType)) {  
//           return getMysqlPageSql(page, sqlBuffer);  
//       } else if ("oracle".equalsIgnoreCase(databaseType)) {  
//           return getOraclePageSql(page, sqlBuffer);  
//       }  
//       return sqlBuffer.toString(); 
       
       if (dialect.supportsLimitOffset()) {
			sql = dialect.getLimitString(sql, page.getStart(), page.getPageSize());
		} else {
			sql = dialect.getLimitString(sql, 0,  page.getPageSize());
		}
       return sql;
    }  
     
    /** 
     * 获取Mysql数据库的分页查询语句 
     * @param page 分页对象 
     * @param sqlBuffer 包含原sql语句的StringBuffer对象 
     * @return Mysql数据库分页语句 
     */  
    private String getMysqlPageSql(Page page, StringBuffer sqlBuffer) {  
       //计算第一条记录的位置，Mysql中记录的位置是从0开始的。  
       int offset = (page.getPageNo() - 1) * page.getPageSize();  
       sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());  
       return sqlBuffer.toString();  
    }  
     
    /** 
     * 获取Oracle数据库的分页查询语句 
     * @param page 分页对象 
     * @param sqlBuffer 包含原sql语句的StringBuffer对象 
     * @return Oracle数据库的分页查询语句 
     */  
    private String getOraclePageSql(Page page, StringBuffer sqlBuffer) {  
       //计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的  
       int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;  
       sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());  
       sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);  
       //上面的Sql语句拼接之后大概是这个样子：  
       //select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r >= 16  
       return sqlBuffer.toString();  
    }  
     
    /** 
     * 给当前的参数对象page设置总记录数 
     * 
     * @param page Mapper映射语句对应的参数对象 
     * @param mappedStatement Mapper映射语句 
     * @param connection 当前的数据库连接 
     */  
    private void setTotalRecord(Page page,  
           MappedStatement mappedStatement, Connection connection) {  
       //获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。  
       //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。  
       BoundSql boundSql = mappedStatement.getBoundSql(page);  
       //获取到我们自己写在Mapper映射语句中对应的Sql语句  
       String sql = boundSql.getSql();  
       //通过查询Sql语句获取到对应的计算总记录数的sql语句  
       String countSql = this.getCountSql(sql);  
       //通过BoundSql获取对应的参数映射  
       List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
       //利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。  
       BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);  
       //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象  
       ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);  
       //通过connection建立一个countSql对应的PreparedStatement对象。  
       PreparedStatement pstmt = null;  
       ResultSet rs = null;  
       try {  
           pstmt = connection.prepareStatement(countSql);  
           //通过parameterHandler给PreparedStatement对象设置参数  
           parameterHandler.setParameters(pstmt);  
           //之后就是执行获取总记录数的Sql语句和获取结果了。  
           rs = pstmt.executeQuery();  
           if (rs.next()) {  
              int totalRecord = rs.getInt(1);  
              //给当前的参数page对象设置总记录数  
              //page.setTotalRecord(totalRecord); 
              page.setTotal(totalRecord);
           }  
       } catch (SQLException e) {  
           e.printStackTrace();  
       } finally {  
           try {  
              if (rs != null)  
                  rs.close();  
               if (pstmt != null)  
                  pstmt.close();  
           } catch (SQLException e) {  
              e.printStackTrace();  
           }  
       }  
    }  
     
    /** 
     * 根据原Sql语句获取对应的查询总记录数的Sql语句 
     * @param sql 
     * @return 
     */  
    private String getCountSql(String sql) {  
       int index = sql.indexOf("from");  
       return "select count(*) " + sql.substring(index);  
    }  
     
    
	
//	public Object intercept(Invocation invocation) throws Throwable {
//		processIntercept(invocation.getArgs());
//		return invocation.proceed();
//	}
//
//	void processIntercept(final Object[] queryArgs) {
//		//queryArgs = query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
//		MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
//		Object parameter = queryArgs[PARAMETER_INDEX];
//		final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
//		int offset = rowBounds.getOffset();
//		int limit = rowBounds.getLimit();
//		
//		if(dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
//			BoundSql boundSql = ms.getBoundSql(parameter);
//			String sql = boundSql.getSql().trim();
//			if (dialect.supportsLimitOffset()) {
//				sql = dialect.getLimitString(sql, offset, limit);
//				offset = RowBounds.NO_ROW_OFFSET;
//			} else {
//				sql = dialect.getLimitString(sql, 0, limit);
//			}
//			limit = RowBounds.NO_ROW_LIMIT;
//			
//			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset,limit);
//			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
//			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
//			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
//		}
//	}
//	
//	//see: MapperBuilderAssistant
//	private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
//		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());
//		
//		builder.resource(ms.getResource());
//		builder.fetchSize(ms.getFetchSize());
//		builder.statementType(ms.getStatementType());
//		builder.keyGenerator(ms.getKeyGenerator());
//		//builderskeyProperty(ms.getKeyProperty());
//		
//		//setStatementTimeout()
//		builder.timeout(ms.getTimeout());
//		
//		//setStatementResultMap()
//		builder.parameterMap(ms.getParameterMap());
//		
//		//setStatementResultMap()
//		builder.resultMaps(ms.getResultMaps());
//		builder.resultSetType(ms.getResultSetType());
//	    
//		//setStatementCache()
//		builder.cache(ms.getCache());
//		builder.flushCacheRequired(ms.isFlushCacheRequired());
//		builder.useCache(ms.isUseCache());
//		
//		return builder.build();
//	}
//
//	public Object plugin(Object target) {
//		return Plugin.wrap(target, this);
//	}
//
//	public void setProperties(Properties properties) {
//		String dialectClass = new PropertiesUtils(properties).getRequiredString("dialectClass");
//		try {
//			dialect = (Dialect)Class.forName(dialectClass).newInstance();
//		} catch (Exception e) {
//			throw new RuntimeException("cannot create dialect instance by dialectClass:"+dialectClass,e);
//		} 
//		//System.out.println(OffsetLimitInterceptor.class.getSimpleName()+".dialect="+dialectClass);
//	}
//	
//	public static class BoundSqlSqlSource implements SqlSource {
//		BoundSql boundSql;
//		public BoundSqlSqlSource(BoundSql boundSql) {
//			this.boundSql = boundSql;
//		}
//		public BoundSql getBoundSql(Object parameterObject) {
//			return boundSql;
//		}
//	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}


	public String getDialectClass() {
		return dialectClass;
	}


	
	
}
