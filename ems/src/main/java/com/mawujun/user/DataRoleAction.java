package com.mawujun.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataRoleAction {

	@Resource(name="dataRoleServiceImpl")
	DataRoleServiceImpl dataRoleServiceImpl;
	
	@RequestMapping("/dataRole/list.do")
	@ResponseBody
	public List<DataRole> list(String node) {
		if("root".equals(node)){
			node=null;
		}

		return dataRoleServiceImpl.list(node);
	}

	@RequestMapping("/dataRole/save.do")
	@ResponseBody
	public String save(DataRole node) {
		if("root".equals(node.getParentId())){
			node.setParentId(null);
		}
		node.setLeaf(true);
		dataRoleServiceImpl.save(node);
		return "success";
	}
	@RequestMapping("/dataRole/update.do")
	@ResponseBody
	public String update(DataRole node) {
		dataRoleServiceImpl.update(node);
		return "success";
	}
	@RequestMapping("/dataRole/delete.do")
	@ResponseBody
	public String delete(String id) {
		dataRoleServiceImpl.delete(id);
		return "success";
	}
}
