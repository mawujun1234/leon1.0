package com.mawujun.mobile.geolocation;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface GeolocationRepository extends IRepository<Geolocation, String> {
	List<GeolocationVO> queryHistoryWorkunit(String loc_time);
	
	public List<Trace> queryHistoryTrace(@Param("loc_time")String loc_time,@Param("loginName")String loginName);
	
	public List<TraceList> queryHistoryTraceList(@Param("sessionId")String sessionId);
}
