package com.mawujun.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.service.AbstractService;

@Service
public class ConstantTypeService extends AbstractService<ConstantType,String>{
	
	@Autowired
	private ConstantTypeRepository constantTypeRepository;

	@Override
	public ConstantTypeRepository getRepository() {
		// TODO Auto-generated method stub
		return constantTypeRepository;
	}


}
