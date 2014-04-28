package com.mawujun.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.org.OrgDimenssion;
import com.mawujun.org.OrgDimenssionRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrgDimenssionService extends AbstractService<OrgDimenssion, String>{

	@Autowired
	private OrgDimenssionRepository orgDimenssionRepository;
	
	@Override
	public OrgDimenssionRepository getRepository() {
		return orgDimenssionRepository;
	}

}
