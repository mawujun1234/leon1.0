package com.mawujun.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;


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

	
	public EquipmentVO getEquipmentByEcode(String ecode,String store_id) {
		EquipmentVO equipment =equipmentRepository.getEquipmentByEcode(ecode,store_id);
		
		return equipment;
	}
	
	public EquipmentVO getEquipmentInfo(String ecode) {
		return equipmentRepository.getEquipmentInfo(ecode);
	}

}
