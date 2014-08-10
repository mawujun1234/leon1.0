package com.mawujun.repair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.service.AbstractService;


import com.mawujun.shiro.ShiroUtils;
import com.mawujun.store.Barcode;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.exception.BusinessException;
import com.mawujun.install.StoreEquipment;
import com.mawujun.install.StoreEquipmentRepository;
import com.mawujun.repair.Repair;
import com.mawujun.repair.RepairRepository;
import com.mawujun.repository.cnd.Cnd;


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
	private StoreEquipmentRepository storeEquipmentRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public RepairRepository getRepository() {
		return repairRepository;
	}
	
	
	public RepairVO getRepairVOByEcode(String ecode) {
		RepairVO repairvo= repairRepository.getRepairVOByEcode(ecode);
		return repairvo;	
	}
	
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
	
	public Page storeQuery(Page page){
		List<Store> stores=storeRepository.queryAll();
		Page results=repairRepository.storeQuery(page);
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
		for(Repair repair:repairs){
			//修改设备状态为“维修中”
			equipmentRepository.update(Cnd.update().set(M.Equipment.status, 7).andEquals(M.Equipment.ecode, repair.getEcode()));
			//修改维修单状态为"维修中"
			repairRepository.update(Cnd.update().set(M.Repair.status, RepairStatus.Two.getValue()).andEquals(M.Repair.id, repair.getId()));
			//把设备中参仓库，移到 维修中心来,原来的仓库减1，维修中心如果没有该设备，就添加该设备，否则就加1
			storeEquipmentRepository.updateNum(repair.getStr_out_id(), repair.getEcode(), "num-1");
			//维修中心数据要添加了
			Long count=storeEquipmentRepository.queryCount(Cnd.count(M.StoreEquipment.id).andEquals(M.StoreEquipment.ecode, repair.getEcode())
					.andEquals(M.StoreEquipment.inStore_id, repair.getRpa_id()));
			if(count>0){
				storeEquipmentRepository.updateNum(repair.getRpa_id(), repair.getEcode(), "num+1");
			} else {
				StoreEquipment storeEquipment=new StoreEquipment();
				storeEquipment.setStore_id(repair.getRpa_id());
				storeEquipment.setEcode(repair.getEcode());
				storeEquipment.setNum(1);
				storeEquipmentRepository.create(storeEquipment);
			}
			
		}
		
		
	}
	
	public void repairOutStore(String[] ids,String[] ecodes){
		
	}
	
}
