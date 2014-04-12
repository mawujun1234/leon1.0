package com.mawujun.group;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.role.Role;
import com.mawujun.utils.page.Page;

@Repository
public interface GroupRepository extends IRepository<Group, String> {
	public Page queryUser(Page page);
	public List<Role> queryRole(Map params);
}
