package com.mawujun.repair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.service.AbstractService;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ScrapService extends AbstractService<Scrap, String>{

	@Autowired
	private ScrapRepository scrapRepository;
	
	@Override
	public ScrapRepository getRepository() {
		return scrapRepository;
	}
	

	

}
