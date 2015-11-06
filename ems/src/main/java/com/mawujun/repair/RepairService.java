package com.mawujun.repair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentCycleService;
import com.mawujun.baseinfo.EquipmentPlace;
import com.mawujun.baseinfo.EquipmentRepair;
import com.mawujun.baseinfo.EquipmentRepairPK;
import com.mawujun.baseinfo.EquipmentRepairRepository;
import com.mawujun.baseinfo.EquipmentRepairType;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentStore;
import com.mawujun.baseinfo.EquipmentStorePK;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentStoreType;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.exception.BusinessException;
import com.mawujun.install.InstallIn;
import com.mawujun.install.InstallInRepository;
import com.mawujun.mobile.task.Task;
import com.mawujun.mobile.task.TaskRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.UserService;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class RepairService extends AbstractService<Repair, String>{

	@Autowired
	private RepairRepository repairRepository;
//	@Autowired
//	private StoreRepository storeRepository;
	@Autowired
	private StoreService storeService;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private EquipmentStoreRepository equipmentStoreRepository;
	@Autowired
	private EquipmentRepairRepository equipmentRepairRepository;
	@Autowired
	private InstallInRepository installInRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserService userService;
	
	@Autowired
	private EquipmentCycleService equipmentCycleService;
//	@Autowired
//	private StoreService storeService;
	//@Resource
	//private EquipmentService equipmentService;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public RepairRepository getRepository() {
		return repairRepository;
	}
	
	
	public RepairVO getRepairVOByEcode(String ecode,String store_id) {
		RepairVO repairvo= repairRepository.getRepairVOByEcode(ecode,store_id);
		if(repairvo==null){
			throw new BusinessException("该设备不在该仓库");
		}
		int count=repairRepository.checkEcodeIsInRepair(ecode);
		if(count>0){
			throw new BusinessException("该设备已经存在维修单!");
		}
//		//获取报修人的相关信息，和报修单id
//		InstallIn installIn=installInRepository.getInstallInByEcode(ecode);
//		if(installIn!=null){
//			repairvo.setWorkunit_id(installIn.getWorkUnit_id());
//			repairvo.setRepair_date(installIn.getOperateDate());
//			repairvo.setInstallIn_id(installIn.getId());
//		}

		//获取任务单的id和其中的故障描述,这里要考虑是否把任务单号直接放置在InstallInList里面
		return repairvo;	
	}
	

	/**
	 * 创建维修单
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param repairs
	 */
	public void newRepair(Repair[] repairs) {
		String id_prev=ymdHmsDateFormat.format(new Date());
		String oper_id=ShiroUtils.getAuthenticationInfo().getId();
		int i=0;
		for(Repair repair:repairs){
			//修改设备状态为 发往维修中心
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.to_repair).andEquals(M.Equipment.ecode, repair.getEcode()));
			
			String id=id_prev+StringUtils.leftPad(i+"", 3, '0');
			i++;
			repair.setId(id);
			//维修单的状态修改为 发往维修中心
			repair.setStatus(RepairStatus.to_repair);
			repair.setStr_out_date(new Date());
			repair.setStr_out_oper_id(oper_id);
			repair.setStr_in_id(repair.getStr_out_id());//出库和入库的仓库必须一致
			//获取报修时间
			InstallIn installIn=installInRepository.get(repair.getInstallIn_id());
			repair.setRepair_date(installIn.getOperateDate());
			if(repair.getTask_id()!=null && !"".equals(repair.getTask_id())){
				Task task=taskRepository.get(repair.getTask_id());
				repair.setBroken_memo(task.getHitchType()+"-"+task.getHitchReason());
			}
			
			
			
			repairRepository.create(repair);	
			
			//记录设备入库的生命周期,目标记录的是出库仓库
			equipmentCycleService.logEquipmentCycle(repair.getEcode(), OperateType.repair_store_out, repair.getId(),TargetType.repair,repair.getRpa_id());
		}
		
	}
	
	public List<EquipmentVO> queryBrokenEquipment(String store_id){
		return repairRepository.queryBrokenEquipment(store_id);
	}
	
	/**
	 * 把仓库中所有是坏的设备全部转换为维修单
	 * @author mawujun 16064988@qq.com 
	 * @param store_id
	 * @param rpa_id 维修中心id
	 * @return
	 */
	public void brokenEquipment2Repair(String store_id,String rpa_id) {
		List<Repair> repairs=repairRepository.queryBrokenEquipment2Reapir(store_id);
		for(Repair repair:repairs){
			repair.setRpa_id(rpa_id);
		}
		newRepair(repairs.toArray(new Repair[repairs.size()]));
		
//		List<EquipmentVO> brokenEquipments=repairRepository.queryBrokenEquipment(store_id);
//		Repair[] repairs=new Repair[brokenEquipments.size()];
//		int i=0;
//		for(EquipmentVO equipment:brokenEquipments){
//			Repair repair=new Repair();
//			repair.setEcode(equipment.getEcode());
//			repair.setProd_id(equipment.getPole_id());
//			repair.setWorkunit_id(equipment.getLast_workunit_id());
//			repair.setInstallIn_id(equipment.getLast_installIn_id());
//			repair.setTask_id(equipment.getLast_task_id());
//			//repair.setBroken_memo(task.getHitchReason());
//			repairs[i]=repair;
//		}
//		newRepair(repairs);
	}
	public Page storeMgrQuery(Page page){
		//List<Store> stores=storeRepository.queryAll();
		Page results=repairRepository.storeMgrQuery(page);
		List<RepairVO> list=results.getResult();
		for(RepairVO repairVO:list){
			if(storeService.get(repairVO.getStr_out_id())!=null){
				repairVO.setStr_out_name(storeService.get(repairVO.getStr_out_id()).getName());
			}
			if(storeService.get(repairVO.getRpa_id())!=null){
				repairVO.setRpa_name(storeService.get(repairVO.getRpa_id()).getName());
			}
			if(storeService.get(repairVO.getStr_in_id())!=null){
				repairVO.setStr_in_name(storeService.get(repairVO.getStr_in_id()).getName());
			}
			
			
			
		}
		return results;
	}
	
	
	public Page repairInQuery(Page page){
		//List<Store> stores=storeRepository.queryAll();
		Page results=repairRepository.repairInQuery(page);
		List<RepairVO> list=results.getResult();
		for(RepairVO repairVO:list){
//			for(Store store:stores){
//				if(store.getId().equals(repairVO.getStr_out_id())){
//					repairVO.setStr_out_name(store.getName());
//				} else if(store.getId().equals(repairVO.getRpa_id())){
//					repairVO.setRpa_name(store.getName());
//				} else if(store.getId().equals(repairVO.getStr_in_id())){
//					repairVO.setStr_in_name(store.getName());
//				}
//			}
			if(storeService.get(repairVO.getStr_out_id())!=null){
				repairVO.setStr_out_name(storeService.get(repairVO.getStr_out_id()).getName());
			}
			if(storeService.get(repairVO.getRpa_id())!=null){
				repairVO.setRpa_name(storeService.get(repairVO.getRpa_id()).getName());
			}
			if(storeService.get(repairVO.getStr_in_id())!=null){
				repairVO.setStr_in_name(storeService.get(repairVO.getStr_in_id()).getName());
			}
		}
		return results;
	}
	public Page repairMgrQuery(Page page){
		//List<Store> stores=storeRepository.queryAll();
		Page results=repairRepository.repairMgrQuery(page);
		List<RepairVO> list=results.getResult();
		for(RepairVO repairVO:list){
//			for(Store store:stores){
//				if(store.getId().equals(repairVO.getStr_out_id())){
//					repairVO.setStr_out_name(store.getName());
//				} else if(store.getId().equals(repairVO.getRpa_id())){
//					repairVO.setRpa_name(store.getName());
//				} else if(store.getId().equals(repairVO.getStr_in_id())){
//					repairVO.setStr_in_name(store.getName());
//				}
//			}
			if(storeService.get(repairVO.getStr_out_id())!=null){
				repairVO.setStr_out_name(storeService.get(repairVO.getStr_out_id()).getName());
			}
			if(storeService.get(repairVO.getRpa_id())!=null){
				repairVO.setRpa_name(storeService.get(repairVO.getRpa_id()).getName());
			}
			if(storeService.get(repairVO.getStr_in_id())!=null){
				repairVO.setStr_in_name(storeService.get(repairVO.getStr_in_id()).getName());
			}
		}
		return results;
	}
	
	/**
	 * 获取已经存在的维修单
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	public RepairVO getRepairVOByEcodeStatus(String ecode,RepairStatus status) {
		//获取当前条码，状态不是完成的维修单，然后判断仓库和维修中心是否一致，如果不一致，就报错
		RepairVO repairVO= repairRepository.getRepairVOByEcodeStatus(ecode,status.toString());
		if(repairVO==null){
			//throw new BusinessException("该设备状态不对或者该设备不是维修设备!");
			return null;
		}
		repairVO.setStr_out_name(storeService.get(repairVO.getStr_out_id()).getName());
		repairVO.setStr_in_name(storeService.get(repairVO.getStr_in_id()).getName());
		repairVO.setRpa_name(storeService.get(repairVO.getRpa_id()).getName());
		
		if(repairVO.getRpa_user_id()!=null){
			
			repairVO.setRpa_user_name(userService.get(repairVO.getRpa_user_id()).getName());
		}
		
		return repairVO;
	}
	/**
	 * 维修中心入库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param repairs
	 */
	public void repairInStore(Repair[] repairs){
		String oper_id=ShiroUtils.getAuthenticationInfo().getId();
		for(Repair repair:repairs){
			//修改设备状态为“维修中”
			//把设备中参仓库，移到 维修中心来
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.outside_repairing)
					//.set(M.Equipment.store_id, repair.getRpa_id())
					.set(M.Equipment.place, EquipmentPlace.repair)
					.andEquals(M.Equipment.ecode, repair.getEcode()));
//			//把设备从仓库转移到维修中心中
//			Params p=Params.init().add(M.EquipmentStore.type, EquipmentStoreType.repair)
//					.add(M.EquipmentStore.type_id, repair.getId())
//					.add(M.EquipmentStore.ecode, repair.getEcode())
//					.add("store_in_id", repair.getRpa_id())//入库仓库，维修中心
//					.add("store_out_id", repair.getStr_out_id());
//			equipmentStoreRepository.changeStore(p);
			
			//从仓库中删除
			EquipmentStorePK equipmentStorePK=new EquipmentStorePK();
			equipmentStorePK.setEcode(repair.getEcode());
			equipmentStorePK.setStore_id(repair.getStr_out_id());
			equipmentStoreRepository.deleteById(equipmentStorePK);
			
			//插入到维修中心表中
			EquipmentRepair equipmentRepair=new EquipmentRepair();
			equipmentRepair.setEcode(repair.getEcode());
			equipmentRepair.setRepair_id(repair.getRpa_id());
			equipmentRepair.setNum(1);
			equipmentRepair.setInDate(new Date());
			equipmentRepair.setType(EquipmentRepairType.repair);
			equipmentRepair.setType_id(repair.getId());
			equipmentRepair.setFrom_id(repair.getStr_out_id());
			equipmentRepairRepository.create(equipmentRepair);
			
			
			//修改维修单状态为"维修中"	
			repairRepository.update(Cnd.update()
					.set(M.Repair.rpa_in_oper_id, oper_id)
					.set(M.Repair.rpa_in_date, new Date())
					.set(M.Repair.status, RepairStatus.repairing)
					.andEquals(M.Repair.id, repair.getId()));		
			
			//记录设备入库的生命周期
			equipmentCycleService.logEquipmentCycle(repair.getEcode(), OperateType.repair_in, repair.getId(),TargetType.repair,repair.getRpa_id());
		}
		
		
	}
	/**
	 * 维修中心出库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ids
	 * @param ecodes
	 */
	public void repairOutStore(Repair[] repairs){
		String oper_id=ShiroUtils.getAuthenticationInfo().getId();
		for(Repair repair:repairs){
			repair=repairRepository.get(repair.getId());
			//查询该维修单的维修人和故障原因有没有填写
			if(!StringUtils.hasText(repair.getRpa_user_id())){
				throw new BusinessException("维修单("+repair.getId()+")的维修人没有填写");
			}
			if(!StringUtils.hasText(repair.getBroken_reson())){
				throw new BusinessException("维修单("+repair.getId()+")的故障原因没有填写");
			}
			
			
			//修改设备状态为"返库途中"
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.out_repair).andEquals(M.Equipment.ecode, repair.getEcode()));
			//维修单的状态也改为"返库途中"
			repairRepository.update(Cnd.update()
					.set(M.Repair.rpa_out_oper_id, oper_id)
					.set(M.Repair.rpa_out_date, new Date())
					.set(M.Repair.status, RepairStatus.back_store).andEquals(M.Repair.id, repair.getId()));
			
			//记录设备入库的生命周期，//出库和入库的仓库必须一致，所以使用了str_out_id
			equipmentCycleService.logEquipmentCycle(repair.getEcode(), OperateType.repair_out, repair.getId(),TargetType.store,repair.getStr_in_id());
		}
	}
	
	/**
	 * 仓库入库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ids
	 * @param ecodes
	 */
	public void storeInStore(Repair[] repairs){
		String oper_id=ShiroUtils.getAuthenticationInfo().getId();
		for(Repair repair:repairs){
			//修改设备状态为"已入库"
			//设备从维修中心挂到仓库中
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.in_storage)
					//.set(M.Equipment.store_id, repair.getStr_in_id())
					.set(M.Equipment.place, EquipmentPlace.store)
					.andEquals(M.Equipment.ecode, repair.getEcode()));
			
//			//把设备从到维修中心转移到仓库
//			Params p=Params.init().add(M.EquipmentStore.type, EquipmentStoreType.repair)
//					.add(M.EquipmentStore.type_id, repair.getId())
//					.add(M.EquipmentStore.ecode, repair.getEcode())
//					.add("str_in_id", repair.getStr_in_id())//入库仓库
//					.add("store_out_id", repair.getRpa_id());//维修中心
//			equipmentStoreRepository.changeStore(p);
			
			//从维修中心中删除
			EquipmentRepairPK equipmentRepairPK=new EquipmentRepairPK();
			equipmentRepairPK.setEcode(repair.getEcode());
			equipmentRepairPK.setRepair_id(repair.getRpa_id());
			equipmentRepairRepository.deleteById(equipmentRepairPK);
			
			//插入到仓库
			EquipmentStore equipmentStore=new EquipmentStore();
			equipmentStore.setEcode(repair.getEcode());
			equipmentStore.setStore_id(repair.getStr_in_id());
			equipmentStore.setNum(1);
			equipmentStore.setInDate(new Date());
			equipmentStore.setType(EquipmentStoreType.repair);
			equipmentStore.setType_id(repair.getId());
			equipmentStore.setFrom_id(repair.getRpa_id());
			equipmentStoreRepository.create(equipmentStore);
			
			//维修单的状态也改为"完成"
			repairRepository.update(Cnd.update()
					.set(M.Repair.str_in_oper_id, oper_id)
					.set(M.Repair.str_in_date, new Date())
					.set(M.Repair.str_in_id, repair.getStr_in_id())
					.set(M.Repair.status, RepairStatus.over).andEquals(M.Repair.id, repair.getId()));
			
			//记录设备入库的生命周期
			equipmentCycleService.logEquipmentCycle(repair.getEcode(), OperateType.repair_store_in, repair.getId(),TargetType.store,repair.getStr_in_id());
	
		}
	}
	

//	public Page queryRepairReport(Page page) {
//		return repairRepository.queryRepairReport(page);
//	}
//	
//	public List<RepairVO> exportRepairReport(Params params) {
//		return repairRepository.queryRepairReport(params);
//	}
//	
//	public Page queryCompleteRepairReport(Page page) {
//		return repairRepository.queryCompleteRepairReport(page);
//	}
//	
//	public List<RepairVO> exportCompleteRepairReport(Params params) {
//		return repairRepository.queryCompleteRepairReport(params);
//	}
	
	
}
