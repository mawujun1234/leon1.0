package com.mawujun.fun;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.cache.FunCacheHolder;
import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.exception.BussinessException;
import com.mawujun.exception.WebCommonExceptionCode3;
import com.mawujun.menu.Menu;
import com.mawujun.menu.MenuItem;
import com.mawujun.menu.MenuItemRepository;
import com.mawujun.menu.MenuItemService;
import com.mawujun.menu.MenuService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
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
	public void delete(Fun entity) {
		//判断是否具有子节点
		//WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getId());
		//int childs=this.queryCount(whereinfo);
		Fun fun=this.get(entity.getId());//Fun缓存了
		int childs=fun.getChildren().size();
		if(childs>0){
			throw new BussinessException("存在子节点，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		

		//menuItemService.delete(fun);
		int menuItemCount=menuItemService.queryCount(Cnd.where().andEquals("fun.id", entity.getId()));
		if(menuItemCount>0){
			throw new BussinessException("有菜单挂钩，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		
		super.delete(fun);
		
		
	}
	
	public String getMaxReportCode(String parent_id){
		//获取父节点的reportcode
		WhereInfo whereinfo=WhereInfo.parse("parent.id", parent_id);
		Object reportCode=this.queryMax("reportCode",whereinfo);
		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		return newReportCode;
	}
	public void create(Fun entity) {
		Fun parent=entity.getParent()==null?null:this.get(entity.getParent().getId());
		if(parent!=null && parent.getFunEnum()==FunEnum.fun){
			throw new BussinessException("功能不能增加下级节点");
		}
		entity.setParent(parent);

		entity.setReportCode(getMaxReportCode(entity.getParent()==null?null:entity.getParent().getId()));
		
		//menuItemService.create(entity);
		
		super.create(entity);

		parent.addChild(entity);
	}
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param entity
	 * @param isUpdateParent 表示更改父节点 暂时注释掉了
	 * @param oldParent_id
	 */
	public void update(Fun entity,Boolean isUpdateParent,String oldParent_id) {	

//		if(isUpdateParent!=null && isUpdateParent==true){
//			entity.setReportCode(getMaxReportCode(entity.getParent().getId()));
//			super.update(entity);
//			
//			 //首先获取在菜单树种，原来所挂的父菜单项
//			WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getParent().getId());
//			WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
//			MenuItem parent_menuItem=menuItemService.queryUnique(whereinfoItem,whereinfoItem1);
//			
//			WhereInfo whereinfoItem11=WhereInfo.parse("fun.id", entity.getId());
//			WhereInfo whereinfoItem111=WhereInfo.parse("menu.id", Menu.default_id);
//			MenuItem menuItem=menuItemService.queryUnique(whereinfoItem11,whereinfoItem111);
//			menuItem.setParent(parent_menuItem);
//			menuItemService.update(menuItem);
//			
//		} else {
			super.update(entity);
			
			WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getId());
			WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
			MenuItem menuItem=menuItemService.queryUnique(whereinfoItem,whereinfoItem1);
			menuItem.setText(entity.getText());
			menuItem.setReportCode(entity.getReportCode());
			//menuItemService.update(menuItem);
//		}
		
		
	}
	
	/**
	 * 构建出整颗树
	 */
	public List<Fun> queryAll() {
		WhereInfo whereinfo=WhereInfo.parse("parent.id_isnull", null);
		List<Fun> funes=this.query(whereinfo);
		recursionFun(funes);
		return funes;
	}
	private void recursionFun( List<Fun> funes){
		for(Fun fun:funes){
			if(fun.isFun()){
				fun.setChecked(false);
			}
			//fun.setExpanded(true);
			this.initLazyProperty(fun.getChildren());
			if(fun.getChildren().size()>0){
				recursionFun(fun.getChildren());
			}
		}
		
	}

	public void initCache(){
		List<Fun> funes=super.queryAll();//super.query(whereinfo);
		for(Fun fun:funes){
			//if(fun.getFunEnum()==FunEnum.fun){
				super.initLazyProperty(fun.getParent());
				super.initLazyProperty(fun.getChildren());
			//}	
		}
	}
}
