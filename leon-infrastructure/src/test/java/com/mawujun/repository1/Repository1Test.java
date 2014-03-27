package com.mawujun.repository1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Transaction;
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
		assertEquals(new Integer(4), entity.getId());
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
		assertNotNull(null);
	}
	@Test
	public void updateCnd() {
		assertNotNull(null);
	}
	@Test
	public void createBatch() {
		assertNotNull(null);
	}
	@Test
	public void createBatchCollection() {
		assertNotNull(null);
	}
	@Test
	public void updateBatch() {
		assertNotNull(null);
	}
	@Test
	public void updateBatchCollection() {
		assertNotNull(null);
	}
	@Test
	public void deleteAll() {
		
	}
	
	public int deleteBatch(Cnd cnd)
	public int deleteBatch(final Collection<T> entities);
	public int deleteBatch(final T... entities);
	public int deleteBatch(final ID... IDS)
	public int deleteBatch(final Serializable... IDS);
	
	public int queryCount(Cnd cnd)
	public Object queryMax(String property,Cnd cnd)
	public Object queryMin(String property,Cnd cnd)
	
	public List<T> query(Cnd cnd,boolean uniqueResult);
	

	public T queryUnique(Cnd cnd);
	public <M> M queryUnique(Cnd cnd,Class<M> classM);
	public QueryResult<Object> queryPage(final PageRequest pageRequest);
}
