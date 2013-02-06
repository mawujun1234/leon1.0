package com.mawujun.repository.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

public abstract class MybatisBatchRepository {

	/**
	 * 返回用于批量处理，还是普通处理的sqlSession
	 * @param batch
	 * @return
	 */
	public abstract SqlSession getBatchSqlSession();
	

	/**
	 * ，不能有一个存在使用不同ExecutorType运行的事务。也要保证在不同的事务中，使用不同执行器来调用SqlSessionTemplate时，比如PROPAGATION_REQUIRES_NEW或完全在一个事务外面
	 * 注意如果已经调用过别的方法，再来调用这个的话，将会发生异常，只能单独调用这个方法不能和其他方法混合调用
	 * <!-- 在外部for循环调用一千次 -->
		<insert id="insert" parameterType="sdc.mybatis.test.Student">
		    insert into student (id, name, sex,
		    address, telephone, t_id
		    )
		    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR},
		    #{sex,jdbcType=VARCHAR},
		    #{address,jdbcType=VARCHAR}, #{telephone,jdbcType=VARCHAR}, #{tId,jdbcType=INTEGER}
		    )
		</insert>
		<!--  批量 ，传入一个长度为1000的list  -->
		<insert id="insertBatch" >
		    insert into student ( <include refid="Base_Column_List" /> ) 
		    values 
		    <foreach collection="list" item="item" index="index" separator=",">
		        (null,#{item.name},#{item.sex},#{item.address},#{item.telephone},#{item.tId})
		    </foreach>
		</insert>
		http://www.cnblogs.com/xcch/articles/2042298.html
	 * @param statement
	 * @param params ExecutorType=true 表示使用ExecutorType.BATCH，这样的话就不能和其他ExecutorType运行的事务同时使用
	 * @param params
	 * @param forEach 是否采用在xml中使用foreach ，这个效率更高，快一倍以上
	 */
	public int batchInsert(String statement,final List params,boolean... forEach) {

		SqlSession sqlSession=this.getBatchSqlSession();
		//sqlSession.
		if(forEach!=null && forEach.length!=0&&forEach[0]==true){
			return sqlSession.insert(statement, params);
		} else {
			int[] result=new int[params.size()];
			int i=0;
			for(Object t:params){
				result[i]=sqlSession.insert(statement, t);
				i++;
			}
			return i;
		}
	}
	/**
	 * ，不能有一个存在使用不同ExecutorType运行的事务。也要保证在不同的事务中，使用不同执行器来调用SqlSessionTemplate时，比如PROPAGATION_REQUIRES_NEW或完全在一个事务外面
	 * @param statement
	 * @param params
	 * * @param params ExecutorType=true 表示使用ExecutorType.BATCH，这样的话就不能和其他ExecutorType运行的事务同时使用
	 * @param forEach
	 * @return
	 */
	public int batchDelete(String statement,final List params,boolean... forEach) {

		SqlSession sqlSession=this.getBatchSqlSession();
		if(forEach!=null && forEach.length!=0&&forEach[0]==true){
			return sqlSession.delete(statement, params);
		} else {
			int[] result=new int[params.size()];
			int i=0;
			for(Object t:params){
				result[i]=sqlSession.delete(statement, t);
				i++;
			}
			return i;
		}
	}
	/**
	 * ，不能有一个存在使用不同ExecutorType运行的事务。也要保证在不同的事务中，使用不同执行器来调用SqlSessionTemplate时，比如PROPAGATION_REQUIRES_NEW或完全在一个事务外面
	 * @param statement
	 * @param params
	 * @param params ExecutorType=true 表示使用ExecutorType.BATCH，这样的话就不能和其他ExecutorType运行的事务同时使用
	 * @param forEach
	 * @return
	 */
	public int batchUpdate(String statement,final List params,boolean... forEach) {

		SqlSession sqlSession=this.getBatchSqlSession();
		if(forEach!=null && forEach.length!=0&&forEach[0]==true){
			return sqlSession.update(statement, params);
		} else {
			int[] result=new int[params.size()];
			int i=0;
			for(Object t:params){
				result[i]=sqlSession.update(statement, t);
				i++;
			}
			return i;
		}
	}
	
	public Object batchSelectOneObj(String statement) {
		return getBatchSqlSession().selectOne(statement);
	}
	public Object batchSelectOneObj(String statement,Object params) {
		if(params==null){
			this.batchSelectOne(statement);
		}
		return getBatchSqlSession().selectOne(statement, params);
	}
	public Map<String,Object> batchSelectOne(String statement) {
		return getBatchSqlSession().selectOne(statement);
	}
	public Map<String,Object> batchSelectOne(String statement,Object params) {
		if(params==null){
			this.batchSelectOne(statement);
		}
		return getBatchSqlSession().selectOne(statement, params);
	}

	/**
	 * 支持动态排序
	 * @param statement
	 * @param params
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Map<String, Object>> batchSelectPage(String statement, PageRequest pageRequest)  {	

		QueryResult<Map<String, Object>> page = new QueryResult<Map<String, Object>>(pageRequest);
		Object params=pageRequest.getParams();
		if(page.isCountTotal()) {
			int totalCount=this.getBatchSqlSession().selectOne(statement+".count", params);
			page.setTotalItems(totalCount);
		}
//		//params.put("sortColumns", pageRequest.getSortColumns());
//		if(params!=null) {
//			if(params instanceof Map){//只有传入参数P为map的时候，才能动态排序
//				if( pageRequest.getSortColumns()!=null){
//					((Map)params).put("sortColumns", pageRequest.getSortColumns());
//				}
//			} 
//		}
		List<Map<String, Object>> result=getBatchSqlSession().selectList(statement,params,new RowBounds(pageRequest.getStart(),pageRequest.getPageSize()));	
		page.setResult(result);
		return page;
	}


	public QueryResult<Object> batchSelectPageObj(String statement, PageRequest pageRequest) {		
		QueryResult<Object> page = new QueryResult<Object>(pageRequest);
		Object params=pageRequest.getParams();
		if(page.isCountTotal()) {
			int totalCount=this.getBatchSqlSession().selectOne(statement+".count", params);
			page.setTotalItems(totalCount);
		}
//		//params.put("sortColumns", pageRequest.getSortColumns());
//		if(params!=null) {
//			if(params instanceof Map){//只有传入参数P为map的时候，才能动态排序
//				if( pageRequest.getSortColumns()!=null){
//					((Map)params).put("sortColumns", pageRequest.getSortColumns());
//				}
//			} 
//		}
		List<Object> result=getBatchSqlSession().selectList(statement,params,new RowBounds(pageRequest.getStart(),pageRequest.getPageSize()));	
		page.setResult(result);
		return page;
	}
	
	
	public List<Map<String,Object>> batchSelectList(String statement, Object params)  {	
		if(params==null){
			return this.batchSelectList(statement);
		}
		List<Map<String,Object>> result=getBatchSqlSession().selectList(statement,params);	
		return result;
	}
	public List<Map<String,Object>> batchSelectList(String statement)  {	
		List<Map<String,Object>> result=getBatchSqlSession().selectList(statement);	
		return result;
	}
	
	public List<Object> batchSelectListObj(String statement, Object params)  {	
		if(params==null){
			return this.batchSelectListObj(statement);
		}
		List<Object> result=getBatchSqlSession().selectList(statement,params);	
		return result;
	}
	public List<Object> batchSelectListObj(String statement)  {	
		List<Object> result=getBatchSqlSession().selectList(statement);	
		return result;
	}
	
	
	public Map<String,Object> batchSelectMap(String statement,String mapKey) {
		return this.getBatchSqlSession().selectMap(statement, mapKey);
	}
	
	public Map<String,Object> batchSelectMap(String statement, Object params,String mapKey) {
		if(params==null){
			return this.batchSelectMap(statement, mapKey);
		}
		return this.getBatchSqlSession().selectMap(statement, params, mapKey);
	}
	
	public int batchSelectInt(String statement, Object params) {
		return (Integer)this.getBatchSqlSession().selectOne(statement, params);
	}
	
	public long batchSelectLong(String statement, Object params) {
		return (Long)this.getBatchSqlSession().selectOne(statement, params);
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
	public void batchCallProcedure(String statement,Object params){
		this.getBatchSqlSession().update(statement);
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
	public Object batchCallProcedureReturnOne(String statement,Object parameter){
		return this.getBatchSqlSession().selectOne(statement, parameter);
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
	public List batchCallProcedureReturnList(String statement,Object parameter){
		return this.getBatchSqlSession().selectList(statement, parameter);
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
	public Object batchCallProcedureReturnParam(String statement,Object parameter){
		this.getBatchSqlSession().selectList(statement, parameter);
		return parameter;
	}
	
}
