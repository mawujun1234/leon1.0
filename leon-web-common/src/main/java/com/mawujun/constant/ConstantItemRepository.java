package com.mawujun.constant;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface ConstantItemRepository extends
		IRepository<ConstantItem, String> {

}