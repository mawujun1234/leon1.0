package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentStorePK;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
import com.mawujun.baseinfo.EquipmentWorkunitType;
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
	@Autowired
	private EquipmentStoreRepository equipmentStoreRepository;
	@Autowired
	private EquipmentWorkunitRepository equipmentWorkunitRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallOutRepository getRepository() {
		return outStoreRepository;
	}

	public void equipOutStore(Equipment[] equipments, InstallOut outStore) {
		// 插入入库单
		String outstore_id = ymdHmsDateFormat.format(new Date());
		// InStore inStore=new InStore();
		outStore.setId(outstore_id);
		outStore.setOperateDate(new Date());
		outStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		//outStore.setType(1);
		outStoreRepository.create(outStore);
		
		for(Equipment equipment:equipments){
			//更新设备状态为出库待安装
			//把设备绑定到作业单位上面
			//把仓库中的该设备移除
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage)
					.set(M.Equipment.last_workunit_id, outStore.getWorkUnit_id())
					//.set(M.Equipment.store_id, null)
					.set(M.Equipment.place, EquipmentPlace.workunit)
					.andEquals(M.Equipment.ecode, equipment.getEcode()));
			
			//从仓库中删除
			EquipmentStorePK equipmentStorePK=new EquipmentStorePK();
			equipmentStorePK.setEcode(equipment.getEcode());
			equipmentStorePK.setStore_id(outStore.getStore_id());
			equipmentStoreRepository.deleteById(equipmentStorePK);
//			equipmentStore.setNum(1);
//			equipmentStore.setInDate(new Date());
//			equipmentStore.setType(EquipmentStoreType.installin);
//			equipmentStore.setType_id(installin.getId());
//			equipmentStoreRepository.create(equipmentStore);
			//插入到workunit中
			EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
			equipmentWorkunit.setEcode(equipment.getEcode());
			equipmentWorkunit.setWorkunit_id(outStore.getWorkUnit_id());
			equipmentWorkunit.setInDate(new Date());
			equipmentWorkunit.setNum(1);
			equipmentWorkunit.setType(EquipmentWorkunitType.installout);
			equipmentWorkunit.setType_id(outstore_id);
			equipmentWorkunit.setFrom_id(outStore.getStore_id());
			equipmentWorkunitRepository.create(equipmentWorkunit);
			
			
			//插入入库单明细
			InstallOutList inStoreList=new InstallOutList();
			inStoreList.setEcode(equipment.getEcode());
			inStoreList.setInstallOut_id(outstore_id);
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
