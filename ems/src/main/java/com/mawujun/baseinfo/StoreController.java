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

import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/store")
public class StoreController {

	@Resource
	private StoreService storeService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/store/query.do")
//	@ResponseBody
//	public List<Store> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Store.parent.id, "root".equals(id)?null:id);
//		List<Store> storees=storeService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Store.class,M.Store.parent.name());
//		return storees;
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
//	@RequestMapping("/store/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Store.sampleName, "%"+sampleName+"%");
//		return storeService.queryPage(page);
//	}

	@RequestMapping("/store/query.do")
	@ResponseBody
	public List<Store> query() {	
		List<Store> storees=storeService.queryAll();
		return storees;
	}
	

	@RequestMapping("/store/load.do")
	public Store load(String id) {
		return storeService.get(id);
	}
	
	@RequestMapping("/store/create.do")
	@ResponseBody
	public Store create(@RequestBody Store store) {
		storeService.create(store);
		return store;
	}
	
	@RequestMapping("/store/update.do")
	@ResponseBody
	public  Store update(@RequestBody Store store) {
		storeService.update(store);
		return store;
	}
	
	@RequestMapping("/store/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		storeService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/store/destroy.do")
	@ResponseBody
	public Store destroy(@RequestBody Store store) {
		storeService.delete(store);
		return store;
	}
	
	
}
