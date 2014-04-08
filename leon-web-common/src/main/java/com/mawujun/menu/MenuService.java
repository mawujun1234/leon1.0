package com.mawujun.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MenuService extends AbstractService<Menu, String> {
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private MenuItemService menuItemService;

//	@Override
//	public BaseRepository<Menu, String> getRepository() {
//		// TODO Auto-generated method stub
//		return menuRepository;
//	}
//	
	@Override
	public IRepository<Menu, String> getRepository() {
		// TODO Auto-generated method stub
		return menuRepository;
	}
	
	public String create(Menu entity) {
		return super.create(entity);

//		MenuItem menuitem=new MenuItem();
//		menuitem.setText(entity.getText());
//		menuitem.setFun(new Fun("root"));
//		//menuitem.setParent(parent);
//		menuitem.setMenu(entity);
//		menuItemService.create(menuitem);
//		
		//entity.setRootId(menuitem.getId());
		//menuRepository.update(entity);
	}
	
	public void delete(Menu entity) {
		if("default".equalsIgnoreCase(entity.getId())){
			throw new BusinessException("默认菜单不能删除");
		}
		getRepository().delete(entity);
	}



}
