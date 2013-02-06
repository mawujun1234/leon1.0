package com.mawujun.repository.mybatis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

public abstract class DymincReloadAction implements InitializingBean{
	

	//private List<String> folders;//监听的目录
	

	public void afterPropertiesSet() throws Exception {
		// Assert.notNull(applicationContext, "applicationContext为null");
//		 Assert.notNull(folders, "folders为null");

//	    if(folders!=null&&folders.size()>0&&this.reloadSqlFile){
//
//	    	DymincReloadMaperEvent event=new DymincReloadMaperEvent();
//	    	event.setFolders(this.getFolders());
//	    	event.setDymincReloadAction(this);
//	    	event.start();
//		}	
	}
	
	//public void doAction() throws Exception;
	public abstract void fileRenamed(int wd, String rootPath, String oldName,String newName);
	public abstract void fileModified( String rootPath, String name);
	public abstract void fileDeleted(String rootPath, String name);
	public abstract void fileCreated(String rootPath, String name) ;
	
	public abstract void setApplicationContext(ApplicationContext applicationContext);

//	public List<String> getFolders() {
//		return folders;
//	}
//	public void setFolders(List<String> folders) {
//		this.folders = folders;
//	}

	
	
	
	
}
