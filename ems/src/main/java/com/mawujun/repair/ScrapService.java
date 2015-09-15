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
import com.mawujun.baseinfo.EquipmentRepairPK;
import com.mawujun.baseinfo.EquipmentRepairRepository;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ScrapService extends AbstractService<Scrap, String>{

	@Autowired
	private ScrapRepository scrapRepository;
	@Autowired
	private RepairRepository repairRepository;
	//@Autowired
	//private StoreRepository storeRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private EquipmentRepairRepository equipmentRepairRepository;
	
	@Autowired
	private EquipmentCycleService equipmentCycleService;
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public ScrapRepository getRepository() {
		return scrapRepository;
	}
	
	/**
	 * 点保存的时候，
	 */
	public String create(Scrap scrap) {
		scrap.setId(ymdHmsDateFormat.format(new Date()));
		super.create(scrap);
		return scrap.getId();
	}
	
	//如果报废单还没有建立，就先建立报废单，如果已经建立过了，就走下面的流程
	public Scrap scrap(Scrap scrap) {
		if(!StringUtils.hasText(scrap.getId())){
			this.create(scrap);
		}
		
		scrap.setScrpReqDate(new Date());
		scrap.setScrpReqOper(ShiroUtils.getAuthenticationInfo().getId());
		scrap.setStatus(ScrapStatus.scrap_confirm);
		
		Repair repair=repairRepository.get(scrap.getRepair_id());
		scrap.setStore_id(repair.getStr_out_id());//以设备出来的仓库作为报废单接收仓库
		scrap.setRpa_id(repair.getRpa_id());
		this.update(scrap);
		

		//把维修单状态改为"报废中"
		repairRepository.update(Cnd.update().set(M.Repair.status, RepairStatus.scrap_confirm).andEquals(M.Repair.id, scrap.getRepair_id()));
		
		return scrap;
	}
	
	public Scrap makeSureScrap(String scrap_id) {
		Scrap scrap=scrapRepository.get(scrap_id);
		scrap.setStatus(ScrapStatus.scrap);
		//修改设备状态为报废
		equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.scrapped)
				.set(M.Equipment.place, EquipmentPlace.scrap)
				.andEquals(M.Equipment.ecode, scrap.getEcode()));
		
		//获取维修中心的id
		Repair repair=repairRepository.get(scrap.getRepair_id());
		
		//从维修中心中删除
		EquipmentRepairPK equipmentRepairPK=new EquipmentRepairPK();
		equipmentRepairPK.setEcode(scrap.getEcode());
		equipmentRepairPK.setRepair_id(repair.getRpa_id());
		equipmentRepairRepository.deleteById(equipmentRepairPK);
		
		Date date=new Date();
		scrap.setOperateDate(date);
		scrap.setScrpReqOper(ShiroUtils.getAuthenticationInfo().getId());
		scrapRepository.update(scrap);
		
		//同时结束维修单--报废
		//Repair repair=repairRepository.get(scrap.getRepair_id());
		repair.setStatus(RepairStatus.scrap);
		repair.setScrapDate(date);
		repairRepository.update(repair);
		//repairRepository.update(Cnd.update().set(M.Repair.status, RepairStatus.scrap.getValue()).andEquals(M.Repair.id, scrap.getRepair_id()));
		
		//记录设备入库的生命周期,目标记录的是出库仓库
		equipmentCycleService.logEquipmentCycle(repair.getEcode(), OperateType.scrap, scrap.getId(),TargetType.store,scrap.getStore_id());
		return scrap;
	}

	
//	public Page queryScrapReport(Page page) {
//		return scrapRepository.queryScrapReport(page);
//	}
//	
//	public List<RepairVO> exportRepairReport(Params params) {
//		return scrapRepository.queryScrapReport(params);
//	}
}
