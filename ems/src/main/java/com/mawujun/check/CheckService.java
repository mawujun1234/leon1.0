package com.mawujun.check;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.service.AbstractService;


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
