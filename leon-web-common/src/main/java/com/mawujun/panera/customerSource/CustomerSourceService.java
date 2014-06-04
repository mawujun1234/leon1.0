package com.mawujun.panera.customerSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.service.AbstractService;


import com.mawujun.panera.customerSource.CustomerSource;
import com.mawujun.panera.customerSource.CustomerSourceRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CustomerSourceService extends AbstractService<CustomerSource, String>{

	@Autowired
	private CustomerSourceRepository customerSourceRepository;
	
	@Override
	public CustomerSourceRepository getRepository() {
		return customerSourceRepository;
	}

}
