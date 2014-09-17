package com.mawujun.mobile.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.mobile.task.HitchType;
import com.mawujun.mobile.task.HitchTypeRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class HitchTypeService extends AbstractService<HitchType, Integer>{

	@Autowired
	private HitchTypeRepository hitchTypeRepository;
	
	@Override
	public HitchTypeRepository getRepository() {
		return hitchTypeRepository;
	}

}
