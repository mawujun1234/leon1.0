package com.mawujun.fun;

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
public class FunService extends BaseService<Fun, String> {
	@Autowired
	private FunRepository funRepository;
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Override
	public BaseRepository<Fun, String> getRepository() {
		// TODO Auto-generated method stub
		return funRepository;
	}

	@Override
	public void delete(Fun entity) {
		//判断是否具有子节点
		WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getId());
		int childs=this.getRepository().queryCount(whereinfo);
		if(childs>0){
			throw new BussinessException("存在子节点，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		
		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getId());
		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", "default");
		//MenuItem menuItem=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
		menuItemRepository.deleteBatch(whereinfoItem,whereinfoItem1);
		
		super.delete(entity);
		
		
	}
	
	public void create(Fun entity) {
		//获取父节点的reportcode
		WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getParent().getId());
		Object reportCode=funRepository.queryMax("reportCode",whereinfo);
		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		entity.setReportCode(newReportCode);
		
		getRepository().create(entity);
		
		//获取对应的父菜单
		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getParent().getId());
		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", "default");
		MenuItem parent=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
		
		MenuItem menuitem=new MenuItem();
		menuitem.setText(entity.getText());
		menuitem.setReportCode(entity.getReportCode());
		menuitem.setFun(entity);
		menuitem.setParent(parent);
		menuitem.setMenu(new Menu("default"));
		menuItemRepository.create(menuitem);
	}
	
	public void update(Fun entity) {
		super.update(entity);
		
		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getId());
		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", "default");
		MenuItem menuItem=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
		menuItem.setText(entity.getText());
		menuItem.setReportCode(entity.getReportCode());
		menuItemRepository.update(menuItem);
	}
	
	

}
