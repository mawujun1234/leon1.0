package com.mawujun.repository1;

import org.springframework.stereotype.Repository;

import com.mawujun.repository.EntityTest;

@Repository("entityTestMapper")
public interface EntityTestMapper extends IRepository<EntityTest, Integer> {

}
