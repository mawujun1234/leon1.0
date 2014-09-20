package com.mawujun.meta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.mawujun.service.AbstractService;


import com.mawujun.meta.MetaVersion;
import com.mawujun.meta.MetaVersionRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MetaVersionService extends AbstractService<MetaVersion, String>{

	@Autowired
	private MetaVersionRepository metaVersionRepository;
	
	@Override
	public MetaVersionRepository getRepository() {
		return metaVersionRepository;
	}

	public void update(Class<?> clazz) {
		MetaVersion metaVersion=metaVersionRepository.get(clazz.getSimpleName());
		if(metaVersion==null){
			metaVersion=new MetaVersion();
			metaVersion.setId(clazz.getSimpleName());
			metaVersion.setVersion(1);
			metaVersionRepository.create(metaVersion);
		} else {
			metaVersion.setVersion(metaVersion.getVersion()+1);
			metaVersionRepository.update(metaVersion);
		}
	}
}
