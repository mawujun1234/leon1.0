package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentCycleService;
import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentStorePK;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
import com.mawujun.baseinfo.EquipmentWorkunitType;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.baseinfo.WorkUnitService;
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
	@Autowired
	private WorkUnitService workUnitService;
	@Autowired
	private EquipmentCycleService equipmentCycleService;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallOutRepository getRepository() {
		return outStoreRepository;
	}

	public String equipOutStore(InstallOutList[] installOutListes, InstallOut outStore) {
		// 插入入库单
		String outstore_id = ymdHmsDateFormat.format(new Date());
		// InStore inStore=new InStore();
		outStore.setId(outstore_id);
		outStore.setOperateDate(new Date());
		outStore.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		//outStore.setType(1);
		outStoreRepository.create(outStore);
		
		for(InstallOutList inStoreList:installOutListes){
			//更新设备状态为出库待安装
			//把设备绑定到作业单位上面
			//把仓库中的该设备移除
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_storage)
					.set(M.Equipment.last_workunit_id, outStore.getWorkUnit_id())
					//.set(M.Equipment.store_id, null)
					.set(M.Equipment.place, EquipmentPlace.workunit)
					.andEquals(M.Equipment.ecode, inStoreList.getEcode()));
			
			//从仓库中删除
			EquipmentStorePK equipmentStorePK=new EquipmentStorePK();
			equipmentStorePK.setEcode(inStoreList.getEcode());
			equipmentStorePK.setStore_id(outStore.getStore_id());
			equipmentStoreRepository.deleteById(equipmentStorePK);
//			equipmentStore.setNum(1);
//			equipmentStore.setInDate(new Date());
//			equipmentStore.setType(EquipmentStoreType.installin);
//			equipmentStore.setType_id(install_in.getId());
//			equipmentStoreRepository.create(equipmentStore);
			//插入到workunit中
			EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
			equipmentWorkunit.setEcode(inStoreList.getEcode());
			equipmentWorkunit.setWorkunit_id(outStore.getWorkUnit_id());
			equipmentWorkunit.setInDate(new Date());
			equipmentWorkunit.setNum(1);
			equipmentWorkunit.setType(EquipmentWorkunitType.installout);
			equipmentWorkunit.setType_id(outstore_id);
			equipmentWorkunit.setFrom_id(outStore.getStore_id());
			equipmentWorkunitRepository.create(equipmentWorkunit);
			
			
//			//插入入库单明细
//			InstallOutList inStoreList=new InstallOutList();
//			inStoreList.setEcode(equipment.getEcode());
			inStoreList.setInstallOut_id(outstore_id);
			outStoreListRepository.create(inStoreList);
			
			//记录设备入库的生命周期
			equipmentCycleService.logEquipmentCycle(inStoreList.getEcode(), OperateType.install_out, outstore_id,TargetType.workunit,outStore.getWorkUnit_id());
		}
		return outstore_id;
	}
	
	public Page queryMain(Page page){

		return outStoreRepository.queryMain(page);

		
	}
	
	public InstallOutVO getInstallOutVO(String installOut_id){
		return outStoreRepository.getInstallOutVO(installOut_id);
	}
	public List<InstallOutListVO> queryList(String installOut_id) {

		return outStoreRepository.queryList(installOut_id);

	}
}
