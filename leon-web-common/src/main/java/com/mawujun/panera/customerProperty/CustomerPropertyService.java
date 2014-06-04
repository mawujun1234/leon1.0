package com.mawujun.panera.customerProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.panera.customerProperty.CustomerProperty;
import com.mawujun.panera.customerProperty.CustomerPropertyRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CustomerPropertyService extends AbstractService<CustomerProperty, String>{

	@Autowired
	private CustomerPropertyRepository customerPropertyRepository;
	
	@Override
	public CustomerPropertyRepository getRepository() {
		return customerPropertyRepository;
	}

}
