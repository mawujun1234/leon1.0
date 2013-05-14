package com.mawujun.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.metadata.ClassMetadata;
import org.jboss.util.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.spring.ExtjsJsonResult;
import com.mawujun.util.web.HtmlUtils;

import freemarker.template.TemplateException;

@Controller
public class GeneratorController {
	@Autowired
	JavaEntityMetaDataService javaEntityMetaDataService;
	
	private static List<Map<String,Object>> packageClass=null;
	
	@RequestMapping("/codeGenerator/listAllClass.do")
	@ResponseBody
	public ExtjsJsonResult queryPage(String id,String node) {
		
		if(StringUtils.hasLength(id) && !"root".equals(id)){
			for(Map<String,Object> map:packageClass){
				if(map.get("id").equals(id)){
					 return ExtjsJsonResult.initResult(map.get("children"));
				}
			}
		}

		
		Map<String,ClassMetadata> queryResult=javaEntityMetaDataService.getSessionFactory().getAllClassMetadata();
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		Set<String> contain=new HashSet<String>();
		for(Entry<String,ClassMetadata> entry:queryResult.entrySet()){
			
			String packageName=entry.getKey().substring(0,entry.getKey().lastIndexOf('.'));
			
			
			Map<String,Object> childrenNode=new HashMap<String,Object>();
			childrenNode.put("id",entry.getKey().substring(entry.getKey().lastIndexOf('.')+1));
			childrenNode.put("text",entry.getKey().substring(entry.getKey().lastIndexOf('.')+1));
			childrenNode.put("leaf", true);
			childrenNode.put("type", "entity");
			childrenNode.put("className", entry.getKey());
			if(contain.contains(packageName)){
				Map<String,Object> packageNode=null;
				for(Map<String,Object> map:result){
					if(map.get("text").equals(packageName)){
						packageNode=map;
						break;
					}
				}
				((List<Map<String,Object>>)packageNode.get("children")).add(childrenNode);
				//packageNode.put("children", childrenNode)
			} else {
				Map<String,Object> packageNode=new HashMap<String,Object>();
				packageNode.put("id",packageName);
				packageNode.put("text",packageName);
				packageNode.put("leaf", false);
				
				List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
				children.add(childrenNode);
				packageNode.put("children", children);
				result.add(packageNode);
			}	
			contain.add(packageName);
		}
		packageClass=result;
		return ExtjsJsonResult.initResult(result);
	}
	//类型和具体的模板文件的对应关系
	HashMap<String,String> ftlMapper=new HashMap<String,String>();
	{
		ftlMapper.put("Controller", "${simpleClassName}Controller.ftl");
		ftlMapper.put("Service", "${simpleClassName}Service.ftl");
		ftlMapper.put("MapperXML", "${simpleClassName}_${dbName}_Mapper.ftl");
		ftlMapper.put("Extjs_Model", "${simpleClassName}.ftl");
	}
	@RequestMapping("/codeGenerator/generator.do")
	@ResponseBody
	public String generator(String className, String type) {
		String writer=null;
		try {
			writer=javaEntityMetaDataService.generatorToString(className,ftlMapper.get(type));	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ExtjsJsonResult.initErrorResult("没有找到这个类");
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ExtjsJsonResult.initErrorResult("模板文件出错");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ExtjsJsonResult.initErrorResult("读取模板文件出错");
		}
		String str=writer.toString();
		//str= str.replaceAll( "\r\n", " <br/> ");
		//str= str.replaceAll( "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		return str;
	}
	
	@RequestMapping("/codeGenerator/exportFile.do")
	@ResponseBody
	public void exportFile(String className, String type,HttpServletResponse response) throws IOException, ClassNotFoundException, TemplateException {
		String fileName=javaEntityMetaDataService.generatorFileName(className, ftlMapper.get(type));
		fileName=fileName.substring(0,fileName.lastIndexOf('.'))+".java";
		fileName = new String((fileName).getBytes("UTF-8"),"ISO8859_1");    
		response.setContentType("application/html;charset=utf-8");
		response.addHeader("Content-Disposition","attachment;filename="+fileName);  

		// 将数据输出到Servlet输出流中。
		//ServletOutputStream sos = response.getOutputStream();
		Writer writer=response.getWriter();
		javaEntityMetaDataService.generator(className,ftlMapper.get(type),writer);	
		writer.close();
		
	}

}
