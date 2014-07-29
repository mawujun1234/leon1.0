package com.mawujun.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.baseinfo.WorkUnitContact;
import com.mawujun.baseinfo.WorkUnitContactRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class WorkUnitContactService extends AbstractService<WorkUnitContact, String>{

	@Autowired
	private WorkUnitContactRepository workUnitContactRepository;
	
	@Override
	public WorkUnitContactRepository getRepository() {
		return workUnitContactRepository;
	}

}
