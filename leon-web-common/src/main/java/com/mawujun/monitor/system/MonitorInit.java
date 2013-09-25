package com.mawujun.monitor.system;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mawujun.utils.FileUtils;
import com.mawujun.utils.JavaLibraryPathUtils;
import com.mawujun.utils.SystemUtils;

public class MonitorInit {
	static final Logger logger=LoggerFactory.getLogger(MonitorInit.class);

	/**
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param args
	 * @throws IOException 
	 */
	public void initialize() throws IOException {
		logger.debug("开始拷贝监控的dll文件");
		// TODO Auto-generated method stub
		File dir=new File(MonitorInit.class.getResource("").getPath()+SystemUtils.FILE_SEPARATOR+"sigar");
		File[] files=FileUtils.listFiles(dir);
		//System.out.println(files.length);
		JavaLibraryPathUtils.copyLibraryFile(true,files);
		logger.debug("结束拷贝监控的dll文件");
	}

}
