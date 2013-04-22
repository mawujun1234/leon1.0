package com.mawujun.fun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public List<Fun> query(String id,String node){		
		
		return funService.queryAll();
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
