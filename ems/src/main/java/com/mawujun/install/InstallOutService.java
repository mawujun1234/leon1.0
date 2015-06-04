package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.controller.spring.SpringContextHolder;
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
public class InstallOutService extends AbstractService<InstallOut, String>{

	@Autowired
	private InstallOutRepository outStoreRepository;
	@Autowired
	private InstallOutListRepository outStoreListRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallOutRepository getRepository() {
		return outStoreRepository;
	}

	public void equipOutStore(Equipment[] equipments, InstallOut outStore) {
		// 插入入库单
		String instore_id = ymdHmsDateFormat.format(new Date());
		// InStore inStore=new InStore();
		outStore.setId(instore_id);
		outStore.setOperateDate(new Date());
		outStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		//outStore.setType(1);
		outStoreRepository.create(outStore);
		
		for(Equipment equipment:equipments){
			//更新设备状态为出库待安装
			//把设备绑定到作业单位上面
			//把仓库中的该设备移除
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage)
					.set(M.Equipment.workUnit_id, outStore.getWorkUnit_id())
					.set(M.Equipment.store_id, null)
					.andEquals(M.Equipment.ecode, equipment.getEcode()));
			
			
			
//			WorkUnitEquipment workUnitEquipment=new WorkUnitEquipment();
//			workUnitEquipment.setEcode(equipment.getEcode());
//			workUnitEquipment.setOutStore_id(instore_id);//不是唯一主键
//			workUnitEquipment.setWorkunit_id(outStore.getWorkUnit_id());
//			workUnitEquipmentRepository.create(workUnitEquipment);
//			
//			//把仓库中的该设备移除
////			storeEquipmentRepository.update(Cnd.update().set(M.StoreEquipment.num, M.StoreEquipment.num+"-1").andEquals(M.StoreEquipment.ecode, equipment.getEcode())
////					.andEquals(M.StoreEquipment.store_id, outStore.getStore_id()));
//			storeEquipmentRepository.updateNum(outStore.getStore_id(), equipment.getEcode(),M.StoreEquipment.num+"-1");
			
			//插入入库单明细
			InstallOutList inStoreList=new InstallOutList();
			inStoreList.setEcode(equipment.getEcode());
			inStoreList.setInstallOut_id(instore_id);
			outStoreListRepository.create(inStoreList);
		}
	}
	
	public Page queryMain(Page page){

		return outStoreRepository.queryMain(page);

		
	}
	public List<InstallOutListVO> queryList(String installOut_id) {

		return outStoreRepository.queryList(installOut_id);

	}
}
