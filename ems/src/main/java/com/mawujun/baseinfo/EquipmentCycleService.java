package com.mawujun.baseinfo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




import com.mawujun.service.AbstractService;


import com.mawujun.shiro.ShiroUtils;
import com.mawujun.baseinfo.EquipmentCycle;
import com.mawujun.baseinfo.EquipmentCycleRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class EquipmentCycleService extends AbstractService<EquipmentCycle, String>{

	@Autowired
	private EquipmentCycleRepository equipmentCycleRepository;
//	@Autowired
//	private EquipmentCycleRepository storeRepository;
	
	@Override
	public EquipmentCycleRepository getRepository() {
		return equipmentCycleRepository;
	}
	/**
	 * 
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @param operateType 操作类型，入库，出库，领用，返库等等
	 * @param type_id 入库id，领用单id等等 各种单据id
	 * @param target_id 目标id。仓库id，作业单位id，维修中心id等
	 * @param target_name 目标的名称。仓库名称，作业单位名称，维修中心名称等
	 */
	public void logEquipmentCycle(String ecode,OperateType operateType,String type_id,String target_id,String target_name){
		EquipmentCycle cycle=new EquipmentCycle();
		cycle.setEcode(ecode);
		
		cycle.setOperateDate(new Date());
		
		cycle.setOperater_id(ShiroUtils.getUserId());
		cycle.setOperater_name(ShiroUtils.getUserName());
		
		cycle.setOperateType(operateType);
		
		cycle.setTarget_id(target_id);
		cycle.setTarget_name(target_name);
		
		cycle.setType_id(type_id);
		
		equipmentCycleRepository.create(cycle);
	}

}
