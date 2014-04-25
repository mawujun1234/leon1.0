package com.mawujun.parameter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.exception.BusinessException;
import com.mawujun.parameter.ParameterSubject.Id;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;

@Service
public class ParameterSubjectService extends
		AbstractService<ParameterSubject, ParameterSubject.Id> {
	@Autowired
	private ParameterSubjectRespository parameterSubjectRespository;
	
	@Override
	public ParameterSubjectRespository getRepository() {
		// TODO Auto-generated method stub
		return parameterSubjectRespository;
	}

	public int create(ParameterSubject[] parametersubjects){
		//删除该主体的所有参数
		//super.deleteBatch(Cnd.select().andEquals("id.subjectId", parametersubjects[0].getId().getSubjectId()).andEquals("id.subjectType", parametersubjects[0].getId().getSubjectType()));
		super.deleteBatch(Cnd.select().andEquals(M.ParameterSubject.id.subjectId, parametersubjects[0].getId().getSubjectId())
				.andEquals(M.ParameterSubject.id.subjectType, parametersubjects[0].getId().getSubjectType()));
		//再进行保存
		for(ParameterSubject ps:parametersubjects){
			super.create(ps);
		}
		return parametersubjects.length;
	}
	public String getParameterValue(String subjectId,SubjectEnum subjectType,String parameterId){
		String result=null;
		if(SubjectEnum.USER==subjectType){
			//如果是用户的话,要首先获取用户，再获取用户所属的职位，组织单元，用户组，角色，系统
			Params pamras=Params.init().add("user_id", subjectId).add("parameter_Id", parameterId);
			List<ParameterValue> list=this.getRepository().query_USER_part_of(pamras);
			for(ParameterValue parameterVO:list){		
				if(parameterVO.getValue()!=null){
					return parameterVO.getValue();
				}
			}	
		} else {
			//如果不是用户，就只获取自己的值
			result= this.queryUnique(Cnd.select().andEquals(M.ParameterSubject.id.subjectId, subjectId).andEquals(M.ParameterSubject.id.subjectType, subjectType)
					.andEquals(M.ParameterSubject.id.parameterId, parameterId).addSelect(M.ParameterSubject.parameterValue),String.class);
		}
		if(result==null || "".equalsIgnoreCase(result)){
			result=getSystemParameterValue(parameterId);
		}
		return result;
	}
	
	public String getSystemParameterValue(String parameterId){
		return this.queryUnique(Cnd.select().andEquals(M.ParameterSubject.id.subjectId, "SYSTEM").andEquals(M.ParameterSubject.id.subjectType, SubjectEnum.SYSTEM)
				.andEquals(M.ParameterSubject.id.parameterId, parameterId).addSelect(M.ParameterSubject.parameterValue),String.class);
	}
	
	public List<ParameterSubjectVO> querySubject(String parameterId,String subjectType){

		Params params=Params.init().put("parameter_Id", parameterId).putIf("subjectType", subjectType);
		//return this.queryList("query_"+subjectType, params,ParameterSubjectVO.class);
		
		if(SubjectEnum.SYSTEM.toString().equals(subjectType)){
			//return this.query(Cnd.select().andEquals("parameterId", parameterId).andEquals("subjectType", subjectType));
			return this.getRepository().query_SYSTEM( params);
		} else if(SubjectEnum.USER.toString().equals(subjectType)){
			return this.getRepository().query_USER(params);
		} else if(SubjectEnum.GROUP.toString().equals(subjectType)){
			return this.getRepository().query_GROUP( params);
		} else if(SubjectEnum.ROLE.toString().equals(subjectType)){
			return this.getRepository().query_ROLE(params);
		} else {
			throw new BusinessException("该主题的参数配置  还没有做!!查询所有主体也还咩有做");
		}
		//return null;
	}
	
	
//	public  Map<String,String> query(String subjectId,String subjectType){
//		//取的是leon_parameter_subject表的值
//		List<ParameterSubject> pses=parametersubjectService.query(Cnd.where().andEquals("subjectId", subjectId).andEquals("subjectType", subjectType));
//		HashMap<String,String> result=new HashMap<String,String>();
//		for(ParameterSubject ps:pses){
//			result.put(ps.getParameterId(), ps.getParameterValue());
//		}
//	}

}
