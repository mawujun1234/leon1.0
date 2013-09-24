package com.mawujun.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.exception.DefaulExceptionCode;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.mybatis.Record;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.utils.page.WhereInfo;

/**
 * 基础的service，封装了一些通用的代码，减少代码重复的编写
 * @author mawujun
 *
 */
public abstract class BaseService<T extends IdEntity<ID>, ID extends Serializable> {
	/**
	 * 返回一个具体实体类的Repository，每个service都必须实现这个方法
	 * 供默认的方法调用
	 */
	public abstract BaseRepository<T,ID> getRepository();
	
	public void create(T entity) {
		getRepository().create(entity);
	}
	
	public void update(T entity) {
		getRepository().update(entity);
	}
	
	public void delete(T entity) {
		getRepository().delete(entity);
	}
	public void delete(ID id) {
		getRepository().delete(id);
	}
	public T get(ID id) {
		return getRepository().get(id);
	}
	
	public List<T> queryAll() {
		return getRepository().queryAll();
	}
	/**
	 * 简单对象的查询，根据一个对象的属性进行过滤的简单查询,使用案例：
	 * WhereInfo whereinfo=WhereInfo.parse("parent.id", node);
	 * BaseService.query(whereinfo);
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param whereInfos
	 * @return
	 */
	public List<T> query(WhereInfo... whereInfos) {
		return getRepository().query(whereInfos);
	}
	
	public QueryResult<T> queryPage(final PageRequest pageRequest) {
		return getRepository().queryPage(pageRequest);
	}
	/**
	 * 主要用于一些简单的查询，减少service出现太多的对dao的简单调用的方法，在前台直接往pageRequest中setSqlId()
	 * 来确定sql的语句。所以http请求参数中必须添加sqlId参数
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Map<String,Object>> queryPageMapByMybatis(final PageRequest pageRequest)  {
		if(pageRequest.getSqlId()==null || "".equals(pageRequest.getSqlId().trim())){
			throw new BusinessException(DefaulExceptionCode.SYSTEM_MYBATIS_STATEMENT_CAN_NOT_NULL);
		}
		return getRepository().queryPageMapBybatis(pageRequest.getSqlId(), pageRequest);
	}
	/**
	 * 主要用于一些简单的查询，减少service出现太多的对dao的简单调用的方法，在前台直接往pageRequest中setSqlId()
	 * 来确定sql的语句。所以http请求参数中必须添加sqlId参数
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Record> queryPageRecordByMybatis(final PageRequest pageRequest)  {
		if(pageRequest.getSqlId()==null || "".equals(pageRequest.getSqlId().trim())){
			throw new BusinessException(DefaulExceptionCode.SYSTEM_MYBATIS_STATEMENT_CAN_NOT_NULL);
		}
		return getRepository().queryPageRecordByMybatis(pageRequest.getSqlId(), pageRequest);
	}
//	/**
//	 * 主要用于一些简单的查询，减少service出现太多的对dao的简单调用的方法，在前台直接往pageRequest中setSqlId()
//	 * 来确定sql的语句。所以http请求参数中必须添加sqlId参数
//	 * @param pageRequest
//	 * @return
//	 */
//	public QueryResult<Object> queryPageObjByMybatis(final PageRequest pageRequest)  {
//		if(pageRequest.getSqlId()==null || "".equals(pageRequest.getSqlId().trim())){
//			throw new BusinessException(DefaulExceptionCode.SYSTEM_MYBATIS_STATEMENT_CAN_NOT_NULL);
//		}
//		return getRepository().queryPageObjByMybatis(pageRequest.getSqlId(), pageRequest);
//	}
}
