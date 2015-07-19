package com.mawujun.baseinfo;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProjectService extends AbstractService<Project, String>{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public ProjectRepository getRepository() {
		return projectRepository;
	}
	private static HashMap<String,Project> projects_cache=new HashMap<String,Project>();
	@Override
	public Project get(String id) {
		if(id==null){
			return null;
		}
		Project project=projects_cache.get(id);
		if(project==null){
			return projectRepository.get(id);
		} else {
			return project;
		}
	}
	@Override
	public void delete( Project project) {
		//判断是否有订单在引用该项目了，如果已经有，就不能删除
		long used=projectRepository.queryAlreadyUsedInOrder(project.getId());
		if(used>0){
			throw new BusinessException("该项目已有订单关联，不能删除!");
		}
		projectRepository.delete(project);
	}

}
