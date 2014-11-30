package com.mawujun.inventory;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface DayInventoryRepository extends IRepository<DayInventory, DayInventory_PK>{
	/**
	 * 执行存储过程
	 * @author mawujun 16064988@qq.com 
	 * @param params
	 */
	public void proc_dayinventory(@Param("store_id_in")String store_id_in,@Param("day_in")String day_in);
	/**
	 * 执行存储过程
	 * @author mawujun 16064988@qq.com 
	 * @param params
	 */
	public void proc_dayinventory1(@Param("store_id_in")String store_id_in,@Param("day_in")String day_in);
	
	public List<DayInventoryVO> queryDayInventory(@Param("store_id")String store_id,@Param("day_start")String day_start,@Param("day_end")String day_end);
}
