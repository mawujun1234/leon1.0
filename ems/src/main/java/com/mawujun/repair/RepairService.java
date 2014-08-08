package com.mawujun.repair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.repair.Repair;
import com.mawujun.repair.RepairRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class RepairService extends AbstractService<Repair, String>{

	@Autowired
	private RepairRepository repairRepository;
	
	@Override
	public RepairRepository getRepository() {
		return repairRepository;
	}

}
