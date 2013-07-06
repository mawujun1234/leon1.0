package com.mawujun.fun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.ToJsonConfigHolder;
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
			whereinfo=WhereInfo.parse("parent.id_isNull", id);
		}
		List<Fun> funes=funService.query(whereinfo);
//		//System.out.println("==================结果输出来了"+funes.size());
//		ModelMap map=new ModelMap();
//		map.put("root", funes);
//		//map.put("filterPropertys", "checked");
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
		
		ToJsonConfigHolder.setFilterPropertys("parent");
		ToJsonConfigHolder.setRootName("children");
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
//		把bussinesType的字符串绑定到Fun的bussniessType的时候报错，是否在Fun中就直接使用bussinessType的字符串作为常数的类型，
//		然后要获取常数项名称的话，再建立一个方法，并且从缓存中获取
//		
//		在extjs中新建一个hasEmbedded的关联类，看看能不能读取到数据，这个方法可能不行
//		
//		自定义字段，这个可能更好
		
		
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
