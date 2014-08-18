package com.mawujun.repair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.exception.BusinessException;
import com.mawujun.install.InstallIn;
import com.mawujun.install.InstallInRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
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
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private InstallInRepository installInRepository;
	
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
		//获取报修人的相关信息，和报修单id
		InstallIn installIn=installInRepository.getInstallInByEcode(ecode);
		if(installIn!=null){
			repairvo.setWorkunit_id(installIn.getWorkUnit_id());
			repairvo.setRepair_date(installIn.getOperateDate());
			repairvo.setInstallIn_id(installIn.getId());
		}

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
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, 6).andEquals(M.Equipment.ecode, repair.getEcode()));
			
			String id=id_prev+StringUtils.leftPad(i+"", 3, '0');
			i++;
			repair.setId(id);
			//维修单的状态修改为 发往维修中心
			repair.setStatus(RepairStatus.One.getValue());
			repair.setStr_out_date(new Date());
			repair.setStr_out_oper_id(oper_id);
			repairRepository.create(repair);	
		}
		
	}
	public Page storeMgrQuery(Page page){
		List<Store> stores=storeRepository.queryAll();
		Page results=repairRepository.storeMgrQuery(page);
		List<RepairVO> list=results.getResult();
		for(RepairVO repairVO:list){
			for(Store store:stores){
				if(store.getId().equals(repairVO.getStr_out_id())){
					repairVO.setStr_out_name(store.getName());
				} else if(store.getId().equals(repairVO.getRpa_id())){
					repairVO.setRpa_name(store.getName());
				} else if(store.getId().equals(repairVO.getStr_in_id())){
					repairVO.setStr_in_name(store.getName());
				}
			}
		}
		return results;
	}
	public Page repairInQuery(Page page){
		List<Store> stores=storeRepository.queryAll();
		Page results=repairRepository.repairInQuery(page);
		List<RepairVO> list=results.getResult();
		for(RepairVO repairVO:list){
			for(Store store:stores){
				if(store.getId().equals(repairVO.getStr_out_id())){
					repairVO.setStr_out_name(store.getName());
				} else if(store.getId().equals(repairVO.getRpa_id())){
					repairVO.setRpa_name(store.getName());
				} else if(store.getId().equals(repairVO.getStr_in_id())){
					repairVO.setStr_in_name(store.getName());
				}
			}
		}
		return results;
	}
	public Page repairMgrQuery(Page page){
		List<Store> stores=storeRepository.queryAll();
		Page results=repairRepository.repairMgrQuery(page);
		List<RepairVO> list=results.getResult();
		for(RepairVO repairVO:list){
			for(Store store:stores){
				if(store.getId().equals(repairVO.getStr_out_id())){
					repairVO.setStr_out_name(store.getName());
				} else if(store.getId().equals(repairVO.getRpa_id())){
					repairVO.setRpa_name(store.getName());
				} else if(store.getId().equals(repairVO.getStr_in_id())){
					repairVO.setStr_in_name(store.getName());
				}
			}
		}
		return results;
	}
	
	public void repairInStore(Repair[] repairs){
		String oper_id=ShiroUtils.getAuthenticationInfo().getId();
		for(Repair repair:repairs){
			//修改设备状态为“维修中”
			//把设备中参仓库，移到 维修中心来
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, 7).set(M.Equipment.store_id, repair.getRpa_id()).andEquals(M.Equipment.ecode, repair.getEcode()));
			//修改维修单状态为"维修中"	
			repairRepository.update(Cnd.update()
					.set(M.Repair.rpa_in_oper_id, oper_id)
					.set(M.Repair.rpa_in_date, new Date())
					.set(M.Repair.status, RepairStatus.Two.getValue())
					.andEquals(M.Repair.id, repair.getId()));
//			//把设备中参仓库，移到 维修中心来,原来的仓库减1，维修中心如果没有该设备，就添加该设备，否则就加1
//			storeEquipmentRepository.updateNum(repair.getStr_out_id(), repair.getEcode(), "num-1");
//			//维修中心数据要添加了
//			Long count=storeEquipmentRepository.queryCount(Cnd.count(M.StoreEquipment.id).andEquals(M.StoreEquipment.ecode, repair.getEcode())
//					.andEquals(M.StoreEquipment.store_id, repair.getRpa_id()));
//			if(count>0){
//				storeEquipmentRepository.updateNum(repair.getRpa_id(), repair.getEcode(), "num+1");
//			} else {
//				StoreEquipment storeEquipment=new StoreEquipment();
//				storeEquipment.setStore_id(repair.getRpa_id());
//				storeEquipment.setEcode(repair.getEcode());
//				storeEquipment.setNum(1);
//				storeEquipmentRepository.create(storeEquipment);
//			}
//			
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
			//修改设备状态为"返库途中"
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, 9).andEquals(M.Equipment.ecode, repair.getEcode()));
			//维修单的状态也改为"返库途中"
			repairRepository.update(Cnd.update()
					.set(M.Repair.rpa_out_oper_id, oper_id)
					.set(M.Repair.rpa_out_date, new Date())
					.set(M.Repair.status, RepairStatus.Three.getValue()).andEquals(M.Repair.id, repair.getId()));
		}
	}
	
	/**
	 * 仓库入库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ids
	 * @param ecodes
	 */
	public void storeInStore(Repair[] repairs,String str_in_id){
		String oper_id=ShiroUtils.getAuthenticationInfo().getId();
		for(Repair repair:repairs){
			//修改设备状态为"已入库"
			//设备从维修中心挂到仓库中
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, 1).set(M.Equipment.store_id, repair.getStr_in_id()).andEquals(M.Equipment.ecode, repair.getEcode()));
			//维修单的状态也改为"完成"
			repairRepository.update(Cnd.update()
					.set(M.Repair.str_in_oper_id, oper_id)
					.set(M.Repair.str_in_date, new Date())
					.set(M.Repair.str_in_id, repair.getStr_in_id())
					.set(M.Repair.status, RepairStatus.Four.getValue()).andEquals(M.Repair.id, repair.getId()));
//			//设备从维修中心挂到仓库中
//			storeEquipmentRepository.updateNum(repair.getRpa_id(), repair.getEcode(), "num-1");
//			//注意选择的是入库仓库
//			Long count=storeEquipmentRepository.queryCount(Cnd.count(M.StoreEquipment.id).andEquals(M.StoreEquipment.ecode, repair.getEcode())
//					.andEquals(M.StoreEquipment.store_id, repair.getStr_in_id()));
//			if(count>0){
//				storeEquipmentRepository.updateNum(repair.getStr_in_id(), repair.getEcode(), "num+1");
//			} else {
//				StoreEquipment storeEquipment=new StoreEquipment();
//				storeEquipment.setStore_id(repair.getStr_in_id());
//				storeEquipment.setEcode(repair.getEcode());
//				storeEquipment.setNum(1);
//				storeEquipmentRepository.create(storeEquipment);
//			}
			
			
		}
	}
	

	
}
