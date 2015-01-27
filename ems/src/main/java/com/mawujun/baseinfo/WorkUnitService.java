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
import com.mawujun.utils.page.Page;
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
	
	public List<EquipmentVO> queryEquipments(EquipmentVO equipmentVO,Integer level,Integer start,Integer limit) {
		//return workUnitRepository.queryEquipments(workUnit_id);
		if(level==1){
			return workUnitRepository.queryEquipments_total(equipmentVO);
		} else if(level==2 || level==3){
			Page page=new Page();
			page.setStart(start);
			page.setPageSize(limit);
			page.setParams(equipmentVO);
			Page result=workUnitRepository.queryEquipments(page);
			List<EquipmentVO> list= result.getResult();
			
			EquipmentVO total=new EquipmentVO();
			total.setSubtype_id("total");
			total.setSubtype_name("<b>合计:</b>");
			int total_num=0;
			for(EquipmentVO equi_temp:list){
				total_num+=equi_temp.getNum();
			}
			total.setNum(total_num);

			list.add(total);
			return list;
		} else {
			return null;
		}
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
