package com.mawujun.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.utils.page.WhereInfo;

@Service
public class RoleFunAssociationService extends BaseRepository<RoleFunAssociation, String> {
	@Autowired
	private FunService funService;
	public List<RoleFunAssociation> query(String roleId){
		//List<Fun> funs=funService.queryAll();
		
		WhereInfo whereinfo=WhereInfo.parse("roleId", roleId);
		List<RoleFunAssociation> selectedFuns=this.query(whereinfo);
		return selectedFuns;
//		List<RoleFunAssociation>  result=recursionRoleFun(funs,selectedFuns,roleId);
//		return result;
	}
	private List<RoleFunAssociation> recursionRoleFun( List<Fun> funs,List<RoleFunAssociation> selectedFuns,String roleId){
		List<RoleFunAssociation> results=new ArrayList<RoleFunAssociation>();
//		for(Fun fun:funs){
//			RoleFunAssociation node=new RoleFunAssociation();
//			node.setId(fun.getId());
//			node.setText(fun.getText());
//			node.setFunId(fun.getId());
//			node.setRoleId(roleId);
//			if(fun.isLeaf()){
//				for(RoleFunAssociation roleFunAssociation:selectedFuns){
//					if(fun.getId().equals(roleFunAssociation.getFunId())){
//						node.setChecked(true);
//						node.setPermissionType(roleFunAssociation.getPermissionType());
//					}
//				}
//			} else if(fun.getChildren().size()>0){
//				List<RoleFunAssociation> children=recursionRoleFun(fun.getChildren(),selectedFuns,roleId);
//				node.setChildren(children);
//			}
//			results.add(node);
//		}
		return results;
	}

}
