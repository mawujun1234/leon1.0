package com.mawujun.generator;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mawujun.baseinfo.EquipmentCycle;
import com.mawujun.mobile.task.LockEquipment;
import com.mawujun.mobile.task.TaskEquipmentList;

import freemarker.template.TemplateException;
/**
 * 生成代码的主类，以ExtenConfig_开头的类，是用来控制代码生成的，因为可能存在在不同的情况下，生成的代码会不一样，有个性化的需求，但大部分一样。
 * 如果大部分都不样的话，就自己重写ftl文件
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class GeneratorMain {
	static GeneratorService generatorService;

	public static void main(String[] args) throws TemplateException, IOException, ClassNotFoundException {	
		init();

			
		
		//generatorService.generatorFile(OrgType.class,FtlFile.JsGridQuery.toString(),"D:",new ExtenConfig());	
		
		//String str="";
		//str=generatorService.generatorToString(MenuItem.class,FtlFile.JsModel.toString(),null);	
        //System.out.println(str);
		
		GeneratorMain.generateAllFile(EquipmentCycle.class,"D:/gen/");
		
		
		
	}
	

	public static void generateAllFile(Class<?> clazz,String dirPath) throws ClassNotFoundException, TemplateException, IOException {
		generateAllFile( clazz, dirPath,new ExtenConfig());
	}
	/**
	 * 生成所有FtlFile配置了的文件,
	 * 不建议生成放在正式开发的地方，因为会覆盖源文件，万一覆盖了修改过的，就悲剧了
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @throws IOException 
	 * @throws TemplateException 
	 * @throws ClassNotFoundException 
	 */
	public static void generateAllFile(Class<?> clazz,String dirPath,ExtenConfig cofig) throws ClassNotFoundException, TemplateException, IOException {

		FtlFile[] allFtlFile = FtlFile.values();
		for (FtlFile ftlFile : allFtlFile) {	
			generatorService.generatorFile(clazz,ftlFile.toString(),dirPath,cofig);	
		}
		//打开文件夹
		Runtime.getRuntime().exec("cmd.exe /c start "+dirPath);
	}
	
//	public static void generateAllFile(Class<?> clazz,String dirPath,ExtenConfig cofig) throws ClassNotFoundException, TemplateException, IOException {
//	if(cofig==null){
//		cofig=new ExtenConfig();
//	}
//	String fileName=generatorService.generatorFileName(clazz,FtlFile.Repository.toString());		
//	generatorService.generatorFile(Menu.class,FtlFile.Repository.toString(),dirPath+fileName,cofig);	
//	
//	fileName=generatorService.generatorFileName(clazz,FtlFile.Repository.toString());		
//	generatorService.generatorFile(Menu.class,FtlFile.Repository.toString(),dirPath+fileName,cofig);	
//	
//	
//	Runtime.getRuntime().exec("cmd.exe /c start "+dirPath);
//}
	
//	private static void fileDemo() throws ClassNotFoundException, TemplateException, IOException{	
//		generatorService.generatorFile(Menu.class,FtlFile.Repository.toString(),"D:/",null);	
//	}
//	
//	private static void strDemo() throws TemplateException, IOException{
//		String str=generatorService.generatorToString(Menu.class,FtlFile.Repository.toString(),null);	
//		System.out.println(str);
//	}
	
	public enum FtlFile {
		Repository("${simpleClassName}Repository.java.ftl"),
		Service("${simpleClassName}Service.java.ftl"),
		Controller("${simpleClassName}Controller.java.ftl"),
		MybatisXml("${simpleClassName}Repository.xml.ftl"),
		//js的领域模型
		JsModel("${simpleClassName}.js.ftl"),
		JsApp("${simpleClassName}App.js.ftl"),
		JspApp("${simpleClassName}App.jsp.ftl"),
		JsTree("${simpleClassName}Tree.js.ftl"),
		JsTreeQuery("${simpleClassName}TreeQuery.js.ftl"),
		JsForm("${simpleClassName}Form.js.ftl"),
		JsGrid("${simpleClassName}Grid.js.ftl"),
		JsGridQuery("${simpleClassName}GridQuery.js.ftl");
		
		private String fileName;
		FtlFile(String fileName){
			this.fileName=fileName;
		}
		
		public String toString(){
			return this.fileName;
		}
		
	}

	public static void init(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/mawujun/generator/generatorContext.xml");  
		generatorService=context.getBean(GeneratorService.class);
		
		//context.close();  
	}

}
