package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class InstallInService extends AbstractService<InstallIn, String>{

	@Autowired
	private InstallInRepository installInRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private InstallInListRepository installInListRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallInRepository getRepository() {
		return installInRepository;
	}
	
	public EquipmentVO getEquipmentByEcode(String ecode,String workunit_id) {
		EquipmentVO equipment= installInRepository.getEquipmentByEcode(ecode,workunit_id);
		if(equipment==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("该条码对应的设备不存在，或者该设备挂在其他作业单位或已经入库了!");
		}
		//设备返库的时候，设备如果不是手持或损坏状态的话，就不能进行返库，说明任务没有扫描或者没有提交
		//if(equipment.getStatus()!=EquipmentStatus.out_storage && equipment.getStatus()!=EquipmentStatus.breakdown){
		if(equipment.getStatus()!=EquipmentStatus.out_storage ){
			throw new BusinessException("设备状态不对,不是作业单位手中持有的设备!");
		}
		return equipment;
	}
	
	
	public void equipmentInStore(Equipment[] equipments, InstallIn installin) { 
		String instore_id = ymdHmsDateFormat.format(new Date());
		// InStore inStore=new InStore();
		installin.setId(instore_id);
		installin.setOperateDate(new Date());
		installin.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		//outStore.setType(1);
		installInRepository.create(installin);
		
		for(Equipment equipment:equipments){
			
			InstallInList list=new InstallInList();
			list.setEcode(equipment.getEcode());
			list.setInstallIn_id(instore_id);
			
			//如果设备状态时损坏，就把设备状态改为 入库待维修，否则就修改为在库
			//把设备挂到相应的仓库上
			//同时减持设备挂在作业单位
//			if(equipment.getStatus()==EquipmentStatus.breakdown){
//				list.setIsBad(true);
//				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.wait_for_repair.getValue())
//						.set(M.Equipment.store_id, installin.getStore_id())
//						.set(M.Equipment.workUnit_id,null)
//						.andEquals(M.Equipment.ecode, equipment.getEcode()));
//			} else {
//				list.setIsBad(false);
//				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage.getValue())
//						.set(M.Equipment.store_id, installin.getStore_id())
//						.set(M.Equipment.workUnit_id,null)
//						.andEquals(M.Equipment.ecode, equipment.getEcode()));
//				
//			}
			if(installin.getType()==InstallInType.bad){
				list.setIsBad(true);
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.wait_for_repair)
						.set(M.Equipment.store_id, installin.getStore_id())
						.set(M.Equipment.workUnit_id,null)
						.andEquals(M.Equipment.ecode, equipment.getEcode()));
			} else {
				list.setIsBad(false);
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage)
						.set(M.Equipment.store_id, installin.getStore_id())
						.set(M.Equipment.workUnit_id,null)
						.andEquals(M.Equipment.ecode, equipment.getEcode()));
				
			}
			
			//添加明细
			installInListRepository.create(list);
			
			
		}
	}

	public Page queryMain(Page page){

		return installInRepository.queryMain(page);

		
	}
	public List<InstallInListVO> queryList(String installIn_id) {

		return installInRepository.queryList(installIn_id);

	}
	
}
