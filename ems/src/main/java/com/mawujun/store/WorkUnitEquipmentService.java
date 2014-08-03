package com.mawujun.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.store.WorkUnitEquipment;
import com.mawujun.store.WorkUnitEquipmentRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class WorkUnitEquipmentService extends AbstractService<WorkUnitEquipment, String>{

	@Autowired
	private WorkUnitEquipmentRepository workUnitEquipmentRepository;
	
	@Override
	public WorkUnitEquipmentRepository getRepository() {
		return workUnitEquipmentRepository;
	}

}
