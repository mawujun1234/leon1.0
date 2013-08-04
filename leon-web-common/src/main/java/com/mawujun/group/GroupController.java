package com.mawujun.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;

@Controller
public class GroupController {
	@Autowired
	private GroupService groupService;
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/group/query")
	@ResponseBody
	public List<Group> query(String id){		
		if("root".equals(id)){
			return groupService.query(Cnd.where().andIsNull("parent"));
		} else {
			return groupService.query(Cnd.where().andEquals("parent", id));
		}
		
	}

	@RequestMapping("/group/load")
	@ResponseBody
	public Group load(String id){		
		return groupService.get(id);
	}

	@RequestMapping("/group/create")
	@ResponseBody
	public Group create(@RequestBody Group group){		
		groupService.create(group);
		return group;
	}
	
	@RequestMapping("/group/update")
	@ResponseBody
	public Group update(@RequestBody Group group){		
		groupService.update(group);
		return group;
	}
	
	@RequestMapping("/group/destroy")
	@ResponseBody
	public Group destroy(@RequestBody Group group){		
		groupService.delete(group);
		return group;
	}
}
