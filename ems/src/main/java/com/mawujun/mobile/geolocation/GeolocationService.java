package com.mawujun.mobile.geolocation;

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

}
