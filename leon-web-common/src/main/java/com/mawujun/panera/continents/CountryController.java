package com.mawujun.panera.continents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;
import com.mawujun.panera.continents.Country;
import com.mawujun.panera.continents.CountryService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/country")
public class CountryController {

	@Resource
	private CountryService countryService;

//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/country/query")
//	@ResponseBody
//	public List<Country> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Country.parent.id, "root".equals(id)?null:id);
//		List<Country> countryes=countryService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Country.class,M.Country.parent.name());
//		return countryes;
//	}
//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/country/query")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Country.sampleName, "%"+sampleName+"%");
//		return countryService.queryPage(page);
//	}

	@RequestMapping("/country/queryContinent")
	@ResponseBody
	public List<Map<String,String>> queryContinent() {	
		List<Map<String,String>> continents=new ArrayList<Map<String,String>>();
		for(Continent continent:Continent.values()){
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", continent.name());
			map.put("name", continent.getText());
			continents.add(map);
		}
		return continents;
	}
	
	@RequestMapping("/country/query")
	@ResponseBody
	public List<Country> query(String continent) {	
		//在客户的表单的时候用的
		if("none".equals(continent)){
			return null;
		}
		List<Country> countryes=new ArrayList<Country>();
		if(continent==null || "".equalsIgnoreCase(continent)){
			countryes=countryService.queryAll();

		} else {
			countryes=countryService.query(Cnd.where().andEquals("continent", Continent.valueOf(continent)));

		}
		//for(Country country:countryes){
		//	country.setContinent_name(country.getContinent().getText());
		//}
		return countryes;
	}
	

	@RequestMapping("/country/load")
	public Country load(String id) {
		return countryService.get(id);
	}
	
	@RequestMapping("/country/create")
	@ResponseBody
	public Country create(@RequestBody Country country) {
		countryService.create(country);
		return country;
	}
	
	@RequestMapping("/country/update")
	@ResponseBody
	public  Country update(@RequestBody Country country) {
		countryService.update(country);
		return country;
	}
	
	@RequestMapping("/country/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		countryService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/country/destroy")
	@ResponseBody
	public Country destroy(@RequestBody Country country) {
		countryService.delete(country);
		return country;
	}
	
	
}
