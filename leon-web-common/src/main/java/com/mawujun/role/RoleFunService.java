package com.mawujun.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.fun.FunService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.utils.page.WhereInfo;

@Service
public class RoleFunService extends BaseRepository<RoleFun, String> {
	@Autowired
	private FunService funService;
	public List<FunRoleVO> query(String roleId){
		//List<Fun> funs=funService.queryAll();
		//h还要读出父类的权限
		Role role=RoleCacheHolder.get(roleId);
		List<FunRoleVO> roleFuns=role.geetFunes();
		
		return roleFuns;
		
//		WhereInfo whereinfo=WhereInfo.parse("role.id", roleId);
//		List<RoleFun> selectedFuns=this.query(whereinfo);
//		return selectedFuns;
	}
	
	public void create(RoleFun roleFun) {
		RoleCacheHolder.add(roleFun);
		super.create(roleFun);
	}
	public void delete(String roleId,String funId) {
		Role role=RoleCacheHolder.get(roleId);
		RoleFun roleFun=role.removeFun(funId);
		super.delete(roleFun);
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
