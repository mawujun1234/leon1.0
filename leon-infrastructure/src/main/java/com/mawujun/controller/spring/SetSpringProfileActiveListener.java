package com.mawujun.controller.spring;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 整合spring和maven的prifle设置，这样的话就不需要再web.xml中设置
 * <context-param>
		<param-name>spring.profiles.active</param-name>
		<param-value>${profiles.active}</param-value>
   </context-param>
   而且又可以使用spring的prifile的优点，定义各自环境中的bean
   
   
   配置方式如下：
   <!--指定配置文件在什么地方 -->
    <context-param>
		<param-name>system_config_file_path</param-name>
		<param-value>system_config.properties</param-value>
   </context-param>
   <listener>
     	<listener-class>com.mawujun.controller.spring.SetSpringProfileActiveListener</listener-class>
    </listener>
    
    文件内容为：
    #这里的配置值是从maven中获取到的，将会自动配置
	spring.profiles.active=${profiles.active}
 * @author mawujun
 *
 */
public class SetSpringProfileActiveListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//System.out.println("=====================================");
		//System.setProperty("spring.profiles.active", "dev");
		//从配置文件中去读取，这个参数的配置，然后设置到System.properties里面或者设置到context-params里卖弄
		//
		String active=sce.getServletContext().getInitParameter("spring.profiles.active");
		if(active==null || "".equals(active.trim())){
			String system_config_file_path=sce.getServletContext().getInitParameter("system_config_file_path");
			if(system_config_file_path!=null && !"".equals(system_config_file_path.trim())){
				system_config_file_path=system_config_file_path.substring(0, system_config_file_path.lastIndexOf('.'));
				//System.out.println(system_config_file_path);
				ResourceBundle buddle=ResourceBundle.getBundle(system_config_file_path);
				//System.out.println(buddle);
				active=buddle.getString("spring.profiles.active");
				//System.out.println(active);
				//sce.getServletContext().setInitParameter("spring.profiles.active", active);
				System.setProperty("spring.profiles.active", active);
			}

		}
		
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
