package com.mawujun.org;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.fun.BussinessType;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.DefaultValues;
import com.mawujun.utils.M;
import com.mawujun.org.OrgDimenssion;
import com.mawujun.org.OrgDimenssionService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/orgDimenssion")
public class OrgDimenssionController {

	@Resource
	private OrgDimenssionService orgDimenssionService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/orgDimenssion/queryChildren")
//	@ResponseBody
//	public List<OrgDimenssion> queryChildren(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.OrgDimenssion.parent.id, "root".equals(id)?null:id);
//		List<OrgDimenssion> orgDimenssiones=orgDimenssionService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(OrgDimenssion.class,M.OrgDimenssion.parent.name());
//		return orgDimenssiones;
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
//	@RequestMapping("/orgDimenssion/queryPage")
//	@ResponseBody
//	public Page queryPage(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.OrgDimenssion.sampleName, "%"+sampleName+"%");
//		return orgDimenssionService.queryPage(page);
//	}

	@RequestMapping("/orgDimenssion/query")
	@ResponseBody
	public List<OrgDimenssion> query() {	
		List<OrgDimenssion> orgDimenssiones=orgDimenssionService.queryAll();
		return orgDimenssiones;
	}
	

	@RequestMapping("/orgDimenssion/load")
	public OrgDimenssion load(String id) {
		return orgDimenssionService.get(id);
	}
	
	@RequestMapping("/orgDimenssion/create")
	@ResponseBody
	public OrgDimenssion create(@RequestBody OrgDimenssion orgDimenssion) {
		orgDimenssionService.create(orgDimenssion);
		return orgDimenssion;
	}
	
	@RequestMapping("/orgDimenssion/update")
	@ResponseBody
	public  OrgDimenssion update(@RequestBody OrgDimenssion orgDimenssion) {
		orgDimenssionService.createOrUpdate(orgDimenssion);
		return orgDimenssion;
	}
	
	@RequestMapping("/orgDimenssion/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		if(DefaultValues.OrgDimenssion_id.equals(id)){
			throw new BusinessException("默认维度不能删除!");
		}
		orgDimenssionService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/orgDimenssion/destroy")
	@ResponseBody
	public OrgDimenssion destroy(@RequestBody OrgDimenssion orgDimenssion) {
		if(DefaultValues.OrgDimenssion_id.equals(orgDimenssion.getId())){
			throw new BusinessException("默认维度不能删除!");
		}
		orgDimenssionService.delete(orgDimenssion);
		return orgDimenssion;
	}
	
	
}
