package com.mawujun.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
public class ConstantItemService extends AbstractService<ConstantItem, String> {
	@Autowired
	private ConstantItemRepository constantItemRepository;

	@Override
	public ConstantItemRepository getRepository() {
		// TODO Auto-generated method stub
		return constantItemRepository;
	}//BaseRepository<ConstantItem,String>{


}
