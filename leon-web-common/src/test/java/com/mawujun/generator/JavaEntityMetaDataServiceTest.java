package com.mawujun.generator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.fun.Fun;
import com.mawujun.menu.Menu;

import freemarker.template.TemplateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"}) 
@TransactionConfiguration(defaultRollback=false,transactionManager="transactionManager")
@Transactional
//@ActiveProfiles("dev")
public class JavaEntityMetaDataServiceTest {
	@Autowired
	private JavaEntityMetaDataService javaEntityMetaDataService;
	{
		System.setProperty("spring.profiles.active", "test");
	}
	
	@Test
	public void getDbTableName() {
		String tableName=javaEntityMetaDataService.getDbTableName(Menu.class);
		System.out.println(tableName+"=================");
		assertEquals("leon_menu", tableName);
		
		List<String>  cols=javaEntityMetaDataService.getDbCellName(Menu.class);
		for(String col:cols){
			System.out.println(col+"======");
		}
	}
	
	@Test
	public void generatorToString() throws TemplateException, IOException {
		String aa=javaEntityMetaDataService.generatorToString(Fun.class, "${simpleClassName}.ftl");
		System.out.println(aa);
	}
}
