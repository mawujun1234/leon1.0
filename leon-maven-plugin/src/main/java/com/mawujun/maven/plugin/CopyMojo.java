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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

/**
 * 监听某个目录下面的文件是否有变动，如果有变动就就把它拷贝过来
 * 主要用于webapp下的文件变化的拷贝
 *
 *以后还要加上动态监听的功能，一直监听着，一有变化就拷贝
 * 
 */
@Mojo( name = "copy", defaultPhase = LifecyclePhase.PROCESS_CLASSES )
//@Mojo( name = "copy")
public class CopyMojo
    extends AbstractMojo
{
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
        	
        	//把所有文件拷贝到一个目录下面
			//FileUtils.copyDirectory(sourceDirectory, destinationDirectory, includes, excludes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        

    }
}
