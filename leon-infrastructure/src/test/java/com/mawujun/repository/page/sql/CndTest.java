package com.mawujun.repository.page.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mawujun.mybatis.MybatisUtil;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.EntityTest;
import com.mawujun.test.DbunitBaseRepositoryTest;
import com.mawujun.utils.FileUtils;
import com.mawujun.utils.hibernate.HibernateUtil;

public class CndTest  extends DbunitBaseRepositoryTest {
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
	public void testWhere() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		//fail("Not yet implemented");
		Cnd cnd0=Cnd.where();
		assertEquals(true, cnd0.getWhere().isTop());
		assertEquals(true,cnd0.getWhere().isEmpty());
		
		Cnd cnd=Cnd.where().and("age", ">", 1);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age>1",cnd.toHql(classMetadata));
		//assertEquals(" WHERE age>1",cnd.toSql(classMetadata));
		
		cnd.and("firstName", "like", "%aa%");
		assertEquals("from com.mawujun.repository.EntityTest WHERE age>1 AND firstName LIKE '%aa%'",cnd.toHql(classMetadata));
		//assertEquals(" WHERE age>1 AND firstName LIKE '%aa%'",cnd.toSql(classMetadata));
		
		
		Cnd cnd1=Cnd.where().and(Cnd.exp("age", ">", 1)).and(Cnd.exps("firstName", "like", "%aa%").or("lastName", "=", "11111"));
		assertEquals("from com.mawujun.repository.EntityTest WHERE age>1 AND (firstName LIKE '%aa%' OR lastName='11111')",cnd1.toHql(classMetadata));
		//assertEquals(" WHERE age>1 AND (firstName LIKE '%aa%' OR lastName='11111')",cnd1.toSql(classMetadata));

		Cnd cnd2=Cnd.where().and("address.address", "=", "胜利街");
		assertEquals("from com.mawujun.repository.EntityTest WHERE address.address='胜利街'",cnd2.toHql(classMetadata));
		//assertEquals(" WHERE address='胜利街'",cnd2.toSql(classMetadata));
		
		Cnd cnd3=Cnd.where().and("parent.id", "=", 2);
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.id=2",cnd3.toHql(classMetadata));
		//assertEquals(" WHERE parent_id=2",cnd3.toSql(classMetadata));
		
		Cnd cnd4=Cnd.where().and("parent.name", "like", "%aa%");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name LIKE '%aa%'",cnd4.toHql(classMetadata));
		//assertEquals(" WHERE parent_id=2",cnd3.toSql(classMetadata));
		
		Cnd cnd5=Cnd.where().and("parent.name", "is", null);
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NULL ",cnd5.toHql(classMetadata));
		
		Cnd cnd6=Cnd.where().and("parent.name", "is NULL", null);
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NULL ",cnd6.toHql(classMetadata));
		
		Cnd cnd7=Cnd.where().and("parent.name", "is NOT NULL", null);
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NOT NULL ",cnd7.toHql(classMetadata));
		
		Cnd cnd8=Cnd.where().and("parent.name", "is NOT NULL", "水电费水电费、");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NOT NULL ",cnd8.toHql(classMetadata));
		
		Cnd cnd9=Cnd.where().and("parent.name", "is NULL", "水电费水电费、");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NULL ",cnd9.toHql(classMetadata));
		
		
		
		//还要测试组件，关联，多个属性返回的时候
		//最后测试加上 from 字子句和select 子句
		
	}

	
//	@Test
//	public void testExp() {
//		fail("Not yet implemented");
//	}


}
