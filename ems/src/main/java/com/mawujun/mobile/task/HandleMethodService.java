package com.mawujun.mobile.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.service.AbstractService;


import com.mawujun.exception.BusinessException;
import com.mawujun.mobile.task.HandleMethod;
import com.mawujun.mobile.task.HandleMethodRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class HandleMethodService extends AbstractService<HandleMethod, String>{

	@Autowired
	private HandleMethodRepository handleMethodRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public HandleMethodRepository getRepository() {
		return handleMethodRepository;
	}

	@Override
	public void delete(HandleMethod entity) {
		int count=taskRepository.check_handleMethod_used(entity.getId());
		if(count>0){
			throw new BusinessException("改处理方法已经被使用,不能删除!");
		}
		this.getRepository().delete(entity);
	}
}
