package com.mawujun.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.mawujun.utils.StringUtils;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author mawujun
 *
 */
@Service
public class GeneratorService {

	@Autowired
	JavaEntityMetaDataService javaEntityMetaDataService;
	
	private Configuration cfg=null;
	
	public void initConfiguration() throws IOException{
		// TODO Auto-generated method stub
		if(cfg!=null){
			return;
		}
		
//		//加載多個文件
//		FileTemplateLoader ftl1 = new FileTemplateLoader(new File(basePath+"\\extjs4"));
//		FileTemplateLoader ftl2 = new FileTemplateLoader(new File(basePath+"\\java\\controller"));
//		FileTemplateLoader ftl3 = new FileTemplateLoader(new File(basePath+"\\java\\service"));
//		FileTemplateLoader ftl4 = new FileTemplateLoader(new File(basePath+"\\java\\mybatis"));
//		TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ftl2,ftl3,ftl4 };
//		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
//		cfg.setTemplateLoader(mtl);
		
		PathMatchingResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
		Resource[] reses= resolver.getResources("classpath:templates/**/*.ftl");
		if(reses==null || reses.length==0){
			return ;
		}
		cfg = new Configuration();
		//循环出 所有包含ftl的文件夹
		Set<String> list=new HashSet<String>();
		List<TemplateLoader> templateLoaders=new ArrayList<TemplateLoader>();
		for(Resource res:reses){
			System.out.println(res.getURI().getPath());
			//System.out.println(res.getURL().getPath());
			String path=res.getURI().getPath().substring(0,res.getURI().getPath().lastIndexOf('/'));//SystemUtils.FILE_SEPARATOR
			if(!list.contains(path)){
				list.add(path);
				FileTemplateLoader ftl1 = new FileTemplateLoader(new File(path));
				templateLoaders.add(ftl1);
			}
		}
		String path=reses[0].getURI().getPath().substring(0,reses[0].getURI().getPath().indexOf("templates")+9);
		FileTemplateLoader ftl1 = new FileTemplateLoader(new File(path));
		templateLoaders.add(ftl1);
		
		MultiTemplateLoader mtl = new MultiTemplateLoader(templateLoaders.toArray(new TemplateLoader[templateLoaders.size()]));
		cfg.setTemplateLoader(mtl);
		
		
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}
	/**
	 * 
	 * @param clazz 要
	 * @param ftl 模板文件在的地方
	 * @throws TemplateException
	 * @throws IOException
	 */
	public  Writer generator(Class clazz,String ftl) throws TemplateException, IOException {
		if(!StringUtils.hasLength(ftl)) {
			throw new NullArgumentException("模板文件名称不能为null");
		}

		//String basePath=System.getProperty("user.dir")+"\\autoCoder\\templates\\";
		initConfiguration();
		
		
		/* 在整个应用的生命周期中，这个工作你可以执行多次 */
		/* 获取或创建模板 */
		Template templete = cfg.getTemplate(ftl);
		/* 创建数据模型 */
		Map root =javaEntityMetaDataService.prepareDate(clazz);
		/* 将模板和数据模型合并 */
		//Writer out = new OutputStreamWriter(System.out);
		Writer out = new StringWriter();

		templete.process(root, out);
		out.flush();
		return out;
	}
	
	

}
