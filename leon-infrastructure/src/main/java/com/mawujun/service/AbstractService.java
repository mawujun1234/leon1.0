package com.mawujun.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

@Transactional(propagation=Propagation.REQUIRED)
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
	 * Cnd cnd=Cnd.insert().set("firstName", "ma").set("lastName", "wujun").set("email", "160649888@163.com").set("id", 4).set("version", 1);
	 * service.create(cnd);
	 * @author mawujun 16064988@qq.com 
	 * @param cnd
	 */
	public void create(final Cnd cnd) {
		this.getRepository().create(cnd);
	}
	public void createOrUpdate(final T entity) {
		this.getRepository().createOrUpdate(entity);
	}
	
	/**
	 * 通过Cnd。update().set(...)。andEquals();来指定更新的字段和条件
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 */
	public void update(Cnd cnd) {
		this.getRepository().update(cnd);
	}
	public void update(T entity) {
		this.getRepository().update(entity);
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
		return this.getRepository().deleteBatch(IDS);
	}
	
	
	public Long queryCount(Cnd cnd) {
		return this.getRepository().queryCount(cnd);
	}
	public Object queryMax(String property,Cnd cnd) {
		return this.getRepository().queryMax(property, cnd);
	}
	public Object queryMin(String property,Cnd cnd) {
		return this.getRepository().queryMin(property, cnd);
	}
	/**
	 * 返回第一个对象
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param whereInfos
	 * @return
	 */
	public T queryUnique(Cnd cnd) {
		return this.getRepository().queryUnique(cnd);
	}
	public <M> M queryUnique(Cnd cnd,Class<M> classM) {
		return this.getRepository().queryUnique(cnd, classM);
	}
	
	public List<T> queryAll() {
		return this.getRepository().queryAll();
	}
	public List<T> query(Cnd cnd) {
		return this.getRepository().query(cnd);
	}
	/**
	 * List<String> ids=super.query(Cnd.select().addSelect("id"), String.class);
	 * @author mawujun 16064988@qq.com 
	 * @param cnd
	 * @param classM 要返回的数据类型
	 * @return
	 */
	public <M> List<M> query(Cnd cnd,Class<M> classM) {
		return this.getRepository().query(cnd,classM);
	}
	public QueryResult<T> queryPage(final PageRequest pageRequest) {
		return this.getRepository().queryPage(pageRequest);
	}
	/**
	 * 会自动建立count的sql语句，id默认为：方法名_count,本例就queryPage_count.你也可以自己定义这个sql，取名规则也是这样，这样的话就会覆盖默认实现的count语句
	 * 后台只需要定义一个sql,select * from XXX where ...就可以了，不需要些分页的代码例如mysql的limit，sqlserver的top等
	 * 如果 要自定义其他查询分页，只需要定义一个方法，把方法名改成你想要的就可以了，其他的应该和这个方法一样.参数和返回值都必须是Page，才会自动进行查询
	 * 
	 * Page的使用方法Page page=Page.getInstance().setPageSize(1).setStart(1).setParams("");
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param statement
	 * @param page
	 * @return
	 */
	public Page queryPage(Page page) {
		return this.getRepository().queryPage(page);
	}
}
