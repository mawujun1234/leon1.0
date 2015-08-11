package com.mawujun.install;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class B2INotifyService extends AbstractService<B2INotify, String>{

	@Autowired
	private B2INotifyRepository b2INotifyRepository;
	
	@Override
	public B2INotifyRepository getRepository() {
		return b2INotifyRepository;
	}

	public List<B2INotifyVO>  queryAllVO() {
		return b2INotifyRepository.queryAllVO(ShiroUtils.getUserId());
	}

}
