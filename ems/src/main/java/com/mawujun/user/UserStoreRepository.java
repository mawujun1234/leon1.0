package com.mawujun.user;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface UserStoreRepository extends IRepository<UserStore, String> {

}
