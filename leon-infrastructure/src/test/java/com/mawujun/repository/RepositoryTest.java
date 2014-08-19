package com.mawujun.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ClassUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mawujun.mybatis.MybatisUtil;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.test.DbunitBaseRepositoryTest;
import com.mawujun.utils.FileUtils;
import com.mawujun.utils.hibernate.HibernateUtil;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.utils.page.WhereInfo;

public class RepositoryTest extends DbunitBaseRepositoryTest {
	
	private String EntityTest_TableName="t_EntityTest";
	private static BaseRepository<EntityTest,Integer> repository;
	private static SessionFactory sessionFactory;
	private static SqlSessionFactory sqlSessionFactory;
	
	private static class EntityTestRepository extends BaseRepository<EntityTest,Integer> {
		
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DbunitBaseRepositoryTest.initHibernate("com/mawujun/repository/hibernate.properties");
		repository=new  EntityTestRepository();
		sessionFactory=HibernateUtil.getSessionFactory("com/mawujun/repository/hibernate.cfg.xml","com/mawujun/repository/hibernate.properties");
		sqlSessionFactory=MybatisUtil.getSessionFactory("com/mawujun/repository/Configuration.xml");
		repository.setSessionFactory(sessionFactory);
		repository.setSqlSessionFactory(sqlSessionFactory);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	// 讀取指定的資料集合
	private static IDataSet getXMLDataSet(String file) throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
	}

	
	@Before
	public void before() throws Exception {
		//DatabaseOperation.CLEAN_INSERT是DELETE_ALL和 INSERT的绑定. 
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, getXMLDataSet(FileUtils.getCurrentClassPath(this)+"RepositoryTest.xml"));
	}
	@After
	public void after() throws Exception {
		//tx.commit();
	}
	@Test
	public void testGet() {
		//fail("Not yet implemented");
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity=repository.get(1);
		assertEquals(new Integer(1),entity.getId());
		assertEquals("admin1",entity.getFirstName());
		assertEquals("123",entity.getLastName());
		assertEquals(1,entity.getVersion());
		tx.commit();
	}
	@Test
	public void testSave() throws DataSetException, SQLException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		//fail("Not yet implemented");
		EntityTest entity=new EntityTest();
		entity.setFirstName("ma");
		entity.setLastName("wujun");
		entity.setEmail("160649888@163.com");
		repository.create(entity);
		tx.commit();
		
		assertNotNull(entity.getId());
		assertEquals(new Integer(4), entity.getId());
		ITable table =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id="+entity.getId());
		assertEquals(1,table.getRowCount());
		assertEquals("ma",table.getValue(0, "firstName"));
		assertEquals("wujun",table.getValue(0, "lastName"));
		assertEquals(0,entity.getVersion());
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testSaveException() throws Exception {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		//tx.begin();
		//fail("Not yet implemented");
		EntityTest entity1=new EntityTest();
		entity1.setFirstName("ma");
		entity1.setLastName("wujun");
		entity1.setEmail("11");//如果不是email的格式，就会爆实体类验证异常。
		try {
			//不回滚的话，后面的测试就执行部下去了，会爆org.hibernate.TransactionException: nested transactions not supported异常
			repository.create(entity1);//这里就报错了
			tx.commit();
		} catch(Exception e){
			tx.rollback();
			throw e;
		}
		
	}

	@Test
	public void testUpdate() throws SQLException, DataSetException {
		//获取事物，再提交事物为什么不放到@Befor和@After方法里呢？因为和dbunit整合的时候，并且h2采用了多版本提交的时候，就会发现更改的数据没有提交，测试就不会通过
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity=new EntityTest();
		entity.setFirstName("ma");
		entity.setLastName("wujun");
		entity.setEmail("160649888@163.com");
		entity.setId(1);
		entity.setVersion(1);
		repository.update(entity);
		tx.commit();
		
		assertEquals(3,dbConn.getRowCount(EntityTest_TableName));
		ITable table =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1");
		assertEquals(1,table.getRowCount());
		assertEquals("ma",table.getValue(0, "firstName"));
		assertEquals("wujun",table.getValue(0, "lastName"));
		
	}

	@Test
	public void testDeleteT() throws SQLException, DataSetException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity=new EntityTest();
		entity.setVersion(1);//这里必须加上version，否则将会报错
		entity.setId(1);
		repository.delete(entity);
		tx.commit();
		
		assertEquals(2,dbConn.getRowCount(EntityTest_TableName));
		ITable table =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1");
		assertEquals(0,table.getRowCount());
	}

	@Test
	public void testDeleteSerializable() throws DataSetException, SQLException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		repository.delete(1);
		tx.commit();
		
		assertEquals(2,dbConn.getRowCount(EntityTest_TableName));
		ITable table =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1");
		assertEquals(0,table.getRowCount());
	}

	@Test
	public void testBatchSaveTArray() throws SQLException {
		//fail("Not yet implemented");
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest[] entitys=new EntityTest[10];
		for(int i=0;i<entitys.length;i++){
			EntityTest entity=new EntityTest();
			entity.setFirstName("1");
			entity.setLastName("1");
			entity.setEmail("1@163.com");
			entitys[i]=entity;
		}
		repository.createBatch(entitys);
		tx.commit();
		assertEquals(13,dbConn.getRowCount(EntityTest_TableName));
	}
	@Test
	public void testBatchSaveCollectionOfT() throws SQLException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		List<EntityTest> entitys=new ArrayList<EntityTest>();
		for(int i=0;i<10;i++){
			EntityTest entity=new EntityTest();
			entity.setFirstName("1");
			entity.setLastName("1");
			entity.setEmail("1@163.com");
			entitys.add(entity);
		}
		repository.saveBatch(entitys);
		tx.commit();
		assertEquals(13,dbConn.getRowCount(EntityTest_TableName));
	}

	@Test
	public void testBatchDeleteTArray() throws SQLException, DataSetException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity1=new EntityTest();
		entity1.setVersion(1);
		entity1.setId(1);
		EntityTest entity2=new EntityTest();
		entity2.setId(2);
		entity2.setVersion(1);
		
		repository.deleteBatch(entity1,entity2);
		tx.commit();
		assertEquals(1,dbConn.getRowCount(EntityTest_TableName));
		
		ITable table =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1 or id=2");
		assertEquals(0,table.getRowCount());
	}
	
	@Test
	public void testBatchDeleteCollectionOfT() throws DataSetException, SQLException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity1=new EntityTest();
		entity1.setId(1);
		entity1.setVersion(1);
		EntityTest entity2=new EntityTest();
		entity2.setId(2);
		entity2.setVersion(1);
		List<EntityTest> entitys=new ArrayList<EntityTest>();
		entitys.add(entity1);
		entitys.add(entity2);
		
		repository.deleteBatch(entitys);
		tx.commit();
		assertEquals(1,dbConn.getRowCount(EntityTest_TableName));
		
		ITable table =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1 or id=2");
		assertEquals(0,table.getRowCount());
	}

	@Test
	public void testBatchDeleteIDArray() throws SQLException, DataSetException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		
		repository.deleteBatch(1,2);
		tx.commit();
		assertEquals(1,dbConn.getRowCount(EntityTest_TableName));
		
		ITable table =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1 or id=2");
		assertEquals(0,table.getRowCount());
	}
	@Test
	public void testBatchDeleteAll() throws DataSetException, SQLException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		repository.deleteAllBatch();
		tx.commit();
		
		ITable table =dbConn.createTable(EntityTest_TableName);
		assertEquals(0,table.getRowCount());
	}
//	@Test
//	public void testBatchDeleteWhereInfoNormal() throws SQLException, DataSetException {
//		//fail("Not yet implemented");
//		//在这里测试所有的where条件，构成的sql而不单单是=,>,<这些二元操作符，还有in等这些
//		WhereInfo whereinfo=new WhereInfo("age",">","20");//WhereInfo.parse("age_gt", "20");
//		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
//		repository.deleteBatch(whereinfo);
//		tx.commit();
//		
//		ITable table =dbConn.createTable(EntityTest_TableName);
//		assertEquals(1,table.getRowCount());
//		assertEquals("admin1",table.getValue(0, "firstName"));
//		assertEquals("123",table.getValue(0, "lastName"));
//		assertEquals("admin1@163.com",table.getValue(0, "email"));
//		assertEquals(20,table.getValue(0, "age"));
//	}
//	
//	@Test
//	public void testBatchDeleteWhereInfoBetween() throws SQLException, DataSetException {
//		//fail("Not yet implemented");
//		//在这里测试所有的where条件，构成的sql而不单单是=,>,<这些二元操作符，还有in等这些
//		WhereInfo whereinfo=new WhereInfo("age","between",new String[]{"20","30"});//WhereInfo.parse("age_between", "20,30");
//		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
//		repository.deleteBatch(whereinfo);
//		tx.commit();
//		
//		ITable table =dbConn.createTable(EntityTest_TableName);
//		assertEquals(1,table.getRowCount());
//	}
//	
//	@Test
//	public void testBatchDeleteWhereInfoIn() throws SQLException, DataSetException {
//		//fail("Not yet implemented");
//		//在这里测试所有的where条件，构成的sql而不单单是=,>,<这些二元操作符，还有in等这些
//		WhereInfo whereinfo=new WhereInfo("age","in",new String[]{"20","30"});//WhereInfo.parse("age_in", "20,30");
//		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
//		repository.deleteBatch(whereinfo);
//		tx.commit();
//		
//		ITable table =dbConn.createTable(EntityTest_TableName);
//		assertEquals(1,table.getRowCount());
//		
//		//ClassUtils.isPrimitiveOrWrapper(type)
//	}
	
	@Test
	public void testBatchDeletCnd() throws SQLException, DataSetException {
		//fail("Not yet implemented");
		//在这里测试所有的where条件，构成的sql而不单单是=,>,<这些二元操作符，还有in等这些
		//WhereInfo whereinfo=WhereInfo.parse("age_in", "20,30");
		
		
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		repository.deleteBatch(Cnd.select().andIn("age", 20,30));
		tx.commit();
		
		ITable table =dbConn.createTable(EntityTest_TableName);
		assertEquals(1,table.getRowCount());
		
		//ClassUtils.isPrimitiveOrWrapper(type)
	}

	@Test
	public void testUpdateDynamic() throws DataSetException, SQLException{
		
		//注意测试，当某个字段为瞬时字段的时候要不要更新，和是否要更新version字段。注意hql怎么调用
		//再测试，当使用hql进行删除，更新后，再get或load出来的数据是否也变了，即测试hql是否会影响一级缓存，如果会影响，那使用hql后要对缓存进行clear。
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		EntityTest entity=new EntityTest();
		entity.setId(1);
		entity.setVersion(1);
		entity.setFirstName("update1New");
		repository.updateIgnoreNull(entity);
		tx.commit();
		
		
		ITable table1 =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1 ");
		assertEquals(1,table1.getRowCount());
		assertEquals("update1New",table1.getValue(0, "firstName"));
		assertEquals("123",table1.getValue(0, "lastName"));
		assertEquals("admin1@163.com",table1.getValue(0, "email"));
	}
	@Test
	public void testUpdateDynamic1() throws DataSetException, SQLException{
		//测试hql和缓存的关系
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		//EntityTest entity0=repository.get(1);
		//assertEquals("admin1",entity0.getFirstName());
		
		EntityTest entity=new EntityTest();
		entity.setId(1);
		entity.setVersion(1);
		entity.setFirstName("update1New");
		repository.updateIgnoreNull(entity);
		
		EntityTest entity1=repository.get(1);
		assertEquals("update1New",entity1.getFirstName());
		tx.commit();
		
		
		ITable table1 =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1 ");
		assertEquals(1,table1.getRowCount());
		assertEquals("update1New",table1.getValue(0, "firstName"));
		assertEquals("123",table1.getValue(0, "lastName"));
		assertEquals("admin1@163.com",table1.getValue(0, "email"));
	}
//	@Test
//	public void testUpdateDynamicWhereInfo() throws DataSetException, SQLException{
//		//fail("Not yet implemented");
//		
//		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
//		EntityTest entity=new EntityTest();
//		entity.setEmail("1111@11.com");
//		
//		WhereInfo where=new WhereInfo("age","in","20,30,40");//WhereInfo.parse("age_in", "20,30,40");
//		repository.updateIgnoreNull(entity, where);
//		tx.commit();
//		
//		ITable table =dbConn.createTable(EntityTest_TableName);
//		assertEquals(3,table.getRowCount());
//		
//		for(int i=0;i<table.getRowCount();i++){
//			assertEquals("1111@11.com",table.getValue(i, "email"));
//		}
//	}
	
	@Test
	public void testUpdateDynamicCnd() throws DataSetException, SQLException{
		//fail("Not yet implemented");
		
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity=new EntityTest();
		entity.setEmail("1111@11.com");
		
		//WhereInfo where=WhereInfo.parse("age_in", "20,30,40");
		repository.updateIgnoreNull(entity, Cnd.select().andIn("age", 20,30,40));
		tx.commit();
		
		ITable table =dbConn.createTable(EntityTest_TableName);
		assertEquals(3,table.getRowCount());
		
		for(int i=0;i<table.getRowCount();i++){
			assertEquals("1111@11.com",table.getValue(i, "email"));
		}
	}

	@Test
	public void testBatchUpdateTArray() throws DataSetException, SQLException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity1=new EntityTest();
		entity1.setId(1);
		entity1.setFirstName("update1");
		entity1.setVersion(1);
		EntityTest entity2=new EntityTest();
		entity2.setId(2);
		entity2.setFirstName("update2");
		entity2.setVersion(1);
		repository.updateBatch(entity1, entity2);
		tx.commit();
		
		ITable table =dbConn.createTable(EntityTest_TableName);
		assertEquals(3,table.getRowCount());
		ITable table1 =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1 ");
		assertEquals(1,table1.getRowCount());
		assertEquals("update1",table1.getValue(0, "firstName"));
		Assert.assertNull(table1.getValue(0, "email"));
		assertEquals(2,table1.getValue(0, "version"));
		ITable table2 =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=2 ");
		assertEquals(1,table2.getRowCount());
		assertEquals("update2",table2.getValue(0, "firstName"));
		assertEquals(2,table2.getValue(0, "version"));
		Assert.assertNull(table2.getValue(0, "email"));

	}

	@Test
	public void testBatchUpdateCollectionOfT() throws DataSetException, SQLException {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity1=new EntityTest();
		entity1.setId(1);
		entity1.setFirstName("update1");
		entity1.setVersion(1);
		EntityTest entity2=new EntityTest();
		entity2.setId(2);
		entity2.setFirstName("update2");
		entity2.setVersion(1);
		List<EntityTest> entitys=new ArrayList<EntityTest>();
		entitys.add(entity1);
		entitys.add(entity2);
		repository.updateBatch(entitys);
		tx.commit();
		
		ITable table =dbConn.createTable(EntityTest_TableName);
		assertEquals(3,table.getRowCount());
		ITable table1 =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=1 ");
		assertEquals(1,table1.getRowCount());
		assertEquals("update1",table1.getValue(0, "firstName"));
		Assert.assertNull(table1.getValue(0, "email"));
		assertEquals(2,table1.getValue(0, "version"));
		ITable table2 =dbConn.createQueryTable(EntityTest_TableName, "select * from "+EntityTest_TableName+" where id=2 ");
		assertEquals(1,table2.getRowCount());
		assertEquals("update2",table2.getValue(0, "firstName"));
		assertEquals(2,table2.getValue(0, "version"));
		Assert.assertNull(table2.getValue(0, "email"));

	}


	@Test
	public void testQueryAll() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		List<EntityTest> entitys=repository.queryAll();
		tx.commit();
		
		assertEquals(3,entitys.size());
	}

	@Test
	public void testQueryByExample() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		EntityTest entity=new EntityTest();
		entity.setLastName("123");
		List<EntityTest> entitys=repository.queryByExample(entity);
		tx.commit();
		assertEquals(2,entitys.size());
	}
	
	@Test
	public void testQueryPage() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		WhereInfo whereinfo=new WhereInfo("age",">","20");//WhereInfo.parse("age_gt", "20");
		WhereInfo whereinfo1=new WhereInfo("firstName","like","admin");//WhereInfo.parse("firstName_like", "admin");
		WhereInfo whereinfo2=new WhereInfo("lastName","in",new String[]{"123","1234"});//WhereInfo.parse("lastName_in", "123","1234");
		PageRequest page=new PageRequest();
		page.setWheres(whereinfo,whereinfo1,whereinfo2);
		page.setStratAndLimit(1, 10);
		
		QueryResult<EntityTest> entitys=repository.queryPage(page);
		tx.commit();
		assertEquals(2,entitys.getTotalItems());
		assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryCnd() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.select().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		List<EntityTest> entitys=repository.query(cnd);
		tx.commit();
		assertEquals(2,entitys.size());
		//assertEquals(1,entitys.getTotalPages());
	}
	@Test
	public void testQueryCnd_Map() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.select().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234").addSelect("age","firstName").asc("age");
		
		List<Map> entitys=repository.queryList(cnd,Map.class);
		tx.commit();
		
		assertEquals(2,entitys.size());
		assertEquals(true,entitys.get(0) instanceof Map);
		assertEquals(30,entitys.get(0).get("age"));
		assertEquals(40,entitys.get(1).get("age"));
		//assertEquals(1,entitys.getTotalPages());
	}
	@Test
	public void testQueryCnd_M() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.select().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234").addSelect("age","firstName").asc("age");
		
		List<EntityTest> entitys=repository.queryList(cnd,EntityTest.class);
		tx.commit();
		
		assertEquals(2,entitys.size());
		assertEquals(true,entitys.get(0) instanceof EntityTest);
		assertEquals((Integer)30,entitys.get(0).getAge());
		assertEquals((Integer)40,entitys.get(1).getAge());
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryCnd_BaseType() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.select().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234").addSelect("firstName").asc("age");
		
		List<String> entitys=repository.queryList(cnd,String.class);
		tx.commit();
		
		assertEquals(2,entitys.size());
		assertEquals(true,entitys.get(0) instanceof String);
		//assertEquals((Integer)30,entitys.get(0).getAge());
		//assertEquals((Integer)40,entitys.get(1).getAge());
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryCountCnd() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.count().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		int counts=repository.queryCount(cnd);
		tx.commit();
		assertEquals(2,counts);
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryCountCndProperty() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.count("id").andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		int counts=repository.queryCount(cnd);
		tx.commit();
		assertEquals(2,counts);
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryUniqueCnd() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.where().and("id","=", 1);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		EntityTest entitys=repository.queryUnique(cnd);
		tx.commit();
		assertEquals((Integer)1,entitys.getId());
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void queryUniqueCnd_M() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.where().and("id","=", 1);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		EntityTest entitys=repository.queryUnique(cnd,EntityTest.class);
		tx.commit();
		assertEquals((Integer)1,entitys.getId());
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void queryUniqueCnd_BaseType() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.where().and("id","=", 1).addSelect("id");//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Integer entitys=repository.queryUnique(cnd,Integer.class);
		tx.commit();
		assertEquals((Integer)1,entitys);
		//assertEquals(1,entitys.getTotalPages());
	}
	@Test
	public void queryUniqueCnd_Map() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.where().and("id","=", 1).addSelect("id","firstName");//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Map entitys=repository.queryUnique(cnd,Map.class);
		tx.commit();
		assertEquals((Integer)1,entitys.get("id"));
	}
	
	
	@Test
	public void testQueryMaxCnd() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.select().andIn("id", 1,2,3);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Object entitys=repository.queryMax("id",cnd);
		tx.commit();
		assertEquals((Integer)3,entitys);
		//assertEquals(1,entitys.getTotalPages());
	}
	@Test
	public void testQueryMaxCnd1() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.max("id").andIn("id", 1,2,3);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Object entitys=repository.queryMax(cnd);
		tx.commit();
		assertEquals((Integer)3,entitys);
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryMinCnd() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.select().andIn("id", 1,2,3);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Object entitys=repository.queryMin("id",cnd);
		tx.commit();
		assertEquals((Integer)1,entitys);
		//assertEquals(1,entitys.getTotalPages());
	}
	@Test
	public void testQueryMinCnd1() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.min("id").andIn("id", 1,2,3);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Object entitys=repository.queryMin(cnd);
		tx.commit();
		assertEquals((Integer)1,entitys);
		//assertEquals(1,entitys.getTotalPages());
	}
	@Test
	public void testQuerySumCnd() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.sum("id").andIn("id", 1,2,3);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Object entitys=repository.queryMin(cnd);
		tx.commit();
		assertEquals(6L,entitys);
		//assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryAvgCnd() {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		Cnd cnd=Cnd.avg("id").andIn("id", 1,2,3);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Object entitys=repository.queryAvg(cnd);
		tx.commit();
		assertEquals(2.0,entitys);
		//assertEquals(1,entitys.getTotalPages());
	}

	
	@Test
	public void testQueryPageByMybatis() {
		//Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		
		WhereInfo whereinfo=new WhereInfo("age",">","20");//WhereInfo.parse("age_gt", "20");
		WhereInfo whereinfo1=new WhereInfo("firstName","like","admin");//WhereInfo.parse("firstName_like", "admin");
		WhereInfo whereinfo2=new WhereInfo("lastName","in",new String[]{"123","1234"});//WhereInfo.parse("lastName_in", "123","1234");
		WhereInfo whereinfo3=new WhereInfo("id","between",new String[]{"1","2"});//WhereInfo.parse("id_between", "1","2");
		PageRequest page=new PageRequest();
		page.setWheres(whereinfo,whereinfo1,whereinfo2,whereinfo3);
		page.setStratAndLimit(1, 10);
		page.setSqlId("queryPage");
		
		QueryResult<EntityTest> entitys=repository.queryPageMybatis(page);
		//tx.commit();
		assertEquals(1,entitys.getTotalItems());
		assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void testQueryPageByMybatis1() {
		//Transaction tx = sessionFactory.getCurrentSession().beginTransaction(); 
		
		WhereInfo whereinfo=new WhereInfo("age",">","20");//;WhereInfo.parse("age_gt", "20");
		WhereInfo whereinfo1=new WhereInfo("firstName","like","admin");//WhereInfo.parse("firstName_like", "admin");
		WhereInfo whereinfo2=new WhereInfo("lastName","in",new String[]{"123","1234"});//WhereInfo.parse("lastName_in", "123","1234");
		WhereInfo whereinfo3=new WhereInfo("id","between",new String[]{"1","2"});//WhereInfo.parse("id_between", "1","2");
		PageRequest page=new PageRequest();
		page.setWheres(whereinfo,whereinfo1,whereinfo2,whereinfo3);
		page.setStratAndLimit(1, 10);
		//主要测试getMybatisStataement方法会不会自动组装com.mawujun.repository.EntityTest.queryPage
		QueryResult<EntityTest> entitys=repository.queryPageMybatis("queryPage",page);
		//tx.commit();
		assertEquals(1,entitys.getTotalItems());
		assertEquals(1,entitys.getTotalPages());
	}

}
