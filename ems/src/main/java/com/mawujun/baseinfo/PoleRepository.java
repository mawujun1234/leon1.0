package com.mawujun.baseinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PoleRepository extends IRepository<Pole, String>{

	/**
	 * 王片区里面添加Pole
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param area_id
	 * @param pole_id
	 */
	public void savePoles(@Param("area_id")String area_id,@Param("pole_id")String pole_id);
	/**
	 * 把pole从某个area中移除
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param pole_id
	 */
	public void deletePoles(@Param("pole_id")String pole_id);
	
	public List<Pole> queryEquipments(@Param("id")String id);
	
	public List<PoleVO> queryPolesAndEquipments(@Param("customer_id")String customer_id);
	public int query_count_equipment_in_pole(@Param("pole_id")String pole_id);
	
	//======================下面的是地图上用的
	public List<Pole> queryNoLngLatPole();
	public Page queryNoLngLatPole(Page page);
	
	public void updateCoordes(@Param("longitude")String longitude,@Param("latitude")String latitude,@Param("pole_id")String pole_id);
	//查询某个客户下的点位
	public Page queryPoles4Map(Page page);
	//查询某个客户下的点位
	public List<Pole> queryPoles4Map(Map<String,Object> params);
	public List<Pole> queryBrokenPoles();
	
	public Pole geetFullAddress(@Param("pole_id")String pole_id);
	
	public List<Pole> queryNoTransformPoles();
	public void updateOrginLngLatByPoleCode(@Param("code")String code,@Param("longitude")String longitude,@Param("latitude")String latitude);
	public void updateLngLatByPoleCode(@Param("code")String code,@Param("longitude")String longitude,@Param("latitude")String latitude);
}


