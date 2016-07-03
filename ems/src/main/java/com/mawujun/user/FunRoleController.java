package com.mawujun.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;

@Controller
public class FunRoleController {

	@Resource(name="funRoleService")
	FunRoleService funRoleService;
	
	@RequestMapping("/funRole/list.do")
	@ResponseBody
	public List<FunRole> list(String node) {
		if("root".equals(node)){
			node=null;
		}
		JsonConfigHolder.setRootName("children");
		return funRoleService.list(node);
	}

	@RequestMapping("/funRole/save.do")
	@ResponseBody
	public String save(FunRole node) {
		if("root".equals(node.getParentId())){
			node.setParentId(null);
		}
		node.setLeaf(true);
		funRoleService.save(node);
		return "success";
	}
	@RequestMapping("/funRole/update.do")
	@ResponseBody
	public String update(FunRole node) {
		funRoleService.update(node);
		return "success";
	}
	@RequestMapping("/funRole/delete.do")
	@ResponseBody
	public String delete(String id) {
		funRoleService.delete(id);
		return "success";
	}
	
	@RequestMapping("/funRole/listNav4Comm.do")
	@ResponseBody
	public List<Navigation> listNav4Comm(String node,String funRole_id) {
		if("root".equals(node)){
			node=null;
		}

		return funRoleService.listNav4Comm(node,funRole_id);
	}
	@RequestMapping("/funRole/listNav4Checked.do")
	@ResponseBody
	public List<Navigation> listNav4Checked(String node,String funRole_id) {
		if("root".equals(node)){
			node=null;
		}
		JsonConfigHolder.setRootName("children");
		return funRoleService.listNav4Checked(node,funRole_id);
	}
	
	@RequestMapping("/funRole/checkchange.do")
	@ResponseBody
	public String checkchange(String navigation_id,String funRole_id,Boolean checked) {
		if(checked){
			funRoleService.checkedNavigation(navigation_id, funRole_id);
		} else {
			funRoleService.unCheckedNavigation(navigation_id, funRole_id);
		}
		return "success";

	}
	/**
	 * 切换功能角色的时候用的
	 * @param funRole_id
	 * @return
	 */
	@RequestMapping("/funRole/selectAllCheckedNav.do")
	@ResponseBody
	public List<String> selectAllCheckedNav(String funRole_id) {
		return funRoleService.selectAllCheckedNav(funRole_id);
	}
}
