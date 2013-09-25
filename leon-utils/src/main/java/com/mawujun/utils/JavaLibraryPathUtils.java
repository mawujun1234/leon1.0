package com.mawujun.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaLibraryPathUtils {
	
	static final Logger logger=LoggerFactory.getLogger(JavaLibraryPathUtils.class);
	/**
	 * 拷贝dll和so文件到java.library.path 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @throws IOException
	 */
	public static void copyLibraryFile(boolean onlyFirst,File... files) throws IOException  {
		if(files==null){
			return;
		}
		String pathSeparator=System.getProperty("path.separator");
		String[] library=StringTokenizerUtils.split(System.getProperty("java.library.path"), pathSeparator);
    	for(String lib:library){
    		for(File file:files){
    			logger.debug("拷贝文件:"+file.toURI().toString());
    			FileUtils.copyFileToDirectory(file, new File(lib));
    		}
    		//只拷贝到第一个路径
    		if(onlyFirst){
    			return;
    		}
    	}
	}

	public static void main(String[] aa) throws IOException{
		File dir=new File(JavaLibraryPathUtils.class.getResource("").getPath()+SystemUtils.FILE_SEPARATOR+"sigar");
		File[] files=FileUtils.listFiles(dir);
		System.out.println(files.length);
		copyLibraryFile(true,files);
	}

}
