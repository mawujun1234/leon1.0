package com.mawujun.icon;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.utils.FileUtils;

@Controller
public class IconController {

//	File[] files;
	public File[] getFiles(String path,String name){

		Collection<File> pngs=null;
		if(StringUtils.hasText(name)){
			IOFileFilter fileFilter = new WildcardFileFilter("*"+name+"*.png");
			IOFileFilter fileFilter1 = new WildcardFileFilter("*"+name+"*.gif");
			
			pngs=FileUtils.listFiles(new File(path), FileFilterUtils.or(fileFilter,fileFilter1),FileFilterUtils.fileFileFilter());
			
		} else {
			pngs=FileUtils.listFiles(new File(path), new String[]{"png","gif"}, false);
		}

		//this.files=pngs.toArray(new File[pngs.size()]);
		return pngs.toArray(new File[pngs.size()]);
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
	@RequestMapping("/png/query")
	//@ResponseBody
	public Map<String,Object> queryPng(HttpServletRequest request,int start,int limit,String name){

		//String path=request.getServletContext().getContextPath();
		int sizepng=32;
		String basePath = request.getSession().getServletContext().getRealPath("/pngs/"+sizepng+"/");
		String contextPath=request.getContextPath();
		
		File[] files= getFiles(basePath,name);
		int size=(start+limit)>files.length?files.length:(start+limit);
		List<Map<String,String>> result=new ArrayList<Map<String,String>>();
		
		for(;start<size;start++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("name", files[start].getName());
			map.put("iconCls", IconUtils.getPngClsName(files[start].getName(),16));//用来在配置的时候设置到按钮上的 所以用16尺寸大小的
			map.put("src", contextPath+"/pngs/"+sizepng+"/"+files[start].getName());
			result.add(map);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("root", result);
		map.put("total", files.length);
		
		
		JsonConfigHolder.setAutoWrap(false);
		return map;
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
	public Map<String,Object> query(HttpServletRequest request,int start,int limit,String name){

		//String path=request.getServletContext().getContextPath();
		String basePath = request.getSession().getServletContext().getRealPath("/icons/");
		String contextPath=request.getContextPath();
		
		File[] files= getFiles(basePath,name);
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
