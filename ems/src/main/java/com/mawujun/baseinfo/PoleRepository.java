package com.mawujun.baseinfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.baseinfo.Pole;
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
}