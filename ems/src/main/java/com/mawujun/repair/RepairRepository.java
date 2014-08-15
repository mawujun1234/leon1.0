package com.mawujun.repair;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.repair.Repair;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface RepairRepository extends IRepository<Repair, String>{

	public RepairVO getRepairVOByEcode(@Param("ecode")String ecode,@Param("store_id")String store_id);
	public Page storeQuery(Page page);
}
