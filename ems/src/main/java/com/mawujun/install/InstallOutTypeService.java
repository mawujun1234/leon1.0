package com.mawujun.install;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.service.AbstractService;


import com.mawujun.exception.BusinessException;
import com.mawujun.install.InstallOutType;
import com.mawujun.install.InstallOutTypeRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class InstallOutTypeService extends AbstractService<InstallOutType, String>{

	@Autowired
	private InstallOutTypeRepository installOutTypeRepository;
	
	@Override
	public InstallOutTypeRepository getRepository() {
		return installOutTypeRepository;
	}
	@Override
	public void delete(InstallOutType entity) {
		//判断是否已经被领用单给用过了
		int count=installOutTypeRepository.isUsedByInstallOut(entity.getId());
		if(count>0){
			throw new BusinessException("该领用类型已被使用，不能删除!");
		}
		this.getRepository().delete(entity);
	}
}
