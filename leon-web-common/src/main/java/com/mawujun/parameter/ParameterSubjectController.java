package com.mawujun.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.BeanUtils;

@Controller
public class ParameterSubjectController {

	@Autowired
	private ParameterSubjectService parametersubjectService;
	@Autowired
	private ParameterService parameterService;
	
//	@RequestMapping("/parametersubject/queryShowModel")
//	@ResponseBody
//	public List<Map<String,String>> queryShowModel(String valueEnum){
//		return ShowModelEnum.getShowModel(ParameterValueEnum.valueOf(valueEnum));
//	}
//	
	@RequestMapping("/parametersubject/querySubjectType")
	@ResponseBody
	public List<Map<String,String>> querySubjectType(){

		return SubjectType.toListMap();
	}


	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/parametersubject/query")
	@ResponseBody
	public Map<String,Object> query(String subjectId,String subjectType){		
		//把参数行列转换后，显示在form里
		List<ParameterSubject> pses=parametersubjectService.query(Cnd.select().andEquals("id.subjectId", subjectId).andEquals("id.subjectType", subjectType));
		HashMap<String,Object> result=new HashMap<String,Object>();
		for(ParameterSubject ps:pses){
			//如果是数组的话，就转换成字符串
			if(parameterService.get(ps.getId().getParameterId()).getShowModel()==ShowModelEnum.CHECKBOXGROUP){
				String value=ps.getParameterValue();
				if(!value.startsWith("[")){
					value="["+value+"]";
				}
				Object o=JSON.parse(value);
				System.out.println(o.getClass());
				result.put(ps.getId().getParameterId(), o);
			} else {
				result.put(ps.getId().getParameterId(), ps.getParameterValue());
			}
			
		}
		return result;
	}
	
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/parametersubject/querySubject")
	@ResponseBody
	public List<ParameterSubjectVO> querySubject(String parameterId,String subjectType){
		//return parametersubjectService.query(Cnd.select().andEquals("parameterId", parameterId).andEquals("subjectType", subjectType));
		return parametersubjectService.querySubject(parameterId, subjectType);
	}
//	@RequestMapping("/parametersubject/load")
//	@ResponseBody
//	public ParameterSubject load(String id){		
//		return parametersubjectService.get(id);
//	}

	@RequestMapping("/parametersubject/create")
	@ResponseBody
	public ParameterSubject[] create(@RequestBody ParameterSubject[] parametersubjects){		
		parametersubjectService.create(parametersubjects);
		return parametersubjects;
	}


}
