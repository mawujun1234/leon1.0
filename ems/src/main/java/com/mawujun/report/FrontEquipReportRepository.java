package com.mawujun.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontEquipReportRepository {
	public List<FrontEquipSumReport_subtype> queryFrontEquipSumReport_header(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1);
	public List<FrontEquipSumReport> queryFrontEquipSumReport(@Param("customer_2")String customer_2,@Param("customer_0or1")String customer_0or1);
}
