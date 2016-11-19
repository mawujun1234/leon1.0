package com.mawujun.check;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
		
		//其他位置（点位，仓库，作业单位，维修中心）上的设备要减掉
		
		//当前位置（点位，仓库，作业单位，维修中心）上加上这个设备，为了通用exchange（）会调用
		
		//在生命周期中纪录这个设备的调整，类型为盘点
		
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
		
		//作业单位身上挂上这个设备
		
		//在神明周期中纪录这个设备的调整
		
		
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
		transfer(pole_eqip);
	}
	
}
