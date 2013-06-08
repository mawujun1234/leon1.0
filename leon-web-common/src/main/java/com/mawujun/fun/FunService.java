package com.mawujun.fun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.exception.BussinessException;
import com.mawujun.exception.WebCommonExceptionCode3;
import com.mawujun.menu.Menu;
import com.mawujun.menu.MenuItem;
import com.mawujun.menu.MenuItemRepository;
import com.mawujun.menu.MenuItemService;
import com.mawujun.menu.MenuService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;
import com.mawujun.utils.help.ReportCodeHelper;
import com.mawujun.utils.page.WhereInfo;

@Service
public class FunService extends BaseRepository<Fun, String> {
//	@Autowired
//	private FunRepository funRepository;
	@Autowired
	private MenuItemRepository menuItemRepository;
	
//	@Override
//	public BaseRepository<Fun, String> getRepository() {
//		// TODO Auto-generated method stub
//		return funRepository;
//	}

	@Override
	public void delete(Fun entity) {
		//判断是否具有子节点
		WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getId());
		int childs=this.queryCount(whereinfo);
		if(childs>0){
			throw new BussinessException("存在子节点，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		
		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getId());
		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
		List<MenuItem> menuItems= menuItemRepository.query(whereinfoItem,whereinfoItem1);
		if(menuItems!=null && menuItems.size()>1){
			StringBuilder builder=new StringBuilder();
			for(MenuItem menuItem:menuItems){
				builder.append(menuItem.getMenu().getText()+":"+menuItem.getText()+";");
			}
			throw new BussinessException("有菜单挂钩，不能删除。<br/>"+builder);
		} else if(menuItems.size()==1){
			menuItemRepository.delete(menuItems.get(0));
		}
		//MenuItem menuItem=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
		//menuItemRepository.deleteBatch(whereinfoItem,whereinfoItem1);
		
		
		super.delete(entity);
		
		
	}
	
	public String getMaxReportCode(String parent_id){
		//获取父节点的reportcode
		WhereInfo whereinfo=WhereInfo.parse("parent.id", parent_id);
		Object reportCode=this.queryMax("reportCode",whereinfo);
		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		return newReportCode;
	}
	public void create(Fun entity) {
		Fun parent=this.get(entity.getParent().getId());
		if(parent.getFunEnum()==FunEnum.fun){
			throw new BussinessException("功能不能增加下级节点");
		}
//		//获取父节点的reportcode
//		WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getParent().getId());
//		Object reportCode=funRepository.queryMax("reportCode",whereinfo);
//		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		entity.setReportCode(getMaxReportCode(entity.getParent().getId()));
		
		this.create(entity);
		
		//获取对应的父菜单,获取第一个匹配的菜单
		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getParent().getId());
		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
		MenuItem menuparent=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
		
		MenuItem menuitem=new MenuItem();
		menuitem.setText(entity.getText());
		menuitem.setReportCode(entity.getReportCode());
		menuitem.setFun(entity);
		menuitem.setParent(menuparent);
		menuitem.setMenu(new Menu(Menu.default_id));
		menuItemRepository.create(menuitem);
	}
	
	public void update(Fun entity,Boolean isUpdateParent,String oldParent_id) {	
		if(isUpdateParent!=null && isUpdateParent==true){
			entity.setReportCode(getMaxReportCode(entity.getParent().getId()));
			super.update(entity);
			
			 //首先获取在菜单树种，原来所挂的父菜单项
			WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getParent().getId());
			WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
			MenuItem parent_menuItem=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
			
			WhereInfo whereinfoItem11=WhereInfo.parse("fun.id", entity.getId());
			WhereInfo whereinfoItem111=WhereInfo.parse("menu.id", Menu.default_id);
			MenuItem menuItem=menuItemRepository.queryUnique(whereinfoItem11,whereinfoItem111);
			menuItem.setParent(parent_menuItem);
			menuItemRepository.update(menuItem);
			
		} else {
			super.update(entity);
			
			WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getId());
			WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
			MenuItem menuItem=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
			menuItem.setText(entity.getText());
			menuItem.setReportCode(entity.getReportCode());
			menuItemRepository.update(menuItem);
		}
		
		
	}
	
	/**
	 * 构建出整颗树
	 */
	public List<Fun> queryAll() {
		WhereInfo whereinfo=WhereInfo.parse("parent.id", "root");
		List<Fun> funes=this.query(whereinfo);
		recursionFun(funes);
		return funes;
	}
	private void recursionFun( List<Fun> funes){
		for(Fun fun:funes){
			//fun.setExpanded(true);
			this.initLazyProperty(fun.getChildren());
			if(fun.getChildren().size()>0){
				recursionFun(fun.getChildren());
			}
		}
		
	}

}
