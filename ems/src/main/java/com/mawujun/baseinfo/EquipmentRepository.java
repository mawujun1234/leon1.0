package com.mawujun.baseinfo;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.baseinfo.Equipment;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface EquipmentRepository extends IRepository<Equipment, String>{

	public Equipment getEquipmentByEcode(String ecode);
}
