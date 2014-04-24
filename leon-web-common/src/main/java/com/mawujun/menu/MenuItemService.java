package com.mawujun.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.fun.FunService;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.T;
import com.mawujun.utils.help.ReportCodeHelper;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MenuItemService extends AbstractService<MenuItem, String> {//extends BaseRepository<MenuItem, String> {
	@Autowired
	private FunService funService;
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Override
	public MenuItemRepository getRepository() {
		// TODO Auto-generated method stub
		return menuItemRepository;
	}

	
	public String create(MenuItem entity) {
		updateReportCode(entity);
		entity.setParent(entity.getParent()==null?null:this.get(entity.getParent().getId()));
		entity.setLeaf(true);
		//MenuItem parnet=this.get(entity.getParent().getId());
		if(entity.getParent()!=null){
			entity.getParent().setLeaf(false);
		}
		
		return this.getRepository().create(entity);	
	}
	
	private void updateReportCode(MenuItem entity) {
		// 获取所有子节点中的最大值
		Object reportCode = null;
		if (entity.getParent() != null) {
			// WhereInfo whereinfo=WhereInfo.parse("parent.id",
			// entity.getParent().getId());
			reportCode = this.getRepository().queryMax(
					M.MenuItem.reportCode,
					Cnd.where().andEquals(M.MenuItem.parent.id,
							entity.getParent().getId()));

			// 获取父节点的reportcode
			if (reportCode == null) {
				MenuItem parent = this.getRepository().get(
						entity.getParent().getId());
				reportCode = parent + ReportCodeHelper.getSperator()
						+ ReportCodeHelper.generate3(null);
				entity.setReportCode((String) reportCode);
			} else {
				String newReportCode = ReportCodeHelper
						.generate3((String) reportCode);
				entity.setReportCode(newReportCode);
			}
		}
	}
	public void update(MenuItem entity) {
		MenuItem exists=this.get(entity.getId());
		BeanUtils.copyProperties(entity, exists, new String[]{M.MenuItem.parent.name(), M.MenuItem.menu.name(),M.MenuItem.fun.name()});
		if(entity.getFun()!=null){
			exists.setFun(funService.get(entity.getFun().getId()));
		}
		entity=exists;
		super.update(exists);
		
	}
	public void delete(MenuItem entity) {
		MenuItem item=this.get(entity.getId());

		super.delete(item);
	}
//	public MenuItem create(String funId,String parentId,String menuId) {
//		//throw new BusinessException("c测试");
//		Fun fun=funService.get(funId);
//		MenuItem parent=this.get(parentId);
//		
//		MenuItem menuitem = new MenuItem();
//		menuitem.setText(fun.getText());
//		//menuitem.setReportCode(fun.getReportCode());
//		menuitem.setFun(fun);
//		menuitem.setParent(parent);
//		menuitem.setMenu(parent==null?menuService.get(menuId):parent.getMenu());
//		//menuitem.setIconCls(fun.getIconCls());
//		menuitem.setLeaf(true);
//		super.create(menuitem);
//		
//		if(parent!=null){
//			parent.setLeaf(false);
//			super.update(parent);
//		}
//		
//
////		if(parent!=null){
////			parent.addChild(menuitem);
////		}
//		
//		return menuitem;
//	}
	
	public MenuItem cut(String id,String parent_id,String oldParent_id,String menuId) {
		MenuItem parent=this.get(parent_id);
		parent.setLeaf(false);
		MenuItem menuItem=this.get(id);
		
		menuItem.setParent(parent);
		
		updateReportCode(menuItem);
		
		this.getRepository().update(menuItem);
		return menuItem;
	}
	
	public List<MenuItemVO> query4Desktop(String menuId,Boolean isAdmin,String parentId,String userId) {

		//如果是管理员，可以获查看到所有的菜单
		//List<String> menuItemLeaf = super.queryList("query4Desktop", ParamUtils.init().add("menu_id", menuId).add("isAdmin", isAdmin),String.class);
		List<String> menuItemLeaf =this.getRepository().query4Desktop(Params.init().add(T.leon_menuItem.menu_id, menuId).add("isAdmin", isAdmin).
				addIf(T.leon_menuItem.parent_id, parentId).add("user_id", userId));//f 用户权限根据用户名获取相应的功能

		Map<String,MenuItemVO> parentKeys=new HashMap<String,MenuItemVO>();
		List<MenuItemVO> menuItems = new ArrayList<MenuItemVO>();
		for (Object menuItemIdObj: menuItemLeaf) {
			String menuItemId=(String)menuItemIdObj;

			MenuItem leaf=this.get(menuItemId);
			
			if(parentKeys.get(menuItemId) !=null){//表示这个功能能已经添加过了
				continue;
			}

			MenuItemVO vo=BeanUtils.copyOrCast(leaf, MenuItemVO.class);
			vo.setFunId(leaf.getFun()!=null?leaf.getFun().getId():null);

			//对菜单进行二次开发==============
			extenMenuItem(leaf,vo);
			//===========================
			menuItems.add(vo);
			
		}
		return menuItems;
	}
	
	private void extenMenuItem(MenuItem leaf,MenuItemVO vo){
		if(StringUtils.hasText(leaf.getJavaClass())){
			try {
				Class clazz=Class.forName(leaf.getJavaClass());
				MenuVOExten menuVOExten=(MenuVOExten)clazz.newInstance();
				UserDetailsImpl impl=(UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				menuVOExten.execute(vo, jdbcTemplate, impl.getUser());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void initCache(){
		List<MenuItem> menuItems=super.queryAll();
		for(MenuItem menuItem:menuItems){
			//if(fun.getFunEnum()==FunEnum.fun){
			//super.initLazyProperty(menuItem.getMenu());
			//super.initLazyProperty(menuItem.getParent());
			
			Hibernate.initialize(menuItem.getMenu());
			Hibernate.initialize(menuItem.getParent());
		}
	}
	/**
	 * 如果没有就返回null
	 * @author mawujun 16064988@qq.com 
	 * @param jspUrl
	 * @param menuId
	 * @return
	 */
	public MenuItemVO queryMenuItem(String jspUrl,String menuId){	
		Map params=Params.init().add("jspUrl", jspUrl).add("menuId", menuId);
		//List<MenuItemVO> menuItems=super.queryList("queryMenuItem", params, MenuItemVO.class);
		List<MenuItemVO> menuItems=this.getRepository().queryMenuItem(params);
		if(menuItems==null || menuItems.size()==0){
			return null;
		} else {
			return menuItems.get(0);
		}
	}


}
