package com.mawujun.mobile.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.mobile.task.HitchReasonTpl;
import com.mawujun.mobile.task.HitchReasonTplRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class HitchReasonTplService extends AbstractService<HitchReasonTpl, Integer>{

	@Autowired
	private HitchReasonTplRepository hitchReasonTplRepository;
	
	@Override
	public HitchReasonTplRepository getRepository() {
		return hitchReasonTplRepository;
	}

}
