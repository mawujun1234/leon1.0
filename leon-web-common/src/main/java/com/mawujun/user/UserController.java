package com.mawujun.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.fun.Fun;
import com.mawujun.fun.FunVO;
import com.mawujun.utils.page.MatchMode;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

/**
 * 
 * @author mawujun mawujun1234@163.com
 *
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	/**
	 * 这是基于分页的几种写法
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/user/query")
	@ResponseBody
	public QueryResult<User> query(Integer start,Integer limit,String userName){

		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(false).addLikeWhere("name", userName,MatchMode.ANYWHERE,true);
		QueryResult<User> users=userService.queryPage(pageRqeust);
		//JsonConfigHolder.setRootName("children");
		return users;
	}
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/user/queryAll")
	@ResponseBody
	public List<User> queryAll(){		
		return userService.queryAll();
	}
	@RequestMapping("/user/load")
	@ResponseBody
	public User load(String id){		
		return userService.get(id);
	}
	
	@RequestMapping("/user/create")
	@ResponseBody
	public User create(@RequestBody User user){		
		user.setCreateDate(new Date());
		if(passwordEncoder!=null){
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		userService.create(user);
		return user;
	}
	
	@RequestMapping("/user/update")
	@ResponseBody
	public User update(@RequestBody User user){		
		 userService.update(user);
		 return user;
	}
	
	@RequestMapping("/user/destroy")
	@ResponseBody
	public User destroy(@RequestBody User user,Boolean physicsDel){	
		if(user.isAdmin()){
			throw new BusinessException("管理员账号不能删除!");
		}
		if(physicsDel!=null && physicsDel){
			userService.delete(user);
			return user;
		}
		user.setDeleted(true);
		user.setDeletedDate(new Date());
		userService.update(user);
		//userService.delete(user);
		return user;
	}
	
	@RequestMapping("/user/recover")
	@ResponseBody
	public String recover(String id){		
		 userService.recover(id);
		 return id;
		 //return "{success:true}";
	}
	@RequestMapping("/user/resetPwd")
	@ResponseBody
	public String resetPwd(String id,String password){
		if(!StringUtils.hasText(password)){
			throw new BusinessException("密码不能为空");
		}
		 userService.resetPwd(id,passwordEncoder.encode(password));
		 return id;
		 //return "{success:true}";
	}
	
	@RequestMapping("/user/queryRole")
	@ResponseBody
	public List<Map<String,Object>> queryRole(String userId){	
		List<Map<String,Object>> roles=userRoleService.queryRole(userId);
		JsonConfigHolder.setRootName("children");
		//userService.delete(user);
		return roles;
	}
	@RequestMapping("/user/addRole")
	@ResponseBody
	public UserRole addRole(UserRolePK userRolePK){	
		 UserRole userRole=new  UserRole();
		 userRole.setId(userRolePK);
		userRoleService.create(userRole);
		//userService.delete(user);
		return userRole;
	}
	@RequestMapping("/user/removeRole")
	@ResponseBody
	public UserRolePK removeRole(UserRolePK userRolePK){		 
		userRoleService.deleteById(userRolePK);
		//userService.delete(user);
		return userRolePK;
	}
	
	@RequestMapping("/user/queryFun")
	@ResponseBody
	public List<FunVO> queryFun(String userId){	
		JsonConfigHolder.setRootName("children");
		return userRoleService.queryFun(userId); 
	}

}
