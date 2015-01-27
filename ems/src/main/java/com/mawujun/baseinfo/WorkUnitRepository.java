package com.mawujun.baseinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.WorkUnit;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface WorkUnitRepository extends IRepository<WorkUnit, String>{

	//public List<EquipmentVO> queryEquipments(@Param("workUnit_id")String workUnit_id);
	public List<EquipmentVO> queryEquipments_total(EquipmentVO equipmentVO) ;
	public Page queryEquipments(Page page) ;
	
	public List<EquipmentSubtype> queryHaveEquipmentInfosTotal(String workUnit_id);
	
	public List<EquipmentVO> queryHaveEquipmentInfosDetail(@Param("workUnit_id")String workUnit_id,@Param("prod_id")String prod_id);
}
