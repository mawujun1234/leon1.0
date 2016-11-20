package com.mawujun.check;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentCycleService;
import com.mawujun.baseinfo.EquipmentPole;
import com.mawujun.baseinfo.EquipmentPolePK;
import com.mawujun.baseinfo.EquipmentPoleRepository;
import com.mawujun.baseinfo.EquipmentRepair;
import com.mawujun.baseinfo.EquipmentRepairPK;
import com.mawujun.baseinfo.EquipmentRepairRepository;
import com.mawujun.baseinfo.EquipmentStore;
import com.mawujun.baseinfo.EquipmentStorePK;
import com.mawujun.baseinfo.EquipmentStoreRepository;
import com.mawujun.baseinfo.EquipmentWorkunit;
import com.mawujun.baseinfo.EquipmentWorkunitPK;
import com.mawujun.baseinfo.EquipmentWorkunitRepository;
import com.mawujun.baseinfo.OperateType;
import com.mawujun.baseinfo.TargetType;
import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CheckService extends AbstractService<Check, String>{

	@Autowired
	private CheckRepository checkRepository;
//	@Autowired
//	private CheckListRepository checkListRepository;
	@Autowired
	private TrimRepository trimRepository;
	@Autowired
	private EquipmentPoleRepository equipmentPoleRepository;
	@Autowired
	private EquipmentWorkunitRepository equipmentWorkunitRepository;
	@Autowired
	private EquipmentStoreRepository equipmentStoreRepository;
	@Autowired
	private EquipmentRepairRepository equipmentRepairRepository;
	@Autowired
	private EquipmentCycleService equipmentCycleService;
	
	SimpleDateFormat yyyyMMddHHmmssDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public CheckRepository getRepository() {
		return checkRepository;
	}
	
	public void complete(String check_id) {
		Check check=checkRepository.get(check_id);
		check.setStatus(CheckStatus.complete);
		check.setCompleteDate(new Date());
		check.setCompleter(ShiroUtils.getUserId());
		checkRepository.update(check);
	}
	
	public List<EquipmentVO1> queryScanEquipment(String check_id) {	

		return checkRepository.queryScanEquipment(check_id);
	}
	public List<EquipmentVO1> queryPoleEquipment(String pole_id) {	

		return checkRepository.queryPoleEquipment(pole_id);
	}

//	public void createCheckList(String check_id,String task_id){
//		checkRepository.createCheckList(check_id, task_id);
//	}
	/**
	 * 转移，就时从另外一个点位，仓库或者作业单位上卸载下来，
	 * 然后这边点位上安装
	 * @param trim
	 */
	public void transfer(Trim trim) {	
		String id=yyyyMMddHHmmssDateFormat.format(new Date());
		trim.setId(id);
		trim.setCreateDate(new Date());
		trim.setCreater(ShiroUtils.getUserId());
		trim.setTrimType(TrimType.transfer);
		trimRepository.create(trim);
		
		//其他位置（点位，仓库，作业单位，维修中心）上的设备要减掉，为了通用exchange（）会调用
		if(trim.getOrginal_type()==TargetType.pole){
			EquipmentPolePK pk=new EquipmentPolePK();
			pk.setEcode(trim.getEcode());
			pk.setPole_id(trim.getOrginal_id());
			EquipmentPole aaa=equipmentPoleRepository.get(pk);
			if(aaa==null){
				throw new BusinessException("该设备不在点位上了，请检查！");
			}
			equipmentPoleRepository.delete(aaa);
		} else if(trim.getOrginal_type()==TargetType.workunit){
			EquipmentWorkunitPK pk=new EquipmentWorkunitPK();
			pk.setEcode(trim.getEcode());
			pk.setWorkunit_id(trim.getOrginal_id());
			EquipmentWorkunit aaa=equipmentWorkunitRepository.get(pk);
			if(aaa==null){
				throw new BusinessException("该设备不在作业单位上了，请检查！");
			}
			equipmentWorkunitRepository.delete(aaa);
			
		} else if(trim.getOrginal_type()==TargetType.repair){
			EquipmentRepairPK pk=new EquipmentRepairPK();
			pk.setEcode(trim.getEcode());
			pk.setRepair_id(trim.getOrginal_id());
			EquipmentRepair aaa=equipmentRepairRepository.get(pk);
			if(aaa==null){
				throw new BusinessException("该设备不在维修中心上了，请检查！");
			}
			equipmentRepairRepository.delete(aaa);
		} else if(trim.getOrginal_type()==TargetType.store){
			EquipmentStorePK pk=new EquipmentStorePK();
			pk.setEcode(trim.getEcode());
			pk.setStore_id(trim.getOrginal_id());
			EquipmentStore aaa=equipmentStoreRepository.get(pk);
			if(aaa==null){
				throw new BusinessException("该设备不在仓库上了，请检查！");
			}
			equipmentStoreRepository.delete(aaa);
		}
		
		
		//当前位置（点位，仓库，作业单位，维修中心）上加上这个设备，为了通用exchange（）会调用
		if(trim.getTarget_type()==TargetType.pole){
			EquipmentPole aaa=new EquipmentPole();
			aaa.setEcode(trim.getEcode());
			aaa.setPole_id(trim.getTarget_id());
			aaa.setFrom_id(trim.getOrginal_id());		
			aaa.setInDate(new Date());
			aaa.setNum(1);
			//aaa.setType(EquipmentPoleType.install);
			aaa.setType_id(trim.getId());
			equipmentPoleRepository.create(aaa);
		} else if(trim.getTarget_type()==TargetType.workunit){
			EquipmentWorkunit aaa=new EquipmentWorkunit();
			aaa.setEcode(trim.getEcode());
			aaa.setWorkunit_id(trim.getTarget_id());
			aaa.setFrom_id(trim.getOrginal_id());		
			aaa.setInDate(new Date());
			aaa.setNum(1);
			aaa.setType_id(trim.getId());
			equipmentWorkunitRepository.create(aaa);
			
		} else if(trim.getTarget_type()==TargetType.repair){
			EquipmentRepair aaa=new EquipmentRepair();
			aaa.setEcode(trim.getEcode());
			aaa.setRepair_id(trim.getTarget_id());
			aaa.setFrom_id(trim.getOrginal_id());		
			aaa.setInDate(new Date());
			aaa.setNum(1);
			aaa.setType_id(trim.getId());
			//aaa.setType(type);
			
			equipmentRepairRepository.create(aaa);
		} else if(trim.getTarget_type()==TargetType.store){
			EquipmentStore aaa=new EquipmentStore();
			aaa.setEcode(trim.getEcode());
			aaa.setStore_id(trim.getTarget_id());
			aaa.setFrom_id(trim.getOrginal_id());		
			aaa.setInDate(new Date());
			aaa.setNum(1);
			aaa.setType_id(trim.getId());
			
			equipmentStoreRepository.create(aaa);
		}
		
		//在生命周期中纪录这个设备的调整，类型为盘点
		equipmentCycleService.logEquipmentCycle(trim.getEcode(), OperateType.check, trim.getId(), trim.getTarget_type(),trim.getTarget_id(),"盘点处理");
		
		
	}
	/**
	 * 卸载，把点位上多余的设备卸载下来，放在作业单位身上，作业单位就时在页面中选，因为以后同个点位可能会属于两个作业单位进行管理
	 * @param trim
	 * @return
	 */
	public void uninstall(Trim trim) {
		String id=yyyyMMddHHmmssDateFormat.format(new Date());
		trim.setId(id);
		trim.setCreateDate(new Date());
		trim.setCreater(ShiroUtils.getUserId());
		trim.setTrimType(TrimType.uninstall);
		trimRepository.create(trim);
		
		//点位上减掉这个设备
		EquipmentPolePK pk=new EquipmentPolePK();
		pk.setEcode(trim.getEcode());
		pk.setPole_id(trim.getOrginal_id());
		EquipmentPole aaa=equipmentPoleRepository.get(pk);
		if(aaa==null){
			throw new BusinessException("该设备不在点位上了，请检查！");
		}
		equipmentPoleRepository.delete(aaa);
		
		//作业单位身上挂上这个设备
		EquipmentWorkunit equipmentWorkunit=new EquipmentWorkunit();
		equipmentWorkunit.setEcode(trim.getEcode());
		equipmentWorkunit.setWorkunit_id(trim.getTarget_id());
		equipmentWorkunit.setFrom_id(trim.getOrginal_id());		
		equipmentWorkunit.setInDate(new Date());
		equipmentWorkunit.setNum(1);
		equipmentWorkunit.setType_id(trim.getId());
		equipmentWorkunitRepository.create(equipmentWorkunit);
		
		//在神明周期中纪录这个设备的调整
		equipmentCycleService.logEquipmentCycle(trim.getEcode(), OperateType.check, trim.getId(), trim.getTarget_type(),trim.getTarget_id(),"盘点处理");
		
	}
	/**
	 * 交换两个设备的安装位置
	 * @param scan_eqip
	 * @param pole_eqip
	 * @return
	 */
	public void exchange(Trim scan_eqip,Trim pole_eqip) {
		//两个设备都时转移
		transfer(scan_eqip);
		//防止两个id一致
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transfer(pole_eqip);
	}
	
}
