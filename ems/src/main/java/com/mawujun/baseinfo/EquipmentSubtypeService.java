package com.mawujun.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.utils.M;
import com.mawujun.baseinfo.EquipmentSubtype;
import com.mawujun.baseinfo.EquipmentSubtypeRepository;
import com.mawujun.exception.BusinessException;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class EquipmentSubtypeService extends AbstractService<EquipmentSubtype, String>{

	@Autowired
	private EquipmentSubtypeRepository equipmentSubtypeRepository;
	@Autowired
	private EquipmentProdRepository equipmentProdRepository;
	
	@Override
	public EquipmentSubtypeRepository getRepository() {
		return equipmentSubtypeRepository;
	}

	public void delete(EquipmentSubtype entity) {
		//this
		Long count=equipmentProdRepository.queryCount(Cnd.select().andEquals(M.EquipmentProd.parent_id, entity.getId()).andEquals(M.EquipmentProd.status, true));
		if(count>0){
			throw new BusinessException("还存在可用的存在品名,不能删除小类!");
		}
		
		this.getRepository().update(Cnd.update().set(M.EquipmentSubtype.status, false).andEquals(M.EquipmentSubtype.id, entity.getId()));
	}
}
