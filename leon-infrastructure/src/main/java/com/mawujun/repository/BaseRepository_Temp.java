package com.mawujun.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
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
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.AppException;
import com.mawujun.repository.hibernate.NamingStrategy;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.mybatis.MybatisRepository;
import com.mawujun.repository.mybatis.Record;
import com.mawujun.utils.AssertUtils;
import com.mawujun.utils.ReflectionUtils;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.utils.page.SortInfo;
import com.mawujun.utils.page.WhereInfo;
import com.mawujun.utils.page.Operation;

/**
 * 
 * 查询和增删改分离，使用领域模型进行增删改，使用sql进行查询
 * T extends IdEntity<ID>，是表示任何一个实例都必须实现这个方法，因为在删除的时候，都会先查询
 * 
 * 还有注意更新的时候，更新的是哪些属性
 * 定义了AutoIdEntity，UUIDEntity，所有实体类都可以继承这个，这样的话，就不用自己定义id的生成方式了
 * 
 * 和mybatis整合的时候xml文件无论放在那里，但是xml中namespace默认是领域类的全限定名称，并且默认的sql的id有get,insert,update,delete,queryPage
 * 并且还有queryPage_count,这个是用来统计一共有多少条记录的,这个也不要写了，也就是会说，会自动分页，自动统计总数，只需要写入sql就可以了.
 * 但是如果查询用了union或union all的话，自动分页和自动统计总数就有问题，除非union上包了一个select 语句
 * 支持动态排序,参数必须是map，如果参数不是map，就不能进行动态排序的判断
 * 
 * xml的文件名称是：类名_数据库_Mapper.xml
 * 
 * 可以再service中继承BaseRepository，也可以新建dao继承BaseRepository，然后service依赖于dao，
 * 这个主要是看BaseRepository的变化是不是会很大
 * 
 * 当有多个数据源的时候，可以使用重载方法，覆盖默认的名称
 * 	@Resource(name="sessionFactory")
	protected void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
 * @author mawujun
 *
 * @param <T>
 * @param <ID>
 */
@Transactional(propagation=Propagation.REQUIRED)
public abstract class BaseRepository_Temp<T extends IdEntity<ID>, ID extends Serializable>  {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="batchSize")
	private Integer batchSize=30;
	
	protected Class<T> entityClass;
	protected SessionFactory sessionFactory;
	
	private MybatisRepository mybatisRepository;
	
	protected BaseRepository_Temp() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		namespace=entityClass.getName();
	}

	protected BaseRepository_Temp(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	/**
	 * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
	 */
	@Autowired
	protected void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	protected void setMybatisRepository(MybatisRepository mybatisRepository){
		this.mybatisRepository=mybatisRepository;
	}
	protected MybatisRepository getMybatisRepository() {
		return mybatisRepository;
	}

	/**
	 * 取得当前Session.
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 取得对象的主键名.
	 */
	protected String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
	
	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 */
	protected T queryUniqueBy(final String propertyName, final Object value) {
		AssertUtils.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}
	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	protected Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	/**
	 * Flush当前Session.
	 */
	protected void flush() {
		getSession().flush();
	}
	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	protected boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = queryUniqueBy(propertyName, newValue);
		return (object == null);
	}
	
	/**
	 * 保存新增或修改的对象.
	 */
	public void insert(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		Map<String,ClassMetadata>  aa=this.sessionFactory.getAllClassMetadata();
		getSession().save(entity);
		logger.debug("save entity: {}", entity);
	}
	/**
	 * 保存新增或修改的对象.
	 */
	public void insertOrUpdate(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
	}

	/**
	 * 修改对象.把数据库中的实例就更新成 : 传入对象
	 * 如果每个属性没有填的话，将会变成null，即使你本来不想改
	 */
	public void update(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		this.getSession().update(entity);
	}
	
	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final T entity) {
		
		AssertUtils.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}
	public int delete(WhereInfo... wheres){
		if(wheres==null || wheres.length==0){
			AssertUtils.notNull(wheres, "wheres不能为空");
		}
		String hql="delete from "+this.entityClass.getName() +" obj where ";
		for(WhereInfo whereInfo:wheres){
			hql=hql+" obj."+whereInfo.getProperty()+whereInfo.getOp()+":"+whereInfo.getProperty();
		}
		 Session session = this.getSession();
		 Query query = session.createQuery(hql);
		 for(WhereInfo whereInfo:wheres){
			 query.setParameter(whereInfo.getProperty(), whereInfo.getValue());
		}
		 return query.executeUpdate();
	}

	/**
	 * 按id删除对象.会先去select一下，然后再删除
	 */
	public void delete(final ID id) {
		AssertUtils.notNull(id, "id不能为空");
		try {
			T t=entityClass.newInstance();
			t.setId(id);
			delete(t);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(entityClass.getSimpleName()+"：该对象没有空的构造函数，不能实例化");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(entityClass.getSimpleName()+"：该对象没有空的构造函数，不能实例化");
		}
		
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/**
	 * 按id获取对象.
	 */
	public T get(final ID id) {
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
	public T load(final ID id) {
		AssertUtils.notNull(id, "id不能为空");
		try {
			return (T)this.getSession().load(entityClass, id);
		} catch(ObjectNotFoundException ex){
			return null;
		}
		
	}
	
	public List<T> queryAll() {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria.list();
	}
	
	/**
	 * 分页获取全部对象.这里是使用自动查询,不会使用params参数
	 * 只能用于单个对象的查询，复杂的查询请使用queryPage*ByMybatis等
	 */
	public QueryResult<T> queryPage(final PageRequest pageRequest) {
		//return queryPage(pageRequest);
		WhereInfo[] wheresNew=changeColumnToProperty(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		
		QueryResult<T> page = new QueryResult<T>(pageRequest);
		Criteria criteria = getSession().createCriteria(entityClass);
		//设置查询条件
		//List<Criterion> criterionList = new ArrayList<Criterion>();
		if(pageRequest.hasWhereInfo()){
			for(WhereInfo whereInfo:pageRequest.getWheres()){
				AssertUtils.notNull(whereInfo.getOp());
				Criterion criterion = null;
				switch(whereInfo.getOp()){
				case EQ:
					criterion = Restrictions.eq(whereInfo.getPropertyToDefault(), whereInfo.getValueToDefault());
					break;
				case LIKE:
					criterion = Restrictions.like(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.ANYWHERE);
					break;
				case LIKESTART:
					criterion = Restrictions.like(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.START);
					break;
				case LIKEEND:
					criterion = Restrictions.like(whereInfo.getPropertyToDefault(),(String) whereInfo.getValueToDefault(), MatchMode.END);
					break;
				case ILIKE:
					criterion = Restrictions.ilike(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.ANYWHERE);
					break;
				case ILIKESTART:
					criterion = Restrictions.ilike(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.START);
					break;
				case ILIKEEND:
					criterion = Restrictions.ilike(whereInfo.getPropertyToDefault(), (String)whereInfo.getValueToDefault(), MatchMode.END);
					break;
				case LE:
					criterion = Restrictions.le(whereInfo.getPropertyToDefault(), whereInfo.getValueToDefault());
					break;
				case LT:
					criterion = Restrictions.lt(whereInfo.getPropertyToDefault(), whereInfo.getValueToDefault());
					break;
				case GE:
					criterion = Restrictions.ge(whereInfo.getPropertyToDefault(), whereInfo.getValueToDefault());
					break;
				case GT:
					criterion = Restrictions.gt(whereInfo.getPropertyToDefault(), whereInfo.getValueToDefault());
					break;
				case ISNULL:
					criterion =Restrictions.isNull(whereInfo.getPropertyToDefault());
					break;
				case ISNOTNULL:
					criterion =Restrictions.isNotNull(whereInfo.getPropertyToDefault());
					break;
				}
				//criterionList.add(criterion);
				criteria.add(criterion);
			}
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
				if(SortInfo.ASC.equalsIgnoreCase(sortInfo.getDirection())){
					criteria.addOrder(Order.asc(sortInfo.getProperty()));
				} else {
					criteria.addOrder(Order.desc(sortInfo.getProperty()));
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
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
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
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
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
	 * 把对象进行批量提交
	 * @param entitys
	 */
	//@Transactional
	public int batchInsert(final T[] entitys) {
		int i=0;
		for (T t : entitys ) {
			this.insert(t);
		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		return i+1;
	}
	//@Transactional
	public int batchDelete(final T[] entitys) {
		int i=0;
		for (T t : entitys ) {
			this.delete(t);
		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		return i+1;
	}
	//@Transactional
	public int batchDelete(final ID... IDS){
		
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
//			this.delete(id);
//		    if ( ++i % batchSize == 0 ) { //20, same as the JDBC batch size
//		        //flush a batch of inserts and release memory:
//		    	this.getSession().flush();
//		    	this.getSession().clear();
//		    }
			query.setParameter("id"+i, id);
			i++;
		}
		return query.executeUpdate();
	}
	//@Transactional
	public int batchUpdate(final T[] entitys){
		int count=0;
		for ( T t:entitys ) {
		    if ( ++count % batchSize == 0 ) {
		    	this.update(t);
		    	this.getSession().flush();
		    	this.getSession().clear();
		    }
		}
		return count+1;
	}
	


	
	//===========================================下面这部分是通过Mybatis进行查询的
	//xml文件无论放在那里，但是namespace必须是
	private String namespace;
	/**
	 * 默认就是类的全限定名称+get
	 * @return
	 */
	public T getByMybatis(Object params){
		return (T)mybatisRepository.selectOneObj(namespace+".get", params);
		
	}
	
	/**
	 * 如果指定的statement中没有指定namespace的话 就使用默认的statement
	 * 默认就是类的全限定名称+get
	 * @return
	 */
	public Object getByMybatis(String statement,Object params){
		if(statement.indexOf('.')>0){
			return mybatisRepository.selectOneObj(statement, params);
		} else {
			return mybatisRepository.selectOneObj(namespace+"."+statement, params);	
		}
		
	}
	
	/**
	 * 默认就是类的全限定名称+insert
	 * @return
	 */
	public int insertByMybatis(Object params){
		return mybatisRepository.insert(namespace+".insert", params);
		
	}
	/**
	 * 如果指定的statement中没有指定namespace的话 就使用默认的statement
	 * 默认就是类的全限定名称+insert
	 * @return
	 */
	public int insertByMybatis(String statement,Object params){
		if(statement.indexOf('.')>0){
			return mybatisRepository.insert(statement, params);
		} else {
			return mybatisRepository.insert(namespace+"."+statement, params);	
		}
	}
	/**
	 * 默认就是类的全限定名称+update
	 * @return
	 */
	public int updateByMybatis(Object params){
		return mybatisRepository.update(namespace+".update", params);
		
	}
	/**
	 * 如果指定的statement中没有指定namespace的话 就使用默认的statement
	 * 默认就是类的全限定名称+update
	 * @return
	 */
	public Object updateByMybatis(String statement,Object params){
		if(statement.indexOf('.')>0){
			return mybatisRepository.update(statement, params);
		} else {
			return mybatisRepository.update(namespace+"."+statement, params);	
		}
	}
	/**
	 * 默认就是类的全限定名称+delete
	 * @return
	 */
	public int  deleteByMybatis(ID params){
		return mybatisRepository.delete(namespace+".delete", params);
		
	}
	/**
	 * 把WhereInfo中面向对象的表示方法，转换成列的名称,例如 org.id--->org_id，这个要看转化规则
	 * @param wheres
	 * @return
	 */
	protected WhereInfo[] changePropertyToColumn(WhereInfo[] wheres){
		if(wheres==null || wheres.length==0){
			return wheres;
		}
		//如果是以列的方式做为查询条件的话，就直接返回
		if(wheres[0].getPropertyToDefault().startsWith(NamingStrategy.columnPrefix)){
			return wheres;
		}
		WhereInfo[] wheresNew=new WhereInfo[wheres.length];
		int i=0;
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) this.getSessionFactory().getClassMetadata(this.entityClass);
		for(WhereInfo whereInfo:wheres){
			String[] columns=classMetadata.getPropertyColumnNames(whereInfo.getPropertyToDefault());

			if(columns==null){
				throw new AppException(whereInfo.getPropertyToDefault()+"不存在这个查询属性");
			}
	
			whereInfo.setProperty(columns[0]);
			wheresNew[i]=whereInfo;
			i++;
		}
		return wheresNew;
	}
	/**
	 * 把WhereInfo中面向数据库表的表示方法，转换成列的名称,例如 org_id--->org.id,c_name-->name，这个要看转化规则
	 * @param wheres
	 * @return
	 */
	protected WhereInfo[] changeColumnToProperty(WhereInfo[] wheres){
		if(wheres==null || wheres.length==0){
			return wheres;
		}
		//如果是以属性的方式做为查询条件的话，就直接返回
		if(!wheres[0].getPropertyToDefault().startsWith(NamingStrategy.columnPrefix)){
			return wheres;
		}
		WhereInfo[] wheresNew=new WhereInfo[wheres.length];
		int i=0;
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) this.getSessionFactory().getClassMetadata(this.entityClass);
		String[] propertyNames = classMetadata.getPropertyNames();
		for(WhereInfo whereInfo:wheres){
			for (String propertyName : propertyNames) {
				// 判断是否一对多的对像,移除
				boolean isCollection = classMetadata.getClassMetadata().getPropertyType(propertyName).isCollectionType();
				if (!isCollection) {
					String[] propertyColumnNames = classMetadata.getPropertyColumnNames(propertyName);
					for (String tempColumnName : propertyColumnNames) {
						if (whereInfo.getPropertyToDefault().equals(tempColumnName)) {
							whereInfo.setProperty(propertyName);
						}
					}
				}
			}
			wheresNew[i]=whereInfo;
			i++;
		}
		return wheresNew;
	}
	/**
	 * WhereInfo的prop即可以是org.id也可以是org_id
	 * @param wheres
	 * @return
	 */
	public int deleteByMybatis(WhereInfo... wheres){
		if(wheres==null || wheres.length==0){
			AssertUtils.notNull(wheres, "wheres不能为空");
		}
		WhereInfo[] wheresNew=changePropertyToColumn(wheres);
		return mybatisRepository.delete(namespace+".deleteByWheres", wheresNew);	
	}
	/**
	 * 如果指定的statement中没有指定namespace的话 就使用默认的statement
	 * 默认就是类的全限定名称+delete
	 * @return
	 */
	public int deleteByMybatis(String statement,Object params){
		if(statement.indexOf('.')>0){
			return mybatisRepository.delete(statement, params);
		} else {
			return mybatisRepository.delete(namespace+"."+statement, params);	
		}
	}
	
	/**
	 * 是通过mybatis进行查询的，返回的是Map对象的结果
	 * @param statement
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Map<String,Object>> queryPageMapByMybatis(final PageRequest pageRequest) {
		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		return this.getMybatisRepository().selectPage(namespace+".queryPage", pageRequest);
	}
	/**
	 *  返回的是自己封装的record的结果
	 * @param statement
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Record> queryPageRecordByMybatis(final PageRequest pageRequest) {
		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		return this.getMybatisRepository().selectPageRecord(namespace+".queryPage", pageRequest);
	}
	
	/**
	 *  返回的是实体对象的结果
	 * @param statement
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Object> queryPageObjByMybatis(final PageRequest pageRequest) {
		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		return this.getMybatisRepository().selectPageObj(namespace+".queryPage", pageRequest);
	}
	public QueryResult<T> queryPageTByMybatis(final PageRequest pageRequest) {
		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		return (QueryResult<T>)this.getMybatisRepository().selectPageObj(namespace+".queryPage", pageRequest);
	}
	
	public QueryResult<Map<String,Object>> queryPageMapByMybatis(String statement,final PageRequest pageRequest) {
		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		if(statement.indexOf('.')>0){
			return mybatisRepository.selectPage(statement, pageRequest);
		} else {
			return mybatisRepository.selectPage(namespace+"."+statement, pageRequest);	
		}
	}
	

	/**
	 *  返回的是实体对象的结果
	 * @param statement
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Object> queryPageObjByMybatis(String statement,final PageRequest pageRequest) {
		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		if(statement.indexOf('.')>0){
			return mybatisRepository.selectPageObj(statement, pageRequest);
		} else {
			return mybatisRepository.selectPageObj(namespace+"."+statement, pageRequest);	
		}
	}
	
	
	public QueryResult<Record> queryPageRecordByMybatis(String statement,final PageRequest pageRequest) {
		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
		pageRequest.setWheres(wheresNew);
		if(statement.indexOf('.')>0){
			return mybatisRepository.selectPageRecord(statement, pageRequest);
		} else {
			return mybatisRepository.selectPageRecord(namespace+"."+statement, pageRequest);	
		}
	}
	
	
}
