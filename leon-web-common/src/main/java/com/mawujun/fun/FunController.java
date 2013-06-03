package com.mawujun.fun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.WhereInfo;

/**
 * 
 * @author mawujun mawujun1234@163.com
 *
 */
@Controller
@Transactional
public class FunController {
	@Autowired
	private FunService funService;

	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/fun/query")
	@ResponseBody
	public ModelMap query(String id){
		WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
		List<Fun> funes=funService.query(whereinfo);
		//System.out.println("==================结果输出来了"+funes.size());
		ModelMap map=new ModelMap();
		map.put("root", funes);
		//map.put("filterPropertys", "checked");
		return map;
	}
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/fun/queryAll")
	@ResponseBody
	public List<Fun> queryAll(){		
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
	
	
}
