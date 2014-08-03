package com.mawujun.store;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.shiro.ShiroUtils;
import com.mawujun.store.OutStore;
import com.mawujun.store.OutStoreRepository;
import com.mawujun.utils.M;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OutStoreService extends AbstractService<OutStore, String>{

	@Autowired
	private OutStoreRepository outStoreRepository;
	@Autowired
	private OutStoreListRepository outStoreListRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private WorkUnitEquipmentRepository workUnitEquipmentRepository;
	@Autowired
	private StoreEquipmentRepository storeEquipmentRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public OutStoreRepository getRepository() {
		return outStoreRepository;
	}

	public void equipOutStore(Equipment[] equipments, OutStore outStore) {
		// 插入入库单
		String instore_id = ymdHmsDateFormat.format(new Date());
		// InStore inStore=new InStore();
		outStore.setId(instore_id);
		outStore.setOperateDate(new Date());
		outStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		outStore.setType(1);
		outStoreRepository.create(outStore);
		
		for(Equipment equipment:equipments){
			//更新设备状态为出库待安装
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, 2).andEquals(M.Equipment.ecode, equipment.getEcode()));
			
			//把设备绑定到作业单位上面
			WorkUnitEquipment workUnitEquipment=new WorkUnitEquipment();
			workUnitEquipment.setEcode(equipment.getEcode());
			workUnitEquipment.setOutStore_id(instore_id);
			workUnitEquipment.setWorkunit_id(outStore.getWorkUnit_id());
			workUnitEquipmentRepository.create(workUnitEquipment);
			
			//把仓库中的该设备移除
			storeEquipmentRepository.update(Cnd.update().set(M.StoreEquipment.num, M.StoreEquipment.num+"-1").andEquals(M.StoreEquipment.ecode, equipment.getEcode())
					.andEquals(M.StoreEquipment.store_id, outStore.getStore_id()));
			
			//插入入库单明细
			OutStoreList inStoreList=new OutStoreList();
			inStoreList.setEcode(equipment.getEcode());
			inStoreList.setOutStore_id(instore_id);
			outStoreListRepository.create(inStoreList);
		}
	}
}
