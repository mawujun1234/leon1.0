package com.mawujun.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.store.InStoreList;
import com.mawujun.store.InStoreListRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class InStoreListService extends AbstractService<InStoreList, String>{

	@Autowired
	private InStoreListRepository inStoreListRepository;
	
	@Override
	public InStoreListRepository getRepository() {
		return inStoreListRepository;
	}

}
