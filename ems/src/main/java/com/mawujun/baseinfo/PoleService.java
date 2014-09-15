package com.mawujun.baseinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;





import com.mawujun.service.AbstractService;


import com.mawujun.baseinfo.Pole;
import com.mawujun.baseinfo.PoleRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PoleService extends AbstractService<Pole, String>{

	@Autowired
	private PoleRepository poleRepository;
	
	@Override
	public PoleRepository getRepository() {
		return poleRepository;
	}
	
	public void savePoles(String area_id,String[] pole_ids) {	
		for(int i=0;i<pole_ids.length;i++){
			poleRepository.savePoles(area_id, pole_ids[i]);
		}
	}
	public void deletePoles(String area_id,String[] pole_ids) {	
		for(int i=0;i<pole_ids.length;i++){
			poleRepository.deletePoles(pole_ids[i]);
		}
	}
	
	public List<Pole> queryEquipments(String id){
		return poleRepository.queryEquipments(id);
	}
}
