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
	
	//public List<Brand> queryProdGrid(@Param("subtype_id") String subtype_id);
}
