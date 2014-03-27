package com.mawujun.repository1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.cnd.SqlType;
import com.mawujun.utils.AssertUtils;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.ReflectionUtils;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

/**
 * 限制 方法名称相同的时候，参数个数必须不同，否则MyMapperProxy在调用的时候不能识别，原因是这里不能使用具体的泛型
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class HibernateInvoke<T> implements IHibernateRepository<T,Serializable>{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private SessionFactory sessionFactory;
	protected int batchSize=20;
	protected Class<T> entityClass;
	
//	public HibernateInvoke(SessionFactory sessionFactory){
//		this.sessionFactory=sessionFactory;
//	}
	public HibernateInvoke(Class<T> clazz,SessionFactory sessionFactory){
		this.entityClass=clazz;
		this.sessionFactory=sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Serializable create(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		logger.debug("create entity: {}", entity);
		Serializable id= getSession().save(entity);	
		getSession().flush();
		return id;
	}

//	public Serializable create(final Object entity) {
//		AssertUtils.notNull(entity, "entity不能为空");
//		logger.debug("create entity: {}", entity);
//		Serializable id= getSession().save(entity);	
//		getSession().flush();
//		return id;
//	}
//	
//	public Serializable[] createBatch(final Object... entities) {
//		if(cancelExecute(entities)){
//			return null;
//		}
//		int i=0;
//		Serializable[] ids=new Serializable[entities.length];
//		for (Object t : entities ) {
//			ids[i]=this.create(t);
//		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
//		        //flush a batch of inserts and release memory:
//		    	this.getSession().flush();
//		    	this.getSession().clear();
//		    }
//		}
//		return ids;
//	}
//	
////	public Serializable[] createBatch(final Collection<Object> entities){
////		if(cancelExecute(entities)){
////			return null;
////		}
////		int i=0;
////		Serializable[] ids=new Serializable[entities.size()];
////		for (Object t : entities ) {
////			ids[i]=this.create(t);
////		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
////		        //flush a batch of inserts and release memory:
////		    	this.getSession().flush();
////		    	this.getSession().clear();
////		    }
////		}
////		return ids;
////	}
//	
//	public void update(final Object entity) {
//		AssertUtils.notNull(entity, "entity不能为空");
//		this.getSession().update(entity);
//		getSession().flush();
//	}
//	
//	public int updateBatch(final Object... entities){
//		if(cancelExecute(entities)){
//			return 0;
//		}
//		int count=0;
//		for ( Object t:entities ) {
//			this.update(t);
//		    if ( ++count % batchSize == 0 ) {
//		    	this.getSession().flush();
//		    	this.getSession().clear();
//		    }
//		}
//		return count+1;
//	}
////	public int updateBatch(final Collection<Object> entities) {
////		if(cancelExecute(entities)){
////			return 0;
////		}
////		int count=0;
////		for ( Object t:entities ) {
////			this.update(t);
////		    if ( ++count % batchSize == 0 ) {
////		    	this.getSession().flush();
////		    	this.getSession().clear();
////		    }
////		}
////		return count+1;
////	}
//	
//	public void delete(final Object entity) {
//		AssertUtils.notNull(entity, "entity不能为空");
//		getSession().delete(entity);
//		logger.debug("delete entity: {}", entity);
//		getSession().flush();
//		//getSession().clear();
//	}
//	
//	public void deleteById(final Serializable id) {
//		AssertUtils.notNull(id, "id不能为空");
//		delete(load(id));
//		getSession().flush();
//		//getSession().clear();
//	}
//	/**
//	 * 删除所有的实体
//	 */
//	public int deleteAll() {
//		StringBuilder builder=new StringBuilder("delete from " + this.entityClass.getName()+ " obj  ");
//		Query query = this.getSession().createQuery(builder.toString());
//		this.getSession().clear();
//		return query.executeUpdate();
//	}
//	
//	public int deleteBatch(final Object... entities) {
//		if(cancelExecute(entities)){
//			return 0;
//		}
//		int i=0;
//		for (Object t : entities ) {
//			this.delete(t);
//		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
//		        //flush a batch of inserts and release memory:
//		    	this.getSession().flush();
//		    	this.getSession().clear();
//		    }
//		}
//		return i+1;
//	}
////	public int deleteBatch(final Collection<Object> entities){
////		if(cancelExecute(entities)){
////			return 0;
////		}
////		int i=0;
////		for (Object t : entities ) {
////			this.delete(t);
////		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
////		        //flush a batch of inserts and release memory:
////		    	this.getSession().flush();
////		    	this.getSession().clear();
////		    }
////		}
////		return i+1;
////	}
//	
//	
//	public int deleteBatch(Cnd cnd){
//		cnd.setSqlType(SqlType.DELETE);
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
//		int rr=query.executeUpdate();
//		//session.clear();
//		session.flush();
//		return rr;
//		
//	}
//	
//	public int deleteBatch(final Serializable... IDS){
//		if(cancelExecute(IDS)){
//			return 0;
//		}
//		Session session = this.getSession();
//		StringBuilder builder=new StringBuilder("delete from " + this.entityClass.getName()+ " obj where ");
//		for (int i=0; i<IDS.length;i++) {   
//			if (i == 0) {
//				//hql = "id=" + selectFlag[i];
//				builder.append(" obj.id=:id"+i);
//			} else {
//				//hql = hql + " or id=" + selectFlag[i];
//				builder.append(" or obj.id=:id"+i);
//			}	
//		}
//		Query query = session.createQuery(builder.toString());
//		int i=0;
//		for (Serializable id : IDS ) {
//			query.setParameter("id"+i, id);
//			i++;
//		}
//		session.clear();
//		return query.executeUpdate();
//	}
//	
//	/**
//	 * 如果没有找到 就返回null
//	 * @param id
//	 * @return
//	 */
//	public Object load(final Serializable id) {
//		AssertUtils.notNull(id, "id不能为空");
//		try {
//			return this.getSession().load(entityClass, id);
//		} catch(ObjectNotFoundException ex){
//			return null;
//		}
//		
//	}
//	public Object get(final Serializable id) {
//		AssertUtils.notNull(id, "id不能为空");
//		//return (T) getSession().get(entityClass, id);
//		try {
//			return this.getSession().get(entityClass, id);
//		} catch(ObjectNotFoundException ex){
//			return null;
//		}
//	}
//	
//	
//	public Long queryCount(Cnd cnd) {
//		cnd.setSqlType(SqlType.SELECT);
//		StringBuilder builder=new StringBuilder("select count(*)   ");
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
//		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//		return (Long)query.uniqueResult();
//	}
//	
//	public Object queryMax(String property,Cnd cnd) {
//		StringBuilder builder=new StringBuilder("select max("+property+") ");
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
//		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//		return query.uniqueResult();
//	}
//	
//	public Object queryMin(String property,Cnd cnd) {
//		StringBuilder builder=new StringBuilder("select min("+property+") ");
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
//		query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//		return query.uniqueResult();
//	}
//	
//	public Object queryUnique(Cnd cnd) {
//		List<Object> list=query(cnd,true);
//		if(list!=null && list.size()!=0){
//			return list.get(0);
//		}
//		return null;
//	}
//	
//	public <M> M queryUnique(Cnd cnd,Class<M> classM) {
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
//		Object obj= query.uniqueResult();//.list().;
//		if(obj==null){
//			return null;
//		}
//		if(classM.isAssignableFrom(obj.getClass())){
//			return (M)obj;
//		}
//		if(!obj.getClass().isArray() && ReflectionUtils.isBaseType(classM)){
//			M m=(M) BeanUtils.convert(obj, classM);
//			return m;
//		} else {
//			List<String> names = cnd.getSelectItems().getNames();
//			try {
//				if(classM.isAssignableFrom(Map.class)){
//					classM=(Class<M>) HashMap.class;
//					M m=classM.newInstance();
//					for(int i=0;i<names.size();i++){
//						((Map)m).put(names.get(i), ((Object[])obj)[i]);
//					}
//					return m;
//				} else {
//					M m = classM.newInstance();
//					for (int i = 0; i < names.size(); i++) {
//						ReflectionUtils.setFieldValue(m, names.get(i), ((Object[])obj)[i]);
//					}
//					return m;
//				}
//				
//			} catch (Exception e) {
//				throw BusinessException.wrap(e);
//			}
//		}
//	}
//	
//	public List<Object> query(Cnd cnd,boolean uniqueResult) {
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
//		if(uniqueResult){
//			query.setFirstResult(0);
//			query.setMaxResults(1);
//		}
//		
//		setParamsByCnd(query,cnd,classMetadata);
//		return query.list();
//
//	}
//	
//	public QueryResult<Object> queryPage(final PageRequest pageRequest) {
//		throw new RuntimeException("还没有实现");
//	}
//
//	public Class<?> getEntityClass() {
//		return entityClass;
//	}
//
//	public void setEntityClass(Class<?> entityClass) {
//		this.entityClass = entityClass;
//	}
//	
//	public SessionFactory getSessionFactory(){
//		return sessionFactory;
//	}
//
//	
//	@Override
//	public void updateIgnoreNull(final Object entity) {
//		AssertUtils.notNull(entity, "entity不能为空");
//
//		
//		StringBuilder builder=getUpdateProp(entity);
//		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
//		String[] propertyNames=classMetadata.getPropertyNames();
//		Object[] propertyValues=classMetadata.getPropertyValues(entity);
//		int versionIndex=classMetadata.getVersionProperty();
//		int i=0;
//		boolean isiFirst=true;//判断是不是第一个set
//		
//		//id要循环的
//		builder.append(" where id=:id");
//		builder.append(" and "+propertyNames[versionIndex]+"=:version");
//		
//		Query query = this.getSession().createQuery(builder.toString());
//		i=0;
//		isiFirst=true;
//		for(Object o :propertyValues ){
//			if(versionIndex!=-66 && versionIndex==i){
//				continue;
//			}
//			if(o!=null){
//				if(i!=0 && !isiFirst){
//					builder.append(",");
//				}
//				query.setParameter(propertyNames[i], o);
//				isiFirst=false;
//			}
//			i++;
//		}
//
//		query.setParameter("id", classMetadata.getIdentifier(entity));
//		query.setParameter(propertyNames[versionIndex], propertyValues[versionIndex]);
//		query.executeUpdate();
//		//无论前面是否获取过这个实体，如果有这个实体的缓存，就进行清空，没有也不会再去查询
//		Object t=this.getSession().load(entityClass, classMetadata.getIdentifier(entity));
//		this.getSession().evict(t);
//		//this.clear();
//		
//	}
//	
//	public void update(Cnd cnd) {
//		if(cnd.getSqlType()!=SqlType.UPDATE ){
//			throw new BusinessException("SqlType不对");
//		}
//		if(cnd.getUpdateItems()==null){
//			throw new BusinessException("没有设置更新字段，请先设置了");
//		}
//		cnd.setSqlType(SqlType.UPDATE);
//		
//		
//		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
//		
//		StringBuilder builder=new StringBuilder();
//		cnd.joinHql(classMetadata, builder);
//		Query query = this.getSession().createQuery(builder.toString());
//		
//		setParamsByCnd(query,cnd,classMetadata);
//
//		query.executeUpdate();
//		
//	}
//	
//	private boolean cancelExecute(Object object){
//		if(object==null){
//			return true;
//		} else if(object.getClass().isArray() && ((Object[])object).length==0){
//			return true;
//		} else if(object instanceof Collection && ((Collection)object).size()==0){
//			return true;
//		}
//		return false;
//	}
//	
//	private StringBuilder getUpdateProp(final Object entity){
//		AbstractEntityPersister classMetadata=(AbstractEntityPersister)this.getSessionFactory().getClassMetadata(entityClass);
//		
//		String[] propertyNames=classMetadata.getPropertyNames();
//		Object[] propertyValues=classMetadata.getPropertyValues(entity);
//		int versionIndex=classMetadata.getVersionProperty();
//		StringBuilder builder=null;
//		if(versionIndex!=-66){//如果有version字段就更新他
//			builder=new StringBuilder("update versioned  " + this.entityClass.getName()+ " obj set ");
//		} else {
//			builder=new StringBuilder("update  " + this.entityClass.getName()+ " obj set ");
//		}
//		
//		int i=0;
//		boolean isiFirst=true;//判断是不是第一个set
//		for(Object o :propertyValues ){
//			if(versionIndex!=-66 && versionIndex==i){
//				continue;
//			}
//			if(o!=null){
//				if(i!=0 && !isiFirst){
//					builder.append(",");
//				}
//				builder.append(" obj."+propertyNames[i]+"=:"+propertyNames[i]);
//				isiFirst=false;
//			}
//			i++;
//		}
//		return builder;
//	}
//	
//	protected void setParamsByCnd(Query query,Cnd cnd,AbstractEntityPersister classMetadata,Object... otherParams) {
//		int position=0;
//		if(otherParams!=null && otherParams.length>0){
//			for(Object o:otherParams){
//				setParams(query,position,o);
//				position++;
//			}
//		}
//		Object[] params = new Object[cnd.paramCount(classMetadata)];
//		int paramsCount = cnd.joinParams(classMetadata, null, params, 0);
//
//		for (int i = 0; i < params.length; i++) {
//			setParams(query,position+i,params[i]);
//		}
//	}
//	
//	protected void setParams(Query query,int position,Object val) {		
//		if(val==null){
//			query.setParameter(position, null);
//		} else if (ReflectionUtils.isString(val)) {
//			query.setString(position, (String) val);
//		} else if (ReflectionUtils.isChar(val)) {
//			query.setString(position, ((Character) val) + "");
//		} else if (ReflectionUtils.isByte(val)) {
//			query.setByte(position, (Byte) val);
//		} else if (ReflectionUtils.isShort(val)) {
//			query.setShort(position, (Short) val);
//		} else if (ReflectionUtils.isInt(val)) {
//			query.setInteger(position, (Integer) val);
//		} else if (ReflectionUtils.isLong(val)) {
//			query.setLong(position, (Long) val);
//		} else if (ReflectionUtils.isFloat(val)) {
//			query.setFloat(position, (Float) val);
//		} else if (ReflectionUtils.isDouble(val)) {
//			query.setDouble(position, (Double) val);
//		} else if (ReflectionUtils.isBoolean(val)) {
//			query.setBoolean(position, ((Boolean) val));
//		}else if (ReflectionUtils.isBigDecimal(val)) {
//			query.setBigDecimal(position, (BigDecimal) val);
//		} else if (ReflectionUtils.isOf(val, java.sql.Date.class)) {
//			query.setDate(position, (java.sql.Date) val);
//		} else if (ReflectionUtils.isOf(val, java.util.Date.class)) {
//			query.setDate(position, (java.util.Date) val);
//		} else if (val.getClass().isArray() && val.getClass().getComponentType() == byte.class) {
//			query.setBinary(position,(byte[]) val);
//		} else if (val.getClass().isEnum()) {
//			query.setString(position, val.toString());
//		} else {
//			throw new RuntimeException("不支持的值类型！" + val.getClass().getName());
//		}
//	}
}
