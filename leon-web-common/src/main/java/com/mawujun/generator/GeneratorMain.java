package com.mawujun.generator;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mawujun.menu.Menu;

import freemarker.template.TemplateException;

public class GeneratorMain {

	public static void main(String[] args) throws TemplateException, IOException {	
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/mawujun/generator/generatorContext.xml");  
		JavaEntityMetaDataService javaEntityMetaDataService=context.getBean(JavaEntityMetaDataService.class);

		String tableName=javaEntityMetaDataService.generatorToString(Menu.class,"${simpleClassName}Service.java.ftl",ExtenConfig_Service.getInstance().setCreate(false));	
		System.out.println(tableName);
		tableName=javaEntityMetaDataService.generatorToString(Menu.class,"${simpleClassName}Controller.java.ftl",ExtenConfig_Controller.getInstance());	
		System.out.println(tableName);
		
        context.close();  
	}
	


}
