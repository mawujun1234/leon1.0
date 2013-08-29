package com.mawujun.fun;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.utils.FileUtils;
import com.mawujun.utils.page.WhereInfo;

/**
 * 
 * @author mawujun mawujun1234@163.com
 *
 */
@Controller
//@Transactional
public class FunController {
	@Autowired
	private FunService funService;

	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/fun/query")
	@ResponseBody
	public List<Fun> query(String id){
		WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
		if("root".equals(id)){
			whereinfo=WhereInfo.parse("parent.id", "is",null);
		}
		List<Fun> funes=funService.query(whereinfo);
		return funes;
	}
	/**
	 * 一次性读取出所有的节点数据,构建出了整棵树
	 * @return
	 */
	@RequestMapping("/fun/queryAll")
	@ResponseBody
	public List<Fun> queryAll(){	
	
//		ModelMap resultMap=new ModelMap();
//		resultMap.put("children", funService.queryAll());
//		resultMap.put(ResultMap.filterPropertysName, "parent");
//		return resultMap;
		
		JsonConfigHolder.setFilterPropertys("parent");
		JsonConfigHolder.setRootName("children");
		return funService.queryAll();
		
	}
	@RequestMapping("/fun/load")
	@ResponseBody
	public Fun load(String id){		
		return funService.get(id);
	}
	
	@RequestMapping("/fun/create")
	@ResponseBody
	public Fun create(@RequestBody Fun fun){				
		funService.create(fun);
		return fun;
	}
	
	@RequestMapping("/fun/update")
	@ResponseBody
	public Fun update(@RequestBody Fun fun,Boolean isUpdateParent,String oldParent_id){		
		 funService.update(fun,isUpdateParent,oldParent_id);
		 return fun;
	}
	
	@RequestMapping("/fun/destroy")
	@ResponseBody
	public Fun destroy(@RequestBody Fun fun){		
		funService.delete(fun);
		return fun;
	}
	
	@RequestMapping("/fun/load/{id}")
	@ResponseBody
	public Fun loadByReset(@PathVariable String id){		
		return funService.get(id);
	}
	
	@RequestMapping("/fun/destory/{id}")
	@ResponseBody
	public void destory(@PathVariable String id){		
		funService.delete(id);
	}
	
	public String getHelpFilePath(HttpServletRequest request,String funId){
		String path=request.getSession().getServletContext().getRealPath("/")+"doc"+FileUtils.FILE_SEPARATOR+funId;
		String filePath=path+FileUtils.FILE_SEPARATOR+funId+".html";
		return filePath;
	}
	
	@RequestMapping("/fun/helpCreateOrupdate")
	@ResponseBody
	public void helpCreateOrupdate(HttpServletRequest request,String funId,String editorValue) throws IOException {				
		//保存为html，以funId为文件夹名称，html文件也是以这个id为名称，如果有图片也上传到这个文件夹下面，
		//展示的时候，新建一个函数，动态的网<script id="editor">标签里面添加内容，也就是util的内容
		//而用户查看的时候，是用一个html包围这些内容的
		String path=request.getSession().getServletContext().getRealPath("/")+"doc"+FileUtils.FILE_SEPARATOR+funId;

		FileUtils.createDir(path);
		
		String filePath=path+FileUtils.FILE_SEPARATOR+funId+".html";
		System.out.println(filePath);
		File file=new File(filePath);
		if(FileUtils.isExists(filePath)){
			FileUtils.deleteFile(filePath);
		}
		
		FileUtils.writeAsString(file, editorValue);
	}
	
	@RequestMapping("/fun/helpGet")
	@ResponseBody
	public String helpGet(HttpServletRequest request,String funId) throws IOException {	
		String filePath=getHelpFilePath(request,funId);
		return FileUtils.readAsString(new File(filePath));
	}
}
