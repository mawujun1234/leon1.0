package com.mawujun.mobile.geolocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.mobile.geolocation.GpsConfig;
import com.mawujun.mobile.geolocation.GpsConfigRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class GpsConfigService extends AbstractService<GpsConfig, Integer>{

	@Autowired
	private GpsConfigRepository gpsConfigRepository;
	
	@Override
	public GpsConfigRepository getRepository() {
		return gpsConfigRepository;
	}

}
