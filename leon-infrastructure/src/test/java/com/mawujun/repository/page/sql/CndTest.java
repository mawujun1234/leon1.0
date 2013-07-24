package com.mawujun.repository.page.sql;

import static com.mawujun.repository.page.sql.Exps.gt;
import static com.mawujun.repository.page.sql.Exps.gte;
import static com.mawujun.repository.page.sql.Exps.inInt;
import static com.mawujun.repository.page.sql.Exps.inLong;
import static com.mawujun.repository.page.sql.Exps.inSql;
import static com.mawujun.repository.page.sql.Exps.inStr;
import static com.mawujun.repository.page.sql.Exps.like;
import static com.mawujun.repository.page.sql.Exps.lt;
import static com.mawujun.repository.page.sql.Exps.lte;
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
		
		//加上一个toHql,但是没有绑定变量的方法，并且在BaseRepository中绑定变量
		
	}
	
	@Test
	public void andIsNull() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andIsNull("parent.name");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NULL ",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void andNotIsNull() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotIsNull("parent.name");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NOT NULL ",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void andEquals() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andEquals("parent.name","AA");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name='AA'",cnd0.toHql(classMetadata));
	}
	@Test
	public void andNotEquals() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotEquals("parent.name","AA");
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT parent.name='AA'",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void andGT() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andGT("age",11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age>11",cnd0.toHql(classMetadata));
	}
	@Test
	public void andGTE() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andGTE("age",11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age >= 11",cnd0.toHql(classMetadata));
	}
	@Test
	public void andLT() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andLT("age",11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age<11",cnd0.toHql(classMetadata));
	}
	@Test
	public void andLTE() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andLTE("age",11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age <= 11",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void andIn() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andIn("id",1,2,3);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id IN (1,2,3)",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void andInInt() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andInInt("id",1,2,3);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id IN (1,2,3)",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void andIn_name() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andIn("firstName","1","2","3");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName IN ('1','2','3')",cnd0.toHql(classMetadata));
	}
	@Test
	public void andInByHql() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andInByHql("firstName","select id from dual");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName IN (select id from dual)",cnd0.toHql(classMetadata));
		
		Cnd cnd1=Cnd.where().andInByHql("firstName","select id from dual where aa=?",1);
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName IN (select id from dual where aa=1)",cnd1.toHql(classMetadata));
	}
	@Test
	public void andNotInByHql() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotInByHql("firstName","select id from dual");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName NOT IN (select id from dual)",cnd0.toHql(classMetadata));
		
		Cnd cnd1=Cnd.where().andNotInByHql("firstName","select id from dual where aa=?",1);
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName NOT IN (select id from dual where aa=1)",cnd1.toHql(classMetadata));
	}
	@Test
	public void andNotIn() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotIn("id",1,2,3);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id NOT IN (1,2,3)",cnd0.toHql(classMetadata));
	}
	@Test
	public void andNotInInt() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotInInt("id",1,2,3);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id NOT IN (1,2,3)",cnd0.toHql(classMetadata));
	}
	@Test
	public void andNotIn_name() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotIn("firstName","1","2","3");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName NOT IN ('1','2','3')",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void andLike() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andLike("firstName","E1");
		assertEquals("from com.mawujun.repository.EntityTest WHERE LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));
	}
	@Test
	public void andNotLike() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andNotLike("firstName","E1");
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));
	}
	@Test
	public void andLike_ignoreCase() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andLike("firstName","E1",true);
		assertEquals("from com.mawujun.repository.EntityTest WHERE LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));

		Cnd cnd1=Cnd.where().andLike("firstName","E1",false);
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName LIKE '%E1%'",cnd1.toHql(classMetadata));
		
	}
	@Test
	public void andNotLike_ignoreCase() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andNotLike("firstName","E1",true);
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));

		Cnd cnd1=Cnd.where().andNotLike("firstName","E1",false);
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT firstName LIKE '%E1%'",cnd1.toHql(classMetadata));
		
	}
	
	@Test
	public void orIsNull() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andIsNull("parent.name").orIsNull("parent.name");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NULL  OR parent.name IS NULL ",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void orNotIsNull() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotIsNull("parent.name").orNotIsNull("parent.name");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name IS NOT NULL  OR parent.name IS NOT NULL ",cnd0.toHql(classMetadata));
	}
	
	@Test
	public void orEquals() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andEquals("parent.name","AA").orEquals("parent.name","AA");
		assertEquals("from com.mawujun.repository.EntityTest WHERE parent.name='AA' OR parent.name='AA'",cnd0.toHql(classMetadata));
	}
	@Test
	public void orNotEquals() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotEquals("parent.name","AA").orNotEquals("parent.name","AA");
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT parent.name='AA' OR  NOT parent.name='AA'",cnd0.toHql(classMetadata));
	}
	@Test
	public void orGT() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andGT("age",11).orGT("age", 11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age>11 OR age>11",cnd0.toHql(classMetadata));
	}
	@Test
	public void orGTE() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andGTE("age",11).orGTE("age", 11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age >= 11 OR age >= 11",cnd0.toHql(classMetadata));
	}
	@Test
	public void orLT() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andLT("age",11).orLT("age", 11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age<11 OR age<11",cnd0.toHql(classMetadata));
	}
	@Test
	public void orLTE() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andLTE("age",11).orLTE("age", 11);
		assertEquals("from com.mawujun.repository.EntityTest WHERE age <= 11 OR age <= 11",cnd0.toHql(classMetadata));
	}
	@Test
	public void orIn() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andIn("id",1,2,3).orIn("id",1,2,3);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id IN (1,2,3) OR id IN (1,2,3)",cnd0.toHql(classMetadata));
	}
	@Test
	public void orInInt() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andIn("id",1,2,3).orIn("id",1,2,3);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id IN (1,2,3) OR id IN (1,2,3)",cnd0.toHql(classMetadata));
	}

	@Test
	public void orIn_name() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andIn("firstName","1","2","3").orIn("firstName","1","2","3");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName IN ('1','2','3') OR firstName IN ('1','2','3')",cnd0.toHql(classMetadata));
	}

	@Test
	public void orInByHql() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andInByHql("firstName","select id from dual").orInBySql("firstName","select id from dual");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName IN (select id from dual) OR firstName IN (select id from dual)",cnd0.toHql(classMetadata));
		
		Cnd cnd1=Cnd.where().andInByHql("firstName","select id from dual where aa=?",1).orInBySql("firstName","select id from dual where aa=?",1);
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName IN (select id from dual where aa=1) OR firstName IN (select id from dual where aa=1)",cnd1.toHql(classMetadata));
	}
	@Test
	public void orNotInByHql() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotInByHql("firstName","select id from dual").orNotInBySql("firstName","select id from dual");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName NOT IN (select id from dual) OR firstName NOT IN (select id from dual)",cnd0.toHql(classMetadata));
		
		Cnd cnd1=Cnd.where().andNotInByHql("firstName","select id from dual where aa=?",1).orNotInBySql("firstName","select id from dual where aa=?",1);;
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName NOT IN (select id from dual where aa=1) OR firstName NOT IN (select id from dual where aa=1)",cnd1.toHql(classMetadata));
	}
	@Test
	public void orNotIn() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotIn("id",1L,2L,3L).orNotIn("id",1L,2L,3L);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id NOT IN (1,2,3) OR id NOT IN (1,2,3)",cnd0.toHql(classMetadata));
	}
	@Test
	public void orNotInInt() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotIn("id",1,2,3).orNotIn("id",1,2,3);
		assertEquals("from com.mawujun.repository.EntityTest WHERE id NOT IN (1,2,3) OR id NOT IN (1,2,3)",cnd0.toHql(classMetadata));
	}
	@Test
	public void orNotIn_name() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		
		Cnd cnd0=Cnd.where().andNotIn("firstName","1","2","3").orNotIn("firstName","1","2","3");
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName NOT IN ('1','2','3') OR firstName NOT IN ('1','2','3')",cnd0.toHql(classMetadata));
	}
	@Test
	public void orLike() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andLike("firstName","E1").orLike("firstName","E1");
		assertEquals("from com.mawujun.repository.EntityTest WHERE LOWER(firstName) LIKE LOWER('%E1%') OR LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));
	}
	@Test
	public void orNotLike() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andNotLike("firstName","E1").orNotLike("firstName","E1");
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT LOWER(firstName) LIKE LOWER('%E1%') OR  NOT LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));
	}
	@Test
	public void orLike_ignoreCase() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andLike("firstName","E1",true).orLike("firstName","E1",true);
		assertEquals("from com.mawujun.repository.EntityTest WHERE LOWER(firstName) LIKE LOWER('%E1%') OR LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));

		Cnd cnd1=Cnd.where().andLike("firstName","E1",false).orLike("firstName","E1",false);
		assertEquals("from com.mawujun.repository.EntityTest WHERE firstName LIKE '%E1%' OR firstName LIKE '%E1%'",cnd1.toHql(classMetadata));
		
	}
	@Test
	public void orNotLike_ignoreCase() {
		AbstractEntityPersister classMetadata=(AbstractEntityPersister)sessionFactory.getClassMetadata(EntityTest.class);
		Cnd cnd0=Cnd.where().andNotLike("firstName","E1",true).orNotLike("firstName","E1",true);
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT LOWER(firstName) LIKE LOWER('%E1%') OR  NOT LOWER(firstName) LIKE LOWER('%E1%')",cnd0.toHql(classMetadata));

		Cnd cnd1=Cnd.where().andNotLike("firstName","E1",false).orNotLike("firstName","E1",false);;
		assertEquals("from com.mawujun.repository.EntityTest WHERE  NOT firstName LIKE '%E1%' OR  NOT firstName LIKE '%E1%'",cnd1.toHql(classMetadata));
		
	}

}