package com.mawujun.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;


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
	
	@Autowired
	private EquipmentCycleService equipmentCycleService;
	
	@Override
	public EquipmentRepository getRepository() {
		return equipmentRepository;
	}

	
	public EquipmentVO getEquipmentByEcode_in_store(String ecode,String store_id) {
		EquipmentVO equipment =equipmentRepository.getEquipmentByEcode_in_store(ecode,store_id);
		
		return equipment;
	}
	
	public EquipmentVO getEquipmentInfo(String ecode) {
		return equipmentRepository.getEquipmentInfo(ecode);
	}
	
	
	public EquipmentWorkunitVO getEquipmentWorkunitVO(String ecode){
		return equipmentRepository.getEquipmentWorkunitVO(ecode);
	}
	public EquipmentStoreVO getEquipmentStoreVO(String ecode){
		return equipmentRepository.getEquipmentStoreVO(ecode);
	}
	public EquipmentRepairVO getEquipmentRepairVO(String ecode){
		return equipmentRepository.getEquipmentRepairVO(ecode);
	}
	public EquipmentPoleVO getEquipmentPoleVO(String ecode){
		return equipmentRepository.getEquipmentPoleVO(ecode);
	}
	
	
	public String wait_for_repair(String ecode,String reason) {
		Equipment equip=this.get(ecode);
		if(equip.getStatus()== EquipmentStatus.wait_for_repair){
			//return "已经是损坏设备，不需要重复设置为损坏设备!";
			throw new BusinessException("已经是损坏设备，不需要重复设置为损坏设备!");
		} else if(equip.getStatus() != EquipmentStatus.in_storage){
			throw new BusinessException("设备不在仓库，不能修改为待维修状态!");
		} else {

			
			EquipmentStoreVO equipmentStoreVO=this.getEquipmentStoreVO(ecode);
			if(equipmentStoreVO==null){
				throw new BusinessException("设备不在仓库中，请注意!");
			}
			equip.setStatus(EquipmentStatus.wait_for_repair);
			this.update(equip);
			EquipmentCycle equipmentCycle=equipmentCycleService.logEquipmentCycle(ecode, OperateType.manual_wait_for_repair, ShiroUtils.getLoginName(), TargetType.store,equipmentStoreVO.getStore_id());
			equipmentCycle.setMemo(reason);
			equipmentCycleService.update(equipmentCycle);
		}
		//equipmentService.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.wait_for_repair).andEquals(M.Equipment.ecode, ecode));
		return "success";
	}
	
	public String to_old(String ecode,String reason) {
		Equipment equip=this.get(ecode);
		if(equip.getIsnew()==false){
			throw new BusinessException("已经是旧设备，不需要重复设置为旧设备!");
		} else {
			equip.setIsnew(false);
			this.update(equip);
			
			EquipmentWorkunitVO aa=this.getEquipmentWorkunitVO(ecode);
			EquipmentStoreVO bb=null;
			EquipmentPoleVO cc=null;
			EquipmentRepairVO dd=null;
			
			TargetType targetType=TargetType.workunit;
			String targetType_id=null;
			if(aa==null){
				bb=this.getEquipmentStoreVO(ecode);

				if(bb!=null){
					targetType=TargetType.store;
					targetType_id=bb.getStore_id();
					
				} else {
					cc=this.getEquipmentPoleVO(ecode);
					
					if(cc!=null){
						targetType=TargetType.pole;	
						targetType_id=cc.getPole_id();
						
					} else {
						dd=this.getEquipmentRepairVO(ecode);
						
						if(dd!=null){
							targetType=TargetType.repair;
							targetType_id=dd.getRepair_id();		
						} else {
							throw new BusinessException("该设备不在仓库，点位，作业单位和维修中心,不能进行手工设旧处理!");
						}
					}
				}
			} else {
				targetType_id=aa.getWorkunit_id();
				targetType=TargetType.workunit;
			}
			
			
			
			
			EquipmentCycle equipmentCycle=equipmentCycleService.logEquipmentCycle(ecode, OperateType.manual_to_old, ShiroUtils.getLoginName(), targetType,targetType_id);
			equipmentCycle.setMemo(reason);
			equipmentCycleService.update(equipmentCycle);
		}
		//equipmentService.update(Cnd.update().set(M.Equipment.isnew, false).andEquals(M.Equipment.ecode, ecode));
		return "success";
	}
	

}
