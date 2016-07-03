package com.mawujun.generator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.id.AbstractUUIDGenerator;
import org.hibernate.id.GUIDGenerator;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.ComponentType;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;

import com.mawujun.repository.idEntity.UUIDGenerator;
import com.mawujun.utils.StringUtils;

/**
 * 用于从领域模型中读取 meta信息的
 * 
 * @author mawujun qq:16064988 e-mail:16064988@qq.com
 */
public class JavaEntityMetaDataService {
	protected String dbName;

	protected SessionFactory sessionFactory;
	//@Autowired
	//PropertyConfigService propertyConfigService;

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	

	/**
	 * 取得数据库中的所有表名
	 * 
	 * @return
	 */
	public List<String> getAllDbTableName() {
		List<String> resultList = new ArrayList<String>();
		SessionFactory factory = this.getSessionFactory();
		Map metaMap = factory.getAllClassMetadata();
		for (String key : (Set<String>) metaMap.keySet()) {
			AbstractEntityPersister classMetadata = (SingleTableEntityPersister) metaMap
					.get(key);
			resultList.add(classMetadata.getTableName());
		}
		return resultList;
	}

	/**
	 * 按对象取得相应的表名
	 * 
	 * @param objClass
	 * @return
	 */
	public String getDbTableName(Class objClass) {
		SessionFactory factory = this.getSessionFactory();
		Class cls = objClass;
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory
				.getClassMetadata(cls);
		return classMetadata.getTableName();
	}

	/**
	 * 取得对象中所有映射的列名
	 * 
	 * @param objClass
	 * @return
	 */
	public List<String> getDbCellName(Class objClass) {
		List<String> resultList = new ArrayList<String>();
		SessionFactory factory = this.getSessionFactory();
		Class cls = objClass;
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(cls);
		// 添加主键
		resultList.addAll(Arrays.asList(classMetadata.getIdentifierColumnNames()));
		String[] propertyNames = classMetadata.getPropertyNames();
		for (String propertyName : propertyNames) {
			// 判断是否一对多的对像,移除
			boolean isCollection = classMetadata.getClassMetadata().getPropertyType(propertyName).isCollectionType();
			if (!isCollection) {
				String[] propertyColumnNames = classMetadata.getPropertyColumnNames(propertyName);
				for (String columnName : propertyColumnNames) {
					resultList.add(columnName);
				}
			}
		}
		return resultList;
	}

	/**
	 * 按列名与对像取出映射对象的字段
	 * 
	 * @param objClass
	 * @param columnName
	 * @return
	 */
	public String getPropertyByColunm(Class objClass, String columnName) {
		SessionFactory factory = this.getSessionFactory();
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(objClass);
		String[] propertyNames = classMetadata.getPropertyNames();
		for (String propertyName : propertyNames) {
			// 判断是否一对多的对像,移除
			boolean isCollection = classMetadata.getClassMetadata().getPropertyType(propertyName).isCollectionType();
			if (!isCollection) {
				String[] propertyColumnNames = classMetadata.getPropertyColumnNames(propertyName);
				for (String tempColumnName : propertyColumnNames) {
					if (columnName.equals(tempColumnName)) {
						return propertyName;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 按列名与对像取出映射对像的类型(不包括主键)
	 * 
	 * @param objClass
	 * @param columnName
	 * @return
	 */
	public Class getPropertyTypeByColumn(Class objClass, String columnName) {
		String propertyName = getPropertyByColunm(objClass, columnName);
		SessionFactory factory = this.getSessionFactory();
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(objClass);
		return classMetadata.getPropertyType(propertyName).getReturnedClass();
	}

	/**
	 * 取得主键propertyColumn
	 * 
	 * @param objClass
	 * @param columnName
	 * @return
	 */
	public String getPrimaryKeyColumn(Class objClass) {
		List<String> resultList = new ArrayList<String>();
		SessionFactory factory = this.getSessionFactory();
		Class cls = objClass;
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(cls);
		// 添加主键
		resultList.addAll(Arrays.asList(classMetadata.getIdentifierColumnNames()));
		
		if (!resultList.isEmpty()) {
			String propertyName = resultList.get(0);
			return propertyName;
		}
		return null;
	}

	/**
	 * 取得主键property
	 * 
	 * @param objClass
	 * @param columnName
	 * @return
	 */
	public String getPrimaryKeyProperty(Class objClass) {
		List<String> resultList = new ArrayList<String>();
		SessionFactory factory = this.getSessionFactory();
		Class cls = objClass;
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(cls);
		classMetadata.getIdentifierType().getName();
		return classMetadata.getIdentifierPropertyName();
	}
	/**
	 * 取得主键property
	 * 
	 * @param objClass
	 * @param columnName
	 * @return
	 */
	public String getPrimaryKeyType(Class objClass) {
		SessionFactory factory = this.getSessionFactory();
		Class cls = objClass;
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(cls);
		return classMetadata.getIdentifierType().getReturnedClass().getSimpleName();
	}

	/**
	 * 取得主键Value
	 * 
	 * @param Object
	 * @param columnName
	 * @return
	 */
	public Object getPrimaryKeyValue(Object object) {
		String propertyPrimaryKey = getPrimaryKeyProperty(object.getClass());
		String getterMethodName = propertyToGetterMethod(propertyPrimaryKey);

		try {
			Method getterMethod = object.getClass().getMethod(getterMethodName);
			return getterMethod.invoke(object, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 按列名与对像取出映射对像的setter方法
	 * 
	 * @param objClass
	 * @param columnName
	 * @return
	 */
	public String getSetterMethodByColumn(Class objClass, String columnName) {
		String property = getPropertyByColunm(objClass, columnName);
		return propertyToSetterMethod(property);
	}

	public String getGetterMethodByColumn(Class objClass, String columnName) {
		String property = getPropertyByColunm(objClass, columnName);
		return propertyToGetterMethod(property);
	}

	/**
	 * 把字段转换为setter方法
	 * 
	 * @param property
	 * @return
	 */
	public String propertyToSetterMethod(String property) {
		if (property != null) {
			property = StringUtils.capitalize(property);
			String result = "set" + property;
			return result;
		}
		return null;
	}

	public String propertyToGetterMethod(String property) {
		if (property != null) {
			property = StringUtils.capitalize(property);
			String result = "get" + property;
			return result;
		}
		return null;
	}

	/**
	 * 是否有指定的数据表
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isExistTableName(String tableName) {
		List<String> allTableNameList = getAllDbTableName();
		for (String tempTableName : allTableNameList) {
			if (tableName.equals(tempTableName)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取领域类的名称，不包括包名
	 * @return
	 */
	public String getSimpleClassName(Class clazz){
		//String name=clazz.getSimpleName();
		return clazz.getSimpleName();
	}
	/**
	 * 获取领域类的包名
	 * @return
	 */
	public String getPackage(Class clazz){
		//String name=clazz.getSimpleName();
		return clazz.getPackage().getName();
	}
	
	public SubjectRoot prepareFilePathDate(Class clazz){
		SessionFactory factory = this.getSessionFactory();
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(clazz);

		SubjectRoot root=new SubjectRoot();

		root.setDbName(dbName);
		root.setTableName(this.getDbTableName(clazz));
		root.setSimpleClassName(clazz.getSimpleName());
		root.setBasepackage(clazz.getPackage().getName());
		root.setIdType(classMetadata.getIdentifierType().getReturnedClass().getSimpleName());
		root.setIdColumnName(this.getPrimaryKeyColumn(clazz));
		root.setIdPropertyName(classMetadata.getIdentifierPropertyName());
		return root;
	}
	
	public SubjectRoot prepareDate(String className) throws ClassNotFoundException{
		Class clazz=Class.forName(className);
		return prepareDate(clazz);
	}
	/**
	 * 准备模板需要的数据
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param clazz
	 * @return
	 */
	public SubjectRoot prepareDate(Class clazz){
		SessionFactory factory = this.getSessionFactory();
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(clazz);
		
		SubjectRoot root=new SubjectRoot();

		root.setDbName(dbName);
		root.setTableName(this.getDbTableName(clazz));
		root.setSimpleClassName(clazz.getSimpleName());
		root.setBasepackage(clazz.getPackage().getName());
		root.setIdType(classMetadata.getIdentifierType().getReturnedClass().getSimpleName());
		root.setIdColumnName(this.getPrimaryKeyColumn(clazz));
		root.setIdPropertyName(classMetadata.getIdentifierPropertyName());

		if(classMetadata.getIdentifierGenerator() instanceof IdentityGenerator){
			root.setIdGeneratorStrategy("identity");
		} else if(classMetadata.getIdentifierGenerator() instanceof UUIDGenerator
			|| classMetadata.getIdentifierGenerator() instanceof AbstractUUIDGenerator){
			root.setIdGeneratorStrategy("uuid");
		} else if(classMetadata.getIdentifierGenerator() instanceof SequenceGenerator){
			root.setIdGeneratorStrategy("sequence");
			SequenceGenerator aa=(SequenceGenerator)classMetadata.getIdentifierGenerator();
			root.setSequenceName(aa.getSequenceName());
		}  else if(classMetadata.getIdentifierGenerator() instanceof GUIDGenerator){//
			//采用数据库底层的guid算法机制，对应MYSQL的uuid()函数，SQL 
			//Server的newid()函数，ORACLE的rawtohex(sys_guid())函数等
			root.setIdGeneratorStrategy("guid");
		}
		
		List<PropertyColumn> propertyColumns =new ArrayList<PropertyColumn>();
		PropertyColumn idpropertyColumn=new PropertyColumn();
		idpropertyColumn.setIsIdProperty(true);
		idpropertyColumn.setProperty(classMetadata.getIdentifierPropertyName());
		idpropertyColumn.setColumn(this.getPrimaryKeyColumn(clazz));
		idpropertyColumn.setJavaType(classMetadata.getIdentifierType().getReturnedClass());
		propertyColumns.add(idpropertyColumn);
		
		String[] propertyNames=classMetadata.getPropertyNames();
		//classMetadata.getid
		for (String propertyName : propertyNames) {
			// 判断是否一对多的对像,移除
			Type propertyType = classMetadata.getPropertyType(propertyName);
			if (propertyType.isCollectionType()) {
				root.setHasResultMap(true);
				
				//如果是关联怎么办
				PropertyColumn propertyColumn=new PropertyColumn();
				propertyColumn.setIsCollectionType(true);
				propertyColumn.setProperty(propertyName);
				propertyColumn.setJavaType(propertyType.getReturnedClass());
				//propertyColumn.setColumn(classMetadata.getPropertyColumnNames(propertyName)[0]);

				CollectionType collectionType= (CollectionType)propertyType;
				//AbstractEntityPersister classMetadataEntity = (SingleTableEntityPersister) factory.getClassMetadata(propertyType.getReturnedClass());
				CollectionMetadata  collectionMetadata =factory.getCollectionMetadata(collectionType.getRole());
				propertyColumn.setJavaType(collectionMetadata.getElementType().getReturnedClass());
				
//				PropertyColumn aa=propertyColumn.addEntityTypePropertyColumn(classMetadataEntity.getIdentifierPropertyName(),
//						classMetadata.getPropertyColumnNames(propertyName+"."+classMetadataEntity.getIdentifierPropertyName())[0],
//						entityType.getReturnedClass());
//				aa.setIsIdProperty(true);
				

				
				propertyColumns.add(propertyColumn);
				//continue;
			} else if(propertyType.isComponentType()){//如果是组件怎么办,把组件中的列映射为主对象的列
				root.setHasResultMap(true);
				
				PropertyColumn propertyColumn=new PropertyColumn();
				propertyColumn.setIsComponentType(true);
//				if(ConstantItemProxy.class.isAssignableFrom(propertyType.getReturnedClass())){
//					//propertyColumn.setIsComponentType(false);
//					propertyColumn.setIsConstantType(true);
//				}
				propertyColumn.setProperty(propertyName);
				propertyColumn.setJavaType(propertyType.getReturnedClass());
				propertyColumn.setColumn(classMetadata.getPropertyColumnNames(propertyName)[0]);

				//String[] columnNames=classMetadata.getPropertyColumnNames(propertyName);
				ComponentType aa=(ComponentType)propertyType;
				String[] compPropertyNames=aa.getPropertyNames();
				for(String compProperty:compPropertyNames){
					String bbPropAss=propertyName+"."+compProperty;
					propertyColumn.addBaseTypePropertyColumn(compProperty, 
							classMetadata.getPropertyColumnNames(bbPropAss)[0],
							classMetadata.getPropertyType(bbPropAss).getReturnedClass());	
				}

				propertyColumns.add(propertyColumn);
			} else if(propertyType.isAssociationType()){//如果是关联的话 就获取关联属性
				root.setHasResultMap(true);
				
				//如果是关联怎么办
				PropertyColumn propertyColumn=new PropertyColumn();
				propertyColumn.setIsAssociationType(true);
				propertyColumn.setProperty(propertyName);
				propertyColumn.setJavaType(propertyType.getReturnedClass());
				propertyColumn.setColumn(classMetadata.getPropertyColumnNames(propertyName)[0]);

				EntityType entityType= (EntityType)propertyType;
				AbstractEntityPersister classMetadataEntity = (SingleTableEntityPersister) factory.getClassMetadata(propertyType.getReturnedClass());
				PropertyColumn aa=propertyColumn.addEntityTypePropertyColumn(classMetadataEntity.getIdentifierPropertyName(),
						classMetadata.getPropertyColumnNames(propertyName+"."+classMetadataEntity.getIdentifierPropertyName())[0],
						entityType.getReturnedClass());
				aa.setIsIdProperty(true);
				

				
				propertyColumns.add(propertyColumn);
			} else 	if(propertyType.isEntityType()) {
				//
				//System.out.println("isEntityType===================");
			} else {
				//否则就直接加进去
				PropertyColumn propertyColumn=new PropertyColumn();
				propertyColumn.setIsBaseType(true);
				propertyColumn.setProperty(propertyName);
				propertyColumn.setColumn(classMetadata.getPropertyColumnNames(propertyName)[0]);
				propertyColumn.setJavaType(propertyType.getReturnedClass());
				propertyColumns.add(propertyColumn);
			}
		}
		
		//对列进行排序，首先是基本类行，接着是组件，再接着是集合
		List<PropertyColumn> newPropertyColumns =new ArrayList<PropertyColumn>();
		for(PropertyColumn propertyColumn:propertyColumns){
			if(propertyColumn.getIsIdProperty()){
				newPropertyColumns.add(propertyColumn);
			}
		}
		for(PropertyColumn propertyColumn:propertyColumns){
			if(propertyColumn.getIsBaseType()){
				newPropertyColumns.add(propertyColumn);
			}
		}
		for(PropertyColumn propertyColumn:propertyColumns){
			if(propertyColumn.getIsComponentType()){
				newPropertyColumns.add(propertyColumn);
			}
		}
		for(PropertyColumn propertyColumn:propertyColumns){
			if(propertyColumn.getIsAssociationType()){
				newPropertyColumns.add(propertyColumn);
			}
		}
		for(PropertyColumn propertyColumn:propertyColumns){
			if(propertyColumn.getIsCollectionType()){
				newPropertyColumns.add(propertyColumn);
			}
		}
		
		
		//List<PropertyConfig> list=propertyConfigService.query(Cnd.select().andEquals("subjectName", clazz.getName()));
		
		//root.put("propertyColumns", newPropertyColumns);
		root.setPropertyColumns(newPropertyColumns);
		List<PropertyColumn> baseTypePropertyColumns=new ArrayList<PropertyColumn>();
		for(PropertyColumn propertyColumn:newPropertyColumns){
			//不要删除，还有用
//			//添加展示方式
//			if(list!=null && list.size()>0){
//				for(PropertyConfig propertyConfig:list){
//					if(propertyConfig.getProperty().equals(propertyColumn.getProperty())){
//						propertyColumn.setLabel(propertyConfig.getLabel());
//						propertyColumn.setShowModel(propertyConfig.getShowModel());
//					}
//				}
//			}
			
			if(propertyColumn.getIsBaseType() || propertyColumn.getIsIdProperty()){
				baseTypePropertyColumns.add(propertyColumn);
			}
		}
		//所有基础属性的 属性集合
		//root.put("baseTypePropertyColumns", baseTypePropertyColumns);
		root.setBaseTypePropertyColumns(baseTypePropertyColumns);
		
		return root;
	}

	
//	private Configuration cfg=null;
//	
//	public void initConfiguration() throws IOException{
//		// TODO Auto-generated method stub
//		if(cfg!=null){
//			return;
//		}
//		
////		//加載多個文件
////		FileTemplateLoader ftl1 = new FileTemplateLoader(new File(basePath+"\\extjs4"));
////		FileTemplateLoader ftl2 = new FileTemplateLoader(new File(basePath+"\\java\\controller"));
////		FileTemplateLoader ftl3 = new FileTemplateLoader(new File(basePath+"\\java\\service"));
////		FileTemplateLoader ftl4 = new FileTemplateLoader(new File(basePath+"\\java\\mybatis"));
////		TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ftl2,ftl3,ftl4 };
////		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
////		cfg.setTemplateLoader(mtl);
//		
//		PathMatchingResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
//		Resource[] reses= resolver.getResources("classpath:templates/**/*.ftl");
////		String basePath=this.getClass().getResource("/").toString();
////		Resource[] reses= resolver.getResources("file:"+1+"/templates/**/*.ftl");
//		if(reses==null || reses.length==0){
//			return ;
//		}
//		cfg = new Configuration();
//		cfg.setEncoding(Locale.CHINA, "UTF-8");
//		//cfg.setEncoding(Locale.CHINA, "UTF-8");
//		
//		
//		//循环出 所有包含ftl的文件夹
//		Set<String> list=new HashSet<String>();
//		List<TemplateLoader> templateLoaders=new ArrayList<TemplateLoader>();
//		for(Resource res:reses){
//			//System.out.println(res.getURI().getPath());
//			//System.out.println(res.getURL().getPath());
//			String path=res.getURI().getPath().substring(0,res.getURI().getPath().lastIndexOf('/'));//SystemUtils.FILE_SEPARATOR
//			if(!list.contains(path)){
//				list.add(path);
//				FileTemplateLoader ftl1 = new FileTemplateLoader(new File(path));
//				templateLoaders.add(ftl1);
//			}
//		}
//		String path=reses[0].getURI().getPath().substring(0,reses[0].getURI().getPath().indexOf("templates")+9);
//		FileTemplateLoader ftl1 = new FileTemplateLoader(new File(path));
//		templateLoaders.add(ftl1);
//		
//		MultiTemplateLoader mtl = new MultiTemplateLoader(templateLoaders.toArray(new TemplateLoader[templateLoaders.size()]));
//		cfg.setTemplateLoader(mtl);
//		
//		
//		cfg.setObjectWrapper(new DefaultObjectWrapper());
//	}
//	
//	/**
//	 * 
//	 * @param clazz 要
//	 * @param ftl 模板文件在的地方
//	 * @throws ClassNotFoundException 
//	 * @throws TemplateException
//	 * @throws IOException
//	 */
//	public  String generatorToString(String className,String ftl,Object extenConfig) throws ClassNotFoundException, TemplateException, IOException  {
//		Class clazz=Class.forName(className);
//		return generatorToString(clazz, ftl,extenConfig);
//	}
//	/**
//	 * jsPackagel，默认是class的Leon.uncapitalize(simpleClassName)
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param clazz
//	 * @param ftl
//	 * @param 
//	 * @return
//	 * @throws TemplateException
//	 * @throws IOException
//	 */
//	public  String generatorToString(Class clazz,String ftl,Object extenConfig) throws TemplateException, IOException {
//		if(!StringUtils.hasLength(ftl)) {
//			throw new NullArgumentException("模板文件名称不能为null");
//		}
//
//		//String basePath=System.getProperty("user.dir")+"\\autoCoder\\templates\\";
//		initConfiguration();
//
//		/* 在整个应用的生命周期中，这个工作你可以执行多次 */
//		/* 获取或创建模板 */
//		Template templete = cfg.getTemplate(ftl,"UTF-8");
//		//templete.setEncoding("UTF-8");
//		//templete.setOutputEncoding("UTF-8");
//		/* 创建数据模型 */
//		SubjectRoot root =this.prepareDate(clazz);
//		if(extenConfig!=null){
//			root.setExtenConfig(extenConfig);
//		}
//		
//		
//		
//		/* 将模板和数据模型合并 */
//		//Writer out = new OutputStreamWriter(System.out);
//		Writer out = new StringWriter();
//
//		templete.process(root, out);
//		out.flush();
//		return out.toString();
//	}
//	private String getJsPackage(Class clazz){
//		return "Leon."+StringUtils.uncapitalize(clazz.getSimpleName());
//	}
//
//	
//	/**
//	 * 
//	 * @author mawujun 16064988@qq.com 
//	 * @param className 领域类
//	 * @param ftl 模板文件
//	 * @param extenConfig 扩展的属性
//	 * @param writer 要输出的对象，可以使控制面板，文件
//	 * @throws ClassNotFoundException
//	 * @throws TemplateException
//	 * @throws IOException
//	 */
//	public  void generator(String className,String ftl,Object extenConfig,Writer writer) throws ClassNotFoundException, TemplateException, IOException  {
//		Class clazz=Class.forName(className);
//		generator(clazz,ftl,extenConfig, writer);
//	}
//	
////	public  void generator(String className,String ftl,Map extenConfig,Writer writer) throws ClassNotFoundException, TemplateException, IOException  {
////		
////	}
//	/**
//	 * 根据字符串产生名称
//	 * @param clazz
//	 * @param ftl
//	 * @return
//	 * @throws ClassNotFoundException
//	 * @throws TemplateException
//	 * @throws IOException
//	 */
//	public  String generatorFileName(Class clazz,String ftl) throws ClassNotFoundException, TemplateException, IOException  {
//		SubjectRoot root =this.prepareFilePathDate(clazz);
//		
//		String fileName=FreemarkerHelper.processTemplateString(ftl,root, cfg);
//		fileName=fileName.substring(0,fileName.lastIndexOf('.'));
//		return fileName;
//	}
//	public  String generatorFileName(String className,String ftl) throws ClassNotFoundException, TemplateException, IOException  {
//		Class clazz=Class.forName(className);
//		return generatorFileName(clazz,ftl);
//	}
////	public  void generator(Class clazz,String ftl,Writer writer) throws TemplateException, IOException  {
////		generator( clazz, ftl,null, writer);
////	}
//	
//	public  void generatorFile(Class clazz,String ftl,String filePath,Object extenConfig) throws TemplateException, IOException {
//		FileWriter fileWriter=new FileWriter(filePath);
//		generator(clazz, ftl, extenConfig, fileWriter);
//	}
//	/**
//	 * 
//	 * @param clazz 要
//	 * @param ftl 模板文件在的地方
//	 * @throws TemplateException
//	 * @throws IOException
//	 */
//	public  void generator(Class clazz,String ftl,Object extenConfig,Writer writer) throws TemplateException, IOException {
//		if(!StringUtils.hasLength(ftl)) {
//			throw new NullArgumentException("模板文件名称不能为null");
//		}
//
//		String basePath=System.getProperty("user.dir");
//		initConfiguration();
//
//		/* 在整个应用的生命周期中，这个工作你可以执行多次 */
//		/* 获取或创建模板 */
//		Template templete = cfg.getTemplate(ftl,"UTF-8");
//		//templete.setEncoding("UTF-8");
//		//templete.setOutputEncoding("UTF-8");
//		/* 创建数据模型 */
//		SubjectRoot root =this.prepareDate(clazz);
//		if(extenConfig!=null){
//			root.setExtenConfig(extenConfig);
//		}
//		
////		if(jsPackage!=null){
////			root.setJsPackage(jsPackage);	
////		} else {
////			root.setJsPackage(getJsPackage(clazz));
////		}
//		
////		String fileName=FreemarkerHelper.processTemplateString(ftl,root, new Configuration());
////		String filePath=basePath+SystemUtils.FILE_SEPARATOR+fileName;
////		File file=new File(filePath);
////		if(!file.exists()){
////			file.createNewFile();
////		}
////		FileWriter out=new FileWriter(file);
//
//		templete.process(root, writer);
//		//out.flush();
//		//return out;
//	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
