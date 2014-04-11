package com.mawujun.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * 根据领域模型来生成D类的代码，可以在开发当中直接使用D。User。name这样引用属性,
 * 也可以用T。leon_User.name这样引用列名
 * 扩展功能：
 * 1：在执行这个命令后，就可以执行监听功能，当文件变动后，就自动进行变化
 * 
 * 
 * 
 */
@Mojo( name = "generateM")
public class GenerateMMojo extends AbstractMojo
{
    /**
     * class文件编译的目录
     */
	@Parameter
    private List<String> classpathDirectorys;
	/**
	 * 加载classpathDirectorys目录下的class的时候会依赖其他类，这里就是加依赖类的
	 */
	@Parameter
    private List<String> packageNames;
	@Parameter
    private List<String> excludePackageNames;
	/**
	 * 超找指定了annotationClass的类作为实体类
	 */
	//@Parameter
	//private String annotationClassName;
	private Class annotationClass=javax.persistence.Entity.class;
	private Class annotationTable=javax.persistence.Table.class;
	@Parameter
	private String targetMDir;

    public void execute()
        throws MojoExecutionException
    {
    	
    	getLog().info("=============================================");
    	//System.out.println("=============================================");
    	
    	try {
    		
    		
    		
    		URL[] urls=new URL[classpathDirectorys.size()];
    		int i=0;
    		for(String classpathDirectory:classpathDirectorys){
    			getLog().info("设置classpath:"+classpathDirectory);
    			File xFile=new File(classpathDirectory);  
        		URL  url= xFile.toURI().toURL();  
        		urls[i]=url;
        		i++;
    		}
    		
			URLClassLoader myloader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
			//annotationClass=myloader.loadClass(annotationClassName);
			
			
			
			List<Class> clazzs =new ArrayList<Class>();
			for(String packageName:packageNames){
				 List<Class> clazzss = getClasssFromPackage(myloader,packageName);//  
				  clazzs.addAll(clazzss);
			}
			getLog().info("找到实体类的个数为:"+clazzs.size()); 
			
			
		  
			getLog().info("=============================================开始生成D的代码");
			generateM(clazzs);
			generateT(clazzs);

		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 getLog().info(e.getMessage()); 
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	
    }
    /**
     * 产生表的字段名
     * @author mawujun email:160649888@163.com qq:16064988
     * @param entities
     * @throws IOException
     */
    public void generateT(List<Class> entities) throws IOException{
    	//生成T
    	File file=new File(targetMDir+File.separatorChar+"T.java");
    	//file.delete();
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	FileWriter fileWrite=new FileWriter(file);
    	
    	    	
    	//StringBuilder builder=new StringBuilder();
    	fileWrite.append("package com.mawujun.utils;\n");
    	fileWrite.append("public final class T {\n");
    	
    	
    	for(Class clazz:entities){
    		

    		Table annoation=(Table)clazz.getAnnotation(annotationTable);
    		if(annoation==null){
    			throw new NullPointerException(clazz.getClass()+"的Table注解没有设置");
    		}
    		getLog().info("============================================="+annoation.name());
    		
    		//fileWrite.append("public static final class "+clazz.getSimpleName()+" {\n");
    		fileWrite.append("public static final class "+annoation.name()+" {\n");
    		 Field[]fields = clazz.getDeclaredFields();
             for (Field field : fields) { //完全等同于上面的for循环
                 //System.out.println(field.getName()+" "+field.getType());
            	 getLog().info(field.getName());
            	
            	 Annotation embeddedIdAnnotataion=field.getAnnotation(EmbeddedId.class);
            	 //是复合主键的情况下
            	 if(embeddedIdAnnotataion!=null){
            		 Class<?> fieldClass=field.getType();
            		 fileWrite.append("	 /**\n");
                	 fileWrite.append("	 * 这个是复合主键。里面的是复合组件的组成列的列名\n");
                	 fileWrite.append("	 */\n");
                	 fileWrite.append("	public static final class "+fieldClass.getSimpleName()+" {\n");
                	 Field[] embeddedIdFields = fieldClass.getDeclaredFields();
                	 for (Field embeddedIdfield : embeddedIdFields) { 
                		 Column columnAnnotation=(Column)embeddedIdfield.getAnnotation(Column.class);
                		 if(columnAnnotation==null || (columnAnnotation!=null && columnAnnotation.name().equals(""))){
            				 fileWrite.append("		public static final String "+embeddedIdfield.getName()+"=\""+embeddedIdfield.getName()+"\";\n");
            			 } else {
            				 fileWrite.append("		public static final String "+columnAnnotation.name()+"=\""+columnAnnotation.name()+"\";\n");
            			 }
                	 }
                	 fileWrite.append("			\n");
                	 fileWrite.append("	}\n");
            	 } else if(isBaseType(field.getType()) || field.getType().isEnum()){
            			 
            			 Column columnAnnotation=(Column)field.getAnnotation(Column.class);
            			 if(columnAnnotation==null || (columnAnnotation!=null && columnAnnotation.name().equals(""))){
            				 fileWrite.append("	public static final String "+field.getName()+"=\""+field.getName()+"\";\n");
            			 } else {
            				 fileWrite.append("	public static final String "+columnAnnotation.name()+"=\""+columnAnnotation.name()+"\";\n");
            			 }
                    	
                 } else if(!isOf(field.getType(),Map.class) && !isOf(field.getType(),Collection.class)){ 
                    	 JoinColumn columnAnnotation=(JoinColumn)field.getAnnotation(Column.class);
                    	 if(columnAnnotation==null || (columnAnnotation!=null && columnAnnotation.name().equals(""))){
                    		 fileWrite.append("	/**\n");
                        	 fileWrite.append("	* 访问外键的列名，用于sql的时候，返回的是"+field.getName()+"_id\n");
                        	 fileWrite.append("	*/\n");
                        	 fileWrite.append("	public static final String "+field.getName()+"_id=\""+field.getName()+"_id\";\n");
                    	 } else {
                    		 fileWrite.append("	/**\n");
                        	 fileWrite.append("	* 访问外键的列名，用于sql的时候，返回的是"+columnAnnotation.name()+"_id\n");
                        	 fileWrite.append("	*/\n");
                        	 fileWrite.append("	public static final String "+columnAnnotation.name()+"=\""+columnAnnotation.name()+"\";\n");
                    	 }
                    	 
                 }     
                
             }
             fileWrite.append("}\n");
    	}
    	fileWrite.append("}\n");
    	fileWrite.close();
    }
    /**
     * 产生领域模型的类
     * @author mawujun email:160649888@163.com qq:16064988
     * @param entities
     * @throws IOException
     */
    public void generateM(List<Class> entities) throws IOException{
    	//生成M
    	File file=new File(targetMDir+File.separatorChar+"M.java");
    	//file.delete();
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	FileWriter fileWrite=new FileWriter(file);
    	
    	    	
    	//StringBuilder builder=new StringBuilder();
    	fileWrite.append("package com.mawujun.utils;\n");
    	fileWrite.append("public final class M {\n");
    	
    	
    	for(Class clazz:entities){
    		getLog().info("============================================="+clazz.getName());

    		fileWrite.append("public static final class "+clazz.getSimpleName()+" {\n");
    		 Field[]fields = clazz.getDeclaredFields();
             for (Field field : fields) { //完全等同于上面的for循环
            	 getLog().info(field.getName());
                 //System.out.println(field.getName()+" "+field.getType());
                 //fileWrite.append("public static final "+field.getType().getName()+" "+field.getName()+"=\""+field.getName()+"\";\n");
                 if(isBaseType(field.getType()) || field.getType().isEnum()){
                	 fileWrite.append("	public static final String "+field.getName()+"=\""+field.getName()+"\";\n");
                 } else if(!isOf(field.getType(),Map.class) && !isOf(field.getType(),Collection.class)){
                	 Class<?> fieldClass=field.getType();
                	 Annotation embeddedIdAnnotataion=field.getAnnotation(EmbeddedId.class);
                	 //fieldClass.getAnnotations();
                	 //是复合主键的情况下
                	 if(embeddedIdAnnotataion!=null){
                		 fileWrite.append("	 /**\n");
                    	 fileWrite.append("	 * 返回复合主键的组成:"+field.getName()+"\n");
                    	 fileWrite.append("	 */\n");
                    	 fileWrite.append("	public static final class "+fieldClass.getSimpleName()+" {\n");
                    	 Field[] embeddedIdFields = fieldClass.getDeclaredFields();
                    	 for (Field embeddedIdfield : embeddedIdFields) { 
                    		 fileWrite.append("		public static final String "+embeddedIdfield.getName()+"=\""+field.getName()+"."+embeddedIdfield.getName()+"\";\n");
                    	 }
                    	 fileWrite.append("			\n");
                    	 fileWrite.append("	}\n");
                    	 
                    	 
                    	 fileWrite.append("	/**\n");
                    	 fileWrite.append("	* 这是一个复合主键，返回的是该复合主键的属性名称，在hql中使用:"+field.getName()+"\n");
                    	 fileWrite.append("	*/\n");
                    	 fileWrite.append("	public static final String "+field.getName()+"=\""+field.getName()+"\";\n");
                	 } else {
	                	 fileWrite.append("	/**\n");
	                	 fileWrite.append("	* 访问关联类的id，用于hql的时候，返回的是"+field.getName()+".id\n");
	                	 fileWrite.append("	*/\n");
	                	 fileWrite.append("	public static final String "+field.getName()+"_id=\""+field.getName()+".id\";\n");
	                	 
	                	 
	                	 fileWrite.append("	/**\n");
	                	 fileWrite.append("	* 返回的是关联类的属性名称，返回的是"+field.getName()+"\n");
	                	 fileWrite.append("	*/\n");
	                	 fileWrite.append("	public static final String "+field.getName()+"=\""+field.getName()+"\";\n");
                	 }
                 } else {
                	 //其他关联类，例如集合等
                	 fileWrite.append("	/**\n");
                	 fileWrite.append("	* 这里一般是集合属性，返回的是"+field.getName()+"\n");
                	 fileWrite.append("	*/\n");
                	 fileWrite.append("	public static final String "+field.getName()+"=\""+field.getName()+"\";\n");
                 }
                
             }
             fileWrite.append("}\n");
    	}
    	fileWrite.append("}\n");
    	fileWrite.close();
    }
    public static boolean isBaseType(Class clz){
		//如果是基本类型就返回true
		if(clz == String.class || clz==Date.class || clz==java.sql.Date.class || clz==java.sql.Timestamp.class || clz.isPrimitive() || isWrapClass(clz)){
			return true;
		}
		return false;
	}
    public static boolean isOf(Class<?> orginType,Class<?> type){
    	return type.isAssignableFrom(orginType);
    }
    
    public static boolean isWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}
    /** 
     * 获得包下面的所有的class 
     *  
     * @param pack 
     *            package完整名称 
     * @return List包含所有class的实例 
     */  
    public List<Class> getClasssFromPackage(URLClassLoader myloader,String pack) {  
        List<Class> clazzs = new ArrayList<Class>();  
      
        // 是否循环搜索子包  
        boolean recursive = true;  
      
        // 包名字  
        String packageName = pack;  
        // 包名对应的路径名称  
        String packageDirName = packageName.replace('.','/');  
      
        Enumeration<URL> dirs;  
      
        try {  
            dirs = myloader.getResources(packageDirName);  
            while (dirs.hasMoreElements()) {  
                URL url = dirs.nextElement();  
      
                String protocol = url.getProtocol();  
      
                if ("file".equals(protocol)) {  
                	getLog().info("file类型的扫描");  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    findClassInPackageByFile(myloader,packageName, filePath, recursive, clazzs);  
                } else if ("jar".equals(protocol)) {  
                	getLog().info("jar类型的扫描");  
                }  
            }  
      
        } catch (Exception e) {  
            e.printStackTrace();  
            getLog().info(e.getMessage()); 
        }  
      
        return clazzs;  
    }  
    
    /** 
     * 在package对应的路径下找到所有的class 
     *  
     * @param packageName 
     *            package名称 
     * @param filePath 
     *            package对应的路径 
     * @param recursive 
     *            是否查找子package 
     * @param clazzs 
     *            找到class以后存放的集合 
     */  
    public void findClassInPackageByFile(URLClassLoader myloader,String packageName, String filePath, final boolean recursive, List<Class> clazzs) {  
    	
    	if(excludePackageNames!=null && excludePackageNames.contains(packageName)){
    		return;
    	}
        File dir = new File(filePath);  
        if (!dir.exists() || !dir.isDirectory()) {  
            return;  
        }  
        // 在给定的目录下找到所有的文件，并且进行条件过滤  
        File[] dirFiles = dir.listFiles(new FileFilter() {  
            public boolean accept(File file) {  
                boolean acceptDir = recursive && file.isDirectory();// 接受dir目录  
                boolean acceptClass = file.getName().endsWith("class");// 接受class文件  
                return acceptDir || acceptClass;  
            }  
        });  
      
        for (File file : dirFiles) {  
            if (file.isDirectory()) {  
                findClassInPackageByFile(myloader,packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);  
            } else {  
                String className = file.getName().substring(0, file.getName().length() - 6);  
                try {  
                	Class clazz=myloader.loadClass(packageName + "." + className);
                	Annotation annoation=clazz.getAnnotation(annotationClass);
    				if(annoation!=null){
    					getLog().info("============================找到实体类:"+clazz.getName());
    					clazzs.add(clazz); 
    				}		
                     
                   
                } catch (Exception e) {  
                    //e.printStackTrace();  
                    getLog().error(e);  
                    
                }  catch (NoClassDefFoundError e) {  
                    //e.printStackTrace();  
                    getLog().error(e);  
                    return;
                }  
                getLog().info(packageName + "." + className);  
            }  
        }  
    }  
    
    /**
     * 从jar文件中读取指定目录下面的所有的class文件
     * getClasssFromJarFile("Jar文件的路径", "Jar文件里面的包路径");  
     * @param jarPaht
     *            jar文件存放的位置
     * @param filePaht
     *            指定的文件目录
     * @return 所有的的class的对象
     */
    public List<Class> getClasssFromJarFile(URLClassLoader myloader,String jarPaht, String filePaht) {
    	List<Class> clazzs = new ArrayList<Class>();

    	JarFile jarFile = null;
    	try {
    		jarFile = new JarFile(jarPaht);
    	} catch (IOException e1) {
    		e1.printStackTrace();
    	}

    	List<JarEntry> jarEntryList = new ArrayList<JarEntry>();

    	Enumeration<JarEntry> ee = jarFile.entries();
    	while (ee.hasMoreElements()) {
    		JarEntry entry = (JarEntry) ee.nextElement();
    		// 过滤我们出满足我们需求的东西
    		if (entry.getName().startsWith(filePaht) && entry.getName().endsWith(".class")) {
    			jarEntryList.add(entry);
    		}
    	}
    	for (JarEntry entry : jarEntryList) {
    		String className = entry.getName().replace('/', '.');
    		className = className.substring(0, className.length() - 6);

    		try {
    			clazzs.add(myloader.loadClass(className));
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}
    	}

    	return clazzs;
    }
    
    /**
	 * 通过流将jar中的一个文件的内容输出
	 * 
	 * @param jarPaht
	 *            jar文件存放的位置
	 * @param filePaht
	 *            指定的文件目录
	 */
	public static void getStream(String jarPaht, String filePaht) {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPaht);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Enumeration<JarEntry> ee = jarFile.entries();

		List<JarEntry> jarEntryList = new ArrayList<JarEntry>();
		while (ee.hasMoreElements()) {
			JarEntry entry = (JarEntry) ee.nextElement();
			// 过滤我们出满足我们需求的东西，这里的fileName是指向一个具体的文件的对象的完整包路径，比如com/mypackage/test.txt
			if (entry.getName().startsWith(filePaht)) {
				jarEntryList.add(entry);
			}
		}
		try {
			InputStream in = jarFile.getInputStream(jarEntryList.get(0));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s = "";

			while ((s = br.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
