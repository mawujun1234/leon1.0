package com.mawujun.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BussinessException;
import com.mawujun.exception.DefaulExceptionCode;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.hibernate.HibernateDao;
import com.mawujun.repository.hibernate.NamingStrategy;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.mybatis.MybatisRepository;
import com.mawujun.repository.mybatis.Record;
import com.mawujun.utils.ReflectionUtils;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.utils.page.WhereInfo;

/**
 * 依赖于hibernate和mybatis，增，删，改使用hiberante，查询使用mybatis，
 * 注意hibernate的一级缓存，所以，在增，删，改后马上flush
 * 
 * 使用方法，新建一个dao继承这个类： class EntityTestRepository extends Repository<EntityTest,Integer>
 * 否则，就不能获取到EntityTest，这个类，要手工调用setEntityClass方法进行设置
 * 
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
 * xml的文件名称是：类名_Mapper.xml
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
@Transactional
public abstract class BaseRepository<T extends IdEntity<ID>, ID extends Serializable> {// implements IRepository<T, ID>
	
	

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	protected Integer batchSize;
	private HibernateDao<T,ID> hibernateDao;
	
	private String namespace;
	private MybatisRepository mybatisRepository;
	
	public BaseRepository() {
		Class<T> entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		hibernateDao=new HibernateDao<T,ID>(entityClass);
		mybatisRepository=new MybatisRepository();
		//namespace=entityClass.getName();
		namespace=entityClass.getPackage().getName();
	}
	
	public void setEntityClass(Class<T> entityClass) {
		hibernateDao.setEntityClass(entityClass);
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		//this.sessionFactory = sessionFactory;
		hibernateDao.setSessionFactory(sessionFactory);	
	}
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		mybatisRepository.setSqlSessionFactory(sqlSessionFactory);
	}
	protected MybatisRepository getMybatisRepository() {
		return mybatisRepository;
	}
	@Value("${hibernate.jdbc.batch_size}")
	public void setBatchSize(Integer batchSize) {
		if(batchSize!=null && batchSize!=0){
			hibernateDao.setBatchSize(batchSize);
		}
	}
	/**
	 * 完善statement，例如com.mawujun.desktop,把这个加上
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param statement
	 * @return
	 */
	protected String supplyNamespace(String statement){
		//表明指定了名称空间
		if(statement.indexOf('.')>0){
			return statement;
		} else {
			return namespace+"."+statement;
		}
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
	 * 这个方法是service中的方法
	 *	public List<User> getAllUserInitialized() {
	 *		List<User> result = (List<User>) userDao.findAll();
	 *		for (User user : result) {//这里不知道会不会产生新能问题
	 *			Hibernates.initLazyProperty(user.getRoleList());
	 *		}
	 *		return result;
	 *	}
	 * 
	 * 
	 * 
	 */
	public void initLazyProperty(Object proxy) {
		hibernateDao.initLazyProperty(proxy);
	}

	public T create(T entity) {
		// TODO Auto-generated method stub
		hibernateDao.save(entity);
		hibernateDao.flush();
		return entity;
	}
	public T createOrUpdate(T entity) {
		hibernateDao.saveOrUpdate(entity);
		hibernateDao.flush();
		return entity;
	}

	/**
	 * 修改对象.把数据库中的实例就更新成 : 传入对象
	 * 如果每个属性没有填的话，将会变成null，即使你本来不想改
	 * @return 
	 */
	public T update(T entity) {
		// TODO Auto-generated method stub
		hibernateDao.update(entity);
		hibernateDao.flush();
		return entity;
	}
	
	public void update(Cnd cnd) {
		hibernateDao.update(cnd);
		hibernateDao.flush();
	}

	public T delete(T entity) {
		// TODO Auto-generated method stub
		hibernateDao.delete(entity);
		hibernateDao.flush();
		return entity;
	}
	/**
	 * 根据传递进来的entity，哪些字段有值，不是null就更新到数据库
	 */
	public T updateIgnoreNull(T entity) {
		hibernateDao.updateIgnoreNull(entity);
		hibernateDao.flush();
		return entity;
	}
	/**
	 * 将符合where条件的，所有记录的值都更新成entity中拥有的键值对，忽略version和id字段
	 */
//	public void updateIgnoreNull(T entity,WhereInfo... wheres) {
//		hibernateDao.updateIgnoreNull(entity,wheres);
//		hibernateDao.flush();
//	}
//	
	/**
	 * 将符合where条件的，所有记录的值都更新成entity中拥有的键值对，忽略version和id字段
	 */
	public void updateIgnoreNull(T entity,Cnd cnd) {
		hibernateDao.updateIgnoreNull(entity,cnd);
		hibernateDao.flush();
	}

	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		hibernateDao.delete(id);
		hibernateDao.flush();
	}

	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return hibernateDao.get(id);
	}
	

	public int createBatch(T... entitys) {
		// TODO Auto-generated method stub
		int i=hibernateDao.saveBatch(entitys);
		hibernateDao.flush();
		return i;
	}

	public int deleteBatch(T... entitys) {
		int i=hibernateDao.deleteBatch(entitys);
		hibernateDao.flush();
		return i;
	}

	public int deleteBatch(ID... IDS) {
		int i=hibernateDao.deleteBatch(IDS);
		hibernateDao.flush();
		return i;
	}
	/**
	 * 删除表内所有的数据
	 */
	public int deleteAllBatch() {
		// TODO Auto-generated method stub
		return hibernateDao.deleteAll();
	}
//	public int deleteBatch(WhereInfo... wheres){
//		return hibernateDao.deleteBatch(wheres);
//	}
	/**
	 * 注意，使用Cnd的地方表示删除的是BaseRopository中泛型指定的类。
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 * @return
	 */
	public int deleteBatch(Cnd cnd){
		return hibernateDao.deleteBatch(cnd);
	}

	public int updateBatch(T... entitys) {
		int i=hibernateDao.updateBatch(entitys);
		hibernateDao.flush();
		return i;
	}

	public int saveBatch(Collection<T> entities) {
		int i=hibernateDao.saveBatch(entities);
		hibernateDao.flush();
		return i;
	}

	public int deleteBatch(Collection<T> entities) {
		int i=hibernateDao.deleteBatch(entities);
		hibernateDao.flush();
		return i;
	}

	public int updateBatch(Collection<T> entities) {
		int i=hibernateDao.updateBatch(entities);
		hibernateDao.flush();
		return i;
	}

	public List<T> queryAll() {
		return hibernateDao.queryAll();
	}
	public List<T> query(WhereInfo... whereInfos) {
		return hibernateDao.query(false,whereInfos);
	}
	public int queryCount(WhereInfo... whereInfos) {
		return hibernateDao.queryCount(whereInfos);
	}
	public T queryUnique(WhereInfo... whereInfos) {
		return hibernateDao.queryUnique(whereInfos);
	}
	public Object queryMax(String property,WhereInfo... whereInfos) {
		return hibernateDao.queryMax(property,whereInfos);
	}
	public List<T> queryByExample(T t) {
		return hibernateDao.queryByExample(t);
	}
	//=============================
	
	public List<T> query(Cnd cnd) {
		return hibernateDao.query(cnd,false);
	}
	/**
	 * List<String> ids=super.queryList(Cnd.select().addSelect("id"), String.class);
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param cnd
	 * @param classT要返回的数据类型
	 * @return
	 */
	public <M> List<M> queryList(Cnd cnd,Class<M> classT) {
		return hibernateDao.queryList(cnd,classT);
	}
	public int queryCount(Cnd cnd) {
		return hibernateDao.queryCount(cnd);
	}
	public T queryUnique(Cnd cnd) {
		return hibernateDao.queryUnique(cnd);
	}
	public <M> M queryUnique(Cnd cnd,Class<M> classM) {
		return hibernateDao.queryUnique(cnd, classM);
	}
	public Object queryMax(String property,Cnd cnd) {
		return hibernateDao.queryMax(property,cnd);
	}
	//=============================
	
	
	

	public QueryResult<T> queryPage(final PageRequest pageRequest) {
		return hibernateDao.queryPage(pageRequest);
	}
	
	
	/**
	 * 把WhereInfo中面向对象的表示方法，转换成列的名称,例如 org.id--->org_id，这个要看转化规则
	 * @param wheres
	 * @return
	 */
	protected void changePropertyToColumn(PageRequest pageRequest){
		if(pageRequest.isSqlModel()){
			return;
		}
		WhereInfo[] wheres=pageRequest.getWheres();
		if(wheres==null || wheres.length==0){
			return;
		}
		//如果是以列的方式做为查询条件的话，就直接返回
		if(wheres[0].getProp().startsWith(NamingStrategy.columnPrefix)){
			return;
		}
		WhereInfo[] wheresNew=new WhereInfo[wheres.length];
		int i=0;
		AbstractEntityPersister classMetadata = (AbstractEntityPersister)hibernateDao.getClassMetadata();
		for(WhereInfo whereInfo:wheres){
			String[] columns=classMetadata.getPropertyColumnNames(whereInfo.getProp());

			if(columns==null){
				//throw new BussinessException(whereInfo.getPropertyToDefault()+"不存在这个查询属性");
				throw new BussinessException(DefaulExceptionCode.SYSTEM_PROPERTY_IS_NOT_EXISTS).set("propertyName", whereInfo.getProp());
			}
	
			whereInfo.setProp(columns[0]);
			wheresNew[i]=whereInfo;
			i++;
		}
		pageRequest.setWheres(wheresNew);
		//return wheresNew;
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
		if(!wheres[0].getProp().startsWith(NamingStrategy.columnPrefix)){
			return wheres;
		}
		WhereInfo[] wheresNew=new WhereInfo[wheres.length];
		int i=0;
		AbstractEntityPersister classMetadata = (AbstractEntityPersister)hibernateDao.getClassMetadata();
		String[] propertyNames = classMetadata.getPropertyNames();
		for(WhereInfo whereInfo:wheres){
			for (String propertyName : propertyNames) {
				// 判断是否一对多的对像,移除
				boolean isCollection = classMetadata.getClassMetadata().getPropertyType(propertyName).isCollectionType();
				if (!isCollection) {
					String[] propertyColumnNames = classMetadata.getPropertyColumnNames(propertyName);
					for (String tempColumnName : propertyColumnNames) {
						if (whereInfo.getProp().equals(tempColumnName)) {
							whereInfo.setProp(propertyName);
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
	 * 默认调用的是
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<T> queryPageMybatis(final PageRequest pageRequest) {
		if(pageRequest.getSqlId()==null || "".equals(pageRequest.getSqlId())){
			throw new BussinessException("sqlId必须设置");
		}
//		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
//		pageRequest.setWheres(wheresNew);
		changePropertyToColumn(pageRequest);
		
		return this.getMybatisRepository().selectPage(supplyNamespace(pageRequest.getSqlId()), pageRequest,hibernateDao.getEntityClass());
	}
	
	/**
	 * 默认调用的是
	 * @param pageRequest
	 * @return
	 */
	public <M> QueryResult<M> queryPageMybatis(final PageRequest pageRequest,Class<M> classM) {
		if(pageRequest.getSqlId()==null || "".equals(pageRequest.getSqlId())){
			throw new BussinessException("sqlId必须设置");
		}
		changePropertyToColumn(pageRequest);
		
		return this.getMybatisRepository().selectPage(supplyNamespace(pageRequest.getSqlId()), pageRequest,classM);
	}
	

	
	/**
	 * 指定自己的查询sql
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<T> queryPageMybatis(final String sqlStatement,final PageRequest pageRequest) {
//		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
//		pageRequest.setWheres(wheresNew);
		changePropertyToColumn(pageRequest);
		return this.getMybatisRepository().selectPage(supplyNamespace(sqlStatement), pageRequest,hibernateDao.getEntityClass());
	}
	
	/**
	 * 是通过mybatis进行查询的，返回的是Map对象的结果
	 * @param statement
	 * @param pageRequest
	 * @return
	 */
	public QueryResult<Map<String,Object>> queryPageMapBybatis(final String sqlStatement,final PageRequest pageRequest) {
//		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
//		pageRequest.setWheres(wheresNew);
		changePropertyToColumn(pageRequest);
		
		return this.getMybatisRepository().selectPage(supplyNamespace(sqlStatement), pageRequest);
	}
	
	
	
//	/**
//	 *  返回的是实体对象的结果
//	 * @param statement
//	 * @param pageRequest
//	 * @return
//	 */
//	public QueryResult<Object> queryPageObjByMybatis(final String statement,final PageRequest pageRequest) {
////		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
////		pageRequest.setWheres(wheresNew);
//		changePropertyToColumn(pageRequest);
//		
//		return this.getMybatisRepository().selectPage(supplyNamespace(statement), pageRequest);
//	}
//	
	
	public QueryResult<Record> queryPageRecordByMybatis(final String statement,final PageRequest pageRequest) {
//		WhereInfo[] wheresNew=changePropertyToColumn(pageRequest.getWheres());
//		pageRequest.setWheres(wheresNew);
		changePropertyToColumn(pageRequest);
		return this.getMybatisRepository().selectPageRecord(supplyNamespace(statement), pageRequest);	
	}
	
//	public List<Map<String,Object>> queryListByMybatis(final String statement,final Map params) {
//		return this.getMybatisRepository().selectList(supplyNamespace(statement),params);
//	}
//	
	public List<Object> queryListObject(final String statement,final Object params) {
		return this.getMybatisRepository().selectListObj(supplyNamespace(statement),params);
	}
	public List<Map<String,Object>> queryList(final String statement,final Object params) {
		return this.getMybatisRepository().selectList(supplyNamespace(statement),params);
	}
	public List<Map<String,Object>> queryList(final String statement,final Map params) {
		return this.getMybatisRepository().selectList(supplyNamespace(statement),params);
	}
	/**
	 * 主要用于数组的参数的时候，例如 name in (...)的时候，而且只有那么这么一个参数,mybatis在接收的时候接收到的是array
	 * 只是省略了数组参数的构造
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param statement
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryList(final String statement,Object... params) {
		return this.getMybatisRepository().selectList(supplyNamespace(statement),params);
	}
	public List<T> queryListT(final String statement,final Map<String,Object> params) {
		return this.getMybatisRepository().selectList(supplyNamespace(statement),params,hibernateDao.getEntityClass());
	}
	public List<T> queryListT(final String statement,final Object params) {
		return this.getMybatisRepository().selectList(supplyNamespace(statement),params,hibernateDao.getEntityClass());
	}

	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param statement
	 * @param params 可以是map，bean等各种参数类型
	 * @param classM
	 * @return
	 */
	public <M> List<M> queryList(String statement, Object params,Class<M> classM)  {
		return this.getMybatisRepository().selectList(supplyNamespace(statement),params,classM);
	}
	public HibernateDao<T, ID> getHibernateDao() {
		return hibernateDao;
	}
	
	public int queryCountMybatis(final String statement,Object params) {
		return this.getMybatisRepository().selectInt(statement, params);
	}

}
