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
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

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
	
	@Autowired
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
//	/**
//	 * 
//	 * @throws IOException
//	 * @throws SQLException 
//	 * @throws DataSetException 
//	 */
//	@Test
//	public void create() throws IOException, DataSetException, SQLException {
//		assertNotNull(entityTestMapper);
//		
//		EntityTest entity=new EntityTest();
//		entity.setFirstName("ma");
//		entity.setLastName("wujun");
//		entity.setEmail("160649888@163.com");
//		Integer id=entityTestMapper.create(entity);
//		
//		assertNotNull(entity.getId());
//		assertEquals(id, entity.getId());
//		//assertEquals(new Integer(4), entity.getId());
//	}
//	
//	@Test
//	public void update() throws IOException, DataSetException, SQLException {
//		EntityTest entity=new EntityTest();
//		entity.setFirstName("ma");
//		entity.setLastName("wujun");
//		entity.setEmail("160649888@163.com");
//		entity.setId(1);
//		entity.setVersion(1);
//		entityTestMapper.update(entity);
//		
//		EntityTest table=entityTestMapper.get(1);
//		assertEquals(new Integer(1),table.getId());
//		assertEquals("ma",table.getFirstName());
//		assertEquals("wujun",table.getLastName());
//		assertEquals(new Integer(2),new Integer(table.getVersion()));
//	}
//	
//	@Test
//	public void deleteT() throws IOException, DataSetException, SQLException {
//		EntityTest entity=new EntityTest();
//		entity.setVersion(1);//这里必须加上version，否则将会报错
//		entity.setId(1);
//		entityTestMapper.delete(entity);
//		
//		EntityTest table=entityTestMapper.get(1);
//		assertEquals(null, table);
//	}
//	
//	@Test
//	public void deleteSerializable() throws DataSetException, SQLException {
//		entityTestMapper.deleteById(1);
//		
//		EntityTest table=entityTestMapper.get(1);
//		assertEquals(null, table);
//	}
//	@Test
//	public void updateIgnoreNull() {
//		//assertNotNull(null);
//		EntityTest entity=new EntityTest();
//		entity.setFirstName("111");
//		//entity.setLastName("wujun");
//		//entity.setEmail("160649888@163.com");
//		entity.setId(1);
//		entity.setVersion(1);
//		entityTestMapper.updateIgnoreNull(entity);
//		
//		EntityTest table=entityTestMapper.get(1);
//		assertEquals(new Integer(1),table.getId());
//		assertEquals("111",table.getFirstName());
//		assertEquals("123",table.getLastName());
//		assertEquals(new Integer(2),new Integer(table.getVersion()));
//	}
//	@Test
//	public void updateCnd() {
//		entityTestMapper.update(Cnd.update().set("email", "1111@11.com").andEquals("id", 1));
//
//		EntityTest table=entityTestMapper.get(1);
//		assertEquals(new Integer(1),table.getId());
//		assertEquals("1111@11.com",table.getEmail());
//		assertEquals("123",table.getLastName());
//		assertEquals(new Integer(2),new Integer(table.getVersion()));
//	}
//	@Test
//	public void createBatch() throws SQLException {
//		assertEquals(3,dbConn.getRowCount(EntityTest_TableName));
//		//assertNotNull(null);
//		EntityTest[] entitys=new EntityTest[10];
//		for(int i=4;i<entitys.length+4;i++){
//			EntityTest entity=new EntityTest();
//			entity.setFirstName(i+"firstName");
//			entity.setLastName(i+"lastName");
//			entity.setEmail(i+"@163.com");
//			entitys[i-4]=entity;
//		}
//		entityTestMapper.createBatch(entitys);
//		//assertEquals(13,dbConn.getRowCount(EntityTest_TableName));
//		EntityTest table5=entityTestMapper.get(entitys[0].getId());
//		//assertEquals(new Integer(5),table5.getId());
//		assertEquals("4@163.com",table5.getEmail());
//		assertEquals("4lastName",table5.getLastName());
//		assertEquals("4lastName",table5.getLastName());
//		assertEquals(new Integer(0),new Integer(table5.getVersion()));
//		
//		EntityTest table6=entityTestMapper.get(entitys[2].getId());
//		//assertEquals(new Integer(6),table6.getId());
//		assertEquals("6@163.com",table6.getEmail());
//		assertEquals("6lastName",table6.getLastName());
//		assertEquals("6lastName",table6.getLastName());
//		assertEquals(new Integer(0),new Integer(table6.getVersion()));
//	}
//	@Test
//	public void createBatchCollection() {
//		//assertNotNull(null);
//		List<EntityTest> entitys=new ArrayList<EntityTest>();
//		for(int i=4;i<14;i++){
//			EntityTest entity=new EntityTest();
//			entity.setFirstName(i+"firstName");
//			entity.setLastName(i+"lastName");
//			entity.setEmail(i+"@163.com");
//			entitys.add(entity);
//		}
//		entityTestMapper.createBatch(entitys);
//		
//		EntityTest table5=entityTestMapper.get(entitys.get(0).getId());
//		//assertEquals(new Integer(5),table5.getId());
//		assertEquals("4@163.com",table5.getEmail());
//		assertEquals("4lastName",table5.getLastName());
//		assertEquals("4lastName",table5.getLastName());
//		assertEquals(new Integer(0),new Integer(table5.getVersion()));
//		
//		EntityTest table6=entityTestMapper.get(entitys.get(2).getId());
//		//assertEquals(new Integer(6),table6.getId());
//		assertEquals("6@163.com",table6.getEmail());
//		assertEquals("6lastName",table6.getLastName());
//		assertEquals("6lastName",table6.getLastName());
//		assertEquals(new Integer(0),new Integer(table6.getVersion()));
//	}
//	@Test
//	public void updateBatch() {
//		//assertNotNull(null);
//		EntityTest entity1=new EntityTest();
//		entity1.setId(1);
//		entity1.setFirstName("update1");
//		entity1.setVersion(1);
//		
//		EntityTest entity2=new EntityTest();
//		entity2.setId(2);
//		entity2.setFirstName("update2");
//		entity2.setVersion(1);
//		entityTestMapper.updateBatch(entity1, entity2);
//		
//		
//		EntityTest table1=entityTestMapper.get(1);
//		assertEquals(new Integer(1),table1.getId());
//		assertEquals("update1",table1.getFirstName());
//		assertEquals(null,table1.getLastName());
//		assertEquals(new Integer(2),new Integer(table1.getVersion()));
//		
//		EntityTest table2=entityTestMapper.get(2);
//		assertEquals(new Integer(2),table2.getId());
//		assertEquals("update2",table2.getFirstName());
//		assertEquals(null,table2.getLastName());
//		assertEquals(new Integer(2),new Integer(table2.getVersion()));
//		
//		
//		
//	}
//	@Test
//	public void updateBatchCollection() {
//		EntityTest entity1=new EntityTest();
//		entity1.setId(1);
//		entity1.setFirstName("update1");
//		entity1.setVersion(1);
//		EntityTest entity2=new EntityTest();
//		entity2.setId(2);
//		entity2.setFirstName("update2");
//		entity2.setVersion(1);
//		List<EntityTest> entitys=new ArrayList<EntityTest>();
//		entitys.add(entity1);
//		entitys.add(entity2);
//		entityTestMapper.updateBatch(entitys);
//		
//		EntityTest table1=entityTestMapper.get(1);
//		assertEquals(new Integer(1),table1.getId());
//		assertEquals("update1",table1.getFirstName());
//		assertEquals(null,table1.getLastName());
//		assertEquals(new Integer(2),new Integer(table1.getVersion()));
//		
//		EntityTest table2=entityTestMapper.get(2);
//		assertEquals(new Integer(2),table2.getId());
//		assertEquals("update2",table2.getFirstName());
//		assertEquals(null,table2.getLastName());
//		assertEquals(new Integer(2),new Integer(table2.getVersion()));
//	}
	@Test
	public void deleteAll() {
		int result=entityTestMapper.deleteAll();
		assertEquals(3,result);
	}
	@Test
	public void deleteBatch() {
		int result=entityTestMapper.deleteBatch(Cnd.select().andIn("age", 20,30));
		assertEquals(2,result);
		assertEquals(null,entityTestMapper.get(1));
		assertEquals(null,entityTestMapper.get(2));
		assertNotNull(entityTestMapper.get(3));
	}
//	@Test
//	public int deleteBatch(final Collection<T> entities) {
//		
//	}
//	@Test
//	public int deleteBatch(final T... entities) {
//		
//	}
//	@Test
//	public int deleteBatch(final ID... IDS) {
//		
//	}
//	@Test
//	public int deleteBatch(final Serializable... IDS) {
//		
//	}
//	
//	public int queryCount(Cnd cnd)
//	public Object queryMax(String property,Cnd cnd)
//	public Object queryMin(String property,Cnd cnd)
//	
//	public List<T> query(Cnd cnd,boolean uniqueResult);
//	
//
//	public T queryUnique(Cnd cnd);
//	public <M> M queryUnique(Cnd cnd,Class<M> classM);
//	public QueryResult<Object> queryPage(final PageRequest pageRequest);
}
