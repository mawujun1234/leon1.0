package com.mawujun.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mawujun.exception.BussinessException;
import com.mawujun.exception.DefaulExceptionCode;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.mybatis.Record;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

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
	
	public void save(T entity) {
		getRepository().save(entity);
	}
	
	public void update(T entity) {
		getRepository().update(entity);
	}
	
	public void delete(T entity) {
		getRepository().delete(entity);
	}
	public void delete(Serializable id) {
		getRepository().delete(id);
	}
	
	public T get(ID id) {
		return getRepository().get(id);
	}
	
	public List<T> queryAll() {
		return getRepository().queryAll();
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
			throw new BussinessException(DefaulExceptionCode.SYSTEM_MYBATIS_STATEMENT_CAN_NOT_NULL);
		}
		return getRepository().queryPageMapByMybatis(pageRequest.getSqlId(), pageRequest);
	}
	/**
	 * 主要用于一些简单的查询，减少service出现太多的对dao的简单调用的方法，在前台直接往pageRequest中setSqlId()
	 * 来确定sql的语句。所以http请求参数中必须添加sqlId参数
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Record> queryPageRecordByMybatis(final PageRequest pageRequest)  {
		if(pageRequest.getSqlId()==null || "".equals(pageRequest.getSqlId().trim())){
			throw new BussinessException(DefaulExceptionCode.SYSTEM_MYBATIS_STATEMENT_CAN_NOT_NULL);
		}
		return getRepository().queryPageRecordByMybatis(pageRequest.getSqlId(), pageRequest);
	}
	/**
	 * 主要用于一些简单的查询，减少service出现太多的对dao的简单调用的方法，在前台直接往pageRequest中setSqlId()
	 * 来确定sql的语句。所以http请求参数中必须添加sqlId参数
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Object> queryPageObjByMybatis(final PageRequest pageRequest)  {
		if(pageRequest.getSqlId()==null || "".equals(pageRequest.getSqlId().trim())){
			throw new BussinessException(DefaulExceptionCode.SYSTEM_MYBATIS_STATEMENT_CAN_NOT_NULL);
		}
		return getRepository().queryPageObjByMybatis(pageRequest.getSqlId(), pageRequest);
	}
}
