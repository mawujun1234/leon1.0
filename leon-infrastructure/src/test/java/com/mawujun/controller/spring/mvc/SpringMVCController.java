package com.mawujun.controller.spring.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mawujun.controller.spring.mvc.JsonResult;
import com.mawujun.exception.BussinessException;
import com.mawujun.exception.DefaulExceptionCode;
import com.mawujun.repository.EntityTest;
import com.mawujun.repository.hibernate.validate.ValidatorUtils;
import com.mawujun.utils.Validate;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.utils.page.WhereInfo;

/**
 * 过滤，日期格式等设置http://hi.baidu.com/ien_leo/item/d1601c4d1a44b23dfa8960d5
 * @author mawujun
 *
 */
@Controller
public class SpringMVCController {
	
	private QueryResult<Map<String,String>> getQueryResult(){
		QueryResult<Map<String,String>> page=new QueryResult<Map<String,String>>();
		page.setStratAndLimit(1, 10);
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(int i=0;i<9;i++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("name", "name"+i);
			map.put("age", "111"+i);
			map.put("weight", "100"+i);
			list.add(map);
		}
		page.setResult(list);
		page.setTotalItems(100);
		return page;
	}
	
	@RequestMapping("/test/queryPage.do")
	@ResponseBody
	public ModelMap queryPage(){		
		QueryResult<Map<String,String>> page=getQueryResult();
		
		ModelMap map=new ModelMap();
		map.put("root", page.getResult());
		map.put("totalProperty", page.getTotalItems());	
		return map;
	}
	@RequestMapping("/test/queryPage1.do")
	@ResponseBody
	public QueryResult queryPage1(){
		QueryResult<Map<String,String>> page=new QueryResult<Map<String,String>>();
		page.setStratAndLimit(1, 10);
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(int i=0;i<9;i++){
			Map<String,String> map=new HashMap<String,String>();
			map.put("name", "name"+i);
			map.put("age", "111"+i);
			map.put("weight", "100"+i);
			list.add(map);
		}
		page.setResult(list);
		page.setTotalItems(100);	
		return page;
	}
	@RequestMapping("/test/queryMap.do")
	@ResponseBody
	public JsonResult queryMap(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", "name");
		map.put("age", "111");
		map.put("weight", "100");
		
		return JsonResult.initResult(map);
	}
	@RequestMapping("/test/queryModel.do")
	@ResponseBody
	public Model queryModel(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(new Date());
		parent.setName("parent");
		
		return parent;
	}
	/**
	 * 死循环默认会解决掉
	 * @return
	 */
	@RequestMapping("/test/queryCycle.do")
	@ResponseBody
	public Model queryCycle(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(new Date());
		parent.setName("parent");
		
		Model child=new Model();
		child.setId(1);
		child.setAge(11);
		child.setName("child");
		child.setParent(parent);
		parent.addChilden(child);
		
		Model child1=new Model();
		child1.setId(2);
		child1.setAge(22);
		child1.setName("child1");
		child1.setParent(parent);
		parent.addChilden(child1);

		return parent;
	}
	/**
	 * 死循环默认会解决掉
	 * @return
	 */
	@RequestMapping("/test/queryCycleList.do")
	@ResponseBody
	public List<Model> queryCycleList(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(new Date());
		parent.setName("parent");
		
		Model child=new Model();
		child.setId(1);
		child.setAge(11);
		child.setName("child");
		child.setParent(parent);
		parent.addChilden(child);
		
		Model child1=new Model();
		child1.setId(2);
		child1.setAge(22);
		child1.setName("child1");
		child1.setParent(parent);
		parent.addChilden(child1);
		
		List<Model> list=new ArrayList<Model>();
		list.add(parent);

		return list;
	}
	
	@RequestMapping("/test/filterProperty.do")
	@ResponseBody
	public ModelMap filterProperty(){
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(new Date());
		parent.setName("parent");
		
		ModelMap map=new ModelMap();
		map.put("filterPropertys", "age,name");//过滤属性的设置
		map.put("root", parent);
		return map;
	}
	
	@RequestMapping("/test/filterPropertyList.do")
	@ResponseBody
	public ModelMap filterPropertyList(){
		//还没有测试root是List的情况
		Model parent=new Model();
		parent.setId(1);
		parent.setAge(11);
		parent.setCreateDate(new Date());
		parent.setName("parent");
		List<Model> list=new ArrayList<Model>();
		list.add(parent);
		
		ModelMap map=new ModelMap();
		map.put("filterPropertys", "age,name");//过滤属性的设置
		map.put("root", list);
		return map;
	}
	@RequestMapping("/test/filterOnlyId.do")
	@ResponseBody
	public ModelMap filterOnlyId(){
		Model parent=new Model();
		parent.setId(2);
		parent.setAge(22);
		parent.setCreateDate(new Date());
		parent.setName("parent");
		
		Model child=new Model();
		child.setId(1);
		child.setAge(11);
		child.setCreateDate(new Date());
		child.setName("child");
		child.setParent(parent);
		
		ModelMap map=new ModelMap();
		map.put("onlyIds", "parent");//parent属性只获取id
		map.put("root", child);
		return map;
	}
	
	@RequestMapping("/test/bindModel.do")
	@ResponseBody
	public Model bindModel(@RequestBody Model model){
		return model;
	}
	
	@RequestMapping("/test/bindPageRequestByJosn.do")
	@ResponseBody
	public QueryResult bindPageRequestByJosn(@RequestBody PageRequest pageRequest){
		QueryResult aa=new QueryResult(pageRequest);
		return aa;
	}
	@RequestMapping("/test/bindPageRequestByConverter.do")
	@ResponseBody
	public QueryResult bindPageRequestByConverter(@RequestParam("pageRequest")PageRequest pageRequest){
		QueryResult aa=new QueryResult(pageRequest);
		return aa;
	}
	//http://www.iteye.com/topic/1122793?page=3#2385378
	@RequestMapping("/test/bindPageRequestNormal.do")
	@ResponseBody
	public QueryResult bindPageRequestNormal(PageRequest pageRequest){
		//pageRequest.setWheres(wheres);
		QueryResult aa=new QueryResult(pageRequest);
//		http://blog.csdn.net/idilent/article/details/1845227
//			http://blog.csdn.net/idilent/article/details/1800262
//		添加sortInfo的测试
		for(WhereInfo where:aa.getWheres()){
			where.getProperty();
		}
		return aa;
	}
	
	@RequestMapping("/test/testException.do")
	public void testException() throws Exception{
		throw new Exception("显示信息错误");
	}
	@RequestMapping("/test/testBussinessException.do")
	public void testBussinessException() throws Exception{
		throw new BussinessException(DefaulExceptionCode.SYSTEM_EXCEPTION);
	}
	@RequestMapping("/test/testConstraintViolationException.do")
	public void testConstraintViolationException() throws Exception{
		EntityTest entity1=new EntityTest();
		entity1.setFirstName("ma");
		entity1.setLastName("wujun");
		entity1.setEmail("11");
		ValidatorUtils.validate(entity1);
	}
}
