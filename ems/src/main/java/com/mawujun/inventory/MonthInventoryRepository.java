package com.mawujun.inventory;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface MonthInventoryRepository extends IRepository<MonthInventory, MonthInventory_PK>{
//	/**
//	 * 执行存储过程
//	 * @author mawujun 16064988@qq.com 
//	 * @param params
//	 */
//	public void callProc(Map<String,Object> params);
	
	public void updateField(Map<String,Object> params);
	public List<MonthInventoryVO> queryBuildMonthReport(@Param("store_id")String store_id,@Param("month_in")String month_in);
	
	//public List<MonthInventoryVO> querySparepartMonthReport(@Param("store_id")String store_id,@Param("month_in")String month_in);
}
