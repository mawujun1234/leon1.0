package com.mawujun.repository1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.EntityTest;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.test.DbunitBaseRepositoryTest;
import com.mawujun.utils.FileUtils;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.utils.page.WhereInfo;

/**
 * 测试测试环境
 * @author mawujun
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/com/mawujun/repository1/applicationContext.xml"}) 
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback =true)  
public class Repository1Test  extends DbunitBaseRepositoryTest{
	private String EntityTest_TableName="t_EntityTest";
	
	@Resource(name="entityTestMapper")
	EntityTestMapper entityTestMapper;
	@Autowired
	DataSource dataSource;


//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		DbunitBaseRepositoryTest.initDataSource(dataSource);
//	}
	private  IDataSet getXMLDataSet(String file) throws Exception {
		
		return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
	}

	@Before
	public void before() throws Exception {
		// DatabaseOperation.CLEAN_INSERT是DELETE_ALL和 INSERT的绑定.
		DbunitBaseRepositoryTest.initDataSource(dataSource);
		DatabaseOperation.CLEAN_INSERT.execute(dbConn,getXMLDataSet(FileUtils.getCurrentClassPath(this)+ "initData.xml"));
		
	}
	@After
	public void after() throws Exception {
		//DatabaseOperation.DELETE_ALL.execute(dbConn, null);
	}
	/**
	 * 
	 * @throws IOException
	 * @throws SQLException 
	 * @throws DataSetException 
	 */
	@Test
	public void create() throws IOException, DataSetException, SQLException {
		assertNotNull(entityTestMapper);
		
		EntityTest entity=new EntityTest();
		entity.setFirstName("ma");
		entity.setLastName("wujun");
		entity.setEmail("160649888@163.com");
		Integer id=entityTestMapper.create(entity);
		
		assertNotNull(entity.getId());
		assertEquals(id, entity.getId());
		//assertEquals(new Integer(4), entity.getId());
	}
	@Test
	public void createCnd() throws IOException, DataSetException, SQLException {
		assertNotNull(entityTestMapper);
		
//		EntityTest entity=new EntityTest();
//		entity.setFirstName("ma");
//		entity.setLastName("wujun");
//		entity.setEmail("160649888@163.com");
		Cnd cnd=Cnd.insert().set("firstName", "ma").set("lastName", "wujun").set("email", "160649888@163.com").set("id", 4).set("version", 1);
		entityTestMapper.create(cnd);
		
		long count= entityTestMapper.queryCount(Cnd.count());
		
		assertEquals(4, count);
		//assertEquals(new Integer(4), entity.getId());
	}
	
	@Test
	public void createOrUpdate() throws IOException, DataSetException, SQLException {
		assertNotNull(entityTestMapper);
		
		EntityTest entity=new EntityTest();
		entity.setFirstName("ma");
		entity.setLastName("wujun");
		entity.setEmail("160649888@163.com");
		entityTestMapper.createOrUpdate(entity);
		
		assertNotNull(entity.getId());
		//assertEquals(id, entity.getId());
		//assertEquals(new Integer(4), entity.getId());
	}
	
	@Test
	public void update() throws IOException, DataSetException, SQLException {
		EntityTest entity=new EntityTest();
		entity.setFirstName("ma");
		entity.setLastName("wujun");
		entity.setEmail("160649888@163.com");
		entity.setId(1);
		entity.setVersion(1);
		entityTestMapper.update(entity);
		
		EntityTest table=entityTestMapper.get(1);
		assertEquals(new Integer(1),table.getId());
		assertEquals("ma",table.getFirstName());
		assertEquals("wujun",table.getLastName());
		assertEquals(new Integer(2),new Integer(table.getVersion()));
	}
	
	@Test
	public void deleteT() throws IOException, DataSetException, SQLException {
		EntityTest entity=new EntityTest();
		entity.setVersion(1);//这里必须加上version，否则将会报错
		entity.setId(1);
		entityTestMapper.delete(entity);
		
		EntityTest table=entityTestMapper.get(1);
		assertEquals(null, table);
	}
	
	@Test
	public void deleteSerializable() throws DataSetException, SQLException {
		entityTestMapper.deleteById(1);
		
		EntityTest table=entityTestMapper.get(1);
		assertEquals(null, table);
	}
	@Test
	public void updateIgnoreNull() {
		//assertNotNull(null);
		EntityTest entity=new EntityTest();
		entity.setFirstName("111");
		//entity.setLastName("wujun");
		//entity.setEmail("160649888@163.com");
		entity.setId(1);
		entity.setVersion(1);
		entityTestMapper.updateIgnoreNull(entity);
		
		EntityTest table=entityTestMapper.get(1);
		assertEquals(new Integer(1),table.getId());
		assertEquals("111",table.getFirstName());
		assertEquals("123",table.getLastName());
		assertEquals(new Integer(2),new Integer(table.getVersion()));
	}
	@Test
	public void updateCnd() {
		entityTestMapper.update(Cnd.update().set("email", "1111@11.com").andEquals("id", 1));

		EntityTest table=entityTestMapper.get(1);
		assertEquals(new Integer(1),table.getId());
		assertEquals("1111@11.com",table.getEmail());
		assertEquals("123",table.getLastName());
		assertEquals(new Integer(1),new Integer(table.getVersion()));
	}
	@Test
	public void createBatch() throws SQLException {
		assertEquals(3,dbConn.getRowCount(EntityTest_TableName));
		//assertNotNull(null);
		EntityTest[] entitys=new EntityTest[10];
		for(int i=4;i<entitys.length+4;i++){
			EntityTest entity=new EntityTest();
			entity.setFirstName(i+"firstName");
			entity.setLastName(i+"lastName");
			entity.setEmail(i+"@163.com");
			entitys[i-4]=entity;
		}
		entityTestMapper.createBatch(entitys);
		//assertEquals(13,dbConn.getRowCount(EntityTest_TableName));
		EntityTest table5=entityTestMapper.get(entitys[0].getId());
		//assertEquals(new Integer(5),table5.getId());
		assertEquals("4@163.com",table5.getEmail());
		assertEquals("4lastName",table5.getLastName());
		assertEquals("4lastName",table5.getLastName());
		assertEquals(new Integer(0),new Integer(table5.getVersion()));
		
		EntityTest table6=entityTestMapper.get(entitys[2].getId());
		//assertEquals(new Integer(6),table6.getId());
		assertEquals("6@163.com",table6.getEmail());
		assertEquals("6lastName",table6.getLastName());
		assertEquals("6lastName",table6.getLastName());
		assertEquals(new Integer(0),new Integer(table6.getVersion()));
	}
	@Test
	public void createBatchCollection() {
		//assertNotNull(null);
		List<EntityTest> entitys=new ArrayList<EntityTest>();
		for(int i=4;i<14;i++){
			EntityTest entity=new EntityTest();
			entity.setFirstName(i+"firstName");
			entity.setLastName(i+"lastName");
			entity.setEmail(i+"@163.com");
			entitys.add(entity);
		}
		entityTestMapper.createBatch(entitys);
		
		EntityTest table5=entityTestMapper.get(entitys.get(0).getId());
		//assertEquals(new Integer(5),table5.getId());
		assertEquals("4@163.com",table5.getEmail());
		assertEquals("4lastName",table5.getLastName());
		assertEquals("4lastName",table5.getLastName());
		assertEquals(new Integer(0),new Integer(table5.getVersion()));
		
		EntityTest table6=entityTestMapper.get(entitys.get(2).getId());
		//assertEquals(new Integer(6),table6.getId());
		assertEquals("6@163.com",table6.getEmail());
		assertEquals("6lastName",table6.getLastName());
		assertEquals("6lastName",table6.getLastName());
		assertEquals(new Integer(0),new Integer(table6.getVersion()));
	}
	@Test
	public void updateBatch() {
		//assertNotNull(null);
		EntityTest entity1=new EntityTest();
		entity1.setId(1);
		entity1.setFirstName("update1");
		entity1.setVersion(1);
		
		EntityTest entity2=new EntityTest();
		entity2.setId(2);
		entity2.setFirstName("update2");
		entity2.setVersion(1);
		int result=entityTestMapper.updateBatch(entity1,entity2);
		
		assertEquals(2,result);
		
		
		EntityTest table1=entityTestMapper.get(1);
		assertEquals(new Integer(1),table1.getId());
		assertEquals("update1",table1.getFirstName());
		assertEquals(null,table1.getLastName());
		assertEquals(new Integer(2),new Integer(table1.getVersion()));
		
		EntityTest table2=entityTestMapper.get(2);
		assertEquals(new Integer(2),table2.getId());
		assertEquals("update2",table2.getFirstName());
		assertEquals(null,table2.getLastName());
		assertEquals(new Integer(2),new Integer(table2.getVersion()));
		
		
		
	}
	@Test
	public void updateBatchCollection() {
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
		int result=entityTestMapper.updateBatch(entitys);
		
		assertEquals(2,result);
		EntityTest table1=entityTestMapper.get(1);
		assertEquals(new Integer(1),table1.getId());
		assertEquals("update1",table1.getFirstName());
		assertEquals(null,table1.getLastName());
		assertEquals(new Integer(2),new Integer(table1.getVersion()));
		
		EntityTest table2=entityTestMapper.get(2);
		assertEquals(new Integer(2),table2.getId());
		assertEquals("update2",table2.getFirstName());
		assertEquals(null,table2.getLastName());
		assertEquals(new Integer(2),new Integer(table2.getVersion()));
	}
	@Test
	public void deleteAll() {
		int result=entityTestMapper.deleteAll();
		assertEquals(3,result);
	}
	@Test
	public void deleteBatchCnd() {
		int result=entityTestMapper.deleteBatch(Cnd.select().andIn("age", 20,30));
		assertEquals(2,result);
		assertEquals(null,entityTestMapper.get(1));
		assertEquals(null,entityTestMapper.get(2));
		assertNotNull(entityTestMapper.get(3));
	}
	@Test
	public void deleteBatchCollection() {
		EntityTest entity1=new EntityTest();
		entity1.setId(1);
		entity1.setVersion(1);
		EntityTest entity2=new EntityTest();
		entity2.setId(2);
		entity2.setVersion(1);
		
		List<EntityTest> entitys=new ArrayList<EntityTest>();
		entitys.add(entity1);
		entitys.add(entity2);
		
		int result=entityTestMapper.deleteBatch(entitys);
		assertEquals(2,result);
		assertEquals(null,entityTestMapper.get(1));
		assertEquals(null,entityTestMapper.get(2));
		assertNotNull(entityTestMapper.get(3));
	}
	@Test
	public void deleteBatch() {
		EntityTest entity1=new EntityTest();
		entity1.setId(1);
		entity1.setVersion(1);
		EntityTest entity2=new EntityTest();
		entity2.setId(2);
		entity2.setVersion(1);
		
		
		int result=entityTestMapper.deleteBatch(entity1,entity2);
		assertEquals(2,result);
		assertEquals(null,entityTestMapper.get(1));
		assertEquals(null,entityTestMapper.get(2));
		assertNotNull(entityTestMapper.get(3));
	}
	@Test
	public void deleteBatchIDS() {
		int result=entityTestMapper.deleteBatch(1,2);
		assertEquals(2,result);
		assertEquals(null,entityTestMapper.get(1));
		assertEquals(null,entityTestMapper.get(2));
		assertNotNull(entityTestMapper.get(3));
	}

	@Test
	public void queryCount() {
		Cnd cnd=Cnd.count().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Long counts=entityTestMapper.queryCount(cnd);
		assertEquals(new Long(2),counts);
	}
	@Test
	public void queryCountProperty() {
		Cnd cnd=Cnd.count("id").andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Long counts=entityTestMapper.queryCount(cnd);
		assertEquals(new Long(2),counts);
	}
	@Test
	public void queryMax() {
		Cnd cnd=Cnd.select().andIn("id", 1,2,3);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		Object entitys=entityTestMapper.queryMax("id",cnd);
		assertEquals((Integer)3,entitys);
	}
	@Test
	public void queryMin() {
		Cnd cnd=Cnd.select().andIn("id", 1,2,3);
		
		Object entitys=entityTestMapper.queryMin("id",cnd);
		assertEquals((Integer)1,entitys);
	}
	@Test
	public void queryAll() {
		List<EntityTest> list=entityTestMapper.queryAll();
		assertEquals(3,list.size());
	}
	@Test
	public void queryCnd() {
		Cnd cnd=Cnd.select().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		List<EntityTest> entitys=entityTestMapper.query(cnd);
		assertEquals(2,entitys.size());
	}
	
	@Test
	public void queryCnd_M() {
		Cnd cnd=Cnd.select().andGT("age", 20).andLike("firstName", "admin").andIn("lastName", "123","1234").addSelect("age","firstName").asc("age");
		
		List<EntityTest> entitys=entityTestMapper.query(cnd,EntityTest.class);
		
		assertEquals(2,entitys.size());
		assertEquals(true,entitys.get(0) instanceof EntityTest);
		assertEquals((Integer)30,entitys.get(0).getAge());
		assertEquals((Integer)40,entitys.get(1).getAge());
	}
	
	@Test
	public void queryUniqueCnd() {
		Cnd cnd=Cnd.where().and("id","=", 1);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		EntityTest entitys=entityTestMapper.queryUnique(cnd);
		assertEquals((Integer)1,entitys.getId());
		assertEquals("admin1",entitys.getFirstName());
		assertEquals("123",entitys.getLastName());
		assertEquals(new Integer(1),new Integer(entitys.getVersion()));
	}
	@Test
	public void queryUniqueCnd_M() {
		Cnd cnd=Cnd.where().and("id","=", 1);//.andLike("firstName", "admin").andIn("lastName", "123","1234");
		
		EntityTest entitys=entityTestMapper.queryUnique(cnd,EntityTest.class);
		assertEquals((Integer)1,entitys.getId());
		assertEquals("admin1",entitys.getFirstName());
		assertEquals("123",entitys.getLastName());
		assertEquals(new Integer(1),new Integer(entitys.getVersion()));
	}
	@Test
	public void queryPageHbernate() {
		WhereInfo whereinfo=new WhereInfo("age",">","20");//WhereInfo.parse("age_gt", "20");
		WhereInfo whereinfo1=new WhereInfo("firstName","like","admin");//WhereInfo.parse("firstName_like", "admin");
		WhereInfo whereinfo2=new WhereInfo("lastName","in",new String[]{"123","1234"});//WhereInfo.parse("lastName_in", "123","1234");
		PageRequest page=new PageRequest();
		page.setWheres(whereinfo,whereinfo1,whereinfo2);
		page.setStratAndLimit(1, 10);
		
		QueryResult<EntityTest> entitys=entityTestMapper.queryPage(page);
		assertEquals(2,entitys.getTotalItems());
		assertEquals(1,entitys.getTotalPages());
	}
	
	@Test
	public void queryPageMybatis() {
		Page page=Page.getInstance().setStart(1).setPageSize(1);
		page=entityTestMapper.queryPage(page);
		assertEquals(1,page.getResultSize());
		assertEquals(3,page.getTotal());
		assertEquals(3,page.getTotalPages());
		assertEquals(1,page.getStart());
		assertEquals(1,page.getPageSize());
		assertEquals(2,page.getPageNo());
		assertEquals(1,page.getPrePageNo());
		assertEquals(3,page.getNextPageNo());
		
		Page page2=Page.getInstance().setStart(2).setPageSize(1);
		page2=entityTestMapper.queryPage(page2);
		assertEquals(1,page2.getResultSize());
		assertEquals(3,page2.getTotal());
		assertEquals(3,page2.getTotalPages());
		assertEquals(2,page2.getStart());
		assertEquals(1,page2.getPageSize());
		assertEquals(3,page2.getPageNo());
		assertEquals(2,page2.getPrePageNo());
		assertEquals(3,page2.getNextPageNo());
		
		Page page3=Page.getInstance().setStart(3).setPageSize(1);
		page3=entityTestMapper.queryPage(page3);
		assertEquals(0,page3.getResultSize());
		assertEquals(3,page3.getTotal());
		assertEquals(3,page3.getTotalPages());
		assertEquals(3,page3.getStart());
		assertEquals(1,page3.getPageSize());
		assertEquals(4,page3.getPageNo());
		assertEquals(3,page3.getPrePageNo());
		assertEquals(4,page3.getNextPageNo());
		
		
		
		Page page4=Page.getInstance().setStart(1).setPageSize(1).addParam("firstName", "%admin%");
		page4=entityTestMapper.queryPage(page4);
		assertEquals(1,page4.getResultSize());
		assertEquals(3,page4.getTotal());
		assertEquals(3,page4.getTotalPages());
		assertEquals(1,page4.getStart());
		assertEquals(1,page4.getPageSize());
		assertEquals(2,page4.getPageNo());
		assertEquals(1,page4.getPrePageNo());
		assertEquals(3,page4.getNextPageNo());
		
	}
	
	@Test
	public void queryPage1Mybatis() {//DefaultSqlSession  SimpleExecutor PreparedStatementHandler BaseStatementHandler
		Page page4=Page.getInstance().setStart(1).setPageSize(1).addParam("firstName", "%admin%");
		page4=entityTestMapper.queryPage1(page4);
		assertEquals(1,page4.getResultSize());
		assertEquals(3,page4.getTotal());
		assertEquals(3,page4.getTotalPages());
		assertEquals(1,page4.getStart());
		assertEquals(1,page4.getPageSize());
		assertEquals(2,page4.getPageNo());
		assertEquals(1,page4.getPrePageNo());
		assertEquals(3,page4.getNextPageNo());
	}
}
