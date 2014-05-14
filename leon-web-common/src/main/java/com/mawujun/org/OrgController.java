package com.mawujun.org;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.DefaultValues;
import com.mawujun.utils.M;
import com.mawujun.org.Org;
import com.mawujun.org.OrgService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/org")
public class OrgController {

	@Resource
	private OrgService orgService;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/org/query")
	@ResponseBody
	public List<Org> query(String id,String orgDimenssionId) {
		//先按维度动态生成tabpanel，这里参数也添加
		if(!StringUtils.hasText(orgDimenssionId)){
			orgDimenssionId=DefaultValues.OrgDimenssion_id;
		}
		,
		Cnd cnd=Cnd.select().andEquals(M.Org.id, "root".equals(id)?null:id);
		List<Org> orges=orgService.query(cnd);
		//JsonConfigHolder.setFilterPropertys(Org.class,M.Org.parent.name());
		return orges;
	}

//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/org/queryPage")
//	@ResponseBody
//	public Page queryPage(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Org.sampleName, "%"+sampleName+"%");
//		return orgService.queryPage(page);
//	}
//
//	@RequestMapping("/org/query")
//	@ResponseBody
//	public List<Org> query() {	
//		List<Org> orges=orgService.queryAll();
//		return orges;
//	}
	

	@RequestMapping("/org/load")
	public Org load(String id) {
		return orgService.get(id);
	}
	
	@RequestMapping("/org/create")
	@ResponseBody
	public Org create(@RequestBody Org org) {
		orgService.create(org);
		return org;
	}
	
	@RequestMapping("/org/update")
	@ResponseBody
	public  Org update(@RequestBody Org org) {
		orgService.update(org);
		return org;
	}
	
	@RequestMapping("/org/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		orgService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/org/destroy")
	@ResponseBody
	public Org destroy(@RequestBody Org org) {
		orgService.delete(org);
		return org;
	}
	
	
}
