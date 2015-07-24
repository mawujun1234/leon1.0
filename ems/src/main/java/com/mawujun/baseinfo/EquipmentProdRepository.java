package com.mawujun.baseinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.baseinfo.EquipmentProd;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface EquipmentProdRepository extends IRepository<EquipmentProd, String>{

	public List<EquipmentProdVO> queryProdGrid(Map map);
	
	public List<EquipmentProd> queryProd_tj_children(@Param("parent_id") String parent_id);
	
	//public List<String> checkProd_used_in_equipment(@Param("prod_id") String prod_id);
	
	public String get_style_by_prod_id(@Param("prod_id") String prod_id);
	public void update_lock_style(@Param("prod_id") String prod_id,@Param("lock_style") Boolean lock_style);
	//public List<Brand> queryProdGrid(@Param("subtype_id") String subtype_id);
	
	public EquipmentProdVO getEquipmentProdVO(@Param("prod_id") String prod_id);
}
