package com.mawujun.parameter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mawujun.repository.cnd.Cnd;

@Controller
public class ParameterController {

	@Autowired
	private ParameterService parameterService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
		//ParameterHolder.getUserParameterValue("402881e53f0a1310013f0a17b7770000", P.tttt);
		
		return parameterService.query(Cnd.where().asc("sort"));
	}
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("/parameter/queryBysubjectType")
	@ResponseBody
	public List<Parameter> queryBysubjectType(String subjectType) throws SQLException{
		//这里和FunController中的内容起冲突了，因为这里是同个线程，那为什么其他请求不会呢，例如角色管理中的
		System.out.println(Thread.currentThread().getId()+"==========================================");
		//System.out.println(JsonConfigHolder.getAutoWrap());
		//如果是java和sql的话 要先从后台去获取，变成congtent然后再传递到前台，或者是getContent的时候去获取，写在getContent方法内
		List<Parameter> parameters=parameterService.query(Cnd.select().andLike("subjects", subjectType).asc("sort"));
		List<Parameter> result=new ArrayList<Parameter>();
		for(Parameter param:parameters){
			if(param.getValueEnum()==ParameterValueEnum.JAVA){
				param.setContent(getJavaContent(param.getContent()));
			}
			result.add(param);
		}
		//System.out.println(JsonConfigHolder.getAutoWrap());
		return result;
	}
	/**
	 * 返回的数据格式为[{key:...,name:...},{key:...,name:...},{key:...,name:...}]
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 * @throws SQLException 
	 */
	private String getJavaContent(String content) throws SQLException{
		//Connection conn=dataSource.getConnection();
		try {
			Class clazz=Class.forName(content);
			JavaBeanDataSource javaBeanDataSource=(JavaBeanDataSource) clazz.newInstance();
			
			List<JavaBeanKeyName> list=javaBeanDataSource.getData(jdbcTemplate);
			String jsonString = JSON.toJSONString(list);
			return jsonString;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
//			if(conn!=null){
//				conn.close();
//			}
			
		}
		return null;
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
	public Parameter destroy(@RequestBody Parameter parameter,Boolean forceDelete){		
		parameterService.delete(parameter,forceDelete);
		return parameter;
	}

}
