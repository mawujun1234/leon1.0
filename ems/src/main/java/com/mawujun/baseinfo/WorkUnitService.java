package com.mawujun.baseinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;










import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.utils.M;
import com.mawujun.baseinfo.WorkUnit;
import com.mawujun.baseinfo.WorkUnitRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class WorkUnitService extends AbstractService<WorkUnit, String>{

	@Autowired
	private WorkUnitRepository workUnitRepository;
	
	@Override
	public WorkUnitRepository getRepository() {
		return workUnitRepository;
	}
	
	public List<EquipmentVO> queryEquipments(String workUnit_id) {
		return workUnitRepository.queryEquipments(workUnit_id);
	}

	public WorkUnit getByLoginName(String loginName){
		return workUnitRepository.queryUnique(Cnd.where().andEquals(M.WorkUnit.loginName, loginName).andEquals(M.WorkUnit.status, true));
	}
	
	/**
	 * mobile上用到的
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public List<EquipmentSubtype> queryHaveEquipmentInfosTotal(String workUnit_id){
		List<EquipmentSubtype> prodes=workUnitRepository.queryHaveEquipmentInfosTotal(workUnit_id);
		return prodes;
	}
	public List<EquipmentVO> queryHaveEquipmentInfosDetail(String workUnit_id,String prod_id) {
		return workUnitRepository.queryHaveEquipmentInfosDetail(workUnit_id, prod_id);
	}
}
