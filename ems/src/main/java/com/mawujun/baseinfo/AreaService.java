package com.mawujun.baseinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




import com.mawujun.service.AbstractService;


import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.Area;
import com.mawujun.baseinfo.AreaRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AreaService extends AbstractService<Area, String>{

	@Autowired
	private AreaRepository areaRepository;
	
	@Override
	public AreaRepository getRepository() {
		return areaRepository;
	}
	
	public List<Area> queryAllWithWorkunit() {
		return this.getRepository().queryAllWithWorkunit();
	}

	public Page queryPoles(Page page) {
		return areaRepository.queryPoles(page);
	}
	
	public List<PoleVO> queryPolesAndEquipments(String area_id) {
		return areaRepository.queryPolesAndEquipments(area_id);
	}
}
