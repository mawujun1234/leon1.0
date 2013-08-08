package com.mawujun.generator;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class Test {

	/**
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path=Test.class.getResource("/").getPath();
		File file=new File(path);

		//FileUtils.lineIterator(file);
		System.out.println(file.getPath());
	}

}
