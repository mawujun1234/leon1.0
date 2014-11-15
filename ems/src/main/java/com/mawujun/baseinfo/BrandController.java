package com.mawujun.baseinfo;
import java.util.List;

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
import com.mawujun.baseinfo.Brand;
import com.mawujun.baseinfo.BrandService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/brand")
public class BrandController {

	@Resource
	private BrandService brandService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/brand/query.do")
//	@ResponseBody
//	public List<Brand> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Brand.parent.id, "root".equals(id)?null:id);
//		List<Brand> brandes=brandService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Brand.class,M.Brand.parent.name());
//		return brandes;
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
//	@RequestMapping("/brand/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Brand.sampleName, "%"+sampleName+"%");
//		return brandService.queryPage(page);
//	}

	@RequestMapping("/brand/query.do")
	@ResponseBody
	public List<Brand> query() {	
		List<Brand> brandes=brandService.queryAll();
		return brandes;
	}
	

	@RequestMapping("/brand/load.do")
	public Brand load(String id) {
		return brandService.get(id);
	}
	
//	@RequestMapping("/brand/create.do")
//	@ResponseBody
//	public Brand create(@RequestBody Brand brand) {
//		brandService.create(brand);
//		return brand;
//	}
	
	@RequestMapping("/brand/update.do")
	@ResponseBody
	public  Brand update(@RequestBody Brand brand) {
		brandService.createOrUpdate(brand);
		return brand;
	}
//	
//	@RequestMapping("/brand/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		brandService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/brand/destroy.do")
	@ResponseBody
	public Brand destroy(@RequestBody Brand brand) {
		//brandService.delete(brand);
		brand.setStatus(false);
		brandService.update(brand);
		return brand;
	}
	
	/**
	 * 用于combobox
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param name
	 * @return
	 */
	@RequestMapping("/brand/queryBrandCombo.do")
	@ResponseBody
	public List<Brand> queryBrandCombo(String name,Boolean containAll) {
		List<Brand> brands=brandService.query(Cnd.select().andEquals(M.Brand.status, true).andLike(M.Brand.name, name));	
		if(containAll!=null && containAll){
			Brand all=new Brand();
			all.setId("");
			all.setName("所有");
			brands.add(0, all);
		}
		return brands;
	}
	
	
}
