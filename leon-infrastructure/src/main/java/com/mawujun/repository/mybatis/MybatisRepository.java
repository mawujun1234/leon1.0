package com.mawujun.repository.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.mawujun.repository.idEntity.UUIDEntity;
import com.mawujun.repository.idEntity.UUIDGenerator;
import com.mawujun.utils.ReflectionUtils;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

//@Repository
/**
 * 会自动分页，自动统计总数,支持动态排序
 * @author mawujun
 */
public class MybatisRepository  {
	
	//=================================拷贝于SqlSessionDaoSupport
	//private SqlSessionFactory sqlSessionFactory;
	protected SqlSession sqlSession;
	//private SqlSession batchSqlSession;//用于批处理的sql session
	protected SqlSessionFactory sqlSessionFactory;
	@Autowired(required = true)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
	      this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);    //进行sqlSession的打开关闭管理
	      //this.batchSqlSession=new SqlSessionTemplate(sqlSessionFactory,ExecutorType.BATCH);
	      this.sqlSessionFactory=sqlSessionFactory;
	}
	
	protected SqlSessionFactory getSqlSessionFactory(){
			return this.sqlSessionFactory;
	}

//	public SqlSession getBatchSqlSession() {
//		return batchSqlSession;
//	}
	/**
	 * @param BATCH true,就返回ExecutorType.BATCH的SqlSession
	 */
	public SqlSession getSqlSession() {
		return sqlSession;
	}
//	@Override
//	public SqlSession getBatchSqlSession() {
//		// TODO Auto-generated method stub
//		return this.batchSqlSession;
//	}


	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

  //=================================拷贝于SqlSessionDaoSupport
	
	
	public int insert(String statement) {
		return getSqlSession().insert(statement);
	}
	/**
	 * 如果T继承了UUIDEntity，就使用自动继承
	 * 如果是用id自动增长，就在Mapper配置文件中修改
	 * @param statement
	 * @param obj 可以是参数Map，也可以是实体类Bean
	 * @return
	 */
	public int insert(String statement,Object obj) {
		//如果该类使用UUID作为主键，那就自动生成
		if(obj instanceof UUIDEntity){
			((UUIDEntity) obj).setId(UUIDGenerator.generate());
		}
		if(obj==null){
			this.insert(statement);
		}
		return getSqlSession().insert(statement, obj);
	}
	
	public int update(String statement) {
		return getSqlSession().update(statement);
	}
	public int update(String statement,Object obj) {
		if(obj==null){
			this.update(statement);
		}
		return getSqlSession().update(statement, obj);
	}
	public int delete(String statement) {
		return getSqlSession().delete(statement);
	}

	public int delete(String statement,Object obj) {
		if(obj==null){
			this.delete(statement);
		}
		return getSqlSession().delete(statement, obj);
	}
	
	public Object selectOneObj(String statement) {
		return getSqlSession().selectOne(statement);
	}
	public Object selectOneObj(String statement,Object params) {
		if(params==null){
			this.selectOne(statement);
		}
		return getSqlSession().selectOne(statement, params);
	}
	public Map<String,Object> selectOne(String statement) {
		return getSqlSession().selectOne(statement);
	}
	public Map<String,Object> selectOne(String statement,Object params) {
		if(params==null){
			this.selectOne(statement);
		}
		return getSqlSession().selectOne(statement, params);
	}
	


	/**
	 * 
	 * @param statement
	 * @param params
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Map<String, Object>> selectPage(String statement, PageRequest pageRequest)  {	

		QueryResult<Map<String, Object>> page = new QueryResult<Map<String, Object>>(pageRequest);
		Map params=prepareCountAndDynicSortSql(statement,pageRequest);
		
		

		if(page.isCountTotal()) {
			createCountMapperstatement(statement);
		}
		if(page.isCountTotal()) {
			List<Map<String, Object>> result=getSqlSession().selectList(statement,params,new RowBounds(pageRequest.getStart(),pageRequest.getPageSize()));	
			page.setResult(result);
			int totalCount=(Integer)this.getSqlSession().selectOne(statement+"_count", params);
			page.setTotalItems(totalCount);
			
		} else {
			List<Map<String, Object>> result=getSqlSession().selectList(statement,params);	
			page.setResult(result);
			page.setTotalItems(result.size());
			page.setPageNo(1);
			page.setPageSize(result.size());
		}
		
		return page;
	}
	

	/**
	 * 如果xml文件中定义的resultType是实体类或map，也可以转换成record，但是回消耗性能，最好是在xml中定义为record
	 * @param statement
	 * @param pageRequest
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public QueryResult<Record> selectPageRecord(String statement, PageRequest pageRequest)  {
		MappedStatement mappedStatement=sqlSessionFactory.getConfiguration().getMappedStatement(statement);
		QueryResult<Record> page = new QueryResult<Record>(pageRequest);
		
		Map params=prepareCountAndDynicSortSql(statement,pageRequest);

		//转换成record
		Class resultTypeClass=mappedStatement.getResultMaps().get(0).getType();//.getName(); 
		if(resultTypeClass.getName().equals(Record.class.getName())){
			if(page.isCountTotal()) {
				int totalCount=(Integer)this.getSqlSession().selectOne(statement+"_count", params);
				page.setTotalItems(totalCount);	
				List<Record> result=getSqlSession().selectList(statement,params,new RowBounds(pageRequest.getStart(),pageRequest.getPageSize()));	
				page.setResult(result);
			} else {
				List<Record> result=getSqlSession().selectList(statement,params);	
				page.setResult(result);
				page.setTotalItems(page.getResult().size());
				page.setPageNo(1);
				page.setPageSize(page.getResult().size());
			}
		} else {//有可能是map，也有可能是实体类
			if(page.isCountTotal()) {
				int totalCount=(Integer)this.getSqlSession().selectOne(statement+"_count", params);
				page.setTotalItems(totalCount);	
				List<Record> result=getSqlSession().selectList(statement,params,new RowBounds(pageRequest.getStart(),pageRequest.getPageSize()));	
				page.setResult(result);
			} else {
				List<Record> result=getSqlSession().selectList(statement,params);	
				page.setResult(result);
				page.setTotalItems(page.getResult().size());
				page.setPageNo(1);
				page.setPageSize(page.getResult().size());
			}
		}

		return page;
	}

	public QueryResult<Object> selectPageObj(String statement, PageRequest pageRequest) {		
		QueryResult<Object> page = new QueryResult<Object>(pageRequest);
		Map params=prepareCountAndDynicSortSql(statement,pageRequest);
		
		if(page.isCountTotal()) {
			createCountMapperstatement(statement);
		}
		this.getSql(statement, params);
		if(page.isCountTotal()) {
			int totalCount=(Integer)this.getSqlSession().selectOne(statement+"_count", params);
			
			page.setTotalItems(totalCount);
			List<Object> result=getSqlSession().selectList(statement,params,new RowBounds(pageRequest.getStart(),pageRequest.getPageSize()));	
			page.setResult(result);
		} else {
			List<Object> result=getSqlSession().selectList(statement,params);	
			page.setResult(result);
			page.setTotalItems(result.size());
			page.setPageNo(1);
			page.setPageSize(result.size());
		}
		return page;
	}
	
	public <M> QueryResult<M> selectPage(String statement, PageRequest pageRequest,Class<M> classM) {		
		QueryResult<M> page = new QueryResult<M>(pageRequest);
		Map params=prepareCountAndDynicSortSql(statement,pageRequest);
		
		if(page.isCountTotal()) {
			createCountMapperstatement(statement);
		}
		this.getSql(statement, params);
		if(page.isCountTotal()) {
			int totalCount=(Integer)this.getSqlSession().selectOne(statement+"_count", params);
			
			page.setTotalItems(totalCount);
			List<M> result=getSqlSession().selectList(statement,params,new RowBounds(pageRequest.getStart(),pageRequest.getPageSize()));	
			page.setResult(result);
		} else {
			List<M> result=getSqlSession().selectList(statement,params);	
			page.setResult(result);
			page.setTotalItems(result.size());
			page.setPageNo(1);
			page.setPageSize(result.size());
		}
		return page;
	}
	
	
	public List<Map<String,Object>> selectList(String statement, Object params)  {	
		if(params==null){
			return this.selectList(statement);
		}
		List<Map<String,Object>> result=getSqlSession().selectList(statement,params);	
		return result;
	}
	public List<Map<String,Object>> selectList(String statement)  {	
		List<Map<String,Object>> result=getSqlSession().selectList(statement);	
		return result;
	}
	
	public List<Object> selectListObj(String statement, Object params)  {	
		if(params==null){
			return this.selectListObj(statement);
		}
		List<Object> result=getSqlSession().selectList(statement,params);	
		return result;
	}
	
	public <M> List<M> selectList(String statement, Object params,Class<M> classM)  {	
		if(params==null){
			return this.selectListObj(statement,classM);
		}
		List<M> result=getSqlSession().selectList(statement,params);	
		return result;
	}
	public <M> List<M> selectListObj(String statement,Class<M> classM)  {	
		List<M> result=getSqlSession().selectList(statement);	
		return result;
	}
	
	public List<Object> selectListObj(String statement)  {	
		List<Object> result=getSqlSession().selectList(statement);	
		return result;
	}
	
	
	public Map<String,Object> selectMap(String statement,String mapKey) {
		return this.getSqlSession().selectMap(statement, mapKey);
	}
	
	public Map<String,Object> selectMap(String statement, Object params,String mapKey) {
		if(params==null){
			return this.selectMap(statement, mapKey);
		}
		return this.getSqlSession().selectMap(statement, params, mapKey);
	}
	
	public int selectInt(String statement, Object params) {
		return (Integer)this.getSqlSession().selectOne(statement, params);
	}
	
	public long selectLong(String statement, Object params) {
		return (Long)this.getSqlSession().selectOne(statement, params);
	}
	
	

	/**
	 * 无返回值的存储过程
	 * <update id="execProcedure" statementType="CALLABLE" parameterType="com.axman.Person">
      	{ call proc_test(#{name},#{ago})}
  		</update>
  		无返回值调用时使用update，不要用select[ForXXX],否则会死等
	 * @param statement
	 * @param params
	 */
	public void callProcedure(String statement,Object params){
		this.getSqlSession().update(statement);
	}
	/**
	 * 返回一个值或一个对象的存储过程
	 * BEGIN
		    #Routine body goes here...
		    insert into Person (name,ago) values (pname,pago);
		     select last_insrt_id();
		END
	 * <select id="execProcedure" statementType="CALLABLE" parameterType="com.axman.Person">
      	{ call proc_test(#{name},#{ago})}
  	   </select>
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public Object callProcedureReturnOne(String statement,Object parameter){
		return this.getSqlSession().selectOne(statement, parameter);
	}
	/**
	 * 存储过程返回一个结果集的查询，和普通的查询一样了
	 * BEGIN
		    #Routine body goes here...
		    insert into Person (name,ago) values (pname,pago);
		     select * from Person;
		END
		 <select id="execProcedure" statementType="CALLABLE" parameterType="com.axman.Person" resultType="hashmap">
      			{ call proc_test(#{name},#{ago})}
  		</select>
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public List callProcedureReturnList(String statement,Object parameter){
		return this.getSqlSession().selectList(statement, parameter);
	}
	/**
	 * 通过存储过程的输出参数来返回需要的值，输出参数可能有多个，存储过程有三种in,out,in-out
	 * 注意mode这个参数还要加上jdbcType这个参数，这两个参数都是需要的
	 * create or replace procedure pp(x in number, y in number ,z out  number ) is  
		  begin  
		      select  x + y into z  from dual;  
		end pp; 
		
		<select id="selectByProc" statementType="CALLABLE">  
		{call pp(#{x},#{y},#{z,mode=OUT,jdbcType=INTEGER})}  
		</select> 
		调用:
		SqlSession  session  =  .......  
        Map map = new HashMap();  
        map.put("x", 1);  
        map.put("y", 2);  
        map.put("z", 0);  
        session.selectOne("emp.selectProc", map);  
        System.out.println(map.get("z"));  
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public Object callProcedureReturnParam(String statement,Object parameter){
		this.getSqlSession().selectList(statement, parameter);
		return parameter;
	}
	
//	/**
//	 * 返回带有占位符?的sql语句和占位符对应的参数
//	 * result[0]:String,是sql语句
//	 * result[1]:List<Object>是占位符对应的参数
//	 * @param statement
//	 * @param params
//	 * @return
//	 */
//	public Object[] getSql1(String statement, Object parameterObject) {
//		Object[] result=new Object[2];
//		MappedStatement ms=getSqlSession().getConfiguration().getMappedStatement(statement);	
//		BoundSql boundSql=ms.getBoundSql(parameterObject);
//		
//		
//		String tempHql=boundSql.getSql();//带有？占位符的sql
//		//System.out.println();
//		List<ParameterMapping> mapping=boundSql.getParameterMappings();
//		
//		List<Object> args = new ArrayList<Object>();
//		MetaObject metaObject = parameterObject == null ? null : MetaObject.forObject(parameterObject);  
//		for (ParameterMapping parameterMapping : mapping) {
//			if (parameterMapping.getMode() != ParameterMode.OUT) {
//				Object value;
//				String propertyName = parameterMapping.getProperty();
//				PropertyTokenizer prop = new PropertyTokenizer(propertyName);
//				if (parameterObject == null) {
//					value = null;
//				} else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
//					value = parameterObject;
//				} else if (boundSql.hasAdditionalParameter(propertyName)) {
//					value = boundSql.getAdditionalParameter(propertyName);
//				} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
//						&& boundSql.hasAdditionalParameter(prop.getName())) {
//					value = boundSql.getAdditionalParameter(prop.getName());
//					if (value != null) {
//						value = MetaObject.forObject(value).getValue(propertyName.substring(prop.getName().length()));
//					}
//				} else {
//					value = metaObject == null ? null : metaObject.getValue(propertyName);
//				}
//				args.add(value);
//			}
//			
//		}
//		result[0]=tempHql;
//		result[1]=args;
//		return result;
//	}
	
	/**
	 * 返回sql语句
	 * @param statement
	 * @param params
	 * @return
	 */
	public String getSql(String statement, Object parameterObject) {
		MappedStatement ms=getSqlSession().getConfiguration().getMappedStatement(statement);	
		//BoundSql boundSql=ms.getBoundSql(parameterObject);
		SqlSource src=ms.getSqlSource();
		String sql=src.getBoundSql(parameterObject).getSql();
		src.getBoundSql(parameterObject).getAdditionalParameter("name");
		return sql;
	}


	/**
	 * 根据已经有的select语句自动构建求总数的sql,注意还要去掉order by等语句 用来提高sql效率
	 */
	private void createCountMapperstatement(String statement){
		if(sqlSessionFactory.getConfiguration().hasStatement(statement+"_count")){
			return;
		}
		MappedStatement mappedStatement=sqlSessionFactory.getConfiguration().getMappedStatement(statement);

		SqlSource sqlSourceOrginal=mappedStatement.getSqlSource();
		SqlNode sqlNode=(SqlNode) ReflectionUtils.getFieldValue(sqlSourceOrginal, "rootSqlNode");
		if(sqlNode instanceof MixedSqlNode){
			 List<SqlNode> contentsOrginal=(List<SqlNode>) ReflectionUtils.getFieldValue(sqlNode, "contents");
			 List<SqlNode> contents=new ArrayList<SqlNode>();
			 contents.addAll(contentsOrginal);
			 
			 //去掉order by语句
			 int i=0;
			 boolean removeSelectBool=true;
			 for(SqlNode node:contentsOrginal){
				 if(node instanceof TextSqlNode){
					 String sql=(String) ReflectionUtils.getFieldValue(node, "text");
					 //只对第一个select子句进行转换,忽略子查询中的select
					 if(removeSelectBool){
						 int fromIndex=sql.toLowerCase().indexOf("from");
						 if(fromIndex>0){
							 sql="select count (*) "+sql.substring(fromIndex);
						 }
						 removeSelectBool=false;
					 }
					 
					 
					 sql=removeOrders(sql);
					 TextSqlNode text=new TextSqlNode(sql);
					 contents.remove(i);
					 contents.add(i, text);
				 }
				 i++;
			 }
			 
//			 //也可以不用子查询，只需要把select 语句 替换成select count(*)，但是如果使用union或union all的时候
//			 //就需要用子查询包起来再查总数，自动分页的时候也存在这个问题吧
//			 TextSqlNode test=new TextSqlNode("select count(*) from (");
//			 contents.add(0, test);
//			 TextSqlNode test1=new TextSqlNode(")");
//			 contents.add(test1);
			 
			 MixedSqlNode newMixedSqlNode=new MixedSqlNode(contents);
			 DynamicSqlSource newDynamicSqlSource=new DynamicSqlSource(sqlSessionFactory.getConfiguration(),newMixedSqlNode);
			// System.out.println(newDynamicSqlSource.getBoundSql(null).getSql());
			 
			MapperBuilderAssistant assistant=new MapperBuilderAssistant(sqlSessionFactory.getConfiguration(),mappedStatement.getResource());

			assistant.setCurrentNamespace(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf('.')));
			String parameterMap=null;
			if(mappedStatement.getParameterMap().getId().endsWith("-Inline")){
				parameterMap=null;
			} else {
				parameterMap=mappedStatement.getParameterMap().getId();
			}
//			 assistant.addMappedStatement(
//						statement+"_count", 
//						newDynamicSqlSource, 
//						mappedStatement.getStatementType(), 
//						mappedStatement.getSqlCommandType(), 
//						mappedStatement.getFetchSize(),
//						mappedStatement.getTimeout(), 
//						parameterMap, 
//						mappedStatement.getParameterMap().getType(), //参数类型parameterType
//						null, //resultMap
//						Integer.class,//resultType
//						mappedStatement.getResultSetType(), 
//						true//flushCache
//						, false//useCache
//						, mappedStatement.getKeyGenerator(),
//						null, //keyProperty
//						null, //keyColumn
//						mappedStatement.getDatabaseId());
//			 
			 assistant.addMappedStatement(statement+"_count", newDynamicSqlSource, 
					 mappedStatement.getStatementType(), 
					 mappedStatement.getSqlCommandType(), 
					 mappedStatement.getFetchSize(), 
					 mappedStatement.getTimeout(), 
					 parameterMap,
					 mappedStatement.getParameterMap().getType(), 
					 null, 
					 Integer.class, 
					 mappedStatement.getResultSetType(), 
					 true, false,
					 false,//resultOrdered,
					 mappedStatement.getKeyGenerator(), 
					 null, null, mappedStatement.getDatabaseId(), mappedStatement.getLang());

		}
		//System.out.println("============================"+sqlSessionFactory.getConfiguration().hasStatement(statement+"_count"));
		//System.out.println("============================"+sqlSessionFactory.getConfiguration().getMappedStatement(statement+"_count").getSqlSource().getBoundSql(null).getSql());	
	  
	}
	private static String removeOrders(String hql) {
		//Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Pattern p = Pattern.compile("order\\s*by([\\s*|,]\\w+(asc|desc|\\s*)*)+", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	private Map prepareCountAndDynicSortSql(String statement, PageRequest pageRequest){
		//这个必须放在动态排序前面
		if(pageRequest.isCountTotal()) {
			createCountMapperstatement(statement);
		}
		
		Map params=pageRequest.getParams();
		
		MappedStatement mappedStatement=sqlSessionFactory.getConfiguration().getMappedStatement(statement);

		//使用动态排序，参数必须是map，如果参数不是map，就不能进行动态排序的判断
			//只有请求了设置了动态排序，才会去动态排序
			if(pageRequest.hasSort()&& mappedStatement.getParameterMap().getType()== Map.class){
				//如果查询条件中存在排序，就去掉原有的排序，然后设置自己的排序
				replaceSortInfo(statement);	
			} 
			
			//包含就表示要使用动态排序
			if(defaultOrderByCache.containsKey(statement)){
				if(params==null){
					params=new HashMap<String,Object>();
				}
				//生成动态排序的参数，并且修改orderBy这个key
				if(pageRequest.hasSort()){
					String orderBy=" order by "+pageRequest.getSortsString();
					((Map)params).put("orderBy1111111111", orderBy);
				} else {
					((Map)params).put("orderBy1111111111", defaultOrderByCache.get(statement));
				}
			}
			
//			System.out.println("=========================================================");
//			SqlSource sqlSourceOrginal=sqlSessionFactory.getConfiguration().getMappedStatement(statement).getSqlSource();
//			SqlNode sqlNode=(SqlNode) ReflectionUtils.getFieldValue(sqlSourceOrginal, "rootSqlNode");
//			if(sqlNode instanceof MixedSqlNode){
//				 List<SqlNode> contents=(List<SqlNode>) ReflectionUtils.getFieldValue(sqlNode, "contents");
//				 System.out.println(contents.size());
//			}
//			System.out.println(sqlSessionFactory.getConfiguration().getMappedStatement(statement).getSqlSource().getBoundSql(params).getSql());
//		
		return params;
	}

	//缓存默认的排序
	private static Map<String,String> defaultOrderByCache=new HashMap<String,String>();
	private void replaceSortInfo(String statement){
		if(defaultOrderByCache.containsKey(statement)){
			return;
		}
		//
		//先判断在sql的最后是否存在order by，如果存在就把order by 之后的内容，把该order by设置为默认的order by，再把order by 后面的内容去掉，
		//就把order by替换成 <if test="orderBy!=null">${orderBy}</if>这种形式，
		//再把新的order by以参数的形式添加进去
		//如果不存在，就往最后添加order by内容，也是添加 <if test="orderBy!=null">#{orderBy}</order by>
		//但这里有个问题，两个表关联查询的时候，有字段相同，并且用这个字段进行排序的时候怎么办，需要在前段指定别名
		//先测试在子查询中存在order by的时候，count能正常处理不。会不会把后面有用的东西也替换掉了,是的，这里有一个这样的bug
		//子查询中也可以使用order by（在子查询中先排序，然后取前几位），但是要在查询top-n的时候。一般是指最大的n条记录或着是最小的n条记录。
		//mysql子查询中可以使用order by
		//但一般不建议这么做，建议在外面做order by
		MappedStatement mappedStatement=sqlSessionFactory.getConfiguration().getMappedStatement(statement);

		SqlSource sqlSourceOrginal=mappedStatement.getSqlSource();
		SqlNode sqlNode=(SqlNode) ReflectionUtils.getFieldValue(sqlSourceOrginal, "rootSqlNode");
		if(sqlNode instanceof MixedSqlNode){
			 List<SqlNode> contents=(List<SqlNode>) ReflectionUtils.getFieldValue(sqlNode, "contents");
			 //去掉order by语句
			 //只要判断了order by aaa asc等等，并且以asc||desc||空白结尾的就行了，并替换掉他，如果有这个就可以了
			 int i=0;
			 int lastOrderBy=-1;
			 
			 Pattern p = Pattern.compile("order\\s*by([\\s*|,]\\w+(asc|desc|\\s*)*$)+",Pattern.CASE_INSENSITIVE);
			 for(SqlNode node:contents){
				 if(node instanceof TextSqlNode){
					String sql = (String) ReflectionUtils.getFieldValue(node,"text");
					Matcher m = p.matcher(sql);
					
					while (m.find()) {
						lastOrderBy=i;
					}
				 }
				 i++;
			 }
			 //替换掉已有的order by为动态sql
			 //String matchContent=null;//缓存默认的排序
			 if(lastOrderBy>-1){
				 SqlNode node=contents.get(lastOrderBy);
				 String sql = (String) ReflectionUtils.getFieldValue(node,"text");
					Matcher m = p.matcher(sql);
					StringBuffer sb = new StringBuffer();
					while (m.find()) {
						m.appendReplacement(sb, "");
						//matchContent=m.group();
						defaultOrderByCache.put(statement, m.group());
					}
					m.appendTail(sb);
					//System.out.println(sb.toString());
					//System.out.println(sb.length());
					//System.out.println(matchContent);
					
					//去掉原来的order by
					ReflectionUtils.setFieldValue(node, "text", sb.toString());	
			 } 
			//添加动态的order by
				//<if test="orderBy!=null">${orderBy}</if>
				TextSqlNode orderByNode=new TextSqlNode(" ${orderBy1111111111} ");
				List<SqlNode> contentsIf=new ArrayList<SqlNode>();
				contentsIf.add(orderByNode);
				MixedSqlNode newMixedSqlNode=new MixedSqlNode(contentsIf);
				IfSqlNode ifSqlNode=new IfSqlNode(newMixedSqlNode," orderBy1111111111!=null ");
				System.out.println(11);
				contents.add(ifSqlNode);
		}
	}
	
}
