package com.mawujun.menu;

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
public class MenuService extends BaseService<Menu, String> {
	
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public BaseRepository<Menu, String> getRepository() {
		// TODO Auto-generated method stub
		return menuRepository;
	}
	
//	public void create(Menu entity) {
//		super.create(entity);
//		
////		//获取对应的父菜单
////		WhereInfo whereinfoItem=WhereInfo.parse("fun.id", entity.getParent().getId());
////		WhereInfo whereinfoItem1=WhereInfo.parse("menu.id", "default");
////		MenuItem parent=menuItemRepository.queryUnique(whereinfoItem,whereinfoItem1);
//		
//		MenuItem menuitem=new MenuItem();
//		menuitem.setText(entity.getText());
//		//menuitem.setFun(entity);
//		//menuitem.setParent(parent);
//		menuitem.setMenu(entity.getId());
//		menuItemRepository.create(menuitem);
//	}

}
