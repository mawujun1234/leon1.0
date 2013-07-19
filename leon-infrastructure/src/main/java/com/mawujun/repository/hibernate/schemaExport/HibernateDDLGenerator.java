package com.mawujun.repository.hibernate.schemaExport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.mawujun.repository.DialectEnum;
import com.mawujun.utils.FileUtils;

/**
 * 用来获取sql
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class HibernateDDLGenerator {

	
	public static String executeReturn(DialectEnum dialectEnum, Class<?>... classes) {
		String path=FileUtils.getTempDirectory()+UUID.randomUUID().toString()+".sql";
		execute(DialectEnum.H2Dialect,path, classes);
		
		BufferedReader reader = null;
		StringBuilder builder=new StringBuilder();
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(path));
            String tempString = null;
            //int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " + line + ": " + tempString);
                //line++;
            	builder.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return builder.toString();
	}

	/**
	 * 制定是哪个数据库，存放在哪个文件上，以及哪个类
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param dialectEnum
	 * @param path
	 * @param classes
	 */
	public static void execute(DialectEnum dialectEnum,String path, Class<?>... classes) {

		if(classes==null || classes.length==0){
			throw new RuntimeException("请输入类");
		}

		Configuration configuration = new AnnotationConfiguration();
		configuration.setProperty(Environment.DIALECT, dialectEnum.getClassName());
		
		
		for (Class<?> entityClass : classes) {
			configuration.addAnnotatedClass(entityClass);
		}
		
		boolean consolePrint = true;//是否输出在控制台
		boolean exportInDatabase = false;//是否导入到数据库
		
		 System.out.println("==============================================="+System.getProperty("user.dir"));	
//		 if(path==null){
//			 consolePrint=true;
//		 }
		 
		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.setDelimiter(";");
		schemaExport.setOutputFile(path);	

		//schemaExport.execute(script, export, justDrop, justCreate)后两个参数表示只有drop的ddl，只有create的dl
		schemaExport.create(consolePrint, exportInDatabase);
	}
	/**
	 * 导出包下面的所有类
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param dialectEnum 哪个数据库
	 * @param path 保存的路径
	 * @param packageNames 包名
	 * @throws Exception
	 */
	public static void execute(DialectEnum dialectEnum,String path,String... packageNames) throws Exception {
		if(packageNames==null || packageNames.length==0){
			throw new RuntimeException("请指定报名");
		}
		List<Class<?>> clases=new ArrayList<Class<?>>();
		for(String name:packageNames){
			clases.addAll(getClasses(name));
		}
		//List<Class<?>> clases=getClasses(packageNames[0]);
		execute(dialectEnum,path,clases.toArray(new Class<?>[clases.size()]));

	}
	
	private static List<Class<?>> getClasses(String packageName) throws Exception {
        File directory = null;
        try {
            ClassLoader cld = getClassLoader();
            URL resource = getResource(packageName, cld);
            directory = new File(resource.getFile());
        } catch (NullPointerException ex) {
            throw new ClassNotFoundException(packageName + " (" + directory
                    + ") does not appear to be a valid package");
        }
        return collectClasses(packageName, directory);
    }

    private static ClassLoader getClassLoader() throws ClassNotFoundException {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new ClassNotFoundException("Can't get class loader.");
        }
        return cld;
    }
    
    private static URL getResource(String packageName, ClassLoader cld) throws ClassNotFoundException {
        String path = packageName.replace('.', '/');
        URL resource = cld.getResource(path);
        if (resource == null) {
            throw new ClassNotFoundException("No resource for " + path);
        }
        return resource;
    }

    /**
     * 获取到的不单单是实体类，所有clss都会获取到
     * @author mawujun email:16064988@163.com qq:16064988
     * @param packageName
     * @param directory
     * @return
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> collectClasses(String packageName, File directory) throws ClassNotFoundException {
    	List<Class<?>> classes = new ArrayList<Class<?>>();
        if (directory.exists()) {
            String[] files = directory.list();
            for (String file : files) {
                if (file.endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(packageName + '.'+ file.substring(0, file.length() - 6)));
                }
            }
        } else {
            throw new ClassNotFoundException(packageName
                    + " is not a valid package");
        }
        return classes;
    }

}
