package com.mawujun.utils.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SetSpringProfileActiveListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("=====================================");
		System.setProperty("spring.profiles.active", "dev");
		//从配置文件中去读取，这个参数的配置，然后设置到System.properties里面或者设置到context-params里卖弄
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
