package com.mawujun.repository1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mawujun.test.DbunitBaseRepositoryTest;
import com.mawujun.utils.PropertiesUtils;

/**
 * 测试测试环境
 * @author mawujun
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/com/mawujun/repository1/applicationContext.xml"}) 
public class Repository1Test  extends DbunitBaseRepositoryTest{
	
	@Autowired
	EntityTestMapper entityTestMapper;


	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		assertNotNull(entityTestMapper);
		
		
		
//		PropertiesUtils aa=PropertiesUtils.load("dialet_config.propertites");//new PropertiesUtils();
//		//aa.load("dialet_config.propertites");
//		assertNotNull(dbName);
//		assertNotNull(mybatisDialet);
//		assertNotNull(hibernateDialet);
//		assertEquals(mybatisDialet, aa.get(dbName+".mybatis.dialet"));
//		assertEquals(hibernateDialet, aa.get(dbName+".hibernate.dialet"));
	}
}
