package com.mawujun.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.fun.Fun;
import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;
import com.mawujun.utils.help.ReportCodeHelper;
import com.mawujun.utils.page.WhereInfo;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MenuItemService extends BaseRepository<MenuItem, String> {

	
	public void create(MenuItem entity) {
		Object reportCode=null;
		if(entity.getParent()!=null){
			WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getParent().getId());
			reportCode=this.queryMax("reportCode",whereinfo);
		}
		//获取父节点的reportcode
		
		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		entity.setReportCode(newReportCode);
		
		super.create(entity);
	}
	/**
	 * 在默认菜单上创建菜单项
	 * @author mawujun 16064988@qq.com 
	 * @param fun
	 */
	public void create(Fun fun) {
		//新建功能的时候同时建立菜单，还没有做
		// 获取对应的父菜单,获取第一个匹配的菜单
		WhereInfo whereinfoItem = WhereInfo.parse("fun.id", fun.getParent()==null?null:fun.getParent().getId());
		WhereInfo whereinfoItem1 = WhereInfo.parse("menu.id", Menu.default_id);
		MenuItem menuparent = this.queryUnique(whereinfoItem,whereinfoItem1);

		MenuItem menuitem = new MenuItem();
		menuitem.setText(fun.getText());
		menuitem.setReportCode(fun.getReportCode());
		menuitem.setFun(fun);
		menuitem.setParent(menuparent);
		menuitem.setMenu(new Menu(Menu.default_id));
		super.create(menuitem);
	}
	
	public List<Map<String,Object>> query4Desktop(String menuId) {
		//根据mybatis去查询
		Map params=new HashMap();
		params.put("menu_id", menuId);
		return super.queryList("query4Desktop", params);
	}

}
