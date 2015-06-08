package com.mawujun.repair;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.repository1.IRepository;
import com.mawujun.repair.Repair;
import com.mawujun.user.User;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface RepairRepository extends IRepository<Repair, String>{

	public RepairVO getRepairVOByEcode(@Param("ecode")String ecode,@Param("store_id")String store_id);
	public Page repairInQuery(Page page);
	public Page repairMgrQuery(Page page);
	public Page storeMgrQuery(Page page);
	
	public Page queryRepairReport(Page page);
	public List<RepairVO> queryRepairReport(Params params);
	
	public Page queryCompleteRepairReport(Page page);
	public List<RepairVO> queryCompleteRepairReport(Params params);
	
	
	public List<EquipmentVO> queryBrokenEquipment(@Param("store_id")String store_id);
	public List<com.mawujun.repair.Repair> queryBrokenEquipment2Reapir(@Param("store_id")String store_id);
	
}
