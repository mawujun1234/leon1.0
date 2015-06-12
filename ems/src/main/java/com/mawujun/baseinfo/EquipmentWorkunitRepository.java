package com.mawujun.baseinfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface EquipmentWorkunitRepository extends IRepository<EquipmentWorkunit,EquipmentWorkunitPK> {
	public EquipmentWorkunit getBorrowEquipment(@Param("ecode")String ecode);
}
