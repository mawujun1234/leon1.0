package com.mawujun.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		//判断有没有设备在引用，如果有就接着下一个判断，否则就直接删除
		Long aa=equipmentRepository.queryCount(Cnd.select().andEquals(M.Equipment.prod_id, entity.getId()));
		if(aa>0){
			throw new BusinessException("该品名已经被使用,不能删除!");
		} else {
			this.getRepository().delete(entity);
		}
		//this
		Long count=equipmentRepository.queryCount(Cnd.select().andEquals(M.Equipment.prod_id, entity.getId()).andNotIn(M.Equipment.status, 0,30));
		if(count>0){
			throw new BusinessException("还存在使用中的设备在使用该品名,不能取消该品名!");
		}
		
		this.getRepository().update(Cnd.update().set(M.EquipmentProd.status, false).andEquals(M.EquipmentProd.id, entity.getId()));
	}
	
	
	public List<EquipmentProd> queryProdGrid(Boolean status,String subtype_id) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put(M.EquipmentSubtype.parent_id, subtype_id);
		if(status!=null){
//			if(status){
//				params.put(M.EquipmentType.status, "Y");
//			} else {
//				params.put(M.EquipmentType.status, "N");
//			}
			params.put(M.EquipmentType.status, status);
		}
		return this.getRepository().queryProdGrid(params);
	}

//	public List<Brand> queryProdGrid(String subtype_id) {
//		return this.getRepository().queryBrandCombo(subtype_id);
//	}
}
