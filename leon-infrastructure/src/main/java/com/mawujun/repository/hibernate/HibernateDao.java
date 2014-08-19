package com.mawujun.repository.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.cnd.SqlType;
import com.mawujun.utils.AssertUtils;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.ReflectUtils;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.utils.page.SortInfo;
import com.mawujun.utils.page.WhereInfo;


public class HibernateDao<T, ID extends Serializable> implements IHibernateDao<T,ID>{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;
	
	protected int batchSize=20;
	
	public HibernateDao(Class<T> entityClass) {
		//this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		this.entityClass=entityClass;
	}
	/**
	 * 使用方式：通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>
	 */
	public HibernateDao() {
		this.entityClass = ReflectUtils.getSuperClassGenricType(getClass());
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setEntityClass(Class<T> entityClass){
		this.entityClass=entityClass;
	}
	public Class<T> getEntityClass(){
		return this.entityClass;
	}
	
	/**
	 * 取得当前Session.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public ClassMetadata getClassMetadata(){
		return this.getSessionFactory().getClassMetadata(entityClass);
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 * 
	 * 使用示例
	 * 获取全部用户对象，并在返回前完成LazyLoad属性的初始化。
	 *
	 *	public List<User> getAllUserInitialized() {
	 *		List<User> result = (List<User>) userDao.findAll();
	 *		for (User user : result) {
	 *			Hibernates.initLazyProperty(user.getRoleList());
	 *		}
	 *		return result;
	 *	}
	 * 
	 * 
	 * 
	 */
	public void initLazyProperty(Object proxy) {
		Hibernate.initialize(proxy);
	}


	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}
	/**
	 * 清空缓存
	 */
	public void clear() {
		getSession().clear();
	}
	

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
	
	/**
	 * 保存新增的对象.
	 */
	public ID create(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		Serializable id=getSession().save(entity);
		logger.debug("save entity: {}", entity);
		getSession().flush();
		return (ID)id;
	}
	/**
	 * 保存新增的对象.
	 */
	public void create(final Cnd cnd) {
		AssertUtils.notNull(cnd, "cnd不能为空");
		if(cnd.getSqlType()!=SqlType.INSERT){
			throw new IllegalArgumentException("Cnd的类型不对，应该使用insert");
		}
		
		if(cnd.getInsertItems()==null){
			throw new BusinessException("没有设置更新字段，请先设置了");
		}
		//cnd.setSqlType(SqlType.INSERT);
		
		
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		
		StringBuilder builder=new StringBuilder();
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getTableName());
		}
		
		cnd.joinHql(builder);
		
		//Query query = this.getSession().createQuery(builder.toString());
		Query query = this.getSession().createSQLQuery(builder.toString());
		setParamsByCnd(query,cnd,classMetadata);
		
		

		query.executeUpdate();
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void createOrUpdate(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
		getSession().flush();
	}
	/**
	 * 通过Cnd。update().set(...)。andEquals();来指定更新的字段和条件
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 */
	public void update(Cnd cnd) {
		if(cnd.getSqlType()!=SqlType.UPDATE ){
			throw new BusinessException("SqlType不对");
		}
		if(cnd.getUpdateItems()==null){
			throw new BusinessException("没有设置更新字段，请先设置了");
		}
		cnd.setSqlType(SqlType.UPDATE);
		
		
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		StringBuilder builder=new StringBuilder();
		cnd.joinHql( builder);
		
		Query query = this.getSession().createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);

		query.executeUpdate();
		
	}
	/**
	 * 修改对象.把数据库中的实例就更新成 : 传入对象
	 * 如果每个属性没有填的话，将会变成null，即使你本来不想改
	 * http://sishuok.com/forum/blogPost/list/2477.html
	 */
	public void update(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		this.getSession().update(entity);
		getSession().flush();
	}

	private StringBuilder getUpdateProp(final T entity){
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		
		String[] propertyNames=classMetadata.getPropertyNames();
		Object[] propertyValues=classMetadata.getPropertyValues(entity);
		int versionIndex=classMetadata.getVersionProperty();
		StringBuilder builder=null;
		if(versionIndex!=-66){//如果有version字段就更新他
			builder=new StringBuilder("update versioned  " + this.entityClass.getName()+ " obj set ");
		} else {
			builder=new StringBuilder("update  " + this.entityClass.getName()+ " obj set ");
		}
		
		int i=0;
		boolean isiFirst=true;//判断是不是第一个set
		for(Object o :propertyValues ){
			if(versionIndex!=-66 && versionIndex==i){
				continue;
			}
			if(o!=null){
				if(i!=0 && !isiFirst){
					builder.append(",");
				}
				builder.append(" obj."+propertyNames[i]+"=:"+propertyNames[i]);
				isiFirst=false;
			}
			i++;
		}
		return builder;
	}
	/**
	 * 使用占位符,第一个参数是更新的语句，不带where信息，第二个参数是 参数值
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param entity
	 * @return
	 */
	private Object[] getUpdateProp_position(final T entity){
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		
		String[] propertyNames=classMetadata.getPropertyNames();
		Object[] propertyValues=classMetadata.getPropertyValues(entity);
		int versionIndex=classMetadata.getVersionProperty();
		
		List<Object> params=new ArrayList<Object>();
		StringBuilder builder=null;
		if(versionIndex!=-66){//如果有version字段就更新他
			builder=new StringBuilder("update versioned  " + this.entityClass.getName()+ " obj set ");
		} else {
			builder=new StringBuilder("update  " + this.entityClass.getName()+ " obj set ");
		}
		
		int i=0;
		boolean isiFirst=true;//判断是不是第一个set
		for(Object o :propertyValues ){
			if(versionIndex!=-66 && versionIndex==i){
				continue;
			}
			if(o!=null){
				if(i!=0 && !isiFirst){
					builder.append(",");
				}
				builder.append(" obj."+propertyNames[i]+"=?");
				params.add(o);
				isiFirst=false;
			}
			i++;
		}
		return new Object[]{builder,params};
	}
	/**
	 * 动态更新，对有值的字段进行更新，即如果字段=null，那就不进行更新
	 * @param entity
	 */
	public void updateIgnoreNull(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");

		
		StringBuilder builder=getUpdateProp(entity);
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		String[] propertyNames=classMetadata.getPropertyNames();
		Object[] propertyValues=classMetadata.getPropertyValues(entity);
		int versionIndex=classMetadata.getVersionProperty();
		int i=0;
		boolean isiFirst=true;//判断是不是第一个set
		
		//id要循环的
		builder.append(" where id=:id");
		builder.append(" and "+propertyNames[versionIndex]+"=:version");
		
		Query query = this.getSession().createQuery(builder.toString());
		i=0;
		isiFirst=true;
		for(Object o :propertyValues ){
			if(versionIndex!=-66 && versionIndex==i){
				continue;
			}
			if(o!=null){
				if(i!=0 && !isiFirst){
					builder.append(",");
				}
				query.setParameter(propertyNames[i], o);
				isiFirst=false;
			}
			i++;
		}

		query.setParameter("id", classMetadata.getIdentifier(entity));
		query.setParameter(propertyNames[versionIndex], propertyValues[versionIndex]);
		query.executeUpdate();
		//无论前面是否获取过这个实体，如果有这个实体的缓存，就进行清空，没有也不会再去查询
		Object t=this.getSession().load(entityClass, classMetadata.getIdentifier(entity));
		this.getSession().evict(t);
		//this.clear();
		
	}
	
	protected void setParams(Query query,int position,Object val) {		
		if(val==null){
			query.setParameter(position, null);
		} else if (ReflectUtils.isString(val)) {
			query.setString(position, (String) val);
		} else if (ReflectUtils.isChar(val)) {
			query.setString(position, ((Character) val) + "");
		} else if (ReflectUtils.isByte(val)) {
			query.setByte(position, (Byte) val);
		} else if (ReflectUtils.isShort(val)) {
			query.setShort(position, (Short) val);
		} else if (ReflectUtils.isInt(val)) {
			query.setInteger(position, (Integer) val);
		} else if (ReflectUtils.isLong(val)) {
			query.setLong(position, (Long) val);
		} else if (ReflectUtils.isFloat(val)) {
			query.setFloat(position, (Float) val);
		} else if (ReflectUtils.isDouble(val)) {
			query.setDouble(position, (Double) val);
		} else if (ReflectUtils.isBoolean(val)) {
			query.setBoolean(position, ((Boolean) val));
		}else if (ReflectUtils.isBigDecimal(val)) {
			query.setBigDecimal(position, (BigDecimal) val);
		} else if (ReflectUtils.isOf(val, java.sql.Date.class)) {
			query.setDate(position, (java.sql.Date) val);
		} else if (ReflectUtils.isOf(val, java.util.Date.class)) {
			query.setDate(position, (java.util.Date) val);
		} else if (val.getClass().isArray() && val.getClass().getComponentType() == byte.class) {
			query.setBinary(position,(byte[]) val);
		} else if (val.getClass().isEnum()) {
			query.setString(position, val.toString());
		} else {
			throw new RuntimeException("不支持的值类型！" + val.getClass().getName());
		}
	}
	
	/**
	 * 注意
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param query
	 * @param cnd
	 * @param classMetadata
	 * @param otherParams 这里的参数比Cnd的参数要再前面
	 */
	protected void setParamsByCnd(Query query,Cnd cnd,AbstractEntityPersister classMetadata,Object... otherParams) {
		int position=0;
		if(otherParams!=null && otherParams.length>0){
			for(Object o:otherParams){
				setParams(query,position,o);
				position++;
			}
		}
		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams(null, params, 0);

		for (int i = 0; i < params.length; i++) {
			setParams(query,position+i,params[i]);
		}
	}
	/**
	 * 把实体里的非null值转换到Cnd里面
	 * @author mawujun 16064988@qq.com 
	 * @param entity
	 * @param cnd
	 */
	private void transEntityValue2Cnd(final T entity,Cnd cnd){
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		
		String[] propertyNames=classMetadata.getPropertyNames();
		Object[] propertyValues=classMetadata.getPropertyValues(entity);
		int versionIndex=classMetadata.getVersionProperty();
//		
//		List<Object> params=new ArrayList<Object>();
//		StringBuilder builder=null;
//		if(versionIndex!=-66){//如果有version字段就更新他
//			builder=new StringBuilder("update versioned  " + this.entityClass.getName()+ " obj set ");
//		} else {
//			builder=new StringBuilder("update  " + this.entityClass.getName()+ " obj set ");
//		}
		
		int i=0;
		//boolean isiFirst=true;//判断是不是第一个set
		for(Object value :propertyValues ){
			
			if(versionIndex!=-66 && versionIndex==i){
				continue;
			}
			if(value!=null){
				cnd.set(propertyNames[i], value);
			}
			i++;
		}
	}
	
	/**
	 * 动态更新，对有值的字段进行更新，即如果字段=null，那就不进行更新
	 * @param entity
	 */
	public void updateIgnoreNull(final T entity,Cnd cnd) {
		cnd.setSqlType(SqlType.UPDATE);
		//updateItems.size()>0防止用T进行更新的时候出现hql重复
		if(cnd.getUpdateItems()!=null){
			cnd.getUpdateItems().clearSets();
		}
		
		
//		Object[] objs=getUpdateProp_position(entity);
//		StringBuilder builder=(StringBuilder)objs[0];
//		List<Object> params11111=(List<Object>)objs[1];
		
		StringBuilder builder=new StringBuilder();
		this.transEntityValue2Cnd(entity, cnd);
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		

		//返回了带where的条件
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);//where条件没加上去,因为值没有，值没有通过set方法调用
		
		Query query = this.getSession().createQuery(builder.toString());
		setParamsByCnd(query,cnd,classMetadata);
		//setParamsByCnd(query,cnd,classMetadata,params11111.toArray(new Object[params11111.size()]));

		
		query.executeUpdate();
		
	}
	/**
	 * 动态更新，对有值的字段进行更新，即如果字段=null，那就不进行更新
	 * @param entity
	 */
	public void updateIgnoreNull(final T entity,WhereInfo... wheres) {
		AssertUtils.notNull(entity, "entity不能为空");
		
		StringBuilder builder=getUpdateProp(entity);
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		String[] propertyNames=classMetadata.getPropertyNames();
		Object[] propertyValues=classMetadata.getPropertyValues(entity);
		int versionIndex=classMetadata.getVersionProperty();
		int i=0;
		boolean isiFirst=true;//判断是不是第一个set
		
		
		builder.append(" where ");
		for(WhereInfo whereInfo:wheres){
			if(whereInfo.getOp().equalsIgnoreCase("between")){
				builder.append(" obj."+whereInfo.getProp()+" "+whereInfo.getOp()+":"+whereInfo.getPropTrans()+0+" and :"+whereInfo.getPropTrans()+1);
			} else if(whereInfo.getOp().equalsIgnoreCase("in")){
				builder.append(" obj."+whereInfo.getProp()+" in (:"+whereInfo.getPropTrans()+")");
			}else {
				builder.append(" obj."+whereInfo.getProp()+whereInfo.getOp()+":"+whereInfo.getPropTrans());
			}
			
		}
		
		
		Query query = this.getSession().createQuery(builder.toString());
		i=0;
		isiFirst=true;
		for(Object o :propertyValues ){
			if(versionIndex!=-66 && versionIndex==i){
				continue;
			}
			if(o!=null){
				if(i!=0 && !isiFirst){
					builder.append(",");
				}
				
				query.setParameter(propertyNames[i], o);
				isiFirst=false;
			}
			i++;
		}
		setParamsByWhereInfo(query,classMetadata,wheres);
		
		query.executeUpdate();
		
	}
	
	private void setParamsByWhereInfo(Query query,AbstractEntityPersister classMetadata,WhereInfo... wheres){
		for(WhereInfo whereInfo:wheres){
			 if(whereInfo.getOp().equalsIgnoreCase("between")){
				 Type type=classMetadata.getPropertyType(whereInfo.getProp());
				 
				 query.setParameter(whereInfo.getPropTrans()+"0",  BeanUtils.convert(((Object[])whereInfo.getValue())[0],type.getReturnedClass()));
				 query.setParameter(whereInfo.getPropTrans()+"1",  BeanUtils.convert(((Object[])whereInfo.getValue())[1],type.getReturnedClass()));
			} else if(whereInfo.getOp().equalsIgnoreCase("in")){

				Object obj1=BeanUtils.convert((String[])whereInfo.getValue(), Integer.class);
				query.setParameterList(whereInfo.getPropTrans(),(Object[])obj1);
			}else {	
				Type type=classMetadata.getPropertyType(whereInfo.getProp());
				//ClassUtils.isPrimitiveOrWrapper(type)
				query.setParameter(whereInfo.getPropTrans(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
			}
			 
		}
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		getSession().flush();
		logger.debug("delete entity: {}", entity);
	}

	/**
	 * 按id删除对象.
	 */
	public void deleteById(final Serializable id) {
		AssertUtils.notNull(id, "id不能为空");
		delete(load(id));
		getSession().flush();
//		String hql="delete " + this.entityClass.getName()+ " obj where id=?";
//		Query query = this.getSession().createQuery(hql);
//		query.setParameter(0, id);
//		query.executeUpdate();
//		//清空缓存
//		//AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
//		Object t=this.getSession().load(entityClass, id);
//		this.getSession().evict(t);
//		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}
	
	/**
	 * 按id获取对象.
	 */
	public T get(final Serializable id) {
		AssertUtils.notNull(id, "id不能为空");
		//return (T) getSession().get(entityClass, id);
		try {
			return (T)this.getSession().get(entityClass, id);
		} catch(ObjectNotFoundException ex){
			return null;
		}
	}

	/**
	 * 如果没有找到 就返回null
	 * @param id
	 * @return
	 */
	public T load(final Serializable id) {
		AssertUtils.notNull(id, "id不能为空");
		try {
			return (T)this.getSession().load(entityClass, id);
		} catch(ObjectNotFoundException ex){
			return null;
		}
		
	}

	
	/**
	 * 把对象进行批量提交
	 * @param entities
	 */
	public Serializable[] createBatch(final T... entities) {
		if(cancelExecute(entities)){
			return null;
		}
		int i=0;
		Serializable[] ids=new Serializable[entities.length];
		for (T t : entities ) {
			ids[i]=this.create(t);
		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		getSession().flush();
		return ids;
	}
	
	public Serializable[] createBatch(final Collection<T> entities){
		if(cancelExecute(entities)){
			return null;
		}
		int i=0;
		Serializable[] ids=new Serializable[entities.size()];
		for (T t : entities ) {
			ids[i]=this.create(t);
		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		getSession().flush();
		return ids;
	}

	private boolean cancelExecute(Object object){
		if(object==null){
			return true;
		} else if(object.getClass().isArray() && ((Object[])object).length==0){
			return true;
		} else if(object instanceof Collection && ((Collection)object).size()==0){
			return true;
		}
		return false;
	}
	public int deleteBatch(final T... entities) {
		if(cancelExecute(entities)){
			return 0;
		}
		int i=0;
		for (T t : entities ) {
			this.delete(t);
		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		return i;
	}
	public int deleteBatch(final Collection<T> entities){
		if(cancelExecute(entities)){
			return 0;
		}
		int i=0;
		for (T t : entities ) {
			this.delete(t);
		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		return i;
	}

	public int deleteBatch(final ID... IDS){
		if(cancelExecute(IDS)){
			return 0;
		}
		Session session = this.getSession();
		StringBuilder builder=new StringBuilder("delete from " + this.entityClass.getName()+ " obj where ");
		for (int i=0; i<IDS.length;i++) {   
			if (i == 0) {
				//hql = "id=" + selectFlag[i];
				builder.append(" obj.id=:id"+i);
			} else {
				//hql = hql + " or id=" + selectFlag[i];
				builder.append(" or obj.id=:id"+i);
			}	
		}
		Query query = session.createQuery(builder.toString());
		int i=0;
		for (ID id : IDS ) {
			query.setParameter("id"+i, id);
			i++;
		}
		session.clear();
		return query.executeUpdate();
	}
	/**
	 * 删除所有的实体
	 */
	public int deleteAll() {
		StringBuilder builder=new StringBuilder("delete from " + this.entityClass.getName()+ " obj  ");
		Query query = this.getSession().createQuery(builder.toString());
		this.getSession().clear();
		return query.executeUpdate();
	}
	/**
	 * 注意，使用Cnd的地方表示删除的是BaseRopository中泛型指定的类。
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 * @return
	 */
	public int deleteBatch(Cnd cnd){
		cnd.setSqlType(SqlType.DELETE);
		StringBuilder builder=new StringBuilder();
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams(null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		
		int rr=query.executeUpdate();
		//session.clear();
		session.flush();
		return rr;
		
	}
//	public int deleteBatch(WhereInfo... wheres){
//		if(cancelExecute(wheres)){
//			return 0;
//		}
//		
//		String hql="delete from "+this.entityClass.getName() +" obj where 1=1 ";
//		for(WhereInfo whereInfo:wheres){
//			if(whereInfo.getOp().equalsIgnoreCase("between")){
//				hql=hql+" and obj."+whereInfo.getProp()+" "+whereInfo.getOp()+":"+whereInfo.getPropTrans()+0+" and :"+whereInfo.getPropTrans()+1;
//			} else if(whereInfo.getOp().equals("in")){
//				hql=hql+" and obj."+whereInfo.getProp()+" in (:"+whereInfo.getPropTrans()+")";
//			}else {
//				hql=hql+" and obj."+whereInfo.getProp()+whereInfo.getOp()+":"+whereInfo.getPropTrans();
//			}
//			
//		}
//		
//		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
//		
//		 Session session = this.getSession();
//		 Query query = session.createQuery(hql);
//		 setParamsByWhereInfo(query,classMetadata,wheres);
////		 for(WhereInfo whereInfo:wheres){
////			 if(whereInfo.getOp().equals(WhereOperationEnum.BETWEEN)){
////				 Type type=classMetadata.getPropertyType(whereInfo.getProperty());
////				 
////				 query.setParameter(whereInfo.getProperty()+"0",  BeanMapper.convertToObject(((Object[])whereInfo.getValue())[0],type.getReturnedClass()));
////				 query.setParameter(whereInfo.getProperty()+"1",  BeanMapper.convertToObject(((Object[])whereInfo.getValue())[1],type.getReturnedClass()));
////			} else if(whereInfo.getOp().equals(WhereOperationEnum.IN)){
////
////				Type type=classMetadata.getPropertyType(whereInfo.getProperty());
////				Object[] old=(Object[])whereInfo.getValue();
////				Object[] aaa=new Object[old.length];
////				for(int i=0;i<old.length;i++){
////					aaa[i]= BeanMapper.convertToObject(old[i], type.getReturnedClass());
////				}
////				query.setParameterList(whereInfo.getProperty(),aaa);
////			}else {	
////				Type type=classMetadata.getPropertyType(whereInfo.getProperty());
////				//ClassUtils.isPrimitiveOrWrapper(type)
////				query.setParameter(whereInfo.getProperty(), BeanMapper.convertToObject(whereInfo.getValue(), type.getReturnedClass()));
////			}
////			 
////		}
//		 
//		 int rr=query.executeUpdate();
//		 session.clear();
//		 return rr;
//	}
	
	public int updateBatch(final T... entities){
		if(cancelExecute(entities)){
			return 0;
		}
		int count=0;
		for ( T t:entities ) {
			this.update(t);
		    if ( ++count % batchSize == 0 ) {
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		getSession().flush();
		return count;
	}
	public int updateBatch(final Collection<T> entities){
		if(cancelExecute(entities)){
			return 0;
		}
		int count=0;
		for ( T t:entities ) {
			this.update(t);
		    if ( ++count % batchSize == 0 ) {
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		getSession().flush();
		return count;
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int executeBatch(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int executeBatch(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		AssertUtils.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		AssertUtils.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	
	/**
	 * 版本属性，表示符属性和关联都会被忽略。默认情况下，null值的属性也被排除在外。
	 * @param t
	 * @return
	 */
	public List<T> queryByExample(T t){
		Example example=Example.create(t);
//		example.excludeZeroes();           //exclude zero valued properties
//	    example.excludeProperty("color");  //exclude the property named "color"
//	    example.ignoreCase();              //perform case insensitive string comparisons
//	    example.enableLike();             //use like for string comparisons
		Criteria criteria = getSession().createCriteria(entityClass);
		List<T> results = criteria.add(example).list();
		return results;
	}
	
	/**
	 *	获取全部对象.
	 */
	public List<T> queryAll() {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria.list();
	}
	public List<T> query(boolean uniqueResult,WhereInfo... whereInfos) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if(uniqueResult){
			criteria.setFirstResult(0);
			criteria.setMaxResults(1);
		}
		whereInfo2Criterion(criteria,whereInfos);
		
		return criteria.list();
	}
	public List<T> query(Cnd cnd) {
		return query(cnd,false);
	}
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 * @param uniqueResult 值返回唯一的一个值，否则返回所有数据
	 * @return
	 */
	public List<T> query(Cnd cnd,boolean uniqueResult) {
		cnd.setSqlType(SqlType.SELECT);
		StringBuilder builder=new StringBuilder();
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams(null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		if(uniqueResult){
			query.setFirstResult(0);
			query.setMaxResults(1);
		}
		
		setParamsByCnd(query,cnd,classMetadata);
		return query.list();

	}
	/**
	 * List<String> ids=super.query(Cnd.select().addSelect("id"), String.class);
	 * @author mawujun 16064988@qq.com 
	 * @param cnd
	 * @param classM
	 * @return
	 */
	public <M> List<M> query(Cnd cnd,Class<M> classM) {
		cnd.setSqlType(SqlType.SELECT);
		StringBuilder builder=new StringBuilder();
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams(null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		
		List<String> names=cnd.getSelectItems().getNames();

		List<M> result=new ArrayList<M>();
		try{
			//当只查一列的时候
			if(names.size()==1){
				List<Object> list= query.list();
				if (ReflectUtils.isBaseType(classM)) {
					for (Object objs : list) {
						M m = (M) BeanUtils.convert(objs, classM);
						result.add(m);
					}
				} else if(classM.isAssignableFrom(Map.class)){
					classM=(Class<M>) HashMap.class;
					for(Object objs:list){
						M m=classM.newInstance();
						
						for(int i=0;i<names.size();i++){
							((Map)m).put(names.get(i), objs);
						}
						result.add(m);
					}
				} else {
					for(Object objs:list){
						M m=classM.newInstance();
						for(int i=0;i<names.size();i++){
							ReflectUtils.setFieldValue(m, names.get(i), objs);
						}
						result.add(m);
					}
				}
			} else {
				//当查询结果是多列的时候
				List<Object[]> list= query.list();
				if(classM.isAssignableFrom(Map.class)){
					classM=(Class<M>) HashMap.class;
					for(Object[] objs:list){
						M m=classM.newInstance();
						
						for(int i=0;i<names.size();i++){
							((Map)m).put(names.get(i), objs[i]);
						}
						result.add(m);
					}
				} else if(ReflectUtils.isBaseType(classM)){
					throw new BusinessException("不能将多个查询列，转换为一个基本类型!");
					
				} else {
					for(Object[] objs:list){
						M m=classM.newInstance();
						for(int i=0;i<names.size();i++){
							ReflectUtils.setFieldValue(m, names.get(i), objs[i]);
						}
						result.add(m);
					}
				}		
			}

		} catch(Exception e) {
			throw  BusinessException.wrap(e);
		}
		
		
		return result;

	}
//	/**
//	 * List<String> ids=super.query(Cnd.select().addSelect("id"), String.class);
//	 * @author mawujun 16064988@qq.com 
//	 * @param cnd
//	 * @param classM
//	 * @return
//	 */
//	public <M> List<M> query(Cnd cnd,Class<M> classM) {
//		cnd.setSqlType(SqlType.SELECT);
//		StringBuilder builder=new StringBuilder();
//		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
//		cnd.joinHql(classMetadata, builder);
//		
//
//		Object[] params = new Object[cnd.paramCount(classMetadata)];
//		int paramsCount = cnd.joinParams(classMetadata, null, params, 0);
//		
//		Session session = this.getSession();
//		Query query = session.createQuery(builder.toString());
//		
//		setParamsByCnd(query,cnd,classMetadata);
//		
//		List<String> names=cnd.getSelectItems().getNames();
//
//		List<M> result=new ArrayList<M>();
//		try{
//			if(names.size()==1){
//				List<Object> list= query.list();
//				if (ReflectionUtils.isBaseType(classM)) {
//					for (Object objs : list) {
//						M m = (M) BeanUtils.convert(objs, classM);
//						result.add(m);
//					}
//				} else if(classM.isAssignableFrom(Map.class)){
//					classM=(Class<M>) HashMap.class;
//					for(Object objs:list){
//						M m=classM.newInstance();
//						
//						for(int i=0;i<names.size();i++){
//							((Map)m).put(names.get(i), objs);
//						}
//						result.add(m);
//					}
//				} else {
//					for(Object objs:list){
//						M m=classM.newInstance();
//						for(int i=0;i<names.size();i++){
//							ReflectionUtils.setFieldValue(m, names.get(i), objs);
//						}
//						result.add(m);
//					}
//				}
//			} else {
//				List<Object[]> list= query.list();
//				if(classM.isAssignableFrom(Map.class)){
//					classM=(Class<M>) HashMap.class;
//					for(Object[] objs:list){
//						M m=classM.newInstance();
//						
//						for(int i=0;i<names.size();i++){
//							((Map)m).put(names.get(i), objs[i]);
//						}
//						result.add(m);
//					}
//				} else if(ReflectionUtils.isBaseType(classM)){
//					throw new BusinessException("不能将多个查询列，转换为一个基本类型!");
//					
//				} else {
//					for(Object[] objs:list){
//						M m=classM.newInstance();
//						for(int i=0;i<names.size();i++){
//							ReflectionUtils.setFieldValue(m, names.get(i), objs[i]);
//						}
//						result.add(m);
//					}
//				}		
//			}
//
//		} catch(Exception e) {
//			throw  BusinessException.wrap(e);
//		}
//		
//		
//		return result;
//
//	}
	
	public int queryCount(WhereInfo... whereInfos) {
		Criteria criteria = getSession().createCriteria(entityClass);
		whereInfo2Criterion(criteria,whereInfos);
		int totalCount = countCriteriaResult(criteria);
		return totalCount;
	}
	public Long queryCount(Cnd cnd) {
		cnd.setSqlType(SqlType.SELECT);
		cnd.setCount();
		//StringBuilder builder=new StringBuilder("select count(*)   ");
		StringBuilder builder=new StringBuilder("");
//		if(cnd.getSelectItems()!=null && cnd.getSelectItems().getNames().size()>0){
//			builder.append(" select count(");
//			//sb.append(" new map(");
//			for (String obi : cnd.getSelectItems().getNames()) {
//				//sb.append(obi+" as "+obi+",");
//				builder.append(obi+",");
//			}
//			builder.setCharAt(builder.length() - 1, ')');
//			builder.append(" ");
//		} else {
//			builder.append("select count(*)   ");
//		}
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams( null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		//return ((Long)query.uniqueResult()).intValue();
		Long result=(Long)query.uniqueResult();
		if(result==null){
			return 0l;
		} else {
			return result;
		}
		 
	}
	/**
	 * 返回第一个对象
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param whereInfos
	 * @return
	 */
	public T queryUnique(WhereInfo... whereInfos) {
		List<T> list=query(true,whereInfos);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 返回第一个对象
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param whereInfos
	 * @return
	 */
	public T queryUnique(Cnd cnd) {
		List<T> list=query(cnd,true);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	public <M> M queryUnique(Cnd cnd,Class<M> classM) {
		cnd.setSqlType(SqlType.SELECT);
		StringBuilder builder=new StringBuilder();
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql( builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams( null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);

		Object obj= query.uniqueResult();//.list().;
		if(obj==null){
			return null;
		}
		if(classM.isAssignableFrom(obj.getClass())){
			return (M)obj;
		}
		if(!obj.getClass().isArray() && ReflectUtils.isBaseType(classM)){
			M m=(M) BeanUtils.convert(obj, classM);
			return m;
		} else {
			List<String> names = cnd.getSelectItems().getNames();
			try {
				if(classM.isAssignableFrom(Map.class)){
					classM=(Class<M>) HashMap.class;
					M m=classM.newInstance();
					for(int i=0;i<names.size();i++){
						((Map)m).put(names.get(i), ((Object[])obj)[i]);
					}
					return m;
				} else {
					M m = classM.newInstance();
					for (int i = 0; i < names.size(); i++) {
						ReflectUtils.setFieldValue(m, names.get(i), ((Object[])obj)[i]);
					}
					return m;
				}
				
			} catch (Exception e) {
				throw BusinessException.wrap(e);
			}
		}
//		List<String> names=cnd.getSelectItems().getNames();
//		
//		try{
//			if(classM.isAssignableFrom(Map.class)){
//				classM=(Class<M>) HashMap.class;
//				for(Object[] objs:list){
//					M m=classM.newInstance();
//					
//					for(int i=0;i<names.size();i++){
//						((Map)m).put(names.get(i), objs[i]);
//					}
//					result.add(m);
//				}
//			} else if(ReflectionUtils.isBaseType(classM)){
//				if(names.size()==1){
//					for(Object objs:list){
//						M m=(M) BeanUtils.convert(objs, classM);
//						result.add(m);
//					}
//				} else {
//					for(Object[] objs:list){
//						M m=(M) BeanUtils.convert(objs[0], classM);
//						result.add(m);
//					}
//				}
//				
//			} else {
//				for(Object[] objs:list){
//					M m=classM.newInstance();
//					for(int i=0;i<names.size();i++){
//						ReflectionUtils.setFieldValue(m, names.get(i), objs[i]);
//					}
//					result.add(m);
//				}
//			}
//		} catch(Exception e) {
//			throw  BusinessException.wrap(e);
//		}
//		return null;
	}
	
	public Object queryMax(String property,WhereInfo... whereInfos) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setProjection(Projections.projectionList().add( Projections.max(property)));
		whereInfo2Criterion(criteria,whereInfos);
		return criteria.uniqueResult();
	}
	
	public Object queryMax(String property,Cnd cnd) {
		StringBuilder builder=new StringBuilder("select max("+property+") ");
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.setSqlType(SqlType.SELECT);
		cnd.joinHql( builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams(null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return query.uniqueResult();
	}
	public Object queryMax(Cnd cnd) {
		cnd.setSqlType(SqlType.SELECT);
		StringBuilder builder=new StringBuilder("");
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams( null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		//return ((Long)query.uniqueResult()).intValue();
		Object result=query.uniqueResult();
		return result;
	}
	
	public Object queryMin(String property,Cnd cnd) {
		StringBuilder builder=new StringBuilder("select min("+property+") ");
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		
		cnd.joinHql( builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams( null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return query.uniqueResult();
	}
	
	public Object queryMin(Cnd cnd) {
		cnd.setSqlType(SqlType.SELECT);
		StringBuilder builder=new StringBuilder("");
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams( null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		//return ((Long)query.uniqueResult()).intValue();
		Object result=query.uniqueResult();
		return result;
	}
	
	public Object querySum(Cnd cnd) {
		cnd.setSqlType(SqlType.SELECT);
		StringBuilder builder=new StringBuilder("");
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams( null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		//return ((Long)query.uniqueResult()).intValue();
		Object result=query.uniqueResult();
		return result;
	}
	public Object queryAvg(Cnd cnd) {
		cnd.setSqlType(SqlType.SELECT);
		StringBuilder builder=new StringBuilder("");
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		if(cnd.getFrom()==null){
			cnd.setFrom(classMetadata.getEntityName());
		}
		cnd.joinHql(builder);
		
		

		Object[] params = new Object[cnd.paramCount()];
		int paramsCount = cnd.joinParams( null, params, 0);
		
		Session session = this.getSession();
		Query query = session.createQuery(builder.toString());
		
		setParamsByCnd(query,cnd,classMetadata);
		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		//return ((Long)query.uniqueResult()).intValue();
		Object result=query.uniqueResult();
		return result;
	}
	
	
	private void whereInfo2Criterion(Criteria criteria,WhereInfo... whereInfos) {
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);    //设置ENTITY级的DISTINCT模式，根实体
		if(whereInfos==null || whereInfos.length==0){
			return;
		}
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		for(WhereInfo whereInfo:whereInfos){
			AssertUtils.notNull(whereInfo.getOp());
			//当property是关联对象的属性的时候，而且不是关联id
			String[] properties=whereInfo.getProp().split("\\.");
			if(properties.length>1){
				if(!"id".equalsIgnoreCase(properties[properties.length-1])){
					Criteria tempCriteria=criteria;
					for(int i=0;i<properties.length-1;i++){
						tempCriteria=tempCriteria.createCriteria(properties[i]);
						
					}
					WhereInfo tempWhereInfo=WhereInfo.parse(properties[properties.length-1], whereInfo.getOp(),whereInfo.getValue());
					tempCriteria.add(returnCriterion(classMetadata,tempWhereInfo));
					
					continue;
				} else {
					criteria.add(returnCriterion(classMetadata,whereInfo));
				}
			} else {
				criteria.add(returnCriterion(classMetadata,whereInfo));
			}
			

		}
	}
	private Criterion returnCriterion(AbstractEntityPersister classMetadata,WhereInfo whereInfo) {
		Type type=classMetadata.getPropertyType(whereInfo.getProp());
		//type.get
		Criterion criterion = null;
		if("=".equals(whereInfo.getOp())){
			criterion = Restrictions.eq(whereInfo.getProp(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
			
		} else if("<".equals(whereInfo.getOp())){
			criterion = Restrictions.lt(whereInfo.getProp(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
			
		} else if(">".equals(whereInfo.getOp())){
			criterion = Restrictions.gt(whereInfo.getProp(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
			
		} else if("<=".equals(whereInfo.getOp())){
			criterion = Restrictions.le(whereInfo.getProp(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
			
		} else if(">=".equals(whereInfo.getOp())){
			criterion = Restrictions.ge(whereInfo.getProp(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
			
		} else if("is".equalsIgnoreCase(whereInfo.getOp())){
			if(whereInfo.isNot()){
				criterion =Restrictions.isNotNull(whereInfo.getProp());
				
			} else {
				criterion =Restrictions.isNull(whereInfo.getProp());
				
			}
		} else if("!=".equals(whereInfo.getOp()) || "<>".equals(whereInfo.getOp())){
			criterion = Restrictions.ne(whereInfo.getProp(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
			
		} else if("like".equalsIgnoreCase(whereInfo.getOp())){
			if(whereInfo.isIgnoreCase()){
				if(whereInfo.getMatchModel()==com.mawujun.utils.page.MatchMode.EXACT){
					criterion = Restrictions.ilike(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.EXACT);
					
				} else if(whereInfo.getMatchModel()==com.mawujun.utils.page.MatchMode.START){
					criterion = Restrictions.ilike(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.START);
					
				} else if(whereInfo.getMatchModel()==com.mawujun.utils.page.MatchMode.END){
					criterion = Restrictions.ilike(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.END);
					
				} else {
					criterion = Restrictions.ilike(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.ANYWHERE);
					
				}
			} else {
				if(whereInfo.getMatchModel()==com.mawujun.utils.page.MatchMode.EXACT){
					criterion = Restrictions.like(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.EXACT);
					
				} else if(whereInfo.getMatchModel()==com.mawujun.utils.page.MatchMode.START){
					criterion = Restrictions.like(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.START);
					
				} else if(whereInfo.getMatchModel()==com.mawujun.utils.page.MatchMode.END){
					criterion = Restrictions.like(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.END);
					
				} else {
					criterion = Restrictions.like(whereInfo.getProp(), (String)whereInfo.getValue(), MatchMode.ANYWHERE);
					
				}
			}
			
		} else if("between".equalsIgnoreCase(whereInfo.getOp())){			
			criterion =Restrictions.between(whereInfo.getProp(),  BeanUtils.convert(((Object[])whereInfo.getValue())[0], type.getReturnedClass()), 
					BeanUtils.convert(((Object[])whereInfo.getValue())[1], type.getReturnedClass()));
			
		} else if("in".equalsIgnoreCase(whereInfo.getOp())){
			Object[] old=(Object[])whereInfo.getValue();
			Object[] aaa=new Object[old.length];
			for(int i=0;i<old.length;i++){
				aaa[i]= BeanUtils.convert(old[i], type.getReturnedClass());
			}
			criterion =Restrictions.in(whereInfo.getProp(), (Object[])whereInfo.getValue());
			
		} else{
			throw new BusinessException("操作符还不支持");
		}
		
//		switch(whereInfo.getOp()){
//		case EQ:
//			criterion = Restrictions.eq(whereInfo.getPropToDefault(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
//			break;
//		case LIKE:
//			criterion = Restrictions.like(whereInfo.getPropToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.ANYWHERE);
//			break;
//		case LIKESTART:
//			criterion = Restrictions.like(whereInfo.getPropToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.START);
//			break;
//		case LIKEEND:
//			criterion = Restrictions.like(whereInfo.getPropToDefault(),(String) whereInfo.getValueToDefault(), MatchMode.END);
//			break;
//		case ILIKE:
//			criterion = Restrictions.ilike(whereInfo.getPropToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.ANYWHERE);
//			break;
//		case ILIKESTART:
//			criterion = Restrictions.ilike(whereInfo.getPropToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.START);
//			break;
//		case ILIKEEND:
//			criterion = Restrictions.ilike(whereInfo.getPropToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.END);
//			break;
//		case LE:
//			criterion = Restrictions.le(whereInfo.getPropToDefault(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
//			break;
//		case LT:
//			criterion = Restrictions.lt(whereInfo.getPropToDefault(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
//			break;
//		case GE:
//			criterion = Restrictions.ge(whereInfo.getPropToDefault(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
//			break;
//		case GT:
//			criterion = Restrictions.gt(whereInfo.getPropToDefault(), BeanUtils.convert(whereInfo.getValue(), type.getReturnedClass()));
//			break;
//		case ISNULL:
//			criterion =Restrictions.isNull(whereInfo.getPropToDefault());
//			break;
//		case ISNOTNULL:
//			criterion =Restrictions.isNotNull(whereInfo.getPropToDefault());
//			break;
//		case BETWEEN:
//			criterion =Restrictions.between(whereInfo.getPropToDefault(),  BeanUtils.convert(((Object[])whereInfo.getValue())[0], type.getReturnedClass()), 
//					BeanUtils.convert(((Object[])whereInfo.getValue())[1], type.getReturnedClass()));
//			break;
//		case IN:
//			Object[] old=(Object[])whereInfo.getValue();
//			Object[] aaa=new Object[old.length];
//			for(int i=0;i<old.length;i++){
//				aaa[i]= BeanUtils.convert(old[i], type.getReturnedClass());
//			}
//			criterion =Restrictions.in(whereInfo.getPropToDefault(), (Object[])whereInfo.getValue());
//			break;
//		default:
//			break;
//		}
		return criterion;
	}
	
	/**
	 * 分页获取全部对象.这里是使用自动查询,不会使用params参数
	 * 只能用于单个对象的查询，复杂的查询请使用queryPage*ByMybatis等
	 */
	public QueryResult<T> queryPage(final PageRequest pageRequest) {
		//return queryPage(pageRequest);
		//WhereInfo[] wheresNew=changeColumnToProperty(pageRequest.getWheres());
		//pageRequest.setWheres(wheresNew);
		
		QueryResult<T> page = new QueryResult<T>(pageRequest);
		Criteria criteria = this.getSession().createCriteria(entityClass);
		
		//AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
		
		//设置查询条件
		//List<Criterion> criterionList = new ArrayList<Criterion>();
		if(pageRequest.hasWhereInfo()){
			whereInfo2Criterion(criteria,pageRequest.getWheres());
//			for(WhereInfo whereInfo:pageRequest.getWheres()){
//				AssertUtils.notNull(whereInfo.getOp());
//				Type type=classMetadata.getPropertyType(whereInfo.getProperty());
//				Criterion criterion = null;
//				switch(whereInfo.getOp()){
//				case EQ:
//					criterion = Restrictions.eq(whereInfo.getPropertyToDefault(), BeanMapper.convert(whereInfo.getValue(), type.getReturnedClass()));
//					break;
//				case LIKE:
//					criterion = Restrictions.like(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.ANYWHERE);
//					break;
//				case LIKESTART:
//					criterion = Restrictions.like(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.START);
//					break;
//				case LIKEEND:
//					criterion = Restrictions.like(whereInfo.getPropertyToDefault(),(String) whereInfo.getValueToDefault(), MatchMode.END);
//					break;
//				case ILIKE:
//					criterion = Restrictions.ilike(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.ANYWHERE);
//					break;
//				case ILIKESTART:
//					criterion = Restrictions.ilike(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.START);
//					break;
//				case ILIKEEND:
//					criterion = Restrictions.ilike(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.END);
//					break;
//				case LE:
//					criterion = Restrictions.le(whereInfo.getPropertyToDefault(), BeanMapper.convert(whereInfo.getValue(), type.getReturnedClass()));
//					break;
//				case LT:
//					criterion = Restrictions.lt(whereInfo.getPropertyToDefault(), BeanMapper.convert(whereInfo.getValue(), type.getReturnedClass()));
//					break;
//				case GE:
//					criterion = Restrictions.ge(whereInfo.getPropertyToDefault(), BeanMapper.convert(whereInfo.getValue(), type.getReturnedClass()));
//					break;
//				case GT:
//					criterion = Restrictions.gt(whereInfo.getPropertyToDefault(), BeanMapper.convert(whereInfo.getValue(), type.getReturnedClass()));
//					break;
//				case ISNULL:
//					criterion =Restrictions.isNull(whereInfo.getPropertyToDefault());
//					break;
//				case ISNOTNULL:
//					criterion =Restrictions.isNotNull(whereInfo.getPropertyToDefault());
//					break;
//				case BETWEEN:
//					criterion =Restrictions.between(whereInfo.getPropertyToDefault(),  BeanMapper.convert(((Object[])whereInfo.getValue())[0], type.getReturnedClass()), 
//							BeanMapper.convert(((Object[])whereInfo.getValue())[1], type.getReturnedClass()));
//					break;
//				case IN:
//					Object[] old=(Object[])whereInfo.getValue();
//					Object[] aaa=new Object[old.length];
//					for(int i=0;i<old.length;i++){
//						aaa[i]= BeanMapper.convert(old[i], type.getReturnedClass());
//					}
//					criterion =Restrictions.in(whereInfo.getPropertyToDefault(), (Object[])whereInfo.getValue());
//					break;
//				default:
//					break;
//				}
//				//criterionList.add(criterion);
//				criteria.add(criterion);
//			}
		}

		//设置分页
		if (pageRequest.isCountTotal()) {
			int totalCount = countCriteriaResult(criteria);
			page.setTotalItems(totalCount);
			criteria.setFirstResult(pageRequest.getStart());
			criteria.setMaxResults(pageRequest.getPageSize());
		} 
		//设置排序
		if(pageRequest.hasSort()){
			for(SortInfo sortInfo:pageRequest.getSorts()){
				if(SortInfo.ASC.equalsIgnoreCase(sortInfo.getDir())){
					criteria.addOrder(Order.asc(sortInfo.getProp()));
				} else {
					criteria.addOrder(Order.desc(sortInfo.getProp()));
				}
				
			}
		}
		//查询数据
		List<T> result = criteria.list();
		page.setResult(result);

		if(!pageRequest.isCountTotal()){
			page.setPageSize(result.size());
			page.setPageNo(1);
			page.setTotalItems(result.size());
		}
		
		
		return page;
	}
	
	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectUtils.getFieldValue(impl, "orderEntries");
			ReflectUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = (Long)c.setProjection(Projections.rowCount()).uniqueResult();
		int totalCount = (totalCountObject != null) ? totalCountObject.intValue() : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	
}
