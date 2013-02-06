package com.mawujun.repository.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.mawujun.utils.ReflectionUtils;





public class DymincReloadActionImp extends DymincReloadAction{
	
	 private final Log logger = LogFactory.getLog(getClass());
		
	private Boolean reloadSqlFile=false;//当监听到文件发生变化的时候，是否重新加载mapper文件
	
	//private String configLocation;
	//private List<String> mapperLocations;
	
	private String sqlSessionFactoryId;
	
	private String mapperFileSuffix="_Mapper.xml";
	ApplicationContext applicationContext;

	public String getMapperFileSuffix() {
		return mapperFileSuffix;
	}

	public void setMapperFileSuffix(String mapperFileSuffix) {
		this.mapperFileSuffix = mapperFileSuffix;
	}

	public DymincReloadActionImp() {
		super();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	    super.afterPropertiesSet();
	    //Assert.notNull(configLocation, "configLocation为null");
		//Assert.notNull(mapperLocations, "mapperLocations为null");
		Assert.notNull(sqlSessionFactoryId, "sqlSessionFactoryId为null");
		//Assert.notNull(mybatisRepository, "mybatisRepository为null");
		//this.applicationContext=SpringContextHolder.getApplicationContext();
	}

	public void doAction(String filePath,String fileName,FileAction create)  {
		if(!this.getReloadSqlFile()){
			return ;
		}

		if(fileName.lastIndexOf(this.getMapperFileSuffix())==-1){
			return;
		}
		
        try {  
    	
        	Thread.currentThread().setContextClassLoader(applicationContext.getClassLoader());
            SqlSessionFactoryBean sqlSessionFactoryBean=(SqlSessionFactoryBean)getApplicationContext().getBean("&"+this.getSqlSessionFactoryId());

            if(filePath!=null && !"".equals(filePath)){
            	List<Resource> list=new ArrayList<Resource>();

            	Resource[] mapperLocations=(Resource[])ReflectionUtils.getFieldValue(sqlSessionFactoryBean, "mapperLocations");
            	for(Resource res:mapperLocations){
            		list.add(res);
            	}
  
            	if(create.equals(FileAction.CREATE)){
            		Resource[] resources=getApplicationContext().getResources("file:"+System.getProperty("file.separator")+filePath+System.getProperty("file.separator")+fileName);
                    for(Resource res:resources) {
                    	list.add(res);
                    }
            	} else {
            		Resource delete=null;
            		for(Resource res:list){
            			if(res.getFilename().equals(fileName)){
            				delete=res;
            				break;
            			}
            		}  
            		if(delete !=null){
            			list.remove(delete);
            		}
            		
            	}

            	sqlSessionFactoryBean.setMapperLocations(list.toArray(new Resource[list.size()]));	
                
            }
            sqlSessionFactoryBean.afterPropertiesSet();
            SqlSessionFactory sqlSessionFactory= sqlSessionFactoryBean.getObject();
    
            Map<String,MybatisRepository> allRepository=this.applicationContext.getBeansOfType(MybatisRepository.class,true,true);
            for(Entry<String,MybatisRepository> entry:allRepository.entrySet()){
            	entry.getValue().setSqlSessionFactory(sqlSessionFactory);
            }
            //System.out.println("重新加载完成");
            logger.debug("重新加载完成");
 
        } catch (BeansException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } 
        //System.out.println("====================================================动态加载成功");
	}
	 public ConfigurableApplicationContext getApplicationContext() {  
	        return (ConfigurableApplicationContext)applicationContext;  
	 } 



	@Override
	public void fileModified(String rootPath, String name) {
		// print("modified " + rootPath + " : " + name);
		doAction(rootPath,name,FileAction.MODEFY);
	}

	@Override
	public void fileDeleted(String rootPath, String name) {
		// print("deleted " + rootPath + " : " + name);
		doAction(rootPath.replaceAll(" ", "%20"),name,FileAction.DELETE);
	}

	@Override
	public void fileCreated(String rootPath, String name) {
		//print("created " + rootPath + " : " + name);
		doAction(rootPath.replaceAll(" ", "%20"),name,FileAction.CREATE);
	}
	void print(String msg) {
	      System.err.println(msg);
	}



	public String getSqlSessionFactoryId() {
		return sqlSessionFactoryId;
	}

	public void setSqlSessionFactoryId(String sqlSessionFactoryId) {
		this.sqlSessionFactoryId = sqlSessionFactoryId;
	}

	public Boolean getReloadSqlFile() {
		return reloadSqlFile;
	}
	public void setReloadSqlFile(Boolean reloadSqlFile) {
		this.reloadSqlFile = reloadSqlFile;
	}

	@Override
	public void fileRenamed(int wd, String rootPath, String oldName,
			String newName) {
		// TODO Auto-generated method stub
		
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}



}
