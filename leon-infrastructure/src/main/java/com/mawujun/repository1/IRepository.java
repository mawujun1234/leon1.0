package com.mawujun.repository1;

import java.io.Serializable;

import com.mawujun.utils.page.WhereInfo;

public interface IRepository<T, ID extends Serializable> {
	public void create(T entity);
	public void update(T entity);
	public void delete(T entity);
	public void delete(Serializable id);
	public T get(Serializable id);
	
	public void updateDynamic(T entity);
	//public void updateDynamic(T entity,WhereInfo... wheres);
	
	
//	public void create(T entity);
//	public void update(T entity);
//	public void updateDynamic(T entity);
//	public void updateDynamic(T entity,WhereInfo... wheres);
//	public void delete(T entity);
//	public void delete(Serializable id);
//	public T get(Serializable id);
//	
//	
//	public int createBatch(final T... entitys);
//	public int saveBatch(final Collection<T> entities);
//	public int deleteBatch(final T... entitys);
//	public int deleteBatch(final Collection<T> entities);
//	public int deleteBatch(final ID... IDS);
//	public int deleteAllBatch();
//	public int deleteBatch(WhereInfo... wheres);
//	
//	public int updateBatch(final T... entitys);
//	public int updateBatch(final Collection<T> entities);
//	
//	public List<T> queryAll();
//	public List<T> queryByExample(T t);
//	public QueryResult<T> queryPage(final PageRequest pageRequest);

}
