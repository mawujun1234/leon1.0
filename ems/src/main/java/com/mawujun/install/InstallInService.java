package com.mawujun.install;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentStore;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentStoreType;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitPK;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
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
	private EquipmentStoreRepository equipmentStoreRepository;
	@Autowired
	private EquipmentWorkunitRepository equipmentWorkunitRepository;
	@Autowired
	private InstallInListRepository installInListRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallInRepository getRepository() {
		return installInRepository;
	}
	
	public InstallInListVO getEquipmentByEcode(String ecode,String workunit_id) {
		//如果存在，再判断该设备是不是借用设备，如果是借用设备就不能返还
		EquipmentWorkunit equipmentWorkunit=equipmentRepository.getBorrowEquipmentWorkunit(ecode);
		if(equipmentWorkunit!=null){
			throw new BusinessException("该设备是借用设备,不能在这里进行返回!");
		}
				
		InstallInListVO equipment= installInRepository.getEquipmentByEcode(ecode,workunit_id);
		
		//若果这个设备部在当前的作业单位身上
		if(equipment==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("该条码对应的设备不存在，或者该设备挂在其他作业单位或已经入库了!");
		}
		
		return equipment;

//		if(equipment==null){
//			//equipment=new Equipment();
//			//equipment.setStatus(0);
//			throw new BusinessException("该条码对应的设备不存在，或者该设备挂在其他作业单位或已经入库了!");
//		}
//		//设备返库的时候，设备如果不是手持或损坏状态的话，就不能进行返库，说明任务没有扫描或者没有提交
//		if(equipment.getStatus()!=EquipmentStatus.out_storage ){
//			throw new BusinessException("设备状态不对,不是作业单位手中持有的设备!");
//		}
//		d
//		return equipment;
	}
	
	/**
	 * 领用返回的时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipments
	 * @param installin
	 */
	public void equipmentInStore(InstallInList[] equipments, InstallIn installin) { 
		String installin_id = ymdHmsDateFormat.format(new Date());
		// InStore inStore=new InStore();
		installin.setId(installin_id);
		installin.setOperateDate(new Date());
		installin.setOperater(ShiroUtils.getAuthenticationInfo().getId());
		//outStore.setType(1);
		installInRepository.create(installin);
		
		for(InstallInList list:equipments){
			
			//InstallInList list=new InstallInList();
			//list.setEcode(equipment.getEcode());
			list.setInstallIn_id(installin_id);
			
			//如果设备状态时损坏，就把设备状态改为 入库待维修，否则就修改为在库
			//把设备挂到相应的仓库上
			//同时减持设备挂在作业单位
			if(installin.getType()==InstallInType.bad){
				list.setIsBad(true);
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.wait_for_repair)
						//.set(M.Equipment.store_id, installin.getStore_id())
						//.set(M.Equipment.workUnit_id,null)
						.set(M.Equipment.place, EquipmentPlace.store)
						.set(M.Equipment.last_installIn_id,installin_id)
						.set(M.Equipment.last_workunit_id,installin.getWorkUnit_id())
						.andEquals(M.Equipment.ecode, list.getEcode()));
			} else {
				list.setIsBad(false);
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage)
						//.set(M.Equipment.store_id, installin.getStore_id())
						//.set(M.Equipment.workUnit_id,null)
						.set(M.Equipment.place, EquipmentPlace.store)
						.set(M.Equipment.last_installIn_id,installin_id)//最后一次入库的入库id，这个字段是返库，无论坏件还是好贱返库的时候的id
						.set(M.Equipment.last_workunit_id,installin.getWorkUnit_id())
						.andEquals(M.Equipment.ecode, list.getEcode()));
			}
			
			//插入仓库中
			EquipmentStore equipmentStore=new EquipmentStore();
			equipmentStore.setEcode(list.getEcode());
			equipmentStore.setStore_id(installin.getStore_id());
			equipmentStore.setNum(1);
			equipmentStore.setInDate(new Date());
			equipmentStore.setType(EquipmentStoreType.installin);
			equipmentStore.setType_id(installin.getId());
			equipmentStore.setFrom_id(installin.getWorkUnit_id());
			equipmentStoreRepository.create(equipmentStore);
			//workunit减掉这个设备
			EquipmentWorkunitPK equipmentWorkunitPK=new EquipmentWorkunitPK();
			equipmentWorkunitPK.setEcode(list.getEcode());
			equipmentWorkunitPK.setWorkunit_id(installin.getWorkUnit_id());
			equipmentWorkunitRepository.deleteById(equipmentWorkunitPK);
			
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
