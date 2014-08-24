package com.mawujun.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.utils.M;
import com.mawujun.baseinfo.EquipmentProd;
import com.mawujun.baseinfo.EquipmentProdRepository;
import com.mawujun.exception.BusinessException;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class EquipmentProdService extends AbstractService<EquipmentProd, String>{

	@Autowired
	private EquipmentProdRepository equipmentProdRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Override
	public EquipmentProdRepository getRepository() {
		return equipmentProdRepository;
	}
	
	public void delete(EquipmentProd entity) {
		//this
		Long count=equipmentRepository.queryCount(Cnd.select().andEquals(M.Equipment.prod_id, entity.getId()).andNotIn(M.Equipment.status, 0,30));
		if(count>0){
			throw new BusinessException("还存在设备在使用该品名,不能删除!");
		}
		
		this.getRepository().update(Cnd.update().set(M.EquipmentProd.status, false).andEquals(M.EquipmentProd.id, entity.getId()));
	}

}
