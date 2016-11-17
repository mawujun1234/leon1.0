package com.mawujun.check;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentVO;
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
	
	public List<EquipmentVO> queryScanEquipment(String check_id) {	

		return checkRepository.queryScanEquipment(check_id);
	}
	public List<EquipmentVO> queryPoleEquipment(String pole_id) {	

		return checkRepository.queryPoleEquipment(pole_id);
	}

//	public void createCheckList(String check_id,String task_id){
//		checkRepository.createCheckList(check_id, task_id);
//	}
	
}
