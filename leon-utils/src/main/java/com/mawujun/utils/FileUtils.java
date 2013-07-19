package com.mawujun.utils;


public class FileUtils extends org.apache.commons.io.FileUtils {
	 public static final String FILE_SEPARATOR =  System.getProperty("file.separator");

	/**
	 * 获取class所在的位置
	 * @return
	 */
	public static String getCurrentClassPath(Object obj){
		return obj.getClass().getResource("").getPath() ;
	}

}
