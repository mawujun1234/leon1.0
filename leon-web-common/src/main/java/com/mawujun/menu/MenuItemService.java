package com.mawujun.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.mybatis.ParamUtils;
import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.help.ReportCodeHelper;
import com.mawujun.utils.page.WhereInfo;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MenuItemService extends BaseRepository<MenuItem, String> {
	@Autowired
	private FunService funService;
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public MenuItem create(MenuItem entity) {
		Object reportCode=null;
		if(entity.getParent()!=null){
			WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getParent().getId());
			reportCode=this.queryMax("reportCode",whereinfo);
		}
		//获取父节点的reportcode
		
		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		entity.setReportCode(newReportCode);
		
		entity.setParent(entity.getParent()==null?null:this.get(entity.getParent().getId()));
		entity.setLeaf(true);
		//MenuItem parnet=this.get(entity.getParent().getId());
		if(entity.getParent()!=null){
			entity.getParent().setLeaf(false);
		}
		
		return super.create(entity);
		
		
	}
	public MenuItem update(MenuItem entity) {
		MenuItem exists=this.get(entity.getId());
		BeanUtils.copyProperties(entity, exists, new String[]{"parent", "menu","fun","children"});
		if(entity.getFun()!=null){
			exists.setFun(funService.get(entity.getFun().getId()));
		}
		entity=exists;
		return super.update(exists);
		
	}
	public MenuItem delete(MenuItem entity) {
		MenuItem item=this.get(entity.getId());

		return super.delete(item);
	}
	public MenuItem create(String funId,String parentId,String menuId) {
		//throw new BusinessException("c测试");
		Fun fun=funService.get(funId);
		MenuItem parent=this.get(parentId);
		
		MenuItem menuitem = new MenuItem();
		menuitem.setText(fun.getText());
		//menuitem.setReportCode(fun.getReportCode());
		menuitem.setFun(fun);
		menuitem.setParent(parent);
		menuitem.setMenu(parent==null?menuService.get(menuId):parent.getMenu());
		menuitem.setIconCls(fun.getIconCls());
		menuitem.setLeaf(true);
		super.create(menuitem);
		
		if(parent!=null){
			parent.setLeaf(false);
			super.update(parent);
		}
		

//		if(parent!=null){
//			parent.addChild(menuitem);
//		}
		
		return menuitem;
	}

	
	public List<MenuItemVO> query4Desktop(String menuId,Boolean isAdmin) {

		//如果是管理员，可以获查看到所有的菜单
		List<String> menuItemLeaf = super.queryList("query4Desktop", ParamUtils.init().add("menu_id", menuId).add("isAdmin", isAdmin)
				,String.class);

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
			
			if(leaf.getParent()!=null){
				List<MenuItem> ancestores=leaf.findAncestors();
				int i=0;
				for(MenuItem ancestor:ancestores){
					MenuItemVO ancestorNew=null;
					if(parentKeys.containsKey(ancestor.getId())){
						ancestorNew=parentKeys.get(ancestor.getId());
					} else {
						ancestorNew=new MenuItemVO();
						BeanUtils.copyOrCast(ancestor, ancestorNew);
						parentKeys.put(ancestorNew.getId(), ancestorNew);
					}
					if(i==0){			
						ancestorNew.addItems(vo);
					} else {
						ancestorNew.addItems(parentKeys.get(ancestores.get(i-1).getId()));
					}
					i++;
					if(i==ancestores.size() && !menuItems.contains(ancestorNew)){
						menuItems.add(ancestorNew);
					}
				}
			} else {		
				menuItems.add(vo);
				parentKeys.put(vo.getId(), vo);
			}
			
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
		List<MenuItem> menuItems=super.queryAll();//super.query(whereinfo);
		for(MenuItem menuItem:menuItems){
			//if(fun.getFunEnum()==FunEnum.fun){
			super.initLazyProperty(menuItem.getMenu());
			super.initLazyProperty(menuItem.getParent());
			//menuItem.getChildren().size();
			//super.initLazyProperty(menuItem.getChildren());
			//}	
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
		Map params=ParamUtils.init().add("jspUrl", jspUrl).add("menuId", menuId);
		List<MenuItemVO> menuItems=super.queryList("queryMenuItem", params, MenuItemVO.class);
		if(menuItems==null || menuItems.size()==0){
			return null;
		} else {
			return menuItems.get(0);
		}
	}

}
