package com.mawujun.install;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface StoreEquipmentRepository extends IRepository<StoreEquipment, String>{
	public void updateNum(@Param("store_id")String store_id,@Param("ecode")String ecode,@Param("num")String num);
}
