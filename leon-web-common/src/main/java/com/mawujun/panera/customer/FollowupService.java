package com.mawujun.panera.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.panera.customer.Followup;
import com.mawujun.panera.customer.FollowupRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class FollowupService extends AbstractService<Followup, String>{

	@Autowired
	private FollowupRepository followupRepository;
	
	@Override
	public FollowupRepository getRepository() {
		return followupRepository;
	}

}
