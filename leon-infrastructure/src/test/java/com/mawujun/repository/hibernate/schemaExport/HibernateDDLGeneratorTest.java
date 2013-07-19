package com.mawujun.repository.hibernate.schemaExport;

import org.junit.Assert;
import org.junit.Test;

import com.mawujun.repository.DialectEnum;

public class HibernateDDLGeneratorTest {

	@Test
	public void getDDL(){
		String ddl=HibernateDDLGenerator.executeReturn(DialectEnum.H2Dialect, Model.class);
		Assert.assertNotNull(ddl);
	}
	
	@Test
	public void generate(){
		HibernateDDLGenerator.execute(DialectEnum.H2Dialect,null, Model.class);
	}
	
	@Test
	public void generateAllClass() throws Exception{
		HibernateDDLGenerator.execute(DialectEnum.H2Dialect,null, "com.mawujun.repository.hibernate.schemaExport");
	}

}
