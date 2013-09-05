package com.mawujun.parameter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.exception.BussinessException;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.BeanUtils;

@Service
public class ParameterService extends BaseRepository<Parameter, String> {
	@Autowired
	private ParameterSubjectService parameterSubjectService;
	@Autowired
	private PGeneratorService pGeneratorService;
	/**
	 * 创建的时候同时更改P文件，添加该参数的id
	 */
	public Parameter create(Parameter entity) {
		entity=super.create(entity);
		this.updatePjavaFile();
		return entity;
	}
	public void delete(Parameter entity,Boolean forceDelete) {
		if(forceDelete){
			//删除引用了这个参数的所有主体
			parameterSubjectService.deleteBatch(Cnd.delete().andEquals("parameterId", entity.getId()));
		} else {
			int count=parameterSubjectService.queryCount(Cnd.where().andEquals("parameterId", entity.getId()));
			if(count>0){
				throw new BussinessException("该参数有主体在引用不能删除!");
			}		
			
		}
		super.delete(entity);
		this.updatePjavaFile();
	}
	
	public void updatePjavaFile(){
		//获取所有的参数id
		List<String> ids=super.queryList(Cnd.select().addSelect("id"), String.class);
		pGeneratorService.updatePjavaFile(ids);
	}
	
	public Parameter update(Parameter entity) {
		//判断主体是否有没引用，如果被引用了并且更新的时候取消了，这个时候就报错
		List<String> list=parameterSubjectService.queryList(Cnd.select().addSelect("subjectType").distinct().andEquals("parameterId", entity.getId()), String.class);
		String subjects=entity.getSubjects();
		subjects=subjects.substring(1, subjects.length()-1);
		String tempArray[]=subjects.split(",");
		for(String newSubject:tempArray) {
			list.contains(newSubject);
		}
		String newSubject_temp=null;
		for(String userSubject:list){
			boolean bool=false;
			newSubject_temp=userSubject;
			for(String newSubject:tempArray) {
				if((newSubject).equalsIgnoreCase("\""+userSubject+"\"")){
					bool=true;				
					break;
				}
			}
			if(!bool){
				throw new BussinessException("查询主体<"+SubjectType.valueOf(newSubject_temp).getName()+">已经设置过参数，所以不能去除!");
			}
		}
		//判断值类型有没有变化
		Parameter paramm=this.get(entity.getId());
		if(list.size()>0 && paramm.getValueEnum()!=entity.getValueEnum()){
			throw new BussinessException("值类型不能更新，因为已经被使用了!");
		}
		BeanUtils.copyOrCast(entity, paramm);
		return super.update(paramm);
	}
	

}
