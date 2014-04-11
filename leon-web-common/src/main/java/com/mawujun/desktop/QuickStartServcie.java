package com.mawujun.desktop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.desktop.QuickStart.Id;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
public class QuickStartServcie extends AbstractService<QuickStart, Id> {
	@Autowired
	private QuickStartRepository quickStartRepository;

	@Override
	public QuickStartRepository getRepository() {
		// TODO Auto-generated method stub
		return quickStartRepository;
	}//BaseRepository<QuickStart, Id> {

}
