package com.mawujun.baseinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.Area;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface AreaRepository extends IRepository<Area, String>{

	public List<Area> queryAllWithWorkunit();
	
	public Page queryPoles(Page page);
	
	public List<PoleVO> queryPolesAndEquipments(@Param("area_id")String area_id);
}
