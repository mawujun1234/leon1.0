package com.mawujun.utils;


public class FileUtils extends org.apache.commons.io.FileUtils {

	/**
	 * 获取class所在的位置
	 * @return
	 */
	public static String getCurrentClassPath(Object obj){
		return obj.getClass().getResource("").getPath() ;
	}

}
