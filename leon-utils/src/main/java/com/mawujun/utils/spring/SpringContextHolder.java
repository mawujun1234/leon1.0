package com.mawujun.utils.spring;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

/**
 * 用来获取已经生成的ApplicationContext
 * @author mawujun
 *
 */

public class SpringContextHolder implements ApplicationContextAware, DisposableBean  {

	private static ApplicationContext applicationContext;//声明一个静态变量保存
	
	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);
	

	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		
		System.out.println("========================初始化SpringContextHolder"+contex.getId());
		logger.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = contex; //NOSONAR
	}

	/**
	 * 实现DisposableBean接口,在Context关闭时清理静态变量.
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clear() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
	/**
	 * 动态载入一个新建的类,参数是：异构系统的id
	 *  向spring的beanFactory动态地装载bean  
     * @param id  异构系统中配置id
	 * @throws IOException 
	 */
	public static void dymincLoadSSOConfigFile(String id) throws IOException{
		if(!StringUtils.hasText(id)){
			return;
		}
//		//if(SpringContextHolder.getBean("sSOProvider_"+id)!=null){
//		//http://hi.baidu.com/cnbxj/blog/item/d26b7e4ca2fc1ee9d62afcfc.html
//		ProviderManager providerClass1=(ProviderManager)applicationContext.getBean("authenticationManager");
//		System.out.println("-------------------------1:"+providerClass1.getProviders().size());
//			
//			removeSSOConfig(id);
//			
//			
//		System.out.println("-------------------------2:"+providerClass1.getProviders().size());	
//		
//		
//		
//		
//		
//		
//		DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(acf);
//		
//		String fileName="authenSource_"+id+".xml";
//		URLClassLoader ClassLoader=new URLClassLoader(new URL[]{ AuthenSourceXmlFileRepository.class.getClassLoader().getResource("") });  
//		reader.loadBeanDefinitions(new ClassPathResource(fileName,ClassLoader));
//		
//			
////			DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
////			String fileName="authenSource_"+id+".xml";
////			//XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource(fileName));
////			System.out.println(AuthenSourceXmlFileRepository.class.getClassLoader().getResource(""));
////			URLClassLoader ClassLoader=new URLClassLoader(new URL[]{ AuthenSourceXmlFileRepository.class.getClassLoader().getResource("") });  
////			XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource(fileName,ClassLoader));
////			//factory.
////			acf.registerBeanDefinition("datasource_"+id, factory.getMergedBeanDefinition("datasource_"+id));
////			acf.registerBeanDefinition("jdbcTemplate_"+id, factory.getMergedBeanDefinition("jdbcTemplate_"+id));
////			acf.registerBeanDefinition("sSOProvider_"+id, factory.getMergedBeanDefinition("sSOProvider_"+id));
////			acf.registerBeanDefinition("sSOUsertailService_"+id, factory.getMergedBeanDefinition("sSOUsertailService_"+id));
////			acf.registerBeanDefinition("daoAuthenticationProvider_"+id, factory.getMergedBeanDefinition("daoAuthenticationProvider_"+id));
////			
//////			//acf.copyConfigurationFrom(factory);
//////			
//////			ProviderManager providerClass=(ProviderManager)acf.getBean("authenticationManager");
//////			
//////			
//////			//这里还要添加到authactionmanager的list中
//////			AccountManager accountManager=(AccountManager)acf.getBean("accountManager");
//////			SSOUsertailService sSOUsertailService=(SSOUsertailService)factory.getBean("sSOUsertailService_"+id);
//////			sSOUsertailService.setAccountManager(accountManager);
//////			
//////			AuthenticationProvider provider=(AuthenticationProvider)factory.getBean("daoAuthenticationProvider_"+id);
//////			System.out.println("新添加的功能:"+provider);
//////			providerClass.getProviders().add(provider);
//////
////////			DriverManagerDataSource driverManagerDataSource=(DriverManagerDataSource)acf.getBean("datasource_"+id);
////////			JdbcTemplate jdbcTemplate=(JdbcTemplate)acf.getBean("jdbcTemplate_"+id);
////////			jdbcTemplate.setDataSource(driverManagerDataSource);
//////			
//////			
//////			//SSOUsertailService sSOUsertailService=(SSOUsertailService)applicationContext.getBean("sSOUsertailService_"+id);
//////			
//////
//////			//((JavaProviderSample)factory.getBean("sSOProvider_"+id)).getJdbcTemplate().getDataSource();
//			
//			
//			
//			
//			
//			
//			
//			
//			//上面注释的替换成了现在的这个 2011.1.20
//			ProviderManager providerClass=(ProviderManager)acf.getBean("authenticationManager");
//			
//			AuthenticationProvider provider=(AuthenticationProvider)acf.getBean("daoAuthenticationProvider_"+id);
//			System.out.println("新添加的功能:"+provider);
//			providerClass.getProviders().add(provider);
//			
//		System.out.println("-------------------------3:"+providerClass1.getProviders().size());

	}
	/**
	 * 移除sso配置
	 * @param id
	 * @param version
	 * @throws IOException
	 */
	public static void removeSSOConfig(String id) throws IOException{
//		if(!StringUtils.hasText(id)){
//			return;
//		}
//		//if(SpringContextHolder.getBean("sSOProvider_"+id)!=null){
//		//http://hi.baidu.com/cnbxj/blog/item/d26b7e4ca2fc1ee9d62afcfc.html
//			DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//			
//			//System.out.println(applicationContext.containsBean("daoAuthenticationProvider_"+id));
//			
//			if(applicationContext.containsBean("daoAuthenticationProvider_"+id)){
//				ProviderManager providerClass=(ProviderManager)applicationContext.getBean("authenticationManager");
//				AuthenSourceAuthenticationProvider sso=null;
//				for(AuthenticationProvider bb:providerClass.getProviders()){
//					//System.out.println("list里面的:"+bb);
//					//System.out.println(obj);
//					if((bb instanceof AuthenSourceAuthenticationProvider)){
//						if(((AuthenSourceAuthenticationProvider)bb).getId().equals("daoAuthenticationProvider_"+id)){
//							sso=(AuthenSourceAuthenticationProvider)bb;
//							break;
//						}
//					}	
//				}
//				providerClass.getProviders().remove(sso);
//				if(sso!=null){
//					sso.setUserDetailsService(null);
//				}
//				acf.destroySingleton("daoAuthenticationProvider_"+id);
//				
//				if(applicationContext.containsBean("sSOUsertailService_"+id)){
//					acf.destroySingleton("sSOUsertailService_"+id);
//				}
//				if(applicationContext.containsBean("sSOProvider_"+id)){
//					acf.destroySingleton("sSOProvider_"+id);
//				}
//				if(applicationContext.containsBean("jdbcTemplate_"+id)){
//					acf.destroySingleton("jdbcTemplate_"+id);
//					
//				}
//				if(applicationContext.containsBean("datasource_"+id)){
//					acf.destroySingleton("datasource_"+id);
//				}
////				SSOUsertailService sSOUsertailService=(SSOUsertailService)applicationContext.getBean("sSOUsertailService_"+id);
////				sSOUsertailService.getJdbcTemplate().setDataSource(null);
////				sSOUsertailService.setJdbcTemplate(null);
////				System.out.println(sSOUsertailService);
//			}
//			if(acf.containsBeanDefinition("daoAuthenticationProvider_"+id)){
////				Object obj=applicationContext.getBean("daoAuthenticationProvider_"+id);//applicationContext.getBean("daoAuthenticationProvider_"+id);//
////				//acf.getBeanDefinition("daoAuthenticationProvider_"+id);
////				System.out.println(obj);
////
////				
////				ProviderManager providerClass=(ProviderManager)applicationContext.getBean("authenticationManager");
////				//System.out.println(providerClass);
////				boolean bool=providerClass.getProviders().remove(obj);
////				for(AuthenticationProvider bb:providerClass.getProviders()){
////					System.out.println("list里面的:"+bb);
////					//System.out.println(obj);
////					if(bb==obj){
////						System.out.println("=============================:他们相等了");
////					}	
////				}
//////				这里的提供者没有删除掉，一个一个的往上加了，所以出错了，所以修改数据库密码，后还是能登录的原因
//////				解决方案给继承DaoAuthenticationProvider，加上id，通过判断这个来区分，
//////				先看看 是不是在getBean的时候，又重新实例化了一个类
////				System.out.println("删除认证提供者："+providerClass.getProviders().size());
//				
//
//				acf.removeBeanDefinition("daoAuthenticationProvider_"+id);
//
//			}
//			
//			if(acf.containsBeanDefinition("sSOUsertailService_"+id)){
//				acf.removeBeanDefinition("sSOUsertailService_"+id);
//			}
//			
//			if(acf.containsBeanDefinition("sSOProvider_"+id)) {//从这个方法acf.containsBean(name)改成containsBeanDefinition方法的
//				acf.removeBeanDefinition("sSOProvider_"+id);		
//			}
//			//System.out.println();
//			if(acf.containsBeanDefinition("jdbcTemplate_"+id)) {
//				//acf.destroyBean("jdbcTemplate_"+id,applicationContext.getBean("jdbcTemplate_"+id));
//				acf.removeBeanDefinition("jdbcTemplate_"+id);
//			}
//			if(acf.containsBeanDefinition("datasource_"+id)) {
//				//acf.destroyBean("datasource_"+id, applicationContext.getBean("datasource_"+id));
//				acf.removeBeanDefinition("datasource_"+id);
//			}

		//}
	}

}
