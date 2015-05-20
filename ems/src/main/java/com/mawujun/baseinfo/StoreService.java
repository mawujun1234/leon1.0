package com.mawujun.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
















import com.mawujun.service.AbstractService;


import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.User;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.exception.BusinessException;


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
		} else if(level==2 || level==3){
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
			for(EquipmentVO equi_temp:list){
				total_num+=equi_temp.getNum();
			}
			total.setNum(total_num);

			list.add(total);
			return list;
		} else {
			return null;
		}
		
	}
	
	public List<Store> queryCombo(Integer[] type,Boolean look,Boolean edit){
//		Map<String,Object> params=new HashMap<String,Object>();
//		params.put("user_id", ShiroUtils.getAuthenticationInfo().getId());
//		params.put("look", look);
//		params.put("edit", edit);
//		params.put("types", type);
//		return storeRepository.queryCombo(params);
		return storeRepository.queryCombo(ShiroUtils.getAuthenticationInfo().getId(),type,look,edit);
	}
	
	public List<User> queryUsersByStore(String store_id,Boolean look,Boolean edit) {
		return storeRepository.queryUsersByStore(store_id,look,edit);
	}
	@Override
	public void delete(Store entity) {
		// 先判断是否有订单引用他了，如果有就不能删除
		if(storeRepository.queryUsedCountByOrder(entity.getId())>0){
			throw new BusinessException("仓库已经被引用,不能删除!");
		} else {
			super.delete(entity);
		}
		//store.setStatus(false);
		//storeRepository.update(store);
		//super.delete(entity);
	}
}
