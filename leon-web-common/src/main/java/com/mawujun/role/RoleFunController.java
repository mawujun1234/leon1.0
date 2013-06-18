package com.mawujun.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;

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
	
	
	@RequestMapping("/roleFun/query")
	@ResponseBody
	public List<Map<String,Object>>  queryRoleFun(String roleId){
		List<RoleFun> roleFunes=roleFunService.query(roleId);
		List<Map<String,Object>> funes=new ArrayList<Map<String,Object>>();
		for (RoleFun roleFun:roleFunes){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("funId", roleFun.getFun().getId());
			System.out.println(map.get("funId"));
			map.put("permissionEnum", roleFun.getPermissionEnum().toString());
			funes.add(map);
		}
		return funes;
	}
	
	@RequestMapping("/roleFun/create")
	@ResponseBody
	public RoleFun create(String roleId,String funId,String permissionEnum){
		RoleFun roleFun=new RoleFun();
		roleFun.setRole(new Role(roleId));
		roleFun.setFun(new Fun(funId));
		roleFun.setPermissionEnum(permissionEnum);
		roleFun.setCreateDate(new Date());
		roleFunService.create(roleFun);
		return roleFun;
	}
	
//	@RequestMapping("/roleFun/update")
//	@ResponseBody
//	public RoleFun update(String roleId,String funId,String permissionEnum){
//		RoleFun roleFun=new RoleFun();
//		roleFun.setRole(new Role(roleId));
//		roleFun.setFun(new Fun(funId));
//		roleFun.setPermissionEnum(permissionEnum);
//		roleFunService.update(roleFun);
//		return roleFun;
//	}
	
	@RequestMapping("/roleFun/destroy")
	@ResponseBody
	public RoleFun destroy(RoleFun roleFun){
		roleFunService.delete(roleFun);
		return roleFun;
	}


}
