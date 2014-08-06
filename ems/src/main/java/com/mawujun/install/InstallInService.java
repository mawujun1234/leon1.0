package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.install.InstallIn;
import com.mawujun.install.InstallInRepository;


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
	@Autowired
	private StoreEquipmentRepository storeEquipmentRepository;
	@Autowired
	private WorkUnitEquipmentRepository workUnitEquipmentRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallInRepository getRepository() {
		return installInRepository;
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
			if(equipment.getStatus()==4){
				list.setIsBad(true);
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, 5).andEquals(M.Equipment.ecode, equipment.getEcode()));
			} else {
				list.setIsBad(false);
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, 1).andEquals(M.Equipment.ecode, equipment.getEcode()));
			}
			
			//添加明细
			installInListRepository.create(list);
			
			//仓库增加这一设备
			storeEquipmentRepository.updateNum(installin.getStore_id(), equipment.getEcode(),M.StoreEquipment.num+"+1");
			
			//手持减掉这一个设备,因为一个设备，只会唯一存在，所以只需要使用encode就可以了
			workUnitEquipmentRepository.deleteBatch(Cnd.delete().andEquals(M.WorkUnitEquipment.ecode, equipment.getEcode()));
					//.andEquals(M.WorkUnitEquipment.workunit_id, installin.getWorkUnit_id()));
			
			
		}
	}

}
