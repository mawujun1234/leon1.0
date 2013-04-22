package com.mawujun.fun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;

@Service
public class FunService extends BaseService<Fun, String> {
	@Autowired
	private FunRepository funRepository;
	
	@Override
	public BaseRepository<Fun, String> getRepository() {
		// TODO Auto-generated method stub
		return funRepository;
	}

}
