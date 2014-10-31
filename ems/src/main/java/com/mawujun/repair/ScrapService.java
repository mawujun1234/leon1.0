package com.mawujun.repair;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;


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
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	
	SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public ScrapRepository getRepository() {
		return scrapRepository;
	}
	
	public String create(Scrap scrap) {
		scrap.setId(ymdHmsDateFormat.format(new Date()));
		super.create(scrap);
		return scrap.getId();
	}
	
	//如果报废单还没有建立，就先建立报废单，如果已经建立过了，就走下面的流程
	public Scrap scrap(Scrap scrap) {
		if(!StringUtils.hasText(scrap.getId())){
			scrap.setScrpReqDate(new Date());
			scrap.setScrpReqOper(ShiroUtils.getAuthenticationInfo().getId());
			this.create(scrap);
		}
		//把维修单状态改为"报废中"
		repairRepository.update(Cnd.update().set(M.Repair.status, RepairStatus.Five.getValue()).andEquals(M.Repair.id, scrap.getRepair_id()));
		
		return scrap;
	}
	
	public Scrap makeSureScrap(Scrap scrap) {
		//修改设备状态为报废
		equipmentRepository.update(Cnd.update().set(M.Equipment.status, EquipmentStatus.scrapped.getValue()).andEquals(M.Equipment.ecode, scrap.getEcode()));
		
		Date date=new Date();
		scrap.setOperateDate(date);
		scrap.setScrpReqOper(ShiroUtils.getAuthenticationInfo().getId());
		this.update(scrap);
		
		//同时结束维修单--报废
		Repair repair=repairRepository.get(scrap.getRepair_id());
		repair.setStatus(RepairStatus.Six.getValue());
		repair.setScrapDate(date);
		repairRepository.update(repair);
		//repairRepository.update(Cnd.update().set(M.Repair.status, RepairStatus.Six.getValue()).andEquals(M.Repair.id, scrap.getRepair_id()));
		return scrap;
	}

}
