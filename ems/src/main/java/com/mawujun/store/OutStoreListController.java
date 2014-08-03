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

import com.mawujun.store.OutStoreList;
import com.mawujun.store.OutStoreListService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/outStoreList")
public class OutStoreListController {

	@Resource
	private OutStoreListService outStoreListService;

//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/outStoreList/query.do")
//	@ResponseBody
//	public List<OutStoreList> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.OutStoreList.parent.id, "root".equals(id)?null:id);
//		List<OutStoreList> outStoreListes=outStoreListService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(OutStoreList.class,M.OutStoreList.parent.name());
//		return outStoreListes;
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
//	@RequestMapping("/outStoreList/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.OutStoreList.sampleName, "%"+sampleName+"%");
//		return outStoreListService.queryPage(page);
//	}

	@RequestMapping("/outStoreList/query.do")
	@ResponseBody
	public List<OutStoreList> query() {	
		List<OutStoreList> outStoreListes=outStoreListService.queryAll();
		return outStoreListes;
	}
	

	@RequestMapping("/outStoreList/load.do")
	public OutStoreList load(String id) {
		return outStoreListService.get(id);
	}
	
	@RequestMapping("/outStoreList/create.do")
	@ResponseBody
	public OutStoreList create(@RequestBody OutStoreList outStoreList) {
		outStoreListService.create(outStoreList);
		return outStoreList;
	}
	
	@RequestMapping("/outStoreList/update.do")
	@ResponseBody
	public  OutStoreList update(@RequestBody OutStoreList outStoreList) {
		outStoreListService.update(outStoreList);
		return outStoreList;
	}
	
	@RequestMapping("/outStoreList/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		outStoreListService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/outStoreList/destroy.do")
	@ResponseBody
	public OutStoreList destroy(@RequestBody OutStoreList outStoreList) {
		outStoreListService.delete(outStoreList);
		return outStoreList;
	}
	
	
}
