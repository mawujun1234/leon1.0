package com.mawujun.role;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.WhereInfo;

@Controller
public class RoleRoleController {
	@Autowired
	private RoleRoleService roleRoleService;
	
	@RequestMapping("/roleRole/query")
	@ResponseBody
	public List<RoleRole> query(String currentId) {
		List<RoleRole> roles=null;

		WhereInfo whereinfo=WhereInfo.parse("current.id", currentId);
		roles=roleRoleService.query(whereinfo);


		return roles;
	}

	@RequestMapping("/roleRole/load")
	@ResponseBody
	public RoleRole load(String id){		
		return roleRoleService.get(id);
	}
	
	@RequestMapping("/roleRole/create")
	@ResponseBody
	public RoleRole create(@RequestBody RoleRole roleRole){		
		roleRole.setCreateDate(new Date());
		roleRoleService.create(roleRole);
		return roleRole;
	}
	
	@RequestMapping("/roleRole/update")
	@ResponseBody
	public RoleRole update(@RequestBody RoleRole roleRole){		
		 roleRoleService.update(roleRole);
		 return roleRole;
	}
	
	@RequestMapping("/roleRole/destroy")
	@ResponseBody
	public RoleRole destroy(@RequestBody RoleRole roleRole){		
		roleRoleService.delete(roleRole);
		return roleRole;
	}
}
