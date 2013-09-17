package com.mawjun.utils;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.parameter.P;
import com.mawujun.parameter.ParameterService;
import com.mawujun.parameter.ParameterSubjectService;
import com.mawujun.parameter.SubjectType;

/**
 * 用来全局获取参数值得
 * ParameterHolder.getUserParameterValue("402881e53f0a1310013f0a17b7770000", P.tttt);
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class ParameterHolder {
	
	static ParameterSubjectService parameterSubjectService=null;
	
	static{
		parameterSubjectService=(ParameterSubjectService)SpringContextHolder.getBean(ParameterSubjectService.class);
	}
	public static void initialize(){
		//parameterSubjectService=(ParameterSubjectService)SpringContextHolder.getBean(ParameterSubjectService.class);
	}
	
	public static String getParameterValue(String subjectId,SubjectType subjectType,P parameterId){
		return parameterSubjectService.getParameterValue(subjectId,subjectType,parameterId.toString());
	}
	
	public static String getUserParameterValue(String subjectId,P parameterId){
		return ParameterHolder.getParameterValue(subjectId, SubjectType.USER, parameterId);
	}
	public static String getGroupParameterValue(String subjectId,P parameterId){
		return ParameterHolder.getParameterValue(subjectId, SubjectType.GROUP, parameterId);
	}
	
	public static String getRoleParameterValue(String subjectId,P parameterId){
		return ParameterHolder.getParameterValue(subjectId, SubjectType.ROLE, parameterId);
	}
	
	public static String getSystemParameterValue(String subjectId,P parameterId){
		return parameterSubjectService.getSystemParameterValue(subjectId,  parameterId.toString());
	}
}
