package com.mawujun.role;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public List<RoleFun> queryRoleFun(String roleId){
		List<RoleFun> funes=roleFunService.query(roleId);
		return funes;
	}
	
	@RequestMapping("/roleFun/create")
	@ResponseBody
	public RoleFun create(RoleFun roleFun){
		roleFun.setCreateDate(new Date());
		roleFunService.create(roleFun);
		return roleFun;
	}
	
	@RequestMapping("/roleFun/update")
	@ResponseBody
	public RoleFun update(RoleFun roleFun){
		roleFunService.update(roleFun);
		return roleFun;
	}
	
	@RequestMapping("/roleFun/destroy")
	@ResponseBody
	public RoleFun destroy(RoleFun roleFun){
		roleFunService.delete(roleFun);
		return roleFun;
	}


}
