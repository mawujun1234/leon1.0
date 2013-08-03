package com.mawujun.parameter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;

@Controller
public class ParameterController {

	@Autowired
	private ParameterService parameterService;
	
	@RequestMapping("/parameter/queryShowModel")
	@ResponseBody
	public List<Map<String,String>> queryShowModel(String valueEnum){
		return ShowModelEnum.getShowModel(ParameterValueEnum.valueOf(valueEnum));
	}
	
	@RequestMapping("/parameter/queryParameterValueEnum")
	@ResponseBody
	public List<Map<String,String>> queryParameterValueEnum(){

		return ParameterValueEnum.toListMap();
	}


	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/parameter/query")
	@ResponseBody
	public List<Parameter> query(){		
		return parameterService.queryAll();
	}
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/parameter/queryBysubjectType")
	@ResponseBody
	public List<Parameter> queryBysubjectType(String subjectType){		
		return parameterService.query(Cnd.select().andLike("targets", subjectType));
	}
	@RequestMapping("/parameter/load")
	@ResponseBody
	public Parameter load(String id){		
		return parameterService.get(id);
	}

	@RequestMapping("/parameter/create")
	@ResponseBody
	public Parameter create(@RequestBody Parameter parameter){		
		parameterService.create(parameter);
		return parameter;
	}
	
	@RequestMapping("/parameter/update")
	@ResponseBody
	public Parameter update(@RequestBody Parameter parameter){		
		parameterService.update(parameter);
		return parameter;
	}
	
	@RequestMapping("/parameter/destroy")
	@ResponseBody
	public Parameter destroy(@RequestBody Parameter parameter){		
		parameterService.delete(parameter);
		return parameter;
	}

}
