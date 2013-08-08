package com.mawujun.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.utils.SystemUtils;

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
	@RequestMapping("/generator/listJavaDir")
	public List<Map<String,String>> listJavaDir(HttpServletRequest request,String id) throws IOException{
		//String path=Test.class.getResource("/").getPath()+"。。/../";
		String basePath = request.getSession().getServletContext().getRealPath("");
		if(id!=null && !"root".equals(id)){
			basePath=id;
		} else {
			basePath=basePath+SystemUtils.FILE_SEPARATOR+".."+SystemUtils.FILE_SEPARATOR+"java";
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
	public boolean createDirectory(String parentId,String text) throws IOException{
		String[] paths=text.split("/");
		d
		File directory=new File(parentId+SystemUtils.FILE_SEPARATOR+text);
		boolean bool=directory.createNewFile();
		return bool;
	}
	
//	@Autowired
//	JavaEntityMetaDataService javaEntityMetaDataService;
//	
//	private static List<Map<String,Object>> packageClass=null;
//	
//	@RequestMapping("/codeGenerator/listAllClass.do")
//	@ResponseBody
//	public ExtjsJsonResult queryPage(String id,String node) {
//		
//		if(StringUtils.hasLength(id) && !"root".equals(id)){
//			for(Map<String,Object> map:packageClass){
//				if(map.get("id").equals(id)){
//					 return ExtjsJsonResult.initResult(map.get("children"));
//				}
//			}
//		}
//
//		
//		Map<String,ClassMetadata> queryResult=javaEntityMetaDataService.getSessionFactory().getAllClassMetadata();
//		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
//		Set<String> contain=new HashSet<String>();
//		for(Entry<String,ClassMetadata> entry:queryResult.entrySet()){
//			
//			String packageName=entry.getKey().substring(0,entry.getKey().lastIndexOf('.'));
//			
//			
//			Map<String,Object> childrenNode=new HashMap<String,Object>();
//			childrenNode.put("id",entry.getKey().substring(entry.getKey().lastIndexOf('.')+1));
//			childrenNode.put("text",entry.getKey().substring(entry.getKey().lastIndexOf('.')+1));
//			childrenNode.put("leaf", true);
//			childrenNode.put("type", "entity");
//			childrenNode.put("className", entry.getKey());
//			if(contain.contains(packageName)){
//				Map<String,Object> packageNode=null;
//				for(Map<String,Object> map:result){
//					if(map.get("text").equals(packageName)){
//						packageNode=map;
//						break;
//					}
//				}
//				((List<Map<String,Object>>)packageNode.get("children")).add(childrenNode);
//				//packageNode.put("children", childrenNode)
//			} else {
//				Map<String,Object> packageNode=new HashMap<String,Object>();
//				packageNode.put("id",packageName);
//				packageNode.put("text",packageName);
//				packageNode.put("leaf", false);
//				
//				List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
//				children.add(childrenNode);
//				packageNode.put("children", children);
//				result.add(packageNode);
//			}	
//			contain.add(packageName);
//		}
//		packageClass=result;
//		return ExtjsJsonResult.initResult(result);
//	}
//	//类型和具体的模板文件的对应关系
//	HashMap<String,String> ftlMapper=new HashMap<String,String>();
//	{
//		ftlMapper.put("Controller", "${simpleClassName}Controller.ftl");
//		ftlMapper.put("Service", "${simpleClassName}Service.ftl");
//		ftlMapper.put("MapperXML", "${simpleClassName}_${dbName}_Mapper.ftl");
//		ftlMapper.put("Extjs_Model", "${simpleClassName}.ftl");
//	}
//	@RequestMapping("/codeGenerator/generator.do")
//	@ResponseBody
//	public String generator(String className, String type) {
//		String writer=null;
//		try {
//			writer=javaEntityMetaDataService.generatorToString(className,ftlMapper.get(type));	
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return ExtjsJsonResult.initErrorResult("没有找到这个类");
//		} catch (TemplateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return ExtjsJsonResult.initErrorResult("模板文件出错");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return ExtjsJsonResult.initErrorResult("读取模板文件出错");
//		}
//		String str=writer.toString();
//		//str= str.replaceAll( "\r\n", " <br/> ");
//		//str= str.replaceAll( "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
//		return str;
//	}
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
