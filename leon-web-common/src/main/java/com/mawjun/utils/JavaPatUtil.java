package com.mawjun.utils;

import com.mawujun.parameter.PGeneratorService;
import com.mawujun.utils.SystemUtils;

/**
 * 用来开发的时候获取java开发目录的
 * @author mawujun 16064988@qq.com  
 *
 */
public class JavaPatUtil {
	
	public String getSrcPath(){
		String path=System.getProperty("user.dir")+SystemUtils.FILE_SEPARATOR+"src"+SystemUtils.FILE_SEPARATOR+"main"+SystemUtils.FILE_SEPARATOR+"java";
		//+SystemUtils.FILE_SEPARATOR+"com"+SystemUtils.FILE_SEPARATOR+"mawujun"+SystemUtils.FILE_SEPARATOR+"parameter"+SystemUtils.FILE_SEPARATOR+"P.java";
		return path;
	}
}
