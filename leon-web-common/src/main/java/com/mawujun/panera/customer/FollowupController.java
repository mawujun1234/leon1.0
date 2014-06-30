package com.mawujun.panera.customer;
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

import com.mawujun.panera.customer.Followup;
import com.mawujun.panera.customer.FollowupService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/followup")
public class FollowupController {

	@Resource
	private FollowupService followupService;




//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/followup/query")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Followup.sampleName, "%"+sampleName+"%");
//		return followupService.queryPage(page);
//	}

	@RequestMapping("/followup/query")
	@ResponseBody
	public List<Followup> query(String customer_id) {	
		List<Followup> followupes=followupService.query(Cnd.select().andEquals("customer_id", customer_id));
		return followupes;
	}
	
	/**
	 * 查找到期的任务
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param customer_id
	 * @return
	 */
	@RequestMapping("/followup/remind")
	@ResponseBody
	public List<Followup> remind() {	
		List<Followup> followupes=followupService.remind();
		return followupes;
	}
	

	@RequestMapping("/followup/load")
	public Followup load(String id) {
		return followupService.get(id);
	}
	
	@RequestMapping("/followup/create")
	@ResponseBody
	public Followup create(Followup followup) {
		followupService.create(followup);
		return followup;
	}
	
	@RequestMapping("/followup/update")
	@ResponseBody
	public  Followup update(Followup followup) {
		followupService.update(followup);
		return followup;
	}
	
	@RequestMapping("/followup/deleteById")
	@ResponseBody
	public String deleteById(String id) {
		followupService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/followup/destroy")
	@ResponseBody
	public Followup destroy(@RequestBody Followup followup) {
		followupService.delete(followup);
		return followup;
	}
	
	
}
