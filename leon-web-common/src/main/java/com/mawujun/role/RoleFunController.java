package com.mawujun.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.user.login.FilterInvocationSecurityMetadataSourceImpl;
import com.mawujun.utils.M;

@Controller
public class RoleFunController {
	@Autowired
	private FunService funService;
	@Autowired
	private RoleFunService roleFunService;
	
//	@RequestMapping("/role/queryFun")
//	@ResponseBody
//	public List<roleFun> queryFun(String id){
////		WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
////		List<Fun> funes=funService.query(whereinfo);
////		for(Fun fun:funes){
////			fun.setChecked(true);
////		}
////		//System.out.println("==================结果输出来了"+funes.size());
////		ModelMap map=new ModelMap();
////		map.put("root", funes);
////		//map.put("filterPropertys", "checked");
////		return map;
//		
//		WhereInfo whereinfo=WhereInfo.parse("roleId", id);
//		List<roleFun> funes=roleFunService.query(whereinfo);
//		return funes;
//	}
	
	
	//@RequestMapping("/roleFun/query")
	//@ResponseBody
	public List<Map<String,Object>>  queryRoleFun(String roleId){
		Set<RoleFun> roleFunes=roleFunService.query(roleId);
		List<Map<String,Object>> funes=new ArrayList<Map<String,Object>>();
		
		for (RoleFun roleFun:roleFunes){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("funId", roleFun.getFun().getId());

//			map.put("permissionEnum", roleFun.getPermissionEnum().toString());
//			//来源还没有做
//			StringBuffer buffer=new StringBuffer("");
//			for(RoleSource roleSource:roleFun.getRoleSources()){
//				buffer.append(roleSource.getName());
//				buffer.append(",");
//			}
//			map.put("roleSources", buffer);
//			map.put("fromParent", roleFun.isFromParent());
			
			funes.add(map);
		}
		return funes;
	}
	
	@Resource(name="roleFunObservers")
	List<RoleFunObserver>  roleFunObservers;
	
	//@RequestMapping("/roleFun/create")
	//@ResponseBody
	public RoleFun create(String roleId,String funId,String permissionEnum){
		//继承的权限如何覆盖和取消。
		//父角色的权限不能取消，当修改权限属性的时候，如果发现时父权限，就新建一个权限
		RoleFun roleFun=new RoleFun();
		roleFun.setRole(new Role(roleId));
		roleFun.setFun(new Fun(funId));
		roleFun.setPermissionEnum(permissionEnum);
		
		roleFunService.create(roleFun);
		
		//filterInvocationSecurityMetadataSourceImpl.addConfigAttribute(roleFun.getFun().getUrl(), roleFun.getRole().getId());
		notifyObservers(roleFun.getRole(),roleFun.getFun(),true);
		JsonConfigHolder.setFilterPropertys(M.RoleFun.role.name(),M.RoleFun.fun.name());
		return roleFun;
	}
	
	//@RequestMapping("/roleFun/update")
	//@ResponseBody
	public RoleFun update(String roleId,String funId,String permissionEnum){
		
		RoleFun roleFun=roleFunService.update( roleId, funId, permissionEnum);
		JsonConfigHolder.setFilterPropertys(M.RoleFun.role.name(),M.RoleFun.fun.name());
		return roleFun;
	}
	
	//@RequestMapping("/roleFun/destroy")
	//@ResponseBody
	public RoleFun destroy(String roleId,String funId){
		RoleFun roleFun=roleFunService.delete(roleId,funId);
		notifyObservers(roleFun.getRole(),roleFun.getFun(),false);
		//filterInvocationSecurityMetadataSourceImpl.removeConfigAttribute(roleFun.getFun().getUrl(), roleId);
		
		JsonConfigHolder.setFilterPropertys(M.RoleFun.role.name(),M.RoleFun.fun.name());
		
		return roleFun;
	}
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param role
	 * @param fun
	 * @param createOrDestroy true 表示创建了，false表示是删除
	 */
	protected void notifyObservers(Role role,Fun fun,boolean createOrDestroy){
		if(roleFunObservers!=null){
			for(RoleFunObserver obser:roleFunObservers){
				if(createOrDestroy){
					obser.create(role, fun);
				} else {
					obser.destroy(role, fun);
				}
				
			}
		}
	}


}
