package com.mawujun.role;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.exception.BussinessException;
import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.idEntity.UUIDGenerator;
import com.mawujun.utils.page.WhereInfo;

@Service
public class RoleFunService extends BaseRepository<RoleFun, String> {
	@Autowired
	private FunService funService;
	@Autowired
	private RoleService roleService;
	
	public Set<RoleFun> query(String roleId){
		//List<Fun> funs=funService.queryAll();
		//h还要读出父类的权限
		Role role=roleService.get(roleId);
		Set<RoleFun> roleFuns=role.getFunes();
		
		for (RoleFun roleFun:roleFuns){
			System.out.println(roleFun.getId());
		}
		
		return roleFuns;
		
//		WhereInfo whereinfo=WhereInfo.parse("role.id", roleId);
//		List<RoleFun> selectedFuns=this.query(whereinfo);
//		return selectedFuns;
	}
	
	public void create(RoleFun roleFun) {
		//System.out.println(RoleCacheHolder.get(roleFun.getRole().getId()).getFunes().size()+"===============================前");
		roleFun.setCreateDate(new Date());
		super.create(roleFun);

		Role role=roleService.get(roleFun.getRole().getId());
		role.addFun(roleFun);
		
		//RoleCacheHolder.add(roleFun);
	}
	public RoleFun delete(String roleId,String funId) {
		Role role=roleService.get(roleId);
		RoleFun roleFun=role.getFun(funId);

		if(roleFun==null){
			throw new BussinessException("该功能是从父角色上继承过来，不能修改!");
		}
		super.delete(roleFun);
		role.removeFun(roleFun);
		return roleFun;
	}
	public RoleFun update(String roleId,String funId,String permissionEnum) {
		Role role=roleService.get(roleId);
		RoleFun roleFun=role.getFun(funId);
		if(roleFun==null){
			roleFun=new RoleFun();
			roleFun.setRole(new Role(roleId));
			roleFun.setFun(new Fun(funId));
			roleFun.setPermissionEnum(permissionEnum);
			this.create(roleFun);
		} else {
			roleFun.setPermissionEnum(permissionEnum);
			super.update(roleFun);
		}
		return roleFun;
		
	}
//	private List<RoleFunAssociation> recursionRoleFun( List<Fun> funs,List<RoleFunAssociation> selectedFuns,String roleId){
//		List<RoleFunAssociation> results=new ArrayList<RoleFunAssociation>();
////		for(Fun fun:funs){
////			RoleFunAssociation node=new RoleFunAssociation();
////			node.setId(fun.getId());
////			node.setText(fun.getText());
////			node.setFunId(fun.getId());
////			node.setRoleId(roleId);
////			if(fun.isLeaf()){
////				for(RoleFunAssociation roleFunAssociation:selectedFuns){
////					if(fun.getId().equals(roleFunAssociation.getFunId())){
////						node.setChecked(true);
////						node.setPermissionType(roleFunAssociation.getPermissionType());
////					}
////				}
////			} else if(fun.getChildren().size()>0){
////				List<RoleFunAssociation> children=recursionRoleFun(fun.getChildren(),selectedFuns,roleId);
////				node.setChildren(children);
////			}
////			results.add(node);
////		}
//		return results;
//	}

}