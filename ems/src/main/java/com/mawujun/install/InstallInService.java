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
import com.mawujun.baseinfo.EquipmentStore;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentStoreType;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitPK;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
import com.mawujun.baseinfo.EquipmentWorkunitType;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.baseinfo.WorkUnitService;
import com.mawujun.exception.BusinessException;
import com.mawujun.mobile.task.LockEquipmentService;
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
	@Autowired
	private InstallOutListRepository installOutListRepository;
	@Autowired
	private WorkUnitService workUnitService;
	@Autowired
	private EquipmentCycleService equipmentCycleService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private LockEquipmentService lockEquipmentService;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public InstallInRepository getRepository() {
		return installInRepository;
	}
	
	public InstallInListVO getEquipmentByEcode(String ecode,String workunit_id) {
		//如果存在，再判断该设备是不是借用设备，如果是借用设备就不能返还
//		EquipmentWorkunit equipmentWorkunit=equipmentRepository.getBorrowEquipmentWorkunit(ecode);
//		if(equipmentWorkunit!=null){
//			throw new BusinessException("该设备是借用设备,不能在这里进行返回!");
//		}
		//判断这个设备有没有被任务锁定
		lockEquipmentService.check_locked(ecode, null);
		
		EquipmentWorkunitPK equipmentWorkunitPK=new EquipmentWorkunitPK();
		equipmentWorkunitPK.setEcode(ecode);
		equipmentWorkunitPK.setWorkunit_id(workunit_id);
		
		EquipmentWorkunit equipmentWorkunit=equipmentWorkunitRepository.get(equipmentWorkunitPK);
		if(equipmentWorkunit==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("该条码对应的设备不存在，或者该设备挂在其他作业单位或已经入库了!");
		}
		//如果存在，再判断该设备是不是借用设备，如果是借用设备就不能返还
		if(equipmentWorkunit.getType()==EquipmentWorkunitType.borrow){
			throw new BusinessException("该设备是借用设备,不能在这里进行返回!");
		}
		
		InstallInListVO installInListVO= installInRepository.getEquipmentByEcode(ecode,workunit_id);
		//若果这个设备不在当前的作业单位身上
		if(installInListVO==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("该条码对应的设备不存在，或者该设备挂在其他作业单位或已经入库了!");
		}
		//如果是领用出去，然后直接返回的
		if(equipmentWorkunit.getType()==EquipmentWorkunitType.installout){
			//如果领出去然后直接返回时，这个设备还是挂在workunit中，并且最新的单据还是领用单
			installInListVO.setInstallInListType(InstallInListType.installout);
			installInListVO.setInstallout_id(equipmentWorkunit.getType_id());
		} else {
			//暂时是，不是领用返回就是拆回返回
			installInListVO.setInstallInListType(InstallInListType.takedown);
		}

		installInListVO.setProject_id(equipmentWorkunit.getProject_id());
		return installInListVO;
	}
	
	/**
	 * 领用返回的时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipments
	 * @param install_in
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
						//.set(M.Equipment.store_id, install_in.getStore_id())
						//.set(M.Equipment.workUnit_id,null)
						.set(M.Equipment.place, EquipmentPlace.store)
						.set(M.Equipment.last_installIn_id,installin_id)
						.set(M.Equipment.last_workunit_id,installin.getWorkUnit_id())
						.andEquals(M.Equipment.ecode, list.getEcode()));
			} else {
				list.setIsBad(false);
				equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage)
						//.set(M.Equipment.store_id, install_in.getStore_id())
						//.set(M.Equipment.workUnit_id,null)
						.set(M.Equipment.place, EquipmentPlace.store)
						.set(M.Equipment.last_installIn_id,installin_id)//最后一次入库的入库id，这个字段是返库，无论坏件还是好贱返库的时候的id
						.set(M.Equipment.last_workunit_id,installin.getWorkUnit_id())
						.andEquals(M.Equipment.ecode, list.getEcode()));
			}
			//更新InstallOut中的返回时间，
			//InstallInList中的installOut_id就是在getEquipmentByEcode中就设置好了
			installOutListRepository.update(Cnd.update().set(M.InstallOutList.returnDate, new Date())
					.set(M.InstallOutList.isReturn, true)
					.andEquals(M.InstallOutList.installOut_id, list.getInstallout_id())
					.andEquals(M.InstallOutList.ecode, list.getEcode()));
			
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
			
			//这里没有修改InstallOutList中的InstallOutListType为借用或领用，因为默认借用单和领用单的明细都是借用，只有在真正使用之后才会变成领用
	
			//添加明细
			installInListRepository.create(list);
			
			//记录设备入库的生命周期
			equipmentCycleService.logEquipmentCycle(list.getEcode(), OperateType.install_in, list.getInstallIn_id(),TargetType.store,installin.getStore_id(),installin.getMemo());
		}
	}

	public Page queryMain(Page page){

		return installInRepository.queryMain(page);

		
	}
	public List<InstallInListVO> queryList(String installIn_id) {

		return installInRepository.queryList(installIn_id);

	}
	
}
