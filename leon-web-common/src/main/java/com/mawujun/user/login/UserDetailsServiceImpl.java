package com.mawujun.user.login;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.user.User;
import com.mawujun.user.UserService;
import com.mawujun.utils.DefaultValues;
import com.mawujun.utils.P;
import com.mawujun.utils.ParameterHolder;

public class UserDetailsServiceImpl implements UserDetailsService {

	private String rolePrefix = "ROLE_";
	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user=userService.queryUnique(Cnd.select().andEquals("loginName", username));
		if(user==null || user.isDeleted()){
			throw new UsernameNotFoundException("用户不存在");
		}
		
		UserDetailsImpl aa=new UserDetailsImpl();
		aa.setUser(user);
		List<String> roleIds=userService.queryRoleId(user.getId());
		//roleIds.add("IS_AUTHENTICATED_FULLY");
		Set<String> rolesSet=new HashSet<String>();
		for(String roleId:roleIds){
			rolesSet.add(rolePrefix+roleId);
		}
		//rolesSet.addAll(roleIds);
		aa.setRoles(rolesSet);
		
		//ParameterHolder.getUserParameterValue(subjectId, parameterId)
		
		//设置用户默认的菜单
		String menuId=ParameterHolder.getUserParameterValue(user.getId(), P.menuId);
		if(menuId==null){
			menuId=DefaultValues.Menu_id;
		}
		aa.setMenuId(menuId);
		
		return aa;
	}
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getRolePrefix() {
		return rolePrefix;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

}
