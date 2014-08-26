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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

/**
 * 监听某个目录下面的文件是否有变动，如果有变动就就把它拷贝过来
 * 无论目标文件有没有改变，直接从源文件哪里拷贝过来并覆盖。
 *
 *以后还要加上动态监听的功能，一直监听着，一有变化就拷贝
 * 
 */
@Mojo( name = "copy", defaultPhase = LifecyclePhase.PROCESS_CLASSES )
//@Mojo( name = "copy")
public class CopyMojo
    extends AbstractMojo
{
	private static Logger logger = LogManager.getLogger(CopyMojo.class.getName());
	/**
	 * 原始文件所在的目录
	 */
	@Parameter(required = true)
	private File sourceDirectory;
    /**
     * 目标目录
     */
    //@Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
	@Parameter(required = true)
    private File destinationDirectory;
	/**
	 * true表示自动监听，而不是一次性的
	 */
	@Parameter
	private Boolean autoCopy=false;
	/**
	 * 例如 a.html,b.html
	 * 通过逗号进行分割
	 */
	@Parameter
	private String includes;
	/**
	 * 例如 a.html,b.html
	 * 通过逗号进行分割
	 */
	@Parameter
	private String excludes;
	
    public void execute() throws MojoExecutionException
    {
    	
        File f = destinationDirectory;

        if ( !f.exists() )
        {
            f.mkdirs();
        }
        
        try {
        	//会拷贝
        	//FileUtils.copyDirectoryStructure(sourceDirectory, destinationDirectory);
        	//
        	FileUtils.copyDirectoryStructureIfModified(sourceDirectory, destinationDirectory);
        	watchFileChange();
        	//把所有文件拷贝到一个目录下面
			//FileUtils.copyDirectory(sourceDirectory, destinationDirectory, includes, excludes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    WatchKey key;
    <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
    /**
     * 实时监控文件的变化
     * @author mawujun email:160649888@163.com qq:16064988
     */
    public void watchFileChange(){
    	logger.info("开始监控指定目录的文件变动");
    	Executors.newCachedThreadPool().submit(new Runnable() {  
            public void run() {  
                try {  
                    WatchService watchService = FileSystems.getDefault().newWatchService();  
                    Path dirpath = Paths.get(sourceDirectory.getPath());  
                    // 注册监听器  
                    key=dirpath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);  
                    while (true) {  
                        // 阻塞方式，消费文件更改事件  
                        List<WatchEvent<?>> watchEvents = watchService.take().pollEvents();  
                        //System.out.println(watchEvents.size());
                        
                        //String tempFile=null;
                        for (WatchEvent<?> watchEvent : watchEvents) {  
                        	Path path = (Path)watchEvent.context();
                        	if(path!=null){	 
                                //logger.info("[%s]文件发生了[%s]事件。%n", path.toAbsolutePath().getFileName(), watchEvent.kind());
                                //logger.info(watchEvent.count());
                                //logger.info(path.toFile().getPath());
                        		System.out.printf("[%s]文件发生了[%s]事件。%n", path, watchEvent.kind()); 
                        		//System.out.println(watchEvent.count());
                                //System.out.println(path.toFile().getAbsolutePath());
                                String absolutePath= path.toString();//path.toFile().getAbsolutePath();
                                if(!absolutePath.endsWith(".TMP") && !absolutePath.endsWith(".tmp")){
                                	 System.out.println("执行的次数....."+path);
                                	 //现在如果文件变大后，可能效率不是很高，以后有机会，具体判断，具体修改，效果会好点
                                	 try {
                                     	FileUtils.copyDirectoryStructureIfModified(sourceDirectory, destinationDirectory);
                                     } catch(IOException e) {
                                     	logger.error(e);
                                     }
                                }
                        	}
                           
                        }  
                        key.reset();
                    }  
                } catch(Exception e) {
                	e.printStackTrace();
                }  
            }  
        });  
    }
}
