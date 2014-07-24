package com.mawujun.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataRoleController {

	@Resource(name="dataRoleService")
	DataRoleService dataRoleService;
	
	@RequestMapping("/dataRole/list.do")
	@ResponseBody
	public List<DataRole> list(String node) {
		if("root".equals(node)){
			node=null;
		}

		return dataRoleService.list(node);
	}

	@RequestMapping("/dataRole/save.do")
	@ResponseBody
	public String save(DataRole node) {
		if("root".equals(node.getParentId())){
			node.setParentId(null);
		}
		node.setLeaf(true);
		dataRoleService.save(node);
		return "success";
	}
	@RequestMapping("/dataRole/update.do")
	@ResponseBody
	public String update(DataRole node) {
		dataRoleService.update(node);
		return "success";
	}
	@RequestMapping("/dataRole/delete.do")
	@ResponseBody
	public String delete(String id) {
		dataRoleService.delete(id);
		return "success";
	}
}
