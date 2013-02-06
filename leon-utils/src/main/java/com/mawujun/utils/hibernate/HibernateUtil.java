package com.mawujun.utils.hibernate;

import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

   // private static final SessionFactory sessionFactory = buildSessionFactory();
	private static SessionFactory sessionFactory = null;

    private static SessionFactory buildSessionFactory(String xml,String pfile) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	// Configuration对象会先加载hibernate.properties文件的内容，再加载hibernate.cfg.xml文件的内容，
        	//然后将hibernate.cfg.xml文件中的信息覆盖率hibernate.properties文件中的信息。
        	//return new Configuration().configure().buildSessionFactory();
        	Configuration cfg = new Configuration(); 
        	if(pfile!=null){
        		InputStream in = HibernateUtil.class.getClassLoader().getResourceAsStream(pfile);
            	Properties properties = new Properties();
            	properties.load(in);

            	cfg.setProperties(properties);
        	} 
        	          
        	if(xml==null){
        		cfg.configure();
        	} else {
        		cfg.configure(xml);
        	}
        	
        	
        	ServiceRegistry  sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();           
        	SessionFactory sf = cfg.buildSessionFactory(sr);  
        	return sf;
            
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * 不指定参数就从classes目录下查找hibernate默认的配置文件
     * 另外，在hibernate的启动过程中，会先找hibernate.properties，然后读取hibernate.cfg.xml，后者会覆盖前者相同的属性
     * @param resources
     * @return
     */
    public static SessionFactory getSessionFactory(String resources) {
    	if(sessionFactory==null){
    		sessionFactory=buildSessionFactory(resources,null);
    	}
        return sessionFactory;
    }
    
    public static SessionFactory getSessionFactory(String xml,String pfile) {
    	if(sessionFactory==null){
    		sessionFactory=buildSessionFactory(xml,pfile);
    	}
        return sessionFactory;
    }

}