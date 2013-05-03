package com.mawujun.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;

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

}
