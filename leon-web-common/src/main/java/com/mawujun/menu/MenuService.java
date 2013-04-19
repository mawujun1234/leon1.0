package com.mawujun.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;

@Service
public class MenuService extends BaseService<Menu, String> {
	
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public BaseRepository<Menu, String> getRepository() {
		// TODO Auto-generated method stub
		return menuRepository;
	}

}
