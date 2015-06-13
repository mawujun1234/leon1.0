package com.mawujun.baseinfo;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface EquipmentStoreRepository extends IRepository<EquipmentStore,EquipmentStorePK> {
	//public void changeStore(Map<String,Object> params);
}
