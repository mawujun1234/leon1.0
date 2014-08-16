package com.mawujun.baseinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;












import com.mawujun.service.AbstractService;


import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.User;
import com.mawujun.utils.page.Page;
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

	public List<EquipmentVO> queryEquipments(EquipmentVO equipmentVO,Integer level,Integer start,Integer limit) {
		if(level==1){
			return storeRepository.queryEquipments_total(equipmentVO);
		} else if(level==2){
			Page page=new Page();
			page.setStart(start);
			page.setPageSize(limit);
			page.setParams(equipmentVO);
			Page result=storeRepository.queryEquipments(page);
			List<EquipmentVO> list= result.getResult();
			EquipmentVO total=new EquipmentVO();
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
	
	public List<Store> queryCombo(Integer type,Boolean look,Boolean edit){
		return storeRepository.queryCombo(ShiroUtils.getAuthenticationInfo().getId(),type,look,edit);
	}
	
	public List<User> queryUsersByStore(String store_id,Boolean look,Boolean edit) {
		return storeRepository.queryUsersByStore(store_id,look,edit);
	}
}
