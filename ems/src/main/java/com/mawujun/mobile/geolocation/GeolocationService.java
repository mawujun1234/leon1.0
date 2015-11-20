package com.mawujun.mobile.geolocation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
public class GeolocationService extends AbstractService<Geolocation, String> {

	@Autowired
	private GeolocationRepository geolocationRepository;
	
	@Override
	public IRepository<Geolocation, String> getRepository() {
		// TODO Auto-generated method stub
		return geolocationRepository;
	}

	public List<GeolocationVO> queryHistoryWorkunit(String loc_time) {
		return geolocationRepository.queryHistoryWorkunit(loc_time);
	}
	
	public List<Trace> queryHistoryTrace(String loc_time,String loginName) {
		return geolocationRepository.queryHistoryTrace(loc_time, loginName);
	}
	
	public List<TraceList> queryHistoryTraceList(String sessionId) {
		return geolocationRepository.queryHistoryTraceList(sessionId);
	}
}
