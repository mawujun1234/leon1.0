package com.mawujun.baseinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.service.AbstractService;


import com.mawujun.baseinfo.WorkUnit;
import com.mawujun.baseinfo.WorkUnitRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class WorkUnitService extends AbstractService<WorkUnit, String>{

	@Autowired
	private WorkUnitRepository workUnitRepository;
	
	@Override
	public WorkUnitRepository getRepository() {
		return workUnitRepository;
	}
	
	public List<EquipmentVO> queryEquipments(String workUnit_id) {
		return workUnitRepository.queryEquipments(workUnit_id);
	}

}
