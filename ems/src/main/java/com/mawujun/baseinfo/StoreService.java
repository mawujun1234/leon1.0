package com.mawujun.baseinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




import com.mawujun.service.AbstractService;


import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class StoreService extends AbstractService<Store, String>{

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public StoreRepository getRepository() {
		return storeRepository;
	}

	public List<Equipment> queryEquipments(Equipment equipment,Integer level) {
		if(level==1){
			return storeRepository.queryEquipments_total(equipment);
		} else if(level==2){
			List<Equipment> list= storeRepository.queryEquipments(equipment);
			Equipment total=new Equipment();
			total.setSubtype_id("total");
			total.setSubtype_name("<b>合计:</b>");
			int total_num=0;
			for(Equipment equi_temp:list){
				total_num+=equi_temp.getNum();
			}
			total.setNum(total_num);
			list.add(total);
			return list;
		} else {
			return null;
		}
		
	}
}
