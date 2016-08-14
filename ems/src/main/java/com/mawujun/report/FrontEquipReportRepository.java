package com.mawujun.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontEquipReportRepository {
	public List<FrontEquipSumReport_subtype> queryFrontEquipSumReport_header(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1,@Param("user_id")String user_id);
	public List<FrontEquipSumReport> queryFrontEquipSumReport(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1,@Param("user_id")String user_id);
	
	public List<FrontEquipSumReport_subtype> queryMachineroomEquipSumReport_header(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1,@Param("user_id")String user_id);
	public List<FrontEquipSumReport> queryMachineroomEquipSumReport(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1,@Param("user_id")String user_id);
	
	
	public List<FrontEquipListReport_subtype> queryFrontEquipListReport_header(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1);
	public List<FrontEquipListReport> queryFrontEquipListReport(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1);
}
