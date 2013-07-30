package com.mawujun.icon;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.utils.FileUtils;

@Controller
public class IconController {

	File[] files;
	public File[] getFiles(String path){
		if(this.files!=null){
			return this.files;
		}
		//String path="E:\\eclipse\\workspace\\leon\\leon-web-common\\src\\main\\webapp\\icons";
		Collection<File> pngs=FileUtils.listFiles(new File(path), new String[]{"png","gif"}, true);
		
		this.files=pngs.toArray(new File[pngs.size()]);
		return files;
	}
	
	/**
	 * 
	 * 的时候
	 * @author mawujun 16064988@qq.com 
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("/icon/query")
	//@ResponseBody
	public Map<String,Object> query(HttpServletRequest request,int start,int limit){

		//String path=request.getServletContext().getContextPath();
		String basePath = request.getSession().getServletContext().getRealPath("/icons/");
		String contextPath=request.getContextPath();
		
		File[] files= getFiles(basePath);
		int size=(start+limit)>files.length?files.length:(start+limit);
		List<Map<String,String>> result=new ArrayList<Map<String,String>>();
		for(;start<size;start++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("name", files[start].getName());
			map.put("iconCls", IconUtils.getClsName(files[start].getName()));
			map.put("src", contextPath+"/icons/"+files[start].getName());
			result.add(map);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("root", result);
		map.put("total", files.length);
		
		
		JsonConfigHolder.setAutoWrap(false);
		return map;
	}
}
