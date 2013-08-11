package com.mawujun.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.utils.PropertiesUtils;
import com.mawujun.utils.SystemUtils;

import freemarker.template.TemplateException;

@Controller
public class GeneratorController {
	@RequestMapping("/generator/listJsDir")
	public List<Map<String,String>> getAppDir(HttpServletRequest request,String id) throws IOException{
		//String path=Test.class.getResource("/").getPath()+"。。/../";
		String basePath = request.getSession().getServletContext().getRealPath("");
		if(id!=null && !"root".equals(id)){
			basePath=id;
		}
		File directory=new File(basePath);
		//Collection<File> files=FileUtils.listFilesAndDirs(directory, TrueFileFilter.TRUE, DirectoryFileFilter.INSTANCE);
		
		File[] files=directory.listFiles();
		List<Map<String,String>> result=new ArrayList<Map<String,String>>();
		List<Map<String,String>> fileResult=new ArrayList<Map<String,String>>();
		for(File file:files){
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", file.getPath());
			map.put("text", file.getName());
			if(!file.isDirectory()){
				map.put("iconCls", "icons_23");
				map.put("leaf", "true");
				map.put("isFile", "true");
				fileResult.add(map);
			} else {
				map.put("isFile", "false");
				result.add(map);
			}
			
		}
		result.addAll(fileResult);
		JsonConfigHolder.setRootName("children");
		return result;//new ArrayList<Map<String,String>>();
		
	}
	
	public String getJavaRootPath(HttpServletRequest request){
		String basePath = request.getSession().getServletContext().getRealPath("");
		return basePath+SystemUtils.FILE_SEPARATOR+".."+SystemUtils.FILE_SEPARATOR+"java";
	}
	@RequestMapping("/generator/listJavaDir")
	public List<Map<String,String>> listJavaDir(HttpServletRequest request,String id) throws IOException{
		//String path=Test.class.getResource("/").getPath()+"。。/../";
		String basePath =null;
		if(id!=null && !"root".equals(id)){
			basePath=id;
		} else {
			basePath=getJavaRootPath(request);
		}
		File directory=new File(basePath);
		//Collection<File> files=FileUtils.listFilesAndDirs(directory, TrueFileFilter.TRUE, DirectoryFileFilter.INSTANCE);
		
		File[] files=directory.listFiles();
		List<Map<String,String>> result=new ArrayList<Map<String,String>>();
		List<Map<String,String>> fileResult=new ArrayList<Map<String,String>>();
		for(File file:files){
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", file.getPath());
			map.put("text", file.getName());
			if(!file.isDirectory()){
				map.put("iconCls", "icons_23");
				map.put("leaf", "true");
				map.put("isFile", "true");
				fileResult.add(map);
			} else {
				map.put("isFile", "false");
				result.add(map);
			}
			
		}
		result.addAll(fileResult);
		JsonConfigHolder.setRootName("children");
		return result;//new ArrayList<Map<String,String>>();
		
	}
	@RequestMapping("/generator/createDirectory")
	@ResponseBody
	public boolean createDirectory(HttpServletRequest request,String parentId,String text) throws IOException{
		//String[] paths=text.split("/");
		text=text.replaceAll(".", SystemUtils.FILE_SEPARATOR);
		String basePath =null;
		if(parentId!=null && !"root".equals(parentId)){
			basePath=parentId;
		} else {
			basePath=getJavaRootPath(request);
		}
		
		File directory=new File(basePath+SystemUtils.FILE_SEPARATOR+text);
		boolean bool=directory.mkdirs();
		return bool;
	}
	
	@Autowired
	JavaEntityMetaDataService javaEntityMetaDataService;
	
	private List<Map<String,Object>> packageClass=null;
	
	@RequestMapping("/generator/listAllClass")
	@ResponseBody
	public List<Map<String,Object>> listAllClass(String id,String node) {
//		
//		if(StringUtils.hasLength(id) && !"root".equals(id)){
//			for(Map<String,Object> map:packageClass){
//				if(map.get("id").equals(id)){
//					 return map.get("children");
//				}
//			}
//		}

		
		Map<String,ClassMetadata> queryResult=javaEntityMetaDataService.getSessionFactory().getAllClassMetadata();
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		//Set<String> contain=new HashSet<String>();
		for(Entry<String,ClassMetadata> entry:queryResult.entrySet()){
			
			String packageName=entry.getKey().substring(0,entry.getKey().lastIndexOf('.'));
			
			
			Map<String,Object> childrenNode=new HashMap<String,Object>();
			//childrenNode.put("id",entry.getKey());
			childrenNode.put("text",entry.getKey().substring(entry.getKey().lastIndexOf('.')+1));
//			childrenNode.put("leaf", true);
//			childrenNode.put("type", "entity");
			childrenNode.put("className", entry.getKey());
			result.add(childrenNode);

		}
		packageClass=result;
		return result;
	}
//	//类型和具体的模板文件的对应关系,之后写到配置文件当中去，这样就可以进行修改了
//	HashMap<String,String> ftlMapper=new HashMap<String,String>();
//	{
//		ftlMapper.put("Controller", "${simpleClassName}Controller.java.ftl");
//		ftlMapper.put("Service", "Controller${simpleClassName}Service.java.ftl");
//		ftlMapper.put("MapperXML", "${simpleClassName}_${dbName}_Mapper.xml.ftl");
//		ftlMapper.put("Extjs_Model", "${simpleClassName}.js.ftl");
//	}
	@RequestMapping("/generator/generatorStr")
	@ResponseBody
	public String generatorStr(String className, String type) throws ClassNotFoundException, TemplateException, IOException {
		PropertiesUtils utils=PropertiesUtils.load("templates/templates.properties");
		
		String writer=null;

		writer=javaEntityMetaDataService.generatorToString(className,utils.getProperty(type));	
		
		String str=writer.toString();
		//str= str.replaceAll( "\r\n", " <br/> ");
		//str= str.replaceAll( "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		JsonConfigHolder.setAutoWrap(false);
		return str;
	}
//	
//	@RequestMapping("/codeGenerator/exportFile.do")
//	@ResponseBody
//	public void exportFile(String className, String type,HttpServletResponse response) throws IOException, ClassNotFoundException, TemplateException {
//		String fileName=javaEntityMetaDataService.generatorFileName(className, ftlMapper.get(type));
//		fileName=fileName.substring(0,fileName.lastIndexOf('.'))+".java";
//		fileName = new String((fileName).getBytes("UTF-8"),"ISO8859_1");    
//		response.setContentType("application/html;charset=utf-8");
//		response.addHeader("Content-Disposition","attachment;filename="+fileName);  
//
//		// 将数据输出到Servlet输出流中。
//		//ServletOutputStream sos = response.getOutputStream();
//		Writer writer=response.getWriter();
//		javaEntityMetaDataService.generator(className,ftlMapper.get(type),writer);	
//		writer.close();
//		
//	}

}
