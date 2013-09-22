package com.mawujun.fun;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawjun.utils.FunCacheHolder;
import com.mawjun.utils.RoleCacheHolder;
import com.mawujun.exception.BussinessException;
import com.mawujun.exception.WebCommonExceptionCode3;
import com.mawujun.menu.Menu;
import com.mawujun.menu.MenuItem;
import com.mawujun.menu.MenuItemRepository;
import com.mawujun.menu.MenuItemService;
import com.mawujun.menu.MenuService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.mybatis.MybatisParamUtils;
import com.mawujun.role.Role;
import com.mawujun.role.RoleEnum;
import com.mawujun.service.BaseService;
import com.mawujun.utils.help.ReportCodeHelper;
import com.mawujun.utils.page.WhereInfo;

@Service
public class FunService extends BaseRepository<Fun, String> {
//	@Autowired
//	private FunRepository funRepository;
	@Autowired
	private MenuItemService menuItemService;
	
//	@Override
//	public BaseRepository<Fun, String> getRepository() {
//		// TODO Auto-generated method stub
//		return funRepository;
//	}

	@Override
	public Fun delete(Fun entity) {
		//判断是否具有子节点
		//WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getId());
		//int childs=this.queryCount(whereinfo);
		Fun fun=this.get(entity.getId());//Fun缓存了
		//int childs=fun.getChildren().size();
		int childs=super.queryCount(Cnd.select().andEquals("parent.id", fun.getId()));
		if(childs>0){
			throw new BussinessException("存在子节点，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		

		//menuItemService.delete(fun);
		int menuItemCount=menuItemService.queryCount(Cnd.select().andEquals("fun.id", entity.getId()));
		if(menuItemCount>0){
			throw new BussinessException("有菜单挂钩，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		
		return super.delete(fun);
		
		
	}
	
	public String getMaxReportCode(String parent_id){
		//获取父节点的reportcode
		WhereInfo whereinfo=WhereInfo.parse("parent.id", parent_id);
		Object reportCode=this.queryMax("reportCode",whereinfo);
		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		return newReportCode;
	}
	public Fun create(Fun entity) {
		Fun parent=entity.getParent()==null?null:this.get(entity.getParent().getId());
		if(parent!=null && parent.getFunEnum()==FunEnum.fun && !entity.isFun()){
			throw new BussinessException("功能不能增加模块节点");
		}
		entity.setParent(parent);
		if(parent!=null){
			parent.setLeaf(false);
			super.update(parent);
		}
		
		entity=super.create(entity);

		return entity;
	}
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param entity
	 * @param isUpdateParent 表示更改父节点 暂时注释掉了
	 * @param oldParent_id
	 */
	public void update(Fun entity,Boolean isUpdateParent,String oldParent_id) {	
		super.update(entity);
	}
	/**
	 * 获取所有界面元素要用的功能id
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param entity
	 * @param isUpdateParent
	 * @param oldParent_id
	 */
	public List<String> queryAllDenyPageElement(String userId,String funId) {	
		return super.queryList("queryAllDenyPageElement", MybatisParamUtils.init().add("user_id", userId).add("parent_id", funId).add("isenable", true), String.class);
//		List<String> list=new ArrayList();
//		//根据权限从数据库中获取，包括角色，组，职位，组织单元，最最终还是获取用户所属的角色所拥有的功能
//		list.add("generator-2c908385412fd0e701412fd93e1d0001");
//		return list;
	}


	public void initCache(){
		List<Fun> funes=super.queryAll();//super.query(whereinfo);
		for(Fun fun:funes){
			//if(fun.getFunEnum()==FunEnum.fun){
				super.initLazyProperty(fun.getParent());
				//super.initLazyProperty(fun.getChildren());
			//}	
		}
	}
}
