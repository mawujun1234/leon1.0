package com.mawujun.baseinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.user.User;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.Store;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface StoreRepository extends IRepository<Store, String>{

	public List<EquipmentVO> queryEquipments_total(EquipmentVO equipmentVO) ;
	public Page queryEquipments(Page page) ;
	
	
	public List<Store> queryCombo(@Param("user_id")String user_id,@Param("types")Integer[] types,@Param("look")Boolean look,@Param("edit")Boolean edit);
	//public List<Store> queryCombo(Map<String,Object> params);
	public List<User> queryUsersByStore(@Param("store_id")String store_id,@Param("look")Boolean look,@Param("edit")Boolean edit);
	
	public Integer queryUsedCountByOrder(@Param("store_id")String store_id);
}
