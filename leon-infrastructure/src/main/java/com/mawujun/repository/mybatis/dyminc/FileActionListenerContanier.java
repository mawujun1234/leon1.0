package com.mawujun.repository.mybatis.dyminc;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 文件监听不单单是监听mybatis的
 * 
 * 一个容器监听多个文件夹，一个监听文件变化的监听器，然后很多动作，党监听到变化后的动作
 * @author mawujun
 *
 */
public class FileActionListenerContanier implements  ApplicationListener<ContextClosedEvent>,ApplicationContextAware {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private Boolean enable=true;//是否开启文件监听
	private List<String> folders;

	private List<DymincReloadAction> dymincReloadActions;
	
	//@Resource
	//监听器
	private DymincReloadMaperEvent event=null;

//	public DymincReloadMaperEvent getEvent() {
//		return event;
//	}
//	public void setEvent(DymincReloadMaperEvent event) {
//		this.event = event;
//	}

	public List<DymincReloadAction> getDymincReloadActions() {
		return dymincReloadActions;
	}
	
	protected ApplicationContext applicationContext =null;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	 public ConfigurableApplicationContext getApplicationContext() {  
	        return (ConfigurableApplicationContext)applicationContext;  
	 } 
	 public void onApplicationEvent(ContextClosedEvent e) {   
			//if(e instanceof ContextClosedEvent)  {   
				 //System.out.println("==================context closed!");  
				 logger.debug("==================context closed!");
				 if(event!=null){
					 event.stopThread();
				 }
			//}   

		 }   

	public void setDymincReloadActions(List<DymincReloadAction> dymincReloadActions) {
		this.dymincReloadActions = dymincReloadActions;
		for(DymincReloadAction action:this.dymincReloadActions){
			action.setApplicationContext(applicationContext);
		}
	}

	public List<String> getFolders() {
		if(folders==null){
			return new ArrayList<String>();
		}
		return folders;
	}

	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	//@Override
	public void afterPropertiesSet() throws Exception {
		//System.out.println("=========================2");
		if(enable){
			DymincReloadMaperEvent event=new DymincReloadMaperEvent();
	    	event.setFolders(this.getFolders());
	    	event.setDymincReloadActions(dymincReloadActions);
	    	event.start();
	    	this.event=event;
		}
	    	
	}

	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		//System.out.println("=========================1");
		this.enable = enable;
	}


}
