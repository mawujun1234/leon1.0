package com.mawujun.group;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.role.Role;
import com.mawujun.user.User;
import com.mawujun.user.UserRole;
import com.mawujun.user.UserRolePK;
import com.mawujun.utils.M;
import com.mawujun.utils.ParamUtils;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

@Controller
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupUserService userGroupService;
	@Autowired
	private GroupRoleService groupRoleService;
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/group/query")
	@ResponseBody
	public List<Group> query(String id){		
		if("root".equals(id)){
			return groupService.query(Cnd.where().andIsNull(M.Group.parent.name()));
		} else {
			return groupService.query(Cnd.where().andEquals(M.Group.parent.name(), id));
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
	public GroupUserPK addUser(GroupUserPK userGroupPK){	
		 GroupUser userGroup=new  GroupUser();
		 userGroup.setId(userGroupPK);
		 userGroupService.create(userGroup);
		//userService.delete(user);
		return userGroupPK;
	}
	@RequestMapping("/group/removeUser")
	@ResponseBody
	public GroupUserPK removeUser(GroupUserPK userGroupPK){		 
		userGroupService.deleteById(userGroupPK);
		//userService.delete(user);
		return userGroupPK;
	}
	
	@RequestMapping("/group/queryUser")
	@ResponseBody
	public QueryResult<User> queryUser(String groupId,String userName,Integer start,Integer limit){
		
//		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(true).setSqlId("queryUser")
//				.addWhere("group_id",groupId).addLikeWhere("name",  userName);
//		QueryResult<User> users=userGroupService.queryPageMybatis(pageRqeust,User.class);
		
		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(true).setSqlId("queryUser")
				.addWhere("group_id",groupId).addLikeWhere("name",  userName);
		QueryResult<User> users=userGroupService.queryPage(pageRqeust);
		return users;
	}
	
	
	
	@RequestMapping("/group/addRole")
	@ResponseBody
	public GroupRolePK addRole(GroupRolePK groupRolePK){	
		GroupRole groupRole=new  GroupRole();
		groupRole.setId(groupRolePK);
		groupRoleService.create(groupRole);
		//userService.delete(user);
		return groupRolePK;
	}
	@RequestMapping("/group/removeRole")
	@ResponseBody
	public GroupRolePK removeRole(GroupRolePK groupRolePK){		 
		groupRoleService.deleteById(groupRolePK);
		return groupRolePK;
	}
	
	@RequestMapping("/group/queryRole")
	@ResponseBody
	public List<Role> queryRole(String groupId,Integer start,Integer limit){	
//		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(true).setSqlId("queryRole")
//				.addWhere("group_id",groupId);
//		QueryResult<Role> roles=userGroupService.queryPageMybatis(pageRqeust,Role.class);
		List<Role> roles=userGroupService.queryList("queryRole", ParamUtils.init().add("group_id", groupId), Role.class);
		JsonConfigHolder.setFilterPropertys("mutex,funes,category",Role.class);
		JsonConfigHolder.setRootName("children");
		return roles;
	}
}
