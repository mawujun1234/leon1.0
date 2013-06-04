package com.mawujun.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.WhereInfo;

@Controller
@Transactional
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/role/query")
	@ResponseBody
	public ModelMap query(String id) {
		List<Role> funes=null;
		if(!"root".equals(id)){
			WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
			funes=roleService.query(whereinfo);
		} else {
			funes=roleService.query();
		}
		
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
	@RequestMapping("/role/queryAll")
	@ResponseBody
	public List<Role> queryAll(){		
		return roleService.queryAll();
	}
	@RequestMapping("/role/load")
	@ResponseBody
	public Role load(String id){		
		return roleService.get(id);
	}
	
	@RequestMapping("/role/create")
	@ResponseBody
	public Role create(@RequestBody Role role){		
		roleService.create(role);
		return role;
	}
	
	@RequestMapping("/role/update")
	@ResponseBody
	public Role update(@RequestBody Role role,Boolean isUpdateParent,String oldParent_id){		
		 roleService.update(role,isUpdateParent,oldParent_id);
		 return role;
	}
	
	@RequestMapping("/role/destroy")
	@ResponseBody
	public Role destroy(@RequestBody Role role){		
		roleService.delete(role);
		return role;
	}

}
