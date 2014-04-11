package com.mawujun.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
public class GroupService extends AbstractService<Group, String> {
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public GroupRepository getRepository() {
		// TODO Auto-generated method stub
		return null;
	}

}
