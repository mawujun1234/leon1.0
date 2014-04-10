package com.mawujun.desktop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.fun.FunRepository;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;

@Service
public class DesktopConfigService extends AbstractService<DesktopConfig, String> {
	@Autowired
	private DesktopConfigRepository desktopConfigRepository;
	@Override
	public DesktopConfigRepository getRepository() {
		// TODO Auto-generated method stub
		return desktopConfigRepository;
	}//BaseRepository<DesktopConfig, String> {
//	

}
