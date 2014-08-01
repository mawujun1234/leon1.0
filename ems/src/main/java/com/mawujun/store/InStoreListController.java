package com.mawujun.store;
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

import com.mawujun.store.InStoreList;
import com.mawujun.store.InStoreListService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/inStoreList")
public class InStoreListController {

	@Resource
	private InStoreListService inStoreListService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/inStoreList/query.do")
//	@ResponseBody
//	public List<InStoreList> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.InStoreList.parent.id, "root".equals(id)?null:id);
//		List<InStoreList> inStoreListes=inStoreListService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(InStoreList.class,M.InStoreList.parent.name());
//		return inStoreListes;
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
//	@RequestMapping("/inStoreList/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.InStoreList.sampleName, "%"+sampleName+"%");
//		return inStoreListService.queryPage(page);
//	}

	@RequestMapping("/inStoreList/query.do")
	@ResponseBody
	public List<InStoreList> query() {	
		List<InStoreList> inStoreListes=inStoreListService.queryAll();
		return inStoreListes;
	}
	

	@RequestMapping("/inStoreList/load.do")
	public InStoreList load(String id) {
		return inStoreListService.get(id);
	}
	
	@RequestMapping("/inStoreList/create.do")
	@ResponseBody
	public InStoreList create(@RequestBody InStoreList inStoreList) {
		inStoreListService.create(inStoreList);
		return inStoreList;
	}
	
	@RequestMapping("/inStoreList/update.do")
	@ResponseBody
	public  InStoreList update(@RequestBody InStoreList inStoreList) {
		inStoreListService.update(inStoreList);
		return inStoreList;
	}
	
	@RequestMapping("/inStoreList/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		inStoreListService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/inStoreList/destroy.do")
	@ResponseBody
	public InStoreList destroy(@RequestBody InStoreList inStoreList) {
		inStoreListService.delete(inStoreList);
		return inStoreList;
	}
	
	
}
