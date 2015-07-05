package com.mawujun.inventory;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface MonthInventoryRepository extends IRepository<MonthInventory, MonthInventory_PK>{
	/**
	 * 在建仓库，马上执行存储过程，马上计算当前库存
	 * @author mawujun 16064988@qq.com 
	 * @param params
	 */
	public void proc_monthinventory1(@Param("store_id_in")String store_id_in,@Param("month_in")String month_in);
	public void proc_monthinventory(@Param("store_id_in")String store_id_in,@Param("month_in")String month_in);
	
	public void updateField(Map<String,Object> params);
	public List<MonthInventoryVO> queryMonthReport(@Param("store_id")String store_id,@Param("month_in")String month_in);
	public List<MonthInventoryVO> queryNullMonthReport(@Param("store_id")String store_id);
	
	//public List<MonthInventoryVO> querySparepartMonthReport(@Param("store_id")String store_id,@Param("month_in")String month_in);
}
