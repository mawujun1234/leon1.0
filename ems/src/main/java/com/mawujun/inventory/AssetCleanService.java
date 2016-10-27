package com.mawujun.inventory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class AssetCleanService {
	@Resource
	private AssetCleanRepository assetCleanRepository;
	
	public void proc_report_assetclean() {
		assetCleanRepository.proc_report_assetclean();
	}
	
	
}
