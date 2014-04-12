package com.mawujun.group;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.role.Role;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Page;

@Service
public class GroupService extends AbstractService<Group, String> {
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public GroupRepository getRepository() {
		// TODO Auto-generated method stub
		return groupRepository;
	}

	public Page queryUser(Page page) {
		return this.getRepository().queryUser(page);
	}
	
	public List<Role> queryRole(Map params) {
		return this.getRepository().queryRole(params);
	}
}
