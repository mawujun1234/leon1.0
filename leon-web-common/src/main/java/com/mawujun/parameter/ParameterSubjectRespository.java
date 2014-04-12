package com.mawujun.parameter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.Params;

@Repository
public interface ParameterSubjectRespository extends
		IRepository<ParameterSubject, ParameterSubject.Id> {
	
	public List<Map<String,Object>> query_USER_part_of(Params params);
	public List<ParameterSubjectVO> query_SYSTEM(Params params);
	public List<ParameterSubjectVO> query_GROUP(Params params);
	public List<ParameterSubjectVO> query_ROLE(Params params);
	public List<ParameterSubjectVO> query_USER(Params params);

}
