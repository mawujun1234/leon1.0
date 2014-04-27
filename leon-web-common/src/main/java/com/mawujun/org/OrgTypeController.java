package com.mawujun.org;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;


import com.mawujun.org.OrgType;
import com.mawujun.org.OrgTypeService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
public class OrgTypeController {

	@Resource
	private OrgTypeService orgTypeService;



	@RequestMapping("/orgType/query")
	@ResponseBody
	public List<OrgType> query() {	
		List<OrgType> orgTypees=orgTypeService.queryAll();
		return orgTypees;
	}
	

	@RequestMapping("/orgType/load")
	public OrgType load(String id) {
		return orgTypeService.get(id);
	}
	
	@RequestMapping("/orgType/create")
	@ResponseBody
	public OrgType create(@RequestBody OrgType orgType) {
		orgTypeService.create(orgType);
		return orgType;
	}
	
	@RequestMapping("/orgType/update")
	@ResponseBody
	public  OrgType update(@RequestBody OrgType orgType) {
		orgTypeService.createOrUpdate(orgType);
		return orgType;
	}
	
	@RequestMapping("/orgType/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		orgTypeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/orgType/destroy")
	@ResponseBody
	public OrgType destroy(@RequestBody OrgType orgType) {
		orgTypeService.delete(orgType);
		return orgType;
	}
	
	
}
