package com.mawujun.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
public class GroupRoleService extends AbstractService<GroupRole, GroupRolePK> {
	@Autowired
	private GroupRoleRepository groupRoleRepository;

	@Override
	public IRepository<GroupRole, GroupRolePK> getRepository() {
		// TODO Auto-generated method stub
		return groupRoleRepository;
	}


}
