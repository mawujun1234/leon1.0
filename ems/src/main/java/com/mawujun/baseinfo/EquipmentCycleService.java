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
	@Autowired
	private WorkUnitService workUnitService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private PoleService poleService;
	
	@Override
	public EquipmentCycleRepository getRepository() {
		return equipmentCycleRepository;
	}
	/**
	 * 
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @param operateType 操作类型，入库，出库，领用，返库等等
	 * @param type_id 入库id，领用单id等等 各种单据id,如果是手动修改，就填写修改人的id
	 * @param targetType 
	 * @param target_id 目标id。仓库id，作业单位id，维修中心id等
	 * @param target_id 备注
	 */
	public EquipmentCycle logEquipmentCycle(String ecode,OperateType operateType,String type_id,TargetType targetType,String target_id,String memo){
		EquipmentCycle cycle=new EquipmentCycle();
		cycle.setEcode(ecode);
		
		cycle.setOperateDate(new Date());
		
		cycle.setOperater_id(ShiroUtils.getUserId());
		cycle.setOperater_name(ShiroUtils.getUserName());
		cycle.setOperater_ipAddr(ShiroUtils.getAuthenticationInfo().getIpAddr());
		
		cycle.setOperateType(operateType);
		
		cycle.setTarget_id(target_id);
		if(targetType==TargetType.store || targetType==TargetType.repair){
			cycle.setTarget_name(storeService.get(target_id).getName());
		} else if(targetType==TargetType.pole){
			Pole pole=poleService.get(target_id);
			cycle.setTarget_name(pole.getName()+"("+pole.getCode()+")");
		} else if(targetType==TargetType.workunit){
			cycle.setTarget_name(workUnitService.get(target_id).getName());
		}
		
		
		cycle.setType_id(type_id);
		
		cycle.setMemo(memo);
		
		equipmentCycleRepository.create(cycle);
		return cycle;
	}
	
//	public EquipmentCycle logEquipmentCycle(String ecode,OperateType operateType,String type_id,TargetType targetType,String target_id){
//		EquipmentCycle cycle=new EquipmentCycle();
//		cycle.setEcode(ecode);
//		
//		cycle.setOperateDate(new Date());
//		
//		cycle.setOperater_id(ShiroUtils.getUserId());
//		cycle.setOperater_name(ShiroUtils.getUserName());
//		
//		cycle.setOperateType(operateType);
//		
//		cycle.setTarget_id(target_id);
//		if(targetType==TargetType.store || targetType==TargetType.repair){
//			cycle.setTarget_name(storeService.get(target_id).getName());
//		} else if(targetType==TargetType.pole){
//			Pole pole=poleService.get(target_id);
//			cycle.setTarget_name(pole.getName()+"("+pole.getCode()+")");
//		} else if(targetType==TargetType.workunit){
//			cycle.setTarget_name(workUnitService.get(target_id).getName());
//		}
//		
//		
//		cycle.setType_id(type_id);
//		
//		
//		equipmentCycleRepository.create(cycle);
//		return cycle;
//	}

}
