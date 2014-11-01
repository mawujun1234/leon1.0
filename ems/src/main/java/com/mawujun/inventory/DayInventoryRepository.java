package com.mawujun.inventory;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface DayInventoryRepository extends IRepository<DayInventory, DayInventory_PK>{
	/**
	 * 执行存储过程
	 * @author mawujun 16064988@qq.com 
	 * @param params
	 */
	public void callProc(Map<String,Object> params);
}
