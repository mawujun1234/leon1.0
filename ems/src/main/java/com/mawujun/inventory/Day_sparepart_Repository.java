package com.mawujun.inventory;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface Day_sparepart_Repository extends IRepository<Day_sparepart, Day_sparepart_PK>{
	/**
	 * 执行存储过程
	 * @author mawujun 16064988@qq.com 
	 * @param params
	 */
	public void proc_report_day_sparepart(@Param("in_store_id")String in_store_id,@Param("in_todaykey")String in_todaykey);
	
	public void proc_report_day_sparepart_all(@Param("in_todaykey")String in_todaykey);
	
	public void proc_equipment_unitprice();

	
	public List<Day_sparepart_type> queryDay_sparepartVO(@Param("store_id")String store_id,@Param("use_in")Boolean use_in,@Param("day_start")String day_start,@Param("day_end")String day_end);
	public List<Month_sparepart_type> queryMonth_sparepartVO(@Param("store_id")String store_id,@Param("use_in")Boolean use_in,@Param("day_start")String day_start,@Param("day_end")String day_end);
	public List<Month_sparepart_prod> queryMonth_yesterdaynum(@Param("store_id")String store_id,@Param("use_in")Boolean use_in,@Param("day_start")String day_start);
	
	public void proc_report_assetclean(@Param("in_todaykey")String in_todaykey);
}
