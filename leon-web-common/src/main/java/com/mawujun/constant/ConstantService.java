package com.mawujun.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
public class ConstantService extends AbstractService<Constant, String> {

	@Autowired
	private ConstantRespository constantRespository;
	@Override
	public ConstantRespository getRepository() {
		// TODO Auto-generated method stub
		return constantRespository;
	}//BaseRepository<Constant,String>{


}
