package com.mawujun.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FunRoleAction {

	@Resource(name="service.funRole")
	FunRoleServiceImpl funRoleServiceImpl;
	
	@RequestMapping("/funRole/list.do")
	@ResponseBody
	public List<FunRole> list(String node) {
		if("root".equals(node)){
			node=null;
		}

		return funRoleServiceImpl.list(node);
	}

	@RequestMapping("/funRole/save.do")
	@ResponseBody
	public String save(FunRole node) {
		if("root".equals(node.getParentId())){
			node.setParentId(null);
		}
		node.setLeaf(true);
		funRoleServiceImpl.save(node);
		return "success";
	}
	@RequestMapping("/funRole/update.do")
	@ResponseBody
	public String update(FunRole node) {
		funRoleServiceImpl.update(node);
		return "success";
	}
	@RequestMapping("/funRole/delete.do")
	@ResponseBody
	public String delete(String id) {
		funRoleServiceImpl.delete(id);
		return "success";
	}
	
	@RequestMapping("/funRole/listNav4Comm.do")
	@ResponseBody
	public List<NavNode> listNav4Comm(String node,String funRole_id) {
		if("root".equals(node)){
			node=null;
		}

		return funRoleServiceImpl.listNav4Comm(node,funRole_id);
	}
	@RequestMapping("/funRole/listNav4Checked.do")
	@ResponseBody
	public List<NavNode> listNav4Checked(String node,String funRole_id) {
		if("root".equals(node)){
			node=null;
		}

		return funRoleServiceImpl.listNav4Checked(node,funRole_id);
	}
	
	@RequestMapping("/funRole/checkchange.do")
	@ResponseBody
	public String checkchange(String navigation_id,String funRole_id,Boolean checked) {
		if(checked){
			funRoleServiceImpl.checkedNavigation(navigation_id, funRole_id);
		} else {
			funRoleServiceImpl.unCheckedNavigation(navigation_id, funRole_id);
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
		return funRoleServiceImpl.selectAllCheckedNav(funRole_id);
	}
}
