package com.mawujun.panera.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.panera.customer.Contact;
import com.mawujun.panera.customer.ContactRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ContactService extends AbstractService<Contact, String>{

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public ContactRepository getRepository() {
		return contactRepository;
	}

}
