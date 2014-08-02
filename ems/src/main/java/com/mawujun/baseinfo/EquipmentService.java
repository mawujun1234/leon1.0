package com.mawujun.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.service.AbstractService;


import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class EquipmentService extends AbstractService<Equipment, String>{

	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Override
	public EquipmentRepository getRepository() {
		return equipmentRepository;
	}

	
	public Equipment getEquipmentByEcode(String ecode) {
		return equipmentRepository.getEquipmentByEcode(ecode);
	}

	
}
