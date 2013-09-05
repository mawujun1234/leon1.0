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
import com.mawujun.repository.mybatis.MybatisParamUtils;
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
		entity=exists;
		return super.update(exists);
		
	}
	public MenuItem delete(MenuItem entity) {
		MenuItem item=this.get(entity.getId());
//		if(item.isAutoCreate()){
//			throw new BussinessException("不能删除自动创建的菜单项。<br/>");
//		}
		return super.delete(item);
	}
	public MenuItem create(String funId,String parentId,String menuId) {
		//throw new BussinessException("c测试");
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
		parent.setLeaf(false);
		super.update(parent);

//		if(parent!=null){
//			parent.addChild(menuitem);
//		}
		
		return menuitem;
	}
//	/**
//	 * 在默认菜单上创建菜单项
//	 * @author mawujun 16064988@qq.com 
//	 * @param fun
//	 */
//	public void create(Fun fun) {
//		//新建功能的时候同时建立菜单，还没有做
//		// 获取对应的父菜单,获取第一个匹配的菜单
//		WhereInfo whereinfoItem = WhereInfo.parse("fun.id", fun.getParent()==null?null:fun.getParent().getId());
//		WhereInfo whereinfoItem1 = WhereInfo.parse("menu.id", Menu.default_id);
//		MenuItem menuparent = this.queryUnique(whereinfoItem,whereinfoItem1);
//
//		MenuItem menuitem = new MenuItem();
//		menuitem.setText(fun.getText());
//		menuitem.setReportCode(fun.getReportCode());
//		menuitem.setFun(fun);
//		menuitem.setParent(menuparent);
//		menuitem.setMenu(new Menu(Menu.default_id));
//		menuitem.setIconCls(fun.getIconCls());
//		
//		super.create(menuitem);
//		fun.setMenuItemId(menuitem.getId());
//	}
//	
//	public void update(Fun fun) {
////		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getId());
////		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
////		MenuItem menuItem=menuItemService.queryUnique(whereinfoItem,whereinfoItem1);
////		menuItem.setText(entity.getText());
////		menuItem.setReportCode(entity.getReportCode());
//		MenuItem menuItem=this.get(fun.getMenuItemId());
//		menuItem.setText(fun.getText());
//		menuItem.setReportCode(fun.getReportCode());
//		super.update(menuItem);
//	}
//	public void delete(Fun fun) {
//		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", fun.getId());
//		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", Menu.default_id);
//		List<MenuItem> menuItems= this.query(whereinfoItem,whereinfoItem1);
//		if(menuItems!=null && menuItems.size()>0){
//			StringBuilder builder=new StringBuilder();
//			for(MenuItem menuItem:menuItems){
//				builder.append(menuItem.getMenu().getText()+":"+menuItem.getText()+";");
//			}
//			throw new BussinessException("有菜单挂钩，不能删除。<br/>"+builder);
//		} else if(menuItems.size()==1){	
//			this.delete(menuItems.get(0));
//		}
//	}
	
	public List<MenuItemVO> query4Desktop(String menuId) {
		
		//https://github.com/DozerMapper/dozer
		//在这里就直接转换成桌面需要用的格式，具有menu，和items等数据
		//dozer 可以把对象拷贝为map。过滤属性
		
		//BeanMapper.convert(value, MenuItemVO.class);
		
		
		//List<Object> menuItemLeaf = super.queryListObj("query4Desktop", menuId);
		List<String> menuItemLeaf = super.queryList("query4Desktop", menuId,String.class);
		// 组装出role树
		Map<String,MenuItemVO> parentKeys=new HashMap<String,MenuItemVO>();
		List<MenuItemVO> menuItems = new ArrayList<MenuItemVO>();
		for (Object menuItemIdObj: menuItemLeaf) {
			String menuItemId=(String)menuItemIdObj;

			MenuItem leaf=this.get(menuItemId);
			
			if(parentKeys.get(menuItemId) !=null){//表示这个功能能已经添加过了
				continue;
			}
			//MenuItemVO fun=parentKeys.get(leaf.getId());
			MenuItemVO vo=BeanUtils.copyOrCast(leaf, MenuItemVO.class);
			vo.setFunId(leaf.getFun()!=null?leaf.getFun().getId():null);
			//fun.addItems(vo);
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
		
//		//根据mybatis去查询
//				Map params=new HashMap();
//				params.put("menu_id", menuId);
//		return (List<MenuItem>)super.queryListT("query4Desktop", params);
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
		Map params=MybatisParamUtils.init().add("jspUrl", jspUrl).add("menuId", menuId);
		List<MenuItemVO> menuItems=super.queryList("queryMenuItem", params, MenuItemVO.class);
		if(menuItems==null || menuItems.size()==0){
			return null;
		} else {
			return menuItems.get(0);
		}
	}

}
