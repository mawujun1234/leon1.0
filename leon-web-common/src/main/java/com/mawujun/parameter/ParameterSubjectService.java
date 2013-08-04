package com.mawujun.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mawujun.exception.BussinessException;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;

@Service
public class ParameterSubjectService extends
		BaseRepository<ParameterSubject, String> {

	public int create(ParameterSubject[] parametersubjects){
		//删除该主体的所有参数
		super.deleteBatch(Cnd.select().andEquals("subjectId", parametersubjects[0].getSubjectId()).andEquals("subjectType", parametersubjects[0].getSubjectType()));
		//再进行保存
		for(ParameterSubject ps:parametersubjects){
			super.create(ps);
		}
		return parametersubjects.length;
	}
	public String getParameterValue(String subjectId,SubjectType subjectType,String parameterId){
		return this.queryUnique(Cnd.select().andEquals("subjectId", subjectId).andEquals("subjectType", subjectType)
				.andEquals("parameterId", parameterId).addSelect("parameterValue"),String.class);
		
	}
	public List<ParameterSubjectVO> querySubject(String parameterId,String subjectType){
		Map<String,String> params=new HashMap<String,String>();
		params.put("parameterId", parameterId);
		params.put("subjectType", subjectType);
		if(SubjectType.SYSTEM.toString().equals(subjectType)){
			//return this.query(Cnd.select().andEquals("parameterId", parameterId).andEquals("subjectType", subjectType));
			return this.queryList("query_SYSTEM", params,ParameterSubjectVO.class);
		} else if(SubjectType.USER.toString().equals(subjectType)){
			return this.queryList("query_USER", params,ParameterSubjectVO.class);
		} else {
			throw new BussinessException("还没有做!!");
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
