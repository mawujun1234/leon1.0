package com.mawujun.group;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface GroupUserRepository extends
		IRepository<GroupUser, GroupUserPK> {

}
