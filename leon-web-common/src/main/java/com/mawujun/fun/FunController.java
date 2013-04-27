package com.mawujun.fun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RequestMapping("/fun/queryChildren")
	@ResponseBody
	public ModelMap queryChildren(String node){		
//		try {
//			 funService.queryAll();
//		} catch (Exception e){
//			e.printStackTrace();
//		}
		WhereInfo whereinfo=WhereInfo.parse("parent.id", node);
		try {
			System.out.println("==================结开始");
		List<Fun> funes=funService.query(whereinfo);
		System.out.println("==================结果输出来了");
		ModelMap map=new ModelMap();
		map.put("filterPropertys", "children");//过滤属性的设置
		map.put("onlyIds", "parent");
		map.put("root", funes);
		return map;
		} catch (Exception e){
			System.out.println("==================异常");
			e.printStackTrace();
			return null;
		}
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
	@RequestMapping("/fun/get")
	@ResponseBody
	public Fun get(String id){		
		return funService.get(id);
	}
	
	@RequestMapping("/fun/create")
	@ResponseBody
	public void create(Fun fun){		
		funService.create(fun);
	}
	
	@RequestMapping("/fun/update")
	@ResponseBody
	public void update(Fun fun){		
		 funService.update(fun);
	}
	
	@RequestMapping("/fun/destory")
	@ResponseBody
	public void destory(Fun fun){		
		funService.delete(fun);
	}
	
	@RequestMapping("/fun/get/{id}")
	@ResponseBody
	public Fun getByReset(@PathVariable String id){		
		return funService.get(id);
	}
	
	@RequestMapping("/fun/destory/{id}")
	@ResponseBody
	public void destory(@PathVariable String id){		
		funService.delete(id);
	}
	
	
}
