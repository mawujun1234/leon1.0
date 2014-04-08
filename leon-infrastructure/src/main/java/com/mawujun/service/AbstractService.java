package com.mawujun.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

public abstract class AbstractService<T extends IdEntity<ID>, ID extends Serializable> {
	/**
	 * 用来返回默认的Repository
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public abstract IRepository<T,ID> getRepository();
	
	public ID create(T entity) {
		return this.getRepository().create(entity);
	}
	
	/**
	 * 通过Cnd。update().set(...)。andEquals();来指定更新的字段和条件
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 */
	public void update(Cnd cnd) {
		this.getRepository().update(cnd);
	}
	/**
	 * 动态更新，对有值的字段进行更新，即如果字段=null，那就不进行更新
	 * @param entity
	 */
	public void updateIgnoreNull(final T entity) {
		this.getRepository().updateIgnoreNull(entity);
	}
	
	
	public void delete(T entity) {
		this.getRepository().delete(entity);
	}
	public void deleteById(ID id) {
		this.getRepository().deleteById(id);
	}
	public T get(ID id) {
		return this.getRepository().get(id);
	}
	
	public Serializable[] createBatch(final T... entities) {
		return this.getRepository().createBatch(entities);
	}
	public Serializable[] createBatch(final Collection<T> entities) {
		return this.getRepository().createBatch(entities);
	}
	
	/**
	 * 注意，如果某个属性没有设置值，将会更新为null
	 * @author mawujun 16064988@qq.com 
	 * @param entities
	 * @return
	 */
	public int updateBatch(final T... entities) {
		return this.getRepository().updateBatch(entities);
	}
	public int updateBatch(final Collection<T> entities) {
		return this.getRepository().updateBatch(entities);
	}
	
	public int deleteAll() {
		return this.getRepository().deleteAll();
	}
	/**
	 * 注意，使用Cnd的地方表示删除的是泛型指定的类。
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 * @return
	 */
	public int deleteBatch(Cnd cnd) {
		return this.getRepository().deleteBatch(cnd);
	}
	public int deleteBatch(final Collection<T> entities) {
		return this.getRepository().deleteBatch(entities);
	}
	public int deleteBatch(final T... entities) {
		return this.getRepository().deleteBatch(entities);
	}
	public int deleteBatch(final ID... IDS) {
		return this.deleteBatch(IDS);
	}
	
	
	public Long queryCount(Cnd cnd) {
		return this.queryCount(cnd);
	}
	public Object queryMax(String property,Cnd cnd) {
		return this.queryMax(property, cnd);
	}
	public Object queryMin(String property,Cnd cnd) {
		return this.queryMin(property, cnd);
	}
	/**
	 * 返回第一个对象
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param whereInfos
	 * @return
	 */
	public T queryUnique(Cnd cnd) {
		return this.queryUnique(cnd);
	}
	public <M> M queryUnique(Cnd cnd,Class<M> classM) {
		return this.queryUnique(cnd, classM);
	}
	
	public List<T> queryAll() {
		return this.queryAll();
	}
	public List<T> query(Cnd cnd) {
		return this.query(cnd);
	}
	public QueryResult<T> queryPage(final PageRequest pageRequest) {
		return this.queryPage(pageRequest);
	}
}
