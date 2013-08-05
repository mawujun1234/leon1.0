package com.mawujun.group;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.user.User;
import com.mawujun.user.UserRole;
import com.mawujun.user.UserRolePK;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

@Controller
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserGroupService userGroupService;
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
	
	@RequestMapping("/group/addUser")
	@ResponseBody
	public UserGroupPK addUser(UserGroupPK userGroupPK){	
		 UserGroup userGroup=new  UserGroup();
		 userGroup.setId(userGroupPK);
		 userGroupService.create(userGroup);
		//userService.delete(user);
		return userGroupPK;
	}
	@RequestMapping("/group/removeUser")
	@ResponseBody
	public UserGroupPK removeUser(UserGroupPK userGroupPK){		 
		userGroupService.delete(userGroupPK);
		//userService.delete(user);
		return userGroupPK;
	}
	
	@RequestMapping("/group/queryUser")
	@ResponseBody
	public QueryResult<User> queryUser(String groupId,String userName,Integer start,Integer limit){	
		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(true).setSqlId("queryUser")
				.addWhere("group_id",groupId).addWhere("name", "like", userName);
		QueryResult<User> users=userGroupService.queryPageMybatis(pageRqeust,User.class);
		//JsonConfigHolder.setRootName("children");
		//userService.delete(user);
		return users;
	}
}
