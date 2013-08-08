package com.mawujun.utils;

import java.io.File;


public class FileUtils extends org.apache.commons.io.FileUtils {
	 public static final String FILE_SEPARATOR =  System.getProperty("file.separator");

	/**
	 * 获取class所在的位置
	 * @return
	 */
	public static String getCurrentClassPath(Object obj){
		return obj.getClass().getResource("").getPath() ;
	}
	/**
	 * 返回某个目录下面的所有文件和目录，不包括子文件夹中的文件
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param directory
	 * @return
	 */
	public static File[] listFiles(File directory){
		return directory.listFiles();
	}

}
