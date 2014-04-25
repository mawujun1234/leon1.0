package com.mawujun.utils;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.parameter.ParameterSubjectService;
import com.mawujun.parameter.SubjectEnum;

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
	
	public static String getParameterValue(String subjectId,SubjectEnum subjectType,P parameterId){
		return parameterSubjectService.getParameterValue(subjectId,subjectType,parameterId.toString());
	}
	/**
	 * 
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param subjectId 某个用户的id
	 * @param parameterId 获取哪个参数的值
	 * @return
	 */
	public static String getUserParameterValue(String subjectId,P parameterId){
		return ParameterHolder.getParameterValue(subjectId, SubjectEnum.USER, parameterId);
	}
	/**
	 * 
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param subjectId 某个组的id
	 * @param parameterId 获取哪个参数的值
	 * @return
	 */
	public static String getGroupParameterValue(String subjectId,P parameterId){
		return ParameterHolder.getParameterValue(subjectId, SubjectEnum.GROUP, parameterId);
	}
	/**
	 * 
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param subjectId 某个角色的id
	 * @param parameterId 获取哪个参数的值
	 * @return
	 */
	public static String getRoleParameterValue(String subjectId,P parameterId){
		return ParameterHolder.getParameterValue(subjectId, SubjectEnum.ROLE, parameterId);
	}
	/**
	 * 
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param subjectId 系统的id
	 * @param parameterId 获取哪个参数的值
	 * @return
	 */
	public static String getSystemParameterValue(P parameterId){
		return parameterSubjectService.getSystemParameterValue(parameterId.toString());
	}
}
