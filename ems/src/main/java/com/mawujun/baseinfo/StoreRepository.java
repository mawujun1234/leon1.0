package com.mawujun.baseinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.baseinfo.Store;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface StoreRepository extends IRepository<Store, String>{

	public List<EquipmentVO> queryEquipments_total(EquipmentVO equipmentVO) ;
	public List<EquipmentVO> queryEquipments(EquipmentVO equipmentVO) ;
	
	
	public List<Store> queryCombo(@Param("user_id")String user_id,@Param("type")Integer type);
}
