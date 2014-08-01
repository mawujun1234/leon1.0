package com.mawujun.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class StoreService extends AbstractService<Store, String>{

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public StoreRepository getRepository() {
		return storeRepository;
	}

}